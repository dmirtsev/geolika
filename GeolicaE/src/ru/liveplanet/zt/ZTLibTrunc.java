package ru.liveplanet.zt;

/**
 * Created by IntelliJ IDEA.
 * User: damir
 * Date: 08.08.2006
 * Time: 12:42:41
 * To change this template use File | Settings | File Templates.
 */
//import swisseph.*;
/**
 * Created by IntelliJ IDEA.
 * User: damir
 * Date: 24.04.2006
 * Time: 10:41:26
 * To change this template use File | Settings | File Templates.
 */
public class ZTLibTrunc {

    static final double DEGTORAD=0.0174532925199433;
    static final double RADTODEG=57.2957795130823;

public static double degnorm(double x)
{
    double y;
    y = x%360.0;
    if (Math.abs(y) < 1e-13) {
      y = 0;
    }
    if( y < 0.0 ) {
      y += 360.0;
    }
    return(y);
  }





    

public static void main (String[] argStr)
{

        double[] plBreak=new double[]{1,2,3,4,55,66,77,78,91,92};

   

}

}
