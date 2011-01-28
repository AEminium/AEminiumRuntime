package aeminium.runtime.tests;

import static org.junit.Assert.fail;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;

import aeminium.runtime.Body;
import aeminium.runtime.ErrorHandler;
import aeminium.runtime.Runtime;
import aeminium.runtime.Task;

public class DoubleScheduleTask extends BaseTest {

	@Test()
	public void scheduleTaskTwice() {
		final AtomicBoolean twice = new AtomicBoolean(false);
		Runtime rt = getRuntime();
		rt.init();
		
		rt.addErrorHandler(new ErrorHandler() {
			
			@Override
			public void handleTaskException(Task task, Throwable t) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void handleTaskDuplicatedSchedule(Task task) {
				twice.set(true);
				
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
				// TODO Auto-generated method stub
				
			}
		});
		
		Task t = rt.createNonBlockingTask(new Body() {
			@Override
			public void execute(Runtime rt, Task current) {	
			}
		}, Runtime.NO_HINTS);

		rt.schedule(t, Runtime.NO_PARENT, Runtime.NO_DEPS);
		rt.schedule(t, Runtime.NO_PARENT, Runtime.NO_DEPS);

		if ( !twice.get() ) {
			System.out.println("not twice");
			fail("Did not detect doubly scheduled task");
		}
		
		rt.shutdown();
	}
}
