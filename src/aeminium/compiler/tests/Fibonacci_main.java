package aeminium.compiler.tests;
import aeminium.runtime.AeminiumHelper;
import java.util.ArrayList;
class Fibonacci_main implements aeminium.runtime.Body {
  Fibonacci_main(  aeminium.runtime.CallerBody ae_parent,  String[] args){
    this.ae_parent=ae_parent;
    this.args=args;
    this.ae_task=AeminiumHelper.createNonBlockingTask(this,AeminiumHelper.NO_HINTS);
    this.ae_Fibonacci_main_1_invoke=new Fibonacci_main_1_invoke(this);
    AeminiumHelper.schedule(this.ae_task,ae_parent == null ? AeminiumHelper.NO_PARENT : ae_parent.ae_task,java.util.Arrays.asList(this.ae_Fibonacci_main_1_invoke.ae_task));
  }
  public void execute(  aeminium.runtime.Runtime rt,  aeminium.runtime.Task task) throws Exception {
  }
  public aeminium.runtime.CallerBody ae_parent;
  public String[] args;
  public aeminium.runtime.Task ae_task;
  public Fibonacci_main_1_invoke ae_Fibonacci_main_1_invoke;
}
