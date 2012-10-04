package aeminium.compiler.tests;
import aeminium.runtime.AeminiumHelper;
import java.util.ArrayList;
class Integrate_computeRec_4_varstmt implements aeminium.runtime.Body {
  Integrate_computeRec_4_varstmt(  Integrate_computeRec ae_parent){
    this.ae_parent=ae_parent;
    this.ae_task=AeminiumHelper.createNonBlockingTask(this,AeminiumHelper.NO_HINTS);
    AeminiumHelper.schedule(this.ae_task,ae_parent == null ? AeminiumHelper.NO_PARENT : ae_parent.ae_task,java.util.Arrays.asList(this.ae_parent.ae_Integrate_computeRec_1_varstmt.ae_task));
  }
  public void execute(  aeminium.runtime.Runtime rt,  aeminium.runtime.Task task) throws Exception {
    hh=this.ae_parent.ae_Integrate_computeRec_1_varstmt.h * 0.5;
  }
  public aeminium.runtime.Task ae_task;
  public Integrate_computeRec ae_parent;
  public volatile double hh;
}