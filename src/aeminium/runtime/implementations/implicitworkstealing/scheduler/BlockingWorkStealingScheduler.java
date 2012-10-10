/**
 * Copyright (c) 2010-11 The AEminium Project (see AUTHORS file)
 * 
 * This file is part of Plaid Programming Language.
 *
 * Plaid Programming Language is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 *  Plaid Programming Language is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Plaid Programming Language.  If not, see <http://www.gnu.org/licenses/>.
 */

package aeminium.runtime.implementations.implicitworkstealing.scheduler;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

import aeminium.runtime.implementations.Configuration;
import aeminium.runtime.implementations.implicitworkstealing.ImplicitWorkStealingRuntime;
import aeminium.runtime.implementations.implicitworkstealing.events.EventManager;
import aeminium.runtime.implementations.implicitworkstealing.scheduler.stealing.WorkStealingAlgorithm;
import aeminium.runtime.implementations.implicitworkstealing.task.ImplicitBlockingTask;
import aeminium.runtime.implementations.implicitworkstealing.task.ImplicitTask;


/* This scheduler works as a manager of all the tasks.
 * The scheduler decides to which thread a new task is sent.
 */
public final class BlockingWorkStealingScheduler {
	protected final ImplicitWorkStealingRuntime rt;
	protected ConcurrentLinkedQueue<WorkStealingThread> parkedThreads;
	protected WorkStealingThread[] threads;
	protected EventManager eventManager = null;
	protected AtomicInteger counter;
	protected Queue<ImplicitTask> submissionQueue;
	protected final int maxParallelism;
	protected WorkStealingAlgorithm wsa;
	protected BlockingThreadPool blockingThreadPool;
	protected static final boolean oneTaskPerLevel       = Configuration.getProperty(BlockingWorkStealingScheduler.class, "oneTaskPerLevel", true);
	protected static final boolean useBlockingThreadPool = Configuration.getProperty(BlockingWorkStealingScheduler.class, "useBlockingThreadPool", false);
	protected static final int maxQueueLength            = Configuration.getProperty(BlockingWorkStealingScheduler.class, "maxQueueLength", 0);
	protected static final int unparkInterval            = Configuration.getProperty(BlockingWorkStealingScheduler.class, "unparkInterval", 0);
	
	public BlockingWorkStealingScheduler(ImplicitWorkStealingRuntime rt) {
		this.rt        = rt;
		maxParallelism = Configuration.getProcessorCount();
	}

	public BlockingWorkStealingScheduler(ImplicitWorkStealingRuntime rt, int maxParallelism) {
		this.rt             = rt;	
		this.maxParallelism = maxParallelism;
	}
	
	/*
	 * Initializes the scheduler, creating threads, queues
	 * and loads the WorkStealing algorith. 
	 */
	public void init(EventManager eventManager) {
		this.eventManager    = eventManager;
		this.parkedThreads   = new ConcurrentLinkedQueue<WorkStealingThread>();
		this.threads         = new WorkStealingThread[maxParallelism];
		this.counter         = new AtomicInteger(threads.length);
		this.submissionQueue = new ConcurrentLinkedQueue<ImplicitTask>();
		this.wsa             = loadWorkStealingAlgorithm(Configuration.getProperty(BlockingWorkStealingScheduler.class, "workStealingAlgorithm", "SequentialReverseScan"));
		if ( useBlockingThreadPool ) {
			blockingThreadPool = new BlockingThreadPool();
			blockingThreadPool.init(rt, eventManager);
		}
		
		// initialize data structures
		for ( int i = 0; i < threads.length; i++ ) {
			threads[i] = new WorkStealingThread(rt, i);
		}

		// setup WorkStealingAlgorithm
		wsa.init(threads, submissionQueue);

		// start and register threads threads
		for ( WorkStealingThread thread : threads ) {
			thread.start();
		}
	}

	/* Shutdowns all threads and releases all states. */
	public void shutdown() {
		counter.set(threads.length);
		while ( counter.get() > 0 ) {
			for ( WorkStealingThread thread : threads ){
				thread.shutdown();
				LockSupport.unpark(thread);
			}
		}

		// cleanup
		wsa.shutdown();
		wsa             = null;
		threads         = null;
		parkedThreads   = null;
		counter         = null;
		submissionQueue = null;
		if ( useBlockingThreadPool ) {
			blockingThreadPool.shutdown();
		}
	}

	
	protected WorkStealingAlgorithm loadWorkStealingAlgorithm(String name) {
		WorkStealingAlgorithm wsa = null;
		
		Class<?> wsaClass = null;
		try {
			wsaClass = getClass().getClassLoader().loadClass("aeminium.runtime.implementations.implicitworkstealing.scheduler.stealing."+name);
		} catch (ClassNotFoundException e) {
			rt.getErrorManager().signalInternalError(new Error("Cannot load work stealing algorithm class : aeminium.runtime.implementations.implicitworkstealing.scheduler.stealing." + name));
		}
		
		try {
			wsa = (WorkStealingAlgorithm)wsaClass.newInstance();
		} catch (Exception e) {
			rt.getErrorManager().signalInternalError(new Error("Cannot load work stealing algorithm class : aeminium.runtime.implementations.implicitworkstealing.scheduler.stealing." + name));
			throw new Error("Cannot load work stealing algorithm class : aeminium.runtime.implementations.implicitworkstealing.scheduler.stealing." + name);
		}
		
		return wsa;
	}
	
	public final void registerThread(WorkStealingThread thread) {
		eventManager.signalNewThread(thread);
	}

	public final void unregisterThread(WorkStealingThread thread) {
		counter.decrementAndGet();
	}

	/* Receives a new task and forwards it to one of the executor threads. */
	public final void scheduleTask(ImplicitTask task) {
		if ( task instanceof ImplicitBlockingTask && useBlockingThreadPool ) {
			blockingThreadPool.submitTask((ImplicitBlockingTask) task);
			return;
		}
		if (maxQueueLength > 0) {
			Thread thread = Thread.currentThread();
			if ( thread instanceof WorkStealingThread) {
				WorkStealingThread wthread = (WorkStealingThread)thread;
				WorkStealingQueue<ImplicitTask> taskQueue = wthread.getTaskQueue();
				if ( taskQueue.size() < maxQueueLength || wthread.remainingRecursionDepth == 0 ) {
					taskQueue.push(task);
					if ( taskQueue.size() <= 1 ) {
						signalWork();
					}
				} else {
					wthread.remainingRecursionDepth--;
					task.invoke(rt);
					wthread.remainingRecursionDepth++;
				}
			} else {
				submissionQueue.add(task);
				signalWork();
			}
		} else {
			Thread thread = Thread.currentThread();
			if ( thread instanceof WorkStealingThread ) {
				// worker thread 
				WorkStealingThread wthread = (WorkStealingThread)thread;
				if ( oneTaskPerLevel ) {
					WorkStealingQueue<ImplicitTask> taskQueue = wthread.getTaskQueue();
					ImplicitTask head = taskQueue.peek();
					if ( head != null && head.level == task.level && wthread.remainingRecursionDepth > 0 ) {
						wthread.remainingRecursionDepth--;
						task.invoke(rt);
						wthread.remainingRecursionDepth++;
					} else {
						taskQueue.push(task);
						if ( taskQueue.size() <= 1 ) {
							signalWork(wthread);
						}
					}
				} else {
					wthread.getTaskQueue().push(task);
					if ( wthread.getTaskQueue().size() <= 1 ) {
						signalWork(wthread);
					}
				}
			} else {
				// external thread
				submissionQueue.add(task);
				signalWork();
			}
		}
	}

	/* Awakes a specific thread. */
	public final void signalWork(WorkStealingThread thread) {
		// TODO: need to fix that to wake up thread waiting for objects to complete
		LockSupport.unpark(thread);
		WorkStealingThread next = wsa.signalWorkInLocalQueue(thread);
		LockSupport.unpark(next);
	}
	
	/* Awakes a thread to perform some work. */
	public final void signalWork() {
		WorkStealingThread threadParked = wsa.signalWorkInSubmissionQueue();
		if ( threadParked != null ) {
			LockSupport.unpark(threadParked);
		}
	}
	
	/* Parks a thread to wait an interval before looking for new work. */
	public final void parkThread(WorkStealingThread thread) {
		eventManager.signalThreadSuspend(thread);
		wsa.threadGoingToPark(thread);
		if (unparkInterval > 0) {
			LockSupport.parkNanos(thread, unparkInterval);
		} else {
			LockSupport.park(thread);
		}
	}
	
	/* Steals work from queues using an algorithm. */
	public final ImplicitTask scanQueues(WorkStealingThread thread) {
		return wsa.stealWork(thread);
	}

	/* Removes a task from que submission queue. */
	public boolean cancelTask(ImplicitTask task) {
		boolean result = submissionQueue.remove(task);
		if ( result ) {
			task.taskFinished(rt);
		}
		return result;
	}
}
