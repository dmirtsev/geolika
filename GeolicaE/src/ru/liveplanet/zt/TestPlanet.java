package ru.liveplanet.zt;

import swisseph.SweConst;
import swisseph.SwissEph;

/**
 * Created by IntelliJ IDEA.
 * User: DBashkirtsev
 * Date: 20.10.2009
 * Time: 9:01:29
 * To change this template use File | Settings | File Templates.
 */
public class TestPlanet {
      public SwissEph sw;
      final double AU2km=SweConst.AUNIT/1000;

    void tp()
    {
        SwissEph sw=new SwissEph();
        double [] pl =new double[23];
          double[] xx=new double[6];
        double[] xx1=new double[6];
        double[] xx2=new double[6];
        StringBuffer sbErr=new StringBuffer();
        //sbStar=i+"";

 for (int i=0;i<=58;i++)
 {
     StringBuffer sbStar=new StringBuffer(i+"");

        int rc=sw.swe_calc_ut(2958470.5,
                              i,
                              SweConst.SEFLG_SPEED,    //SEFLG_EQUATORIAL
                              xx,
                              sbErr);
       int rc1=sw.swe_calc_ut(2958470.5,
                              i,
                              SweConst.SEFLG_EQUATORIAL,    //
                              xx1,
                              sbErr);


        if ((rc==SweConst.ERR)|| (rc1==SweConst.ERR)) {
          System.exit(1);
            System.out.println("__________________________________________________________");
        }

        System.out.println(
            sw.swe_get_planet_name(i)+": "+i+
            "\tLongitude:          "+xx[0]+
           "\n\tLatitude:           "+xx[1]+
            "\n\tDistance:           "+xx[2]+" AU"+
            "\n\t                   ("+(xx[2]*AU2km)+" km)"+
            "\n\tLongitudinal speed: "+xx[3]+" degs/day");
     System.out.println(
            sw.swe_get_planet_name(i)+": "+i+
            "\t@Longitude:          "+xx1[0]+
           "\n\t@Latitude:           "+xx1[1]+
            "\n\t@Distance:           "+xx1[2]+" AU"+
            "\n\t@                   ("+(xx1[2]*AU2km)+" km)"+
            "\n\t@Longitudinal speed: "+xx1[3]+" degs/day");

     if ((rc==SweConst.ERR)||(rc1==SweConst.ERR)) {
       continue;
         //System.out.println("__________________________________________________________");
     }  else
     {

        pl[i]=xx[0];

     }

    }
    }

    public static void main (String argStr[])
    {
       TestPlanet tp = new TestPlanet();
        tp.tp();
    }
}
