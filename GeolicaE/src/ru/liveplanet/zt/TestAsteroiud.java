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

// Использует базу эфемерид se00000s.se1, где 00000 - номер астероида который нобходимо расчитать
    // ежемесячно открывается примерно 1000 астероидов, сейчас их больше 20000.
    // На FTP Swiss астероиды в папаках, например ast0, СОДЕРЖИТ АСТЕРОИДЫ С НОМЕРА 0 ДО 999
    // ast1, СОДЕРЖИТ АСТЕРОИДЫ С НОМЕРА 1000 ДО 1999
    // При необходимости файлы можно докачать конкретно по его реальному номеру
    // Актуальный список астероидов с номерами здесь http://www.minorplanetcenter.org/iau/lists/NumberedMPs.html
    // Имена астероидов по алфавиту здесь http://www.minorplanetcenter.org/iau/lists/MPNames.html
    // Список на 21 апреля 2011 в каталоге "C:\Users\Дамир\Documents\GEO\Projects Java\Geolika\zt\numberAndListAsteroids.txt"
                                   
public class TestAsteroiud {
      public SwissEph sw;
      final double AU2km=SweConst.AUNIT/1000;

    void tp()
    {
        SwissEph sw=new SwissEph();
        double [] pl =new double[2225];
          double[] xx=new double[6];
        double[] xx1=new double[6];
        double[] xx2=new double[6];
        StringBuffer sbErr=new StringBuffer();
        //sbStar=i+"";

        //int num=SweConst.SE_AST_OFFSET+4;
        int num=SweConst.SE_AST_OFFSET+4;
        int realNumAsteroid=0;

 //for (int i=num;i<=20250;i++)
        for (int i=num;i<=13000;i++)  // Подставлять в i<= нужное номер до которого расчитать+10000 
 {
     //StringBuffer sbStar=new StringBuffer(i+"");
          try
          {
        int rc=sw.swe_calc_ut(2488000.5,
                              i,
                              SweConst.SEFLG_SPEED,    //SEFLG_EQUATORIAL
                              xx,
                              sbErr);
       int rc1=sw.swe_calc_ut(2488000.5,
                              i,
                              SweConst.SEFLG_EQUATORIAL,    //
                              xx1,
                              sbErr);


         realNumAsteroid=i-SweConst.SE_AST_OFFSET; // реальный номер астероида в каталоге астероидов

        System.out.println(
            sw.swe_get_planet_name(i)+": "+realNumAsteroid+
            "\tLongitude:          "+xx[0]+
           "\n\tLatitude:           "+xx[1]+
            "\n\tDistance:           "+xx[2]+" AU"+
            "\n\t                   ("+(xx[2]*AU2km)+" km)"+
            "\n\tLongitudinal speed: "+xx[3]+" degs/day");

   //  if ((rc==SweConst.ERR)||(rc1==SweConst.ERR)) {
     //    System.exit(0);
       //  System.out.println("__________________________________________________________");
         //continue;
  //   }


   //  else
     //{

       // pl[i-10001]=xx[0];

     //}
          }
            catch (Exception e)
              {
                System.out.println("Видимо нет файла для астероида "+realNumAsteroid);
              }

    }
    }

    public static void main (String argStr[])
    {
       TestAsteroiud tp = new TestAsteroiud();
        tp.tp();
    }
}
