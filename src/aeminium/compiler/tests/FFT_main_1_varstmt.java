package aeminium.compiler.tests;
import java.util.Random;
import aeminium.runtime.AeminiumHelper;
import java.util.ArrayList;
class FFT_main_1_varstmt implements aeminium.runtime.Body {
  FFT_main_1_varstmt(  FFT_main ae_parent){
    this.ae_parent=ae_parent;
    this.ae_task=AeminiumHelper.createNonBlockingTask(this,AeminiumHelper.NO_HINTS);
    this.ae_FFT_main_1_varstmt_1_invoke=new FFT_main_1_varstmt_1_invoke(this);
    AeminiumHelper.schedule(this.ae_task,ae_parent == null ? AeminiumHelper.NO_PARENT : ae_parent.ae_task,java.util.Arrays.asList(this.ae_FFT_main_1_varstmt_1_invoke.ae_task));
  }
  public void execute(  aeminium.runtime.Runtime rt,  aeminium.runtime.Task task) throws Exception {
    input=this.ae_FFT_main_1_varstmt_1_invoke.ae_ret;
  }
  public aeminium.runtime.Task ae_task;
  public FFT_main ae_parent;
  public FFT_main_1_varstmt_1_invoke ae_FFT_main_1_varstmt_1_invoke;
  public volatile Complex[] input;
}
