package aeminium.compiler.tests;
import java.util.Random;
import aeminium.runtime.AeminiumHelper;
import java.util.ArrayList;
class FFT_main_4_whileloop_2_block_1_exprstmt_1_invoke_1_arrayidx extends FFT_main_4_while_2_block_1_exprstmt_1_invoke_1_arrayidx implements aeminium.runtime.Body {
  FFT_main_4_whileloop_2_block_1_exprstmt_1_invoke_1_arrayidx(  FFT_main_4_whileloop_2_block_1_exprstmt_1_invoke ae_parent){
    this.ae_parent=ae_parent;
    this.ae_task=AeminiumHelper.createNonBlockingTask(this,AeminiumHelper.NO_HINTS);
    AeminiumHelper.schedule(this.ae_task,ae_parent == null ? AeminiumHelper.NO_PARENT : ae_parent.ae_task,java.util.Arrays.asList(this.ae_parent.ae_parent.ae_parent.ae_parent.ae_previous.ae_parent.ae_FFT_main_2_varstmt.ae_task,this.ae_parent.ae_parent.ae_parent.ae_parent.ae_previous.ae_FFT_main_4_while_1_field.ae_FFT_main_4_while_1_field_1_paren.ae_task,this.ae_parent.ae_parent.ae_parent.ae_parent.ae_previous.ae_FFT_main_4_while_2_block.ae_FFT_main_4_while_2_block_2_exprstmt.ae_task,this.ae_parent.ae_parent.ae_parent.ae_parent.ae_previous.ae_parent.ae_FFT_main_2_varstmt.ae_FFT_main_2_varstmt_1_invoke.ae_task,this.ae_parent.ae_parent.ae_parent.ae_parent.ae_FFT_main_4_while_1_field.ae_FFT_main_4_while_1_field_1_paren.ae_task));
  }
  public void execute(  aeminium.runtime.Runtime rt,  aeminium.runtime.Task task) throws Exception {
    this.ae_ret=this.ae_parent.ae_parent.ae_parent.ae_parent.ae_previous.ae_parent.ae_FFT_main_2_varstmt.output[this.ae_parent.ae_parent.ae_parent.ae_parent.ae_previous.ae_parent.ae_FFT_main_3_varstmt.i];
  }
}
