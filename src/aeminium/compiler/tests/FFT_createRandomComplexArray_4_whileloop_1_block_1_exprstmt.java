package aeminium.compiler.tests;
import java.util.Random;
import aeminium.runtime.AeminiumHelper;
import java.util.ArrayList;
class FFT_createRandomComplexArray_4_whileloop_1_block_1_exprstmt extends FFT_createRandomComplexArray_4_while_1_block_1_exprstmt implements aeminium.runtime.Body {
  FFT_createRandomComplexArray_4_whileloop_1_block_1_exprstmt(  FFT_createRandomComplexArray_4_whileloop_1_block ae_parent){
    this.ae_parent=ae_parent;
    this.ae_task=AeminiumHelper.createNonBlockingTask(this,AeminiumHelper.NO_HINTS);
    this.ae_FFT_createRandomComplexArray_4_while_1_block_1_exprstmt_1_new=new FFT_createRandomComplexArray_4_whileloop_1_block_1_exprstmt_1_new(this);
    AeminiumHelper.schedule(this.ae_task,ae_parent == null ? AeminiumHelper.NO_PARENT : ae_parent.ae_task,java.util.Arrays.asList(this.ae_FFT_createRandomComplexArray_4_while_1_block_1_exprstmt_1_new.ae_task,this.ae_parent.ae_parent.ae_previous.ae_FFT_createRandomComplexArray_4_while_1_block.ae_FFT_createRandomComplexArray_4_while_1_block_1_exprstmt.ae_task,this.ae_parent.ae_parent.ae_previous.ae_FFT_createRandomComplexArray_4_while_1_block.ae_FFT_createRandomComplexArray_4_while_1_block_2_exprstmt.ae_task));
  }
  public void execute(  aeminium.runtime.Runtime rt,  aeminium.runtime.Task task) throws Exception {
    this.ae_parent.ae_parent.ae_previous.ae_parent.ae_FFT_createRandomComplexArray_2_varstmt.x[this.ae_parent.ae_parent.ae_previous.ae_parent.ae_FFT_createRandomComplexArray_3_varstmt.i]=this.ae_FFT_createRandomComplexArray_4_while_1_block_1_exprstmt_1_new.ae_ret;
  }
}
