package aeminium.compiler.tests;
import java.util.Random;
import aeminium.runtime.AeminiumHelper;
import java.util.ArrayList;
class FFT_FFT_2_if_2_block_10_ret implements aeminium.runtime.Body {
  FFT_FFT_2_if_2_block_10_ret(  FFT_FFT_2_if_2_block ae_parent){
    this.ae_parent=ae_parent;
    this.ae_task=AeminiumHelper.createNonBlockingTask(this,AeminiumHelper.NO_HINTS);
    AeminiumHelper.schedule(this.ae_task,ae_parent == null ? AeminiumHelper.NO_PARENT : ae_parent.ae_task,java.util.Arrays.asList(this.ae_parent.ae_FFT_FFT_2_if_2_block_9_while.ae_task));
  }
  public void execute(  aeminium.runtime.Runtime rt,  aeminium.runtime.Task task) throws Exception {
    this.ae_parent.ae_parent.ae_parent.ae_ret=this.ae_parent.ae_FFT_FFT_2_if_2_block_7_varstmt.y;
    if (this.ae_parent.ae_parent.ae_parent.ae_parent != null)     this.ae_parent.ae_parent.ae_parent.ae_parent.ae_ret=this.ae_parent.ae_parent.ae_parent.ae_ret;
  }
  public aeminium.runtime.Task ae_task;
  public FFT_FFT_2_if_2_block ae_parent;
}
