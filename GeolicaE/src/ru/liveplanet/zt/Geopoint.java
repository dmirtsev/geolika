package ru.liveplanet.zt;

/**
 * Created by IntelliJ IDEA.
 * User: damir
 * Date: 09.08.2006
 * Time: 14:34:22
 * To change this template use File | Settings | File Templates.
 */
import swisseph.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Geopoint {
    SwissData swed;
    public swisseph.SwissLib sl;
    private double eclShir=0;
    double ztoch[] =new double[4] ;

    //IntObj iobj;

    public Geopoint()
    {
        swed     = new swisseph.SwissData();
        sl       = new swisseph.SwissLib(this.swed);
      //  iobj= new IntObj();
    }

    public double getEclShir()
    {
       return eclShir;
    }

static final double VERY_SMALL=1E-10;

    private double sind(double x) {
      return Math.sin(x * ZTLib.DEGTORAD);
    }
    private double asind(double x) {
      return (Math.asin(x) * ZTLib.RADTODEG);
    }

    private double cosd(double x) {
      return Math.cos(x * ZTLib.DEGTORAD);
    }
    private double tand(double x) {
      return Math.tan(x * ZTLib.DEGTORAD);
    }
    private double atand(double x) {
      return (Math.atan(x) * ZTLib.RADTODEG);
    }
     static double ZTKONST=16.216666;
    static int ORB=2;
    /**
     * Процедура делает преобразование эклиптической щироты в экваториальную
     * @param shir широта местности
     * @param dol долгота местности
     * @param ecl
     * @return преобразованная широта
     */
    private double cotrans(double shir,double dol,double ecl)
    {
        dol=dol+ZTKONST;

        return ((asind((sind(shir)*cosd(ecl))-(cosd(shir)*sind(ecl)*sind(dol)))));

    }

    private double Asc1 (double x1, double f, double sine, double cose) {
      int n;
      double ass;
      x1 = sl.swe_degnorm(x1);
      n  = (int) ((x1 / 90) + 1);
      if (n == 1) {
        ass = ( Asc2 (x1, f, sine, cose));
      } else if (n == 2) {
        ass = (180 - Asc2 (180 - x1, - f, sine, cose));
      } else if (n == 3) {
        ass = (180 + Asc2 (x1 - 180, - f, sine, cose));
      } else {
        ass = (360 - Asc2 (360- x1,  f, sine, cose));
      }
      ass = sl.swe_degnorm(ass);
      if (Math.abs(ass - 90) < VERY_SMALL)        /* rounding, e.g.: if */ {
        ass = 90;                           /* fi = 0 & st = 0, ac = 89.999... */
      }
      if (Math.abs(ass - 180) < VERY_SMALL) {
        ass = 180;
      }
      if (Math.abs(ass - 270) < VERY_SMALL)        /* rounding, e.g.: if */ {
        ass = 270;                          /* fi = 0 & st = 0, ac = 89.999... */
      }
      if (Math.abs(ass - 360) < VERY_SMALL) {
        ass = 0;
      }
      return ass;
    }  /* Asc1 */

    private double Asc2 (double x, double f, double sine, double cose) {
      int n;
      double ass, sinx;
      ass = - tand(f) * sine + cose * cosd(x);
      if (Math.abs(ass) < VERY_SMALL) {
        ass = 0;
      }
      sinx = sind(x);
      if (Math.abs(sinx) < VERY_SMALL) {
        sinx = 0;
      }
      if (sinx == 0) {
        if (ass < 0) {
          ass = -VERY_SMALL;
        } else {
          ass = VERY_SMALL;
        }
      } else if (ass == 0) {
        if (sinx < 0) {
          ass = -90;
        } else {
          ass = 90;
        }
      } else {
        ass = atand(sinx / ass);
      }
      if (ass < 0) {
        ass = 180 + ass;
      }
      return (ass);
    } /* Asc2 */



    /**
     * Метод, вычисляющий землеточки на конкретную долготу и широту местности
     * @param geolon географическая долгота
     * @param fiekv  географическая широта
     * @param ekl наклон эклиптики
     * @return Четыре землеточки
     * double[0] Землецель
     * double[1] Землеявность
     * double[2] Землебаза
     * double[3] Землетыл
     */
    public double[] zemletochka(double geolon,double fiekv, double ekl)
      {
        //double[] zt= new double[4];
        double[] zt= {0,0,0,0};
        double  tant,th,tane,tanfi,fi;
        double sine, cose;
        //double xeq[]=new double[6];
        cose  = cosd(ekl);
        sine  = sind(ekl);
        tane  = tand(ekl);
        //SwissLib sw=new SwissLib();
        double[] gkoord={geolon,fiekv,0};

        //sl.swe_cotrans(gkoord,0,xeq,0,ekl);
        fi=cotrans(fiekv,geolon,ekl); //*  преобразование эклиптической щироты в экваториальную
        eclShir=fi;

        geolon=geolon+ZTKONST;

        //fi=xeq[1]; // Это географическая широта, спроектированная на "эклиптику"
       // System.out.println(fi);
        //System.out.println((int)fi+"гр"+(Math.abs((int)((fi-(int)fi)*60)))+"мин");
        //System.out.print("Эклиптическая географИЧЕСКАЯ ШИРОТА "+xeq[0]);
        th=geolon; // Превратить geolon в th

        /* north and south poles */
        if (Math.abs(Math.abs(fi) - 90) < VERY_SMALL) {
          if (fi < 0) {
            fi = -90 + VERY_SMALL;
          } else {
            fi = 90 - VERY_SMALL;
          }
        }
        tanfi = tand(fi);
        /**
         *   МС она же ЗемлеЦель
         * NB! Входящая долгота, чтобы преобразоваться в ЗЦ
         * должна пройти некую процедуру, в результате котрой ЗЦ не является точно истинной долготой (+ZTКонстанта),
         * которую мы нашли с помощью добавления Геоноля
         * Полученная МС отличается на величину примерно 1 градус
         * Объяснение:
         * По сути истинная долгота приравнивается к звездному времени рождения
         * И поэтому только из звездного времени (истинной долготы) рассчитывается ЗЦ
         * */

        if (Math.abs(th - 90) > VERY_SMALL
          && Math.abs(th - 270) > VERY_SMALL) {
          tant = tand(th);
          zt[0] = atand(tant / cose);
          if (Math.abs(th) > 90 && Math.abs(th) <= 270) {
            zt[0] = sl.swe_degnorm(zt[0] + 180);
          }
        } else {
          if (Math.abs(th - 90) <= VERY_SMALL) {
            zt[0] = 90;
          } else {
            zt[0] = 270;
          }
        } /*  if */
        zt[0] = sl.swe_degnorm(zt[0]);
        zt[2] = sl.swe_degnorm(zt[0]+180);

        /* Асцендент - он же ЗемлеЯвность */
        zt[1] = Asc1 (th + 90, fi, sine, cose);
        zt[3] = sl.swe_degnorm(zt[1]+180);


/*
       System.out.println("Землецель " +zt[0] ); // новое
       System.out.println("Землеявность "+zt[1] ); // новое
       System.out.println("Землебаза "+zt[2] ); // новое
       System.out.println("Землетыл "+zt[3] ); // новое
  */
          return zt;
      }
  public static void main(String[] str)
  {
      Geopoint gp =new Geopoint();
      //double [] zemlet= gp.zemletochka(29.883333206,56.966667175,23.4440459607407);
       double [] zemlet= gp.zemletochka(37.617633,55.755786,23.4440459607407);
      System.out.println("Землецель\n"+ zemlet[0]); // новое
      System.out.println("ЗемлеЯвность\n"+ zemlet[1]); // новое
      System.out.println("ЗемлеБаза\n"+ zemlet[2]); // новое
      System.out.println("ЗемлеТыл\n"+ zemlet[3]); // новое
/*

      int[] posFrac;
        posFrac=gp.sl.swe_split_deg1(zemlet[0],8);
        System.out.println("Землецель\n" + posFrac[1]+" гр. "+posFrac[2]+" мин. "+posFrac[3]+" сек "+ (posFrac[0]+1)+" номер знака Зодиака." ); // новое
        posFrac=gp.sl.swe_split_deg1(zemlet[1],8);
        System.out.println("Землеявность \n" + posFrac[1]+" гр. "+posFrac[2]+" мин. "+posFrac[3]+" сек "+ (posFrac[0]+1)+" номер знака Зодиака." ); // новое
        posFrac=gp.sl.swe_split_deg1(zemlet[2],8);
        System.out.println("Землебаза \n" + posFrac[1]+" гр. "+posFrac[2]+" мин. "+posFrac[3]+" сек "+ (posFrac[0]+1)+" номер знака Зодиака." ); // новое
        posFrac=gp.sl.swe_split_deg1(zemlet[3],8);
        System.out.println("Землетыл \n" + posFrac[1]+" гр. "+posFrac[2]+" мин. "+posFrac[3]+" сек "+ (posFrac[0]+1)+" номер знака Зодиака." ); // новое
  */

      final double AU2km= swisseph.SweConst.AUNIT/1000;
      swisseph.SwissEph sw=new swisseph.SwissEph();
      //Год месяц день час
      swisseph.SweDate sd=new swisseph.SweDate(1964,9,16,6+52./60.);

      // In this array, the values will be returned:
      double[] res=new double[6];
     StringBuffer sbErr=new StringBuffer();

      int rc=sw.swe_calc_ut(sd.getJulDay(),
                            swisseph.SweConst.SE_SATURN,
                            swisseph.SweConst.SEFLG_SPEED,
                            res,
                            sbErr);

      if (sbErr.length()>0) {
        System.out.println(sbErr.toString());
      }
      if (rc== swisseph.SweConst.ERR) {
        System.exit(1);
      }
      System.out.println(
          sw.swe_get_planet_name(swisseph.SweConst.SE_SATURN)+":"+
          "\n\tLongitude:          "+res[0]+
          "\n\tLatitude:           "+res[1]+
          "\n\tDistance:           "+res[2]+" AU"+
          "\n\t                   ("+(res[2]*AU2km)+" km)"+
          "\n\tLongitudinal speed: "+res[3]+" degs/day");

      swisseph.SwissEph se =new swisseph.SwissEph();
      double eclipsPar[]={0,0,0,0,0,0,0,0,0,0};
      StringBuffer sb=new StringBuffer();
      se.swe_sol_eclipse_when_glob(sd.getJulDay(),2,0,eclipsPar,1,sb);
/*      System.out.println("eclipsPar[0] "+ eclipsPar[0]);
      System.out.println("eclipsPar[1] "+ eclipsPar[1]);
      System.out.println("eclipsPar[2] "+ eclipsPar[2]);
      System.out.println("eclipsPar[3] "+ eclipsPar[3]);
      System.out.println("eclipsPar[4] "+ eclipsPar[4]);
      System.out.println("eclipsPar[5] "+ eclipsPar[5]);
      System.out.println("eclipsPar[6] "+ eclipsPar[6]);
      System.out.println("eclipsPar[7] "+ eclipsPar[7]);
      System.out.println("eclipsPar[8] "+ eclipsPar[8]);
      System.out.println("eclipsPar[9] "+ eclipsPar[9]);
  */
      Date dt =new Date();
      //SimpleDateFormat formatter =new SimpleDateFormat("E dd.MM.yyyy 'в' HH:mm:ss  zzz");
      SimpleDateFormat formatter =new SimpleDateFormat("E dd.MM.yyyy 'в' HH:mm:ss  z");
      dt=sd.getDate(eclipsPar[0]);
      System.out.println("Дата затмения -1 перед событием  "+ formatter.format(dt));

      double eclipsPar1[]={0,0,0,0,0,0,0,0,0,0};
      se.swe_sol_eclipse_when_glob(eclipsPar[2],2,0,eclipsPar1,1,sb);
      dt=sd.getDate(eclipsPar1[0]);
      System.out.println("Дата затмения -2 перед событием  "+ formatter.format(dt));

    /*  System.out.println("\neclipsPar[0] "+ eclipsPar1[0]);
      System.out.println("eclipsPar[1] "+ eclipsPar1[1]);
      System.out.println("eclipsPar[2] "+ eclipsPar1[2]);
      System.out.println("eclipsPar[3] "+ eclipsPar1[3]);
      System.out.println("eclipsPar[4] "+ eclipsPar1[4]);
      System.out.println("eclipsPar[5] "+ eclipsPar1[5]);
      System.out.println("eclipsPar[6] "+ eclipsPar1[6]);
      System.out.println("eclipsPar[7] "+ eclipsPar1[7]);
      System.out.println("eclipsPar[8] "+ eclipsPar1[8]);
      System.out.println("eclipsPar[9] "+ eclipsPar1[9]);
      */
      double eclipsPar2[]={0,0,0,0,0,0,0,0,0,0};
      se.swe_sol_eclipse_when_glob(eclipsPar1[2],2,0,eclipsPar2,1,sb);
      dt=sd.getDate(eclipsPar2[0]);
      System.out.println("Дата затмения -3 перед событием  "+ formatter.format(dt));

/*      System.out.println("\neclipsPar[0] "+ eclipsPar2[0]);
      System.out.println("eclipsPar[1] "+ eclipsPar2[1]);
      System.out.println("eclipsPar[2] "+ eclipsPar2[2]);
      System.out.println("eclipsPar[3] "+ eclipsPar2[3]);
      System.out.println("eclipsPar[4] "+ eclipsPar2[4]);
      System.out.println("eclipsPar[5] "+ eclipsPar2[5]);
      System.out.println("eclipsPar[6] "+ eclipsPar2[6]);
      System.out.println("eclipsPar[7] "+ eclipsPar2[7]);
      System.out.println("eclipsPar[8] "+ eclipsPar2[8]);
      System.out.println("eclipsPar[9] "+ eclipsPar2[9]);
*/
// Пареметры последненго лунного затмения
      double eclipsPar3[]={0,0,0,0,0,0,0,0,0,0};
      se.swe_lun_eclipse_when(sd.getJulDay(),2,0,eclipsPar3,1,sb);
      System.out.println("Пареметры последнего лунного затмения");
      dt=sd.getDate(eclipsPar3[0]);
      System.out.println("Дата последнего лунного затмения перед событием  "+ formatter.format(dt));

  /*    System.out.println("\neclipsPar[0] "+ eclipsPar3[0]);
      System.out.println("eclipsPar[1] "+ eclipsPar3[1]);
      System.out.println("eclipsPar[2] "+ eclipsPar3[2]);
      System.out.println("eclipsPar[3] "+ eclipsPar3[3]);
      System.out.println("eclipsPar[4] "+ eclipsPar3[4]);
      System.out.println("eclipsPar[5] "+ eclipsPar3[5]);
      System.out.println("eclipsPar[6] "+ eclipsPar3[6]);
      System.out.println("eclipsPar[7] "+ eclipsPar3[7]);
      System.out.println("eclipsPar[8] "+ eclipsPar3[8]);
      System.out.println("eclipsPar[9] "+ eclipsPar3[9]);
    */


  }
}