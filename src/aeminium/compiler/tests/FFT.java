package aeminium.compiler.tests;
import java.util.Random;
import aeminium.runtime.AeminiumHelper;
import java.util.ArrayList;
class FFT {
  public static Complex[] FFT(  Complex[] x){
    int N=(x).length;
    if (N == 1)     return new Complex[]{x[0]};
 else {
      Complex[] even=new Complex[N / 2];
      Complex[] odd=new Complex[N / 2];
      int k=0;
      while (k < N / 2) {
        even[k]=x[2 * k];
        odd[k]=x[2 * k + 1];
        ++k;
      }
      Complex[] q=FFT(even);
      Complex[] r=FFT(odd);
      Complex[] y=new Complex[N];
      k=0;
      while (k < N / 2) {
        double kth=-2 * k * Math.PI / N;
        Complex wk=new Complex(Math.cos(kth),Math.sin(kth));
        y[k]=q[k].plus(wk.times(r[k]));
        y[k + N / 2]=q[k].minus(wk.times(r[k]));
        ++k;
      }
      return y;
    }
  }
  public static Complex[] createRandomComplexArray(  int n,  long seed){
    Random r=new Random(seed);
    Complex[] x=new Complex[n];
    int i=0;
    while (i < n) {
      x[i]=new Complex(2 * r.nextDouble() - 1,0);
      ++i;
    }
    return x;
  }
  public static void main(  String[] args){
    AeminiumHelper.init();
    new FFT_main(null,args);
    AeminiumHelper.shutdown();
  }
}
