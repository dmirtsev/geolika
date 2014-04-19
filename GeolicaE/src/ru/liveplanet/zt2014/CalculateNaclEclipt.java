package ru.liveplanet.zt2014;

import swisseph.SweConst;
import swisseph.SwissEph;
import swisseph.SweDate;

/**
 * Нужен для расчета землеточек
 * Но влияние его мало и при орбе 3 градуса его можно игнорировать, беря на 1000 год, например
 * Наклон эклиптики на -200 год до н э 23.716921605858097
 * ЗЦ Москвы 56.20815658994736
 * Наклон эклиптики на 2000 г 23.43767962559196
 * ЗЦ Москвы 56.15180484809385 
 *  * @author dbashkirtsev
 *
 */
public class CalculateNaclEclipt
{
    public double naklEcl=0;
    double dateInputJul;


/**
 * Наклон эклиптики на гражданскую дату
 * @param year
 * @param month
 * @param day
 * @param hour
 */
    CalculateNaclEclipt(int year,int month,int day,int hour)
    {
        SweDate sd=new SweDate(year,month,day,hour);
        this.dateInputJul=sd.getJulDay();
        this.naklEcl=naklonEclipt(this.dateInputJul);
     }
/**
 * Наклон эклиптики на юлианскую дату
 * @param dateJuli
 */
     public CalculateNaclEclipt(double dateJuli)
    {
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
            }
            ne=xx[0];
            return ne;
            };


    public static void main(String argStr[])
    {
        CalculateNaclEclipt nEcl=new CalculateNaclEclipt(-225,1,1,1);
        System.out.println("@@@ "+nEcl.naklEcl+" @@");
    }

}
