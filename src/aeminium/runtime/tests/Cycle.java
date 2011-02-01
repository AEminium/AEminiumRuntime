package aeminium.runtime.tests;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import junit.framework.Assert;

import org.junit.Test;

import aeminium.runtime.Body;
import aeminium.runtime.ErrorHandler;
import aeminium.runtime.Runtime;
import aeminium.runtime.Task;

public class Cycle extends BaseTest {

	@Test(timeout=2000)
	public void testRoundDeadlock() {
		final AtomicBoolean cycle = new AtomicBoolean();
		Runtime rt = getRuntime();
		rt.init();
		
		rt.addErrorHandler(new ErrorHandler() {
			
			@Override
			public void handleTaskException(Task task, Throwable t) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void handleTaskDuplicatedSchedule(Task task) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void handleLockingDeadlock() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void handleInternalError(Error err) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void handleDependencyCycle(Task task) {
				cycle.set(true);
				
			}
		});

		Task t1 = rt.createNonBlockingTask(createBody(1), Runtime.NO_HINTS);
		Task t2 = rt.createNonBlockingTask(createBody(2), Runtime.NO_HINTS);
		Task t3 = rt.createNonBlockingTask(createBody(3), Runtime.NO_HINTS);
		Task t4 = rt.createNonBlockingTask(createBody(4), Runtime.NO_HINTS);

		rt.schedule(t1, Runtime.NO_PARENT, Arrays.asList(t4));
		rt.schedule(t2, Runtime.NO_PARENT, Arrays.asList(t1));
		rt.schedule(t3, Runtime.NO_PARENT, Arrays.asList(t2));
		rt.schedule(t4, Runtime.NO_PARENT, Arrays.asList(t3));

		if ( !cycle.get() ) {
			Assert.fail("Did not detect self cylcle.");
			rt.shutdown();
		}
	}

	public Body createBody(final int i) {
		return new Body() {
			public void execute(Runtime rt, Task parent) {
				System.out.println("Task " + i);
			}
			
			public String toString() {
				return "" + i;
			}
		};
	}

}