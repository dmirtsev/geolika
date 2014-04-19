package ru.liveplanet.zt;

/**
 * Created by IntelliJ IDEA.
 * User: damir
 * Date: 08.08.2006
 * Time: 12:45:10
 * To change this template use File | Settings | File Templates.
 */

import swisseph.SwissLib;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by IntelliJ IDEA.
 * User: damir
 * Date: 21.04.2006
 * Time: 11:55:02
 * To change this template use File | Settings | File Templates.
 */
public class MoonPhase {
    private int faza;
    private boolean retro;
    //double dateJuli;

    public MoonPhase()
    {
//      this.faza = faza;
//      this.retro = retro;
        sl=new SwissLib();
    }
    public SwissLib sl;
    static final double DEGTORAD=0.0174532925199433;
    double sind(double x) {
      return Math.sin(x * DEGTORAD);
    }
    double cosd(double x) {
      return Math.cos(x * DEGTORAD);
    }
/**
 *
 * @param year год в дробной части
 * @param faza какую фазу 0 - новолуние, 1 - первая четверть, 2 - полнолуние, 3 - 3 четверть
 * @param retro фаза перед входящей датой true, особенно относительно полнолуния
 * @return юлианская дата выбранной фазы
 */
    public double getMoonPhase(double year,int faza,boolean retro)
    {
// Преобразование юлианской даты в годс дробной частью
//double year=0;
//Целое k - новолуние
//k+0.25 - 1 четверть
//k+0.5 -  полнолуние
//k+0.75 - 3 четверть
   double  k1,k;
    k1=k=(year-1900)*12.3685; // это приближенное значение k

    switch(faza)
    {
        case 0 :
              {
                k=(int)k;
              }; break;
        case 1 :
              {
                if (retro!=true) k=(int)k+0.25; else k=(int)k-0.25;
              }; break;
        case 2 :
              {
                if (retro!=true) k=(int)k+0.5; else k=(int)k-0.5;
              }; break;
        case 3 :
              {
                if (retro!=true) k=(int)k+0.75; else k=(int)k-0.75;
              }; break;
    }

// t- юлианские столетия от эпохи 1900, январь 0.5
    double t= k/1236.85 ; // приблизительное значение t

    double   dateJuli=2415020.75933+29.53058868*k+0.0001178*t*t- //Моменты средних фаз Луны
                    0.000000155*t*t*t+0.00033*sind
                    (166.56+132.87*t-0.009173*t*t);

           //Средняя аномалия солнца
     double   m=sl.swe_degnorm(359.2242+29.10535608*k
                    - 0.0000333*t*t
                    - 0.00000347*t*t*t);
          //Средняя аномалия луны
     double    m1=sl.swe_degnorm(306.0253+385.81691806*k
                     +0.0107306*t*t
                     +0.00001236*t*t*t);
          //Аргумент широты Луны
     double    f=sl.swe_degnorm(21.2964+390.67050646*k-
                     0.0016528*t*t-
                     0.00000239*t*t*t);
// ***********************
    double a,b,c,d,e,o,g,h,i,j,q,l,p,r,s,popr;
          //Поправки к Phaza_moon
          if ((faza==0) || (faza==2))
                    {
                     a=(0.1734-0.000393*t)*sind(m)  ;
                     b=0.0021*sind(2*m)             ;
                     c=0.4068*sind(m1)              ;
                     d=0.0161*sind(2*m1)            ;
                     e=0.0004*sind(3*m1)            ;
                     o=0.0104*sind(2*f)             ;
                     g=0.0051*sind(m+m1)            ;
                     h=0.0074*sind(m-m1)            ;
                     i=0.0004*sind(2*f+m)           ;
                     j=0.0004*sind(2*f-m)           ;
                     q=0.0006*sind(2*f+m1)          ;
                     l=0.0010*sind(2*f-m1)          ;
                     p=0.0005*sind(m+2*m1)          ;
                     popr=a+b-c+d-e+o-g-h+i-j-q+l+p;
                   }
               else
                  {
                     a=(0.1721-0.0004*t)*sind(m);
                     b=0.0021*sind(2*m)   ;
                     c=0.6280*sind(m1)     ;
                     d=0.0089*sind(2*m1)    ;
                     e=0.0004*sind(3*m1)    ;
                     o=0.0079*sind(2*f)     ;
                     g=0.0119*sind(m+m1)    ;
                     h=0.0047*sind(m-m1)    ;
                     i= 0.0003*sind(2*f+m)  ;
                     j= 0.0004*sind(2*f-m)  ;
                     q=0.0006*sind(2*f+m1) ;
                     l=0.0021*sind(2*f-m1) ;
                     p=0.0003*sind(m+2*m1)  ;
                     r=0.0004*sind(m-2*m1)  ;
                     s=0.0003*sind(2*m+m1) ;
                     popr=a+b-c+d-e+o-g-h+i-j-q+l+p+r-s;
                 }
         //дОПОЛНИТЕЛЬНО ПОПРАВКИ К 1 и 3 четвертям
         if (faza==1)
             {
                a=0.0028-0.0004*cosd(m)+0.0003*cosd(m);
                popr=popr+a;
             }
         if (faza==3)
                 {
                       a=-0.0028 +0.0004*cosd(m)
                                  -0.0003*cosd(m);
                       popr=popr+a;
                 }
     dateJuli=dateJuli+popr;// Дата с приближенной точностью



    return dateJuli;
    }
    public static void main (String[] argStr)  throws Exception
    {
        SimpleDateFormat formatDateTimeInBatFile =new SimpleDateFormat(" dd.MM.yyyy HH:mm:ss"); //Для форматирования строки
        FileOutputStream fos = new FileOutputStream("moonPhaze.txt");
        Writer w =
          new BufferedWriter(new OutputStreamWriter(fos, "Cp866"));
        Geolika geol= new Geolika(2012,07,17,12,55,55);
        double yearFrac=geol.yearFrac(17,8,2012);
        MoonPhase mf=new MoonPhase();
        //System.out.println("-> "+mf.getMoonPhase(2012,2,true));
         double nl =mf.getMoonPhase(yearFrac,0,true);
         //w.write(formatDateTimeInBatFile.format(geol.sd.getDate(nl))+"\n");
       // System.out.println("-> "+formatDateTimeInBatFile.format(geol.sd.getDate(nl)));

        
        GregorianCalendar gcal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    Date start = sdf.parse("01.01.1800");
    Date end = sdf.parse("01.12.2050");
        Date dt;
    gcal.setTime(start);
        double nlOld=0;
         //w.write("Новолуния с "+start.toString()+"по "+end.toString()+"\n");
    while (gcal.getTime().before(end))
    {
        gcal.add(Calendar.DAY_OF_YEAR, 1);
        //System.out.println( gcal.getTime().toString());
        //dt=gcal.get(Calendar.DAY_OF_MONTH);

        yearFrac=geol.yearFrac(gcal.get(Calendar.DATE),gcal.get(Calendar.MONTH),gcal.get(Calendar.YEAR));

        nl =mf.getMoonPhase(yearFrac,2,true);
        System.out.println(nl);
        nl=nl-6/24;
        System.out.println(" --"+nl);
        if (nl!=nlOld)
        {
        //System.out.println("-> "+formatDateTimeInBatFile.format(geol.sd.getDate(nl)));
         w.write(formatDateTimeInBatFile.format(geol.sd.getDate(nl))+"\n");
             //System.out.println("-> "+formatDateTimeInBatFile.format(geol.sd.getDate(nl)));
        }
        nlOld=nl;
            }
        w.flush(); //Завершает запись в файл
       w.close(); //Завершает запись в файл

}
}
