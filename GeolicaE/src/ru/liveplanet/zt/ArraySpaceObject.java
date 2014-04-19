package ru.liveplanet.zt;

import swisseph.SweConst;
import swisseph.SwissEph;

/**
 * Created by IntelliJ IDEA.
 * User: DBashkirtsev
 * Date: 22.10.2009
 * Time: 9:54:01
 * To change this template use File | Settings | File Templates.
 */
public class ArraySpaceObject
{
    SpaceObject[] soPlanet;
    SpaceObject[] soFixStar;
     public SwissEph sw;
    double juliDate;

    ArraySpaceObject(double juliDate)
    {
      this.juliDate=juliDate;
      // Создается и инициализируется массив SpaceObject
         soPlanet = new SpaceObject[23];
for(int i=0; i<soPlanet.length; i++)
{
    soPlanet[i] = new SpaceObject();
}

        soFixStar=new SpaceObject[1002]; //Массив неподвижных звезд
        for(int i=0; i<soFixStar.length; i++)
        {
            soFixStar[i] = new SpaceObject();
        }


        sw=new SwissEph();
    }

    /**
     *
     * @param so
     * @return Массив эклиптических долгот планет
     */


    double [] arrDolgota(SpaceObject[] so)
    {
        int cnt=so.length;
        double[] ad=new double[cnt] ;

        for (int i=0;i<=cnt-1;i++)
        {
         ad[i]=so[i].lambda;
        }

        return ad;
    }
    void planet()
    {
          final double AU2km= SweConst.AUNIT/1000;
           //double [] pl =new double[23];
          double[] xx=new double[6];
        double[] xx1=new double[6];
        StringBuffer sbErr=new StringBuffer();

        for (int i=0;i<=22;i++)
 {
    // StringBuffer sbStar=new StringBuffer(i+"");

        int rc=sw.swe_calc_ut(juliDate,
                              i,
                              SweConst.SEFLG_SPEED,    //SEFLG_EQUATORIAL
                              xx,
                              sbErr);

       int rc1=sw.swe_calc_ut(juliDate,
                              i,
                              SweConst.SEFLG_EQUATORIAL,    //
                              xx1,
                              sbErr);
     soPlanet[i].lambda=xx[0];
     soPlanet[i].betta=xx[1];
     soPlanet[i].alfa=xx1[0];
     soPlanet[i].sigma=xx1[1];
     soPlanet[i].distans=xx[2]*AU2km;
     soPlanet[i].speedLambda=xx[3];
     soPlanet[i].speedBetta=xx[4];
     soPlanet[i].speedDistans=xx[5];
     if ((xx1[0]-Geopoint.ZTKONST)<=180)
     soPlanet[i].soDolgota=xx1[0]-Geopoint.ZTKONST; else
     soPlanet[i].soDolgota=xx1[0]-Geopoint.ZTKONST-360;

     soPlanet[i].soShirota=xx1[1];
     soPlanet[i].priznak=1;
     soPlanet[i].nameSpaceObject=sw.swe_get_planet_name(i);

     /**
      * Заполняем SpaceObject[i]
      *     double lambda; // эклиптическая долгота
    double betta;// эклиптическая широта
    double alfa;// прямое восхождение
    double sigma;// склонение
    double distans;// дистанция в АЕ
    double speedLambda;// скорость по долготе
    double speedBetta;// скорость по широте
    double speedDistans;// скорость удаления
    double soDolgota; // географическая долгота небесного объекта
    double soShirota; // географическая широта небесного объекта
    int priznak;//1-Планеты;2-звезды;3-астероиды
    String nameSpaceObject; //Наименование - идентификатор объекта
      */




        if ((rc==SweConst.ERR)|| (rc1==SweConst.ERR)) {
          //System.exit(1);
            System.out.println("__________________________________________________________");
        }
            /*
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
            */

     if ((rc==SweConst.ERR)||(rc1==SweConst.ERR)) {
       continue;
         //System.out.println("__________________________________________________________");
     }  else
     {

        //pl[i]=xx[0];

     }


    }
    }

      void fxStars()
    {
          final double AU2km=SweConst.AUNIT/1000;
           //double [] pl =new double[23];
          double[] xx=new double[6];
        double[] xx1=new double[6];
        StringBuffer sbErr=new StringBuffer();

        for (int i=0;i<=1001;i++)
 {
     StringBuffer sbStar=new StringBuffer(i+"");
     StringBuffer sbStar1=new StringBuffer(i+"");
          int rc=sw.swe_fixstar_ut(sbStar,juliDate,SweConst.SEFLG_SPEED,xx,sbErr);
          String star1=sbStar.toString();
     int rc1=sw.swe_fixstar_ut(sbStar,juliDate,SweConst.SEFLG_EQUATORIAL,xx1,sbErr);
     //String star2=sbStar.toString();



     soFixStar[i].lambda=xx[0];
     soFixStar[i].betta=xx[1];
     soFixStar[i].alfa=xx1[0];
     soFixStar[i].sigma=xx1[1];
     soFixStar[i].distans=xx[2]*AU2km;
     soFixStar[i].speedLambda=xx[3];
     soFixStar[i].speedBetta=xx[4];
     soFixStar[i].speedDistans=xx[5];
     if ((xx1[0]-Geopoint.ZTKONST)<=180)
     soFixStar[i].soDolgota=xx1[0]-Geopoint.ZTKONST; else
     soFixStar[i].soDolgota=xx1[0]-Geopoint.ZTKONST-360;

     soFixStar[i].soShirota=xx1[1];
     soFixStar[i].priznak=2;
     soFixStar[i].nameSpaceObject=star1;

     /**
      * Заполняем SpaceObject[i]
      *     double lambda; // эклиптическая долгота
    double betta;// эклиптическая широта
    double alfa;// прямое восхождение
    double sigma;// склонение
    double distans;// дистанция в АЕ
    double speedLambda;// скорость по долготе
    double speedBetta;// скорость по широте
    double speedDistans;// скорость удаления
    double soDolgota; // географическая долгота небесного объекта
    double soShirota; // географическая широта небесного объекта
    int priznak;//1-Планеты;2-звезды;3-астероиды
    String nameSpaceObject; //Наименование - идентификатор объекта
      */




        if ((rc==SweConst.ERR)|| (rc1==SweConst.ERR)) {
          //System.exit(1);
            System.out.println("__________________________________________________________");
        }
            /*
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
            */

     if ((rc==SweConst.ERR)||(rc1==SweConst.ERR)) {
       continue;
         //System.out.println("__________________________________________________________");
     }  else
     {

        //pl[i]=xx[0];

     }


    }
    }
}
