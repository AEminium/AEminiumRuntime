package aeminium.compiler.tests;
import java.util.Random;
import aeminium.runtime.AeminiumHelper;
import java.util.ArrayList;
class FFT_FFT_2_if_2_block_9_whileloop_2_block_2_varstmt_1_new_1_invoke extends FFT_FFT_2_if_2_block_9_while_2_block_2_varstmt_1_new_1_invoke implements aeminium.runtime.Body {
  FFT_FFT_2_if_2_block_9_whileloop_2_block_2_varstmt_1_new_1_invoke(  FFT_FFT_2_if_2_block_9_whileloop_2_block_2_varstmt_1_new ae_parent){
    this.ae_parent=ae_parent;
    this.ae_task=AeminiumHelper.createNonBlockingTask(this,AeminiumHelper.NO_HINTS);
    AeminiumHelper.schedule(this.ae_task,ae_parent == null ? AeminiumHelper.NO_PARENT : ae_parent.ae_task,java.util.Arrays.asList(this.ae_parent.ae_parent.ae_parent.ae_FFT_FFT_2_if_2_block_9_while_2_block_1_varstmt.ae_task));
  }
  public void execute(  aeminium.runtime.Runtime rt,  aeminium.runtime.Task task) throws Exception {
    this.ae_ret=Math.cos(this.ae_parent.ae_parent.ae_parent.ae_FFT_FFT_2_if_2_block_9_while_2_block_1_varstmt.kth);
  }
}
