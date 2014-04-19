package ru.liveplanet.zt;

import swisseph.SweConst;
import swisseph.SwissEph;
import swisseph.SweDate;
import swisseph.SwissLib;

/**
 * Created by IntelliJ IDEA.
 * User: DBashkirtsev
 * Date: 19.10.2009
 * Time: 12:48:32
 * To change this template use File | Settings | File Templates.
 */
public class NaclEclipt
{

    double naklEcl=0;
    Geolika geol;
    double dateInputJul;
    //int year=2009,month=12,day=12,hour=0;

/**
 * Наклон эклиптики на гражданскую дату
 * @param year
 * @param month
 * @param day
 * @param hour
 */
    NaclEclipt(int year,int month,int day,int hour)
    {
        SweDate sd=new SweDate(year,month,day,hour);
          //sl=new SwissLib();
          this.dateInputJul=sd.getJulDay();
        this.naklEcl=naklonEclipt(this.dateInputJul);
     }
/**
 * Наклон эклиптики на юлианскую дату
 * @param dateJuli
 */
     NaclEclipt(double dateJuli)
    {
        //SweDate sd=new SweDate(year,month,day,hour);
          //sl=new SwissLib();
          //this.dateInputJul=sd.getJulDay();
        this.naklEcl=naklonEclipt(dateJuli);
     }

    public double naklonEclipt(double dateJuli)
            {
            double ne=0;
            StringBuffer sbErr=new StringBuffer();
          double[] xx=new double[6];
                SwissEph sw = new SwissEph();
         int rc=sw.swe_calc_ut(dateJuli,
                                  -1,
                                  SweConst.SEFLG_SPEED,    //SEFLG_EQUATORIAL
                                  xx,
                                  sbErr);
             if (sbErr.length()>0) {
              System.out.println(sbErr.toString());
            }
            if (rc==SweConst.ERR) {
//          System.exit(1);
                //System.out.println("__________________________________________________________");
            }
            ne=xx[0];
            return ne;
            };

    /*
    public double ne(int year,int month, int day)
    {
        Geolika geol = new Geolika(year,month,day,2,40,40);
        this.naklEcl=geol.naklonEclipt(geol.dateInputJuli); //Расчитан наклон эклиптики на JD
        return naklEcl;
    }

    public double neForGeolika()
    {
        this.naklEcl=geol.naklonEclipt(dateInputJul); //Расчитан наклон эклиптики на JD
        return naklEcl;
    }
      */

    public static void main(String argStr[])
    {
        NaclEclipt nEcl=new NaclEclipt(-225,1,1,1);
        //nEcl.naklEcl=nEcl.ne(2009,11,1);
       // nEcl.naklEcl=nEcl.naklonEclipt(nEcl.dateInputJul);
        System.out.println("@@@ "+nEcl.naklEcl+" @@");

    }

}
