package aeminium.compiler.tests;
import aeminium.runtime.AeminiumHelper;
import java.util.ArrayList;
class Fibonacci_f_2_ret_1_infix_1_invoke extends aeminium.runtime.CallerBodyWithReturn<Long> implements aeminium.runtime.Body {
  Fibonacci_f_2_ret_1_infix_1_invoke(  Fibonacci_f_2_ret_1_infix ae_parent){
    this.ae_parent=ae_parent;
    this.ae_task=AeminiumHelper.createNonBlockingTask(this,AeminiumHelper.NO_HINTS);
    AeminiumHelper.schedule(this.ae_task,ae_parent == null ? AeminiumHelper.NO_PARENT : ae_parent.ae_task,java.util.Arrays.asList(this.ae_parent.ae_parent.ae_parent.ae_Fibonacci_f_1_if.ae_task));
  }
  public void execute(  aeminium.runtime.Runtime rt,  aeminium.runtime.Task task) throws Exception {
    if (!this.ae_parent.ae_parent.ae_parent.ae_finished) {
      if (rt.parallelize())       new Fibonacci_f(this,this.ae_parent.ae_parent.ae_parent.n - 1);
 else       this.ae_ret=Fibonacci.f(this.ae_parent.ae_parent.ae_parent.n - 1);
    }
  }
  public Fibonacci_f_2_ret_1_infix ae_parent;
}
