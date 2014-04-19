package ru.liveplanet.zt;/**
Батник для Geolika
 set ARG=Владимир_Плая-дель-Кармен
 java -jar GeolikaBatCreator.jar   -d 4.4.1976 -t 7:49.0 -l -87.0731251 -s 20.6312992  -c %ARG%  -z -j -b -m -i
 call %ARG%.bat



 Формирует 6 строк для расчетов
 javaw -jar Geolika.jar  -d 16.9.1964 -t 6:52.0 -l 55.933 -s 54.733 -c БДР-Уфа -z -m -b
javaw -jar Geolika.jar -d 09.07.1964 -t 11:17:20 -l 55.933 -s 54.733  -c Солнечное_затмение_1_перед_БДР-Уфа -z -m -b
javaw -jar Geolika.jar -d 10.06.1964 -t 04:33:31 -l 55.933 -s 54.733 -c Солнечное_затмение_2_перед_БДР-Уфа -z -m -b
Орб оси затмений 2-1 2 гр.3 мин.
javaw -jar Geolika.jar -d 25.06.1964 -t 01:06:14 -l 55.933 -s 54.733 -c Лунное_затмение_перед_БДР-Уфа -z -m -b
javaw -jar Geolika.jar  -d 06.09.1964 -t 04:34:21 -l 55.933 -s 54.733 -c Новолуние_перед_БДР-Уфа -z -m -b
javaw -jar Geolika.jar -d 23.08.1964 -t 05:26:42 -l 55.933 -s 54.733  -c Полнолуние_перед_БДР-Уфа -z -m -b
 */
import swisseph.SweConst;
import swisseph.SweDate;
import swisseph.SwissEph;
import swisseph.SwissLib;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by IntelliJ IDEA.
 * User: damir
 * Date: 19.04.2006
 * Time: 14:40:40
 * To change this template use File | Settings | File Templates.
 */
public class Geolika
 {
         SweDate sd;
    public SwissEph sw;
    public SwissLib sl;
    public ZTLib ztl;
    private int year;
    private int month;
    private int day;
    private double hour;

    double dateInputJuli;
    private double[] ztoh;
    private Date dateInput;
    private double longi;
    private double lat;
     double[] planetInput=new double[25] ;
     double[] houseInput=new double[13] ;
    double[] house=new double[13] ;
     double dateSE1;
    double[] planetSE1=new double[25];
    double[] houseSE1=new double[13];
    double dateSE2;
    double[] planetSE2=new double[25];
    double[] houseSE2=new double[13];
    double dateSE3;
    double[] planetSE3=new double[25];
    double[] houseSE3=new double[13];
    double dateME;
    double[] planetME=new double[25];
    double[] houseME=new double[13];
    double dateNewMoon;
    double[] planetNewMoon=new double[25];
    double[] houseNewMoon=new double[13];
    double dateFullMoon;
    double[] planetFullMoon=new double[25];
    double[] houseFullMoon=new double[13];
    double[] planet=new double[25];
    double orbAxis;
    Geopoint gp;
    double ztoch[] =new double[4] ;
    double ascmc[] =new double[10] ;
    double eclSir=0;
      double naclEkl=0; // Наклон эклиптики
   //  double naclE;

    public Geolika(int year,int month,int day,double hour,double longi,double lat)
    {
       // dateInput=this.dateInput;
        this.longi=longi;
        this.lat=lat;
        this.year=year;
        this.month=month;
        this.day=day;
        this.hour=hour;
        gp=new Geopoint();
        //Geopoint gp;
        sw=new SwissEph();
        sd=new SweDate(year,month,day,hour);
        sl=new SwissLib();
        dateInputJuli=sd.getJulDay();
        ztl= new ZTLib();
        //dateInputJuli=SweDate.

        NaclEclipt ne =new NaclEclipt(dateInputJuli);  // Переделал чтобы входила юлианская дата вместо даты с воздуха
          //      ne.ne(2009,11,1);
                this.naclEkl=ne.naklEcl;

        System.out.println(this.naclEkl);
         /*
        NaclEclipt ne =new NaclEclipt(1908,9,6,12);

                this.naclEkl=ne.naklEcl;
        System.out.println(this.naclEkl);
           */
        //NaclEclipt ncl =new NaclEclipt();
        //ncl.dateInputJul=dateInputJuli;
        //this.naclEkl=ncl.neForGeolika();
    }

    /**
     * Второй конструктор для создает класс без ввода дат - для получения землеточек из внешнего класса,
     * например класса, который создает таблицы Землеточек
     */
    public Geolika()
    {
        /*
        this.longi=longi;
        this.lat=lat;
        this.year=year;
        this.month=month;
        this.day=day;
        this.hour=hour;
        */
        gp=new Geopoint();
        sw=new SwissEph();
        //sd=new SweDate(year,month,day,hour);
        sl=new SwissLib();
        //dateInputJuli=sd.getJulDay();
        ztl= new ZTLib();
       // NaclEclipt ne =new NaclEclipt();
          //      ne.ne(2009,11,1);
       // this.naclE=ne.naklEcl;
    }




     /**
      * Вычисляется наклон эклиптики на юлиантскую дату
     // * @param// dateJuli
      * @return
      */                    /*
     public double naklonEclipt(double dateJuli)
        {
        double ne=0;
        StringBuffer sbErr=new StringBuffer();
      double[] xx=new double[6];
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
                          */
    public void setLat(double lat)
    {
        this.lat=lat;
    }

    public void setLong(double longi)
    {
        this.longi=longi;
    }

    public double getEclShir()
    {
        return gp.getEclShir();
    }


    public double yearFrac(double day,double month,double year)
    //       Порядковый номер дня в году                    и
    //      перевод даты в формат дробной части года
    {
    double nday,num;
    nday= year/4-(int)year/4;
     if (nday!=0) //Для обычного года
    {
          nday=(int)(275*month/9)-2*(int)((month+9)/12)+day-30;
          num=365;
    }
      else     //Для високосного года
     {
          nday=(int)(275*month/9)-(int)((month+9)/12)+day-30;
          num=366;
     }
      return (nday/num+year); //Дата в дробной части года
    }
/*
    public void moonFases1(double dateJuli)
    {
        final double AU2km=SweConst.AUNIT/1000;
        final double  FULMOON=180.0;
//      In this array, the values will be returned:
        double[] res=new double[6];
        double sunKoord=0,moonKoord=0,delta=0;
//      double [] pl =new double[25];
        StringBuffer sbErr=new StringBuffer();

  do
  {
  for(int i=0;i<=1;i++)
  {
        int rc=sw.swe_calc_ut(dateJuli,i,SweConst.SEFLG_SPEED,res,sbErr);
         if (sbErr.length()>0) {
          System.out.println(sbErr.toString());
        }
        if (rc==SweConst.ERR) {
          System.exit(1);
        }
      if (i==0)  sunKoord=res[0];
      if (i==1) moonKoord = res[0];
  }

      //sl.swe_difdeg2n()
        delta=sl.swe_degnorm(sunKoord-moonKoord);
        if (delta<=0.001) break;

        if(delta>11)
        {
            delta=(delta-FULMOON)/11;
            dateJuli=dateJuli-delta;
        }
      dateJuli=dateJuli-1/24/60;

  }
      while(delta>=0.001);
    }
  */

    /**
     * Снимает в переменную ztoch[] вычисленные землеточки
     * @return Знач. землеточек
     */

    public double[] getZT(double ne)
    {
        //naklonEclipt(dateJuli);
       // System.out.println("*** "+ne+" ****");
        ztoch=gp.zemletochka(longi,lat,ne);
        return ztoch;
    }
    public double[] getPlanet (double dateJuli)
    {
        final double AU2km=SweConst.AUNIT/1000;

        // In this array, the values will be returned:


// Массив для вывода рез-тов
        double[] xx=new double[6];
        /**
         * xx[0]:   longitude
xx[1]:   latitude
xx[2]:   distance in AU
xx[3]:   speed in longitude (degree / day)
xx[4]:   speed in latitude (degree / day)
xx[5]:   speed in distance (AU / day)
         */
        double [] pl =new double[25];
        StringBuffer sbErr=new StringBuffer();
 for (int i=0;i<=20;i++)
 {
        int rc=sw.swe_calc_ut(dateJuli,
                              i,
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
           /*
        System.out.println(
            sw.swe_get_planet_name(i)+":"+
            "\tLongitude:          "+xx[0]);

            "\n\tLatitude:           "+xx[1]+
            "\n\tDistance:           "+xx[2]+" AU"+
            "\n\t                   ("+(xx[2]*AU2km)+" km)"+
            "\n\tLongitudinal speed: "+xx[3]+" degs/day");
            */
        pl[i]=xx[0];

 }
        return pl;
    }
    public void getHouse (double dateJuli)
    {
      /*
      *                   A  equal от АСЦ  65
      *                   E  equal   69
      *                   C  Campanus 83
      *                   H  horizon / azimuth 72
      *                   K  Koch 75
      *                   O  Porphyry 79
      *                   P  Placidus 80
      *                   R  Regiomontanus 82
      *                   V  equal Vehlow 86
      *                   X  axial rotation system/ Meridian houses 88
      *                   G  36 Gauquelin sectors 71
      */
      int rc=sw.swe_houses(dateJuli,0,lat,longi,80,house,ascmc);
    }
    public String numToNameZodiak(int num)
    {
        String nameZodiak="";
        switch (num)
                {case (1): nameZodiak="Ari";break;
                case (2): nameZodiak="Tau";break;
                case (3): nameZodiak="Gem";break;
                case (4): nameZodiak="Can";break;
                case (5): nameZodiak="Leo";break;
                case (6): nameZodiak="Vir";break;
                case (7): nameZodiak="Lib";break;
                case (8): nameZodiak="Sco";break;
                case (9): nameZodiak="Sag";break;
                case (10): nameZodiak="Cap";break;
                case (11): nameZodiak="Aqu";break;
                case (12): nameZodiak="Pic";break;
        }
        return nameZodiak;
    }
    public double[]  getDateSolEclips(double dateJuli)
    {
        double eclipsPar[]={0,0,0,0,0,0,0,0,0,0}; //записываются даты фаз затмения eclipsPar[0] - time of maximum eclipse.
        StringBuffer sb=new StringBuffer();
        sw.swe_sol_eclipse_when_glob(dateJuli,2,0,eclipsPar,1,sb);
        return eclipsPar;
    }
    public double[] getDateMoonEclips(double dateJuli)
    {
        double eclipsPar[]={0,0,0,0,0,0,0,0,0,0};//записываются даты фаз затмения eclipsPar[0] - time of maximum eclipse.
        StringBuffer sb=new StringBuffer();
        sw.swe_lun_eclipse_when(dateJuli,2,0,eclipsPar,1,sb);
        return eclipsPar;
    }
      static void doHelp(int returnValue)
      {
          //h-помощь d дата t время l долгота s широта c название p рисовать аспекты планет
          // z рисовать аспекты землеточек m выводить таблицы домов на графику

        System.err.println("Usage: Geolika [-h][-d][-t][-l][-s][-p][-z][-m][-w][-j][-b][-x] [-i]");
        System.err.println(" [-h] - help");
        System.err.println(" [-d] - date format day.month.year - example:21.12.2007");
        System.err.println(" [-t] - time format hour:min:sec or hour:min - example:19:23:56 or 19:24");
        System.err.println(" [-l] - geo.longitude decimal format - example: 45.34 ");
        System.err.println("                West -; East+");
        System.err.println(" [-s] - geo.latitude decimal format - example: -12.34 ");
        System.err.println("                South -; Nouth+");
        System.err.println(" [-p] - draw aspect Planet-Planet");
        System.err.println(" [-z] - draw aspect Planet-ZT");
        System.err.println(" [-m] - draw houses in table");
        System.err.println(" [-b] - if catalog Man"); // например человек - рисунки будут в каталог Man
       System.err.println(" [-x] - fix draw"); // например человек - рисунки будут в каталог Man
          System.err.println(" [-i] - вывод интерпретации"); // например человек - рисунки будут в каталог Man
        System.err.println(" [-w] - classpath");
        System.err.println(" [-j] - if create bat-file for JAR");
          System.err.println(" Пример ");
          System.err.println("-d 16.09.1964 -t 6:52 -l 55.933 -s 54.733 -c БДР-Уфа  -z -j -b -m -i");
        System.exit(returnValue);

    }

     static String addKey(boolean p, boolean z, boolean d,boolean x,boolean b,boolean i)
     {
         String str="";
         if (p) str=str+"-p ";
         if (z) str=str+"-z ";
         if (d) str=str+"-m ";
         if (b) str=str+"-b ";
         if (x) str=str+"-x "; //fix draw
         if (i) str=str+"-i ";

         return str;
     }

     //public

   public static void main(String[] str) throws Exception
   {


      // Получение параметров командной строки
       String hlp="",dat="",tim="",lon="",shir="",comment="",classPath="";
       boolean drAspPlan=false, drAspPlZT=true,drDomTab=false,forJAR=false,fixDraw=false,catalogMan=false, interpr=false;
       String[] strArrDate=new String[3];
       String[] strArrTime=new String[3];


       GetOpt go = new GetOpt("hd:t:l:s:c:pzmw:jxbi");

       //h-помощь d дата t время l долгота s широта c название p рисовать аспекты планет
       // z рисовать аспекты землеточек m выводить таблицы домов на графику

       char c;
       while ((c = go.getopt(str)) != GetOpt.DONE)
       {
         switch(c)
         {
         case 'h':
             doHelp(0);
             break;
         case 'd':
             dat=go.optarg();
             break;
         case 't':
             tim = go.optarg();
             break;
         case 'l':
             lon = go.optarg();
             break;
             case 's':
              shir = go.optarg();
              break;
         case 'c':
             comment = go.optarg();
             break;
         case 'p':
                 drAspPlan=true;
                 break;
         case 'z':
                 drAspPlZT=true;
                 break;
         case 'm':
                 drDomTab=true;
                 break;
          case 'j':
                  forJAR=true;
                  break;

         case 'w':
                 classPath = go.optarg();
                 break;
          case 'x':
                  fixDraw = true;
                    break;
             case 'b':
                     catalogMan = true;
                       break;
                case 'i':
                     interpr = true;
                       break;



         }
     }
       strArrDate=ZT.parsDate(dat);
       strArrTime=ZT.parsTime(tim);

       double min=Double.valueOf(strArrTime[1]).doubleValue();
       double sec=(Double.valueOf(strArrTime[2]).doubleValue())/60;
       double minSec=min+sec;
       double hour=Double.valueOf(strArrTime[0]).doubleValue()+minSec/60;

      // Получение параметров командной строки


       //FileOutputStream fos = new FileOutputStream("geolicaCreate.bat");
       FileOutputStream fos = new FileOutputStream(comment+".bat");

       Writer w =
         //new BufferedWriter(new OutputStreamWriter(fos, "Cp1251866"));
       new BufferedWriter(new OutputStreamWriter(fos, "Cp866"));

    Geolika geol;

    //int day=6, month=10,year=1952;
    //double  hour=23+47/60,longi=(30.3+0/60),lat=(59.93333+0/60);
       /*
       int day=8, month=7,year=2006;
       double hour,h=22,m=5;
       double  longi,lat,lonGr=104.333,lonMin=0,latGr=52.267, latMin=0;
         */
       int day=Integer.valueOf(strArrDate[0]).intValue();
       int month=Integer.valueOf(strArrDate[1]).intValue();
       int year=Integer.valueOf(strArrDate[2]).intValue();


       double longi=Double.valueOf(lon).doubleValue();
       double lat=Double.valueOf(shir).doubleValue();
       /*
       if (lonGr>0)       longi=lonGr+lonMin/60; else longi=lonGr-lonMin/60;
       if (latGr>0) lat=latGr+latMin/60; else lat=latGr-latMin/60;
       hour=h+m/60;
       */
      // записывает в батник для запуска ZT
    //w.write("java -cp F:\\documents\\ProjectJava\\ProjectIdea\\sw\\classes;.; ZT -d "+day+"."
      if (forJAR==false)

      w.write("javaw -cp "+classPath+";.; ru.liveplanet.zt.ZT -d "+day+"."
    +month+"."+year+" -t "+(int)Double.valueOf(strArrTime[0]).doubleValue()+":"+minSec+" -l "+longi+" -s "+
            lat+" -c "+comment+" "+addKey(drAspPlan,drAspPlZT,drDomTab,fixDraw,catalogMan,interpr));
       else // если для jar-файла
      w.write("javaw -jar Geolika.jar  -d "+day+"."
        +month+"."+year+" -t "+(int)Double.valueOf(strArrTime[0]).doubleValue()+":"+minSec+" -l "+longi+" -s "+
               lat+" -c "+comment+" "+addKey(drAspPlan,drAspPlZT,drDomTab,fixDraw,catalogMan,interpr));



    geol = new Geolika(year,month,day,hour,longi,lat);
    //geol.naclEkl=geol.naklonEclipt(geol.dateInputJuli); //Расчитан наклон эклиптики на JD

    int[] posFrac=new int[4];
    //geol.moonFases1(geol.dateInputJuli);
    //double yearFrac=geol.yearFrac(16+(6+52/60)/24,9,1964);

    //geol.getZT(geol.naclEkl);
   /* ЗемлеТочки здесь не нужны вроде
   System.out.println("\nНаклон эклиптики на "+geol.dateInputJuli+" "+ geol.naclEkl );
       for (int i=0;i<=3;i++)
       {
           posFrac=geol.ztl.swe_split_deg1(geol.ztoch[i],8);
           //System.out.println("\n***** "+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
             //      geol.numToNameZodiak(posFrac[0]+1));
       }
    */
   //***************************** Блок вычисления планет и домов на юлианскую дату
    geol.planetInput=geol.getPlanet(geol.dateInputJuli);
       for (int i=0;i<=20;i++)
       {
           posFrac=geol.ztl.swe_split_deg1(geol.planetInput[i],8);
           //System.out.println(
           //geol.sw.swe_get_planet_name(i)+":"+"\t"+
             //      posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
                   //geol.numToNameZodiak(posFrac[0]+1));
       }

     geol.getHouse(geol.dateInputJuli);
       // Заполняем в соотв переменную, так как процедура заполняет в единую house[]
       for (int i=1;i<=12;i++)
       {

           geol.houseInput[i]=geol.house[i];
        posFrac=geol.ztl.swe_split_deg1(geol.houseInput[i],8);
           //System.out.println(
           //":Дом "+ i + "    "+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
             //      geol.numToNameZodiak(posFrac[0]+1));
//);
       }
   //***************************** Блок вычисления планет и домов на юлианскую дату

      SimpleDateFormat formatter =new SimpleDateFormat("E dd.MM.yyyy 'в' HH:mm:ss  z"); //Для форматирования строки
      SimpleDateFormat formatDateTimeInBatFile =new SimpleDateFormat("'-d' dd.MM.yyyy '-t' HH:mm:ss"); //Для форматирования строки
      TimeZone tz = TimeZone.getTimeZone("GMT"); // or PST, MID, etc ...
//    Date now = new Date();
//    DateFormat df = new SimpleDateFormat ("yyyy.mm.dd  hh:mm:ss ");
      formatter.setTimeZone(tz);
      formatDateTimeInBatFile.setTimeZone(tz);
    //String currentTime = df.format(now);

//**********Дата солнечного затмения 1 *************************
       geol.dateSE1=geol.getDateSolEclips(geol.dateInputJuli)[0];
       //System.out.println("Солнечное затмения -1 перед событием  "+ formatter.format(geol.sd.getDate(geol.dateSE1)));
       if (forJAR==false)
       w.write("\njavaw -cp "+classPath+";.; ru.liveplanet.zt.ZT " +
               formatDateTimeInBatFile.format(geol.sd.getDate(geol.dateSE1))+
               " -l "+longi+" -s "+lat+"  -c "+"Солнечное_затмение_1_перед_"+
               comment+" "+addKey(drAspPlan,drAspPlZT,drDomTab,fixDraw,catalogMan,interpr));
       else // для jar файла
           w.write("\njavaw -jar Geolika.jar " +
                   formatDateTimeInBatFile.format(geol.sd.getDate(geol.dateSE1))+
                   " -l "+longi+" -s "+lat+"  -c "+"Солнечное_затмение_1_перед_"+
                   comment+" "+addKey(drAspPlan,drAspPlZT,drDomTab,fixDraw,catalogMan,interpr));



       //***************************** Блок вычисления планет и домов на юлианскую дату
//System.out.println("Планеты и дома на дату солнечного затмения -1 перед событием  ");
        geol.planetSE1=geol.getPlanet(geol.dateSE1);
    //posFrac=geol.ztl.swe_split_deg1(geol.planet[i],8);
           for (int i=0;i<=20;i++)
           {
               posFrac=geol.ztl.swe_split_deg1(geol.planetSE1[i],8);
//               System.out.println(
  //             geol.sw.swe_get_planet_name(i)+":"+
    //           "\t"+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
      //             geol.numToNameZodiak(posFrac[0]+1));
           }

         geol.getHouse(geol.dateSE1);
           // Заполняем в соотв переменную, так как процедура заполняет в единую house[]
           for (int i=1;i<=12;i++)
           {

               geol.houseSE1[i]=geol.house[i];
               posFrac=geol.ztl.swe_split_deg1(geol.houseSE1[i],8);

//               System.out.println(
  //             ":Дом "+ i + "    "+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
    //               geol.numToNameZodiak(posFrac[0]+1));
           }
       //***************************** Блок вычисления планет и домов на юлианскую дату
//**********Дата солнечного затмения 1 *************************

//**********Дата солнечного затмения 2 *************************
       geol.dateSE2=geol.getDateSolEclips(geol.dateSE1)[0];
       //System.out.println("Дата солнечного затмения -2 перед событием  "+ formatter.format(geol.sd.getDate(geol.dateSE2)));
       if (forJAR==false)
       w.write("\njavaw -cp "+classPath+";.; ru.liveplanet.zt.ZT " +
               formatDateTimeInBatFile.format(geol.sd.getDate(geol.dateSE2))+
               " -l "+longi+" -s "+lat+" -c "+"Солнечное_затмение_2_перед_"+
               comment+" "+addKey(drAspPlan,drAspPlZT,drDomTab,fixDraw,catalogMan,interpr));
       else
           w.write("\njavaw -jar Geolika.jar " +
                   formatDateTimeInBatFile.format(geol.sd.getDate(geol.dateSE2))+
                   " -l "+longi+" -s "+lat+" -c "+"Солнечное_затмение_2_перед_"+
                   comment+" "+addKey(drAspPlan,drAspPlZT,drDomTab,fixDraw,catalogMan,interpr));


       //***************************** Блок вычисления планет и домов на юлианскую дату
//System.out.println("Планеты и дома на дату солнечного затмения -2 перед событием  ");
        geol.planetSE2=geol.getPlanet(geol.dateSE2);
           for (int i=0;i<=20;i++)
           {
               posFrac=geol.ztl.swe_split_deg1(geol.planetSE2[i],8);
//               System.out.println(
  //             geol.sw.swe_get_planet_name(i)+":"+
    //           "\t"+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
      //             geol.numToNameZodiak(posFrac[0]+1));
           }

         geol.getHouse(geol.dateSE2);
           // Заполняем в соотв переменную, так как процедура заполняет в единую house[]
           for (int i=1;i<=12;i++)
           {
               geol.houseSE2[i]=geol.house[i];
               posFrac=geol.ztl.swe_split_deg1(geol.houseSE2[i],8);

//               System.out.println(
  //             ":Дом "+ i + "    "+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
    //               geol.numToNameZodiak(posFrac[0]+1));
           }
       //***************************** Блок вычисления планет и домов на юлианскую дату
       posFrac=geol.ztl.swe_split_deg1(geol.sl.swe_degnorm(geol.planetSE2[0]-geol.planetSE1[0]),8);
       //System.out.println("Орб оси затмений 2-1 "+ posFrac[1]+" гр."+posFrac[2]+" мин.");
       w.write("\nОрб оси затмений 2-1 "+ posFrac[1]+" гр."+posFrac[2]+" мин.");


//**********Дата солнечного затмения 2 *************************

//**********Дата солнечного затмения 3 *************************
       geol.dateSE3=geol.getDateSolEclips(geol.dateSE2)[0];
       //System.out.println("Дата солнечного затмения -3 перед событием  "+ formatter.format(geol.sd.getDate(geol.dateSE3)));

       //***************************** Блок вычисления планет и домов на юлианскую дату
//System.out.println("Планеты и дома на дату солнечного затмения -3 перед событием  ");
        geol.planetSE3=geol.getPlanet(geol.dateSE3);
           for (int i=0;i<=20;i++)
           {
               posFrac=geol.ztl.swe_split_deg1(geol.planetSE3[i],8);
//               System.out.println(
  //             geol.sw.swe_get_planet_name(i)+":"+
    //           "\t"+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
      //             geol.numToNameZodiak(posFrac[0]+1));
           }

         geol.getHouse(geol.dateSE3);
           // Заполняем в соотв переменную, так как процедура заполняет в единую house[]
           for (int i=1;i<=12;i++)
           {
               geol.houseSE3[i]=geol.house[i];
               posFrac=geol.ztl.swe_split_deg1(geol.houseSE3[i],8);

//               System.out.println(
  //             ":Дом "+ i + "    "+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
    //               geol.numToNameZodiak(posFrac[0]+1));
           }
       //***************************** Блок вычисления планет и домов на юлианскую дату
//**********Дата солнечного затмения 2 *************************

//**********Дата солнечного затмения 2 *************************
//posFrac=geol.ztl.swe_split_deg1(geol.sl.swe_degnorm(geol.planetSE3[0]-geol.planetSE1[0]),8);
//System.out.println("Орб оси затмений 3-1 "+ posFrac[1]+" гр."+posFrac[2]+" мин.");
//posFrac=geol.ztl.swe_split_deg1(geol.sl.swe_degnorm(geol.planetSE3[0]-geol.planetSE2[0]),8);
//System.out.println("Орб оси затмений 3-2 "+ posFrac[1]+" гр."+posFrac[2]+" мин.");

//**********Дата лунного затмения  *************************
       geol.dateME=geol.getDateMoonEclips(geol.dateInputJuli)[0];
       //System.out.println("Дата лунного затмения перед событием  "+ formatter.format(geol.sd.getDate(geol.dateME)));
       if (forJAR==false)

       w.write("\njavaw -cp "+classPath+";.; ru.liveplanet.zt.ZT " +
               formatDateTimeInBatFile.format(geol.sd.getDate(geol.dateME))+
               " -l "+longi+" -s "+lat+" -c "+"Лунное_затмение_перед_"+
               comment+" "+addKey(drAspPlan,drAspPlZT,drDomTab,fixDraw,catalogMan,interpr));
       else
           w.write("\njavaw -jar Geolika.jar " +
                   formatDateTimeInBatFile.format(geol.sd.getDate(geol.dateME))+
                   " -l "+longi+" -s "+lat+" -c "+"Лунное_затмение_перед_"+
                   comment+" "+addKey(drAspPlan,drAspPlZT,drDomTab,fixDraw,catalogMan,interpr));


       //***************************** Блок вычисления планет и домов на юлианскую дату
//System.out.println("Планеты и дома на дату лунного затмения перед событием  ");
        geol.planetME=geol.getPlanet(geol.dateME);
           for (int i=0;i<=20;i++)
           {
               posFrac=geol.ztl.swe_split_deg1(geol.planetME[i],8);
//               System.out.println(
  //             geol.sw.swe_get_planet_name(i)+":"+
    //           "\t"+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
      //             geol.numToNameZodiak(posFrac[0]+1));
           }

         geol.getHouse(geol.dateME);
           // Заполняем в соотв переменную, так как процедура заполняет в единую house[]
           for (int i=1;i<=12;i++)
           {
               geol.houseME[i]=geol.house[i];
               posFrac=geol.ztl.swe_split_deg1(geol.houseME[i],8);
//               System.out.println(
  //             ":Дом "+ i + "    "+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
    //               geol.numToNameZodiak(posFrac[0]+1));
           }
       //***************************** Блок вычисления планет и домов на юлианскую дату

//**********Дата солнечного затмения 2 *************************

//**********Дата лунного затмения  *************************


//**********Для новолуния и полнолуния  *************************
       double yearFrac=geol.yearFrac(day,month,year);
//       System.out.println("Год в дроби "+ yearFrac );
      //SimpleDateFormat formatter =new SimpleDateFormat("E dd.MM.yyyy 'в' HH:mm:ss  z"); //Для форматирования строки
      MoonPhase mf=new MoonPhase();

      double nl =mf.getMoonPhase(yearFrac,0,true);
      // Проверка корректности вычисленной фазы - была ошибка, например на
       // 13092004 ближайшее новолуние перед событием высчитывал как
       // 14092004 - то есть уже после события
       int ii=0;
       while(nl>geol.dateInputJuli)
       {
//        System.out.println("С коррекцией по новолунию - в превый проход поймал дату новолуния больше чем входная дата  ");
  //      System.out.println("Ошибочная (после события) дата новолуния   "+ formatter.format(geol.sd.getDate(nl)));
        ii++;
        nl =mf.getMoonPhase(yearFrac-(ii/365.25),0,true);

       }
      // Проверка корректности завершена
      geol.dateNewMoon=nl;
//      System.out.println("Дата новолуния   "+ formatter.format(geol.sd.getDate(nl)));
       if (forJAR==false)
       w.write("\njavaw -cp "+classPath+";.; ru.liveplanet.zt.ZT " +
               formatDateTimeInBatFile.format(geol.sd.getDate(nl))+
               " -l "+longi+" -s "+lat+" -c "+"Новолуние_перед_"+
               comment+" "+addKey(drAspPlan,drAspPlZT,drDomTab,fixDraw,catalogMan,interpr));
       else
           w.write("\njavaw -jar Geolika.jar  " +
                   formatDateTimeInBatFile.format(geol.sd.getDate(nl))+
                   " -l "+longi+" -s "+lat+" -c "+"Новолуние_перед_"+
                   comment+" "+addKey(drAspPlan,drAspPlZT,drDomTab,fixDraw,catalogMan,interpr));


       //***************************** Блок вычисления планет и домов на юлианскую дату
//System.out.println("Планеты и дома на новолуние перед событием  ");
        geol.planetNewMoon=geol.getPlanet(geol.dateNewMoon);
           for (int i=0;i<=20;i++)
           {
                posFrac=geol.ztl.swe_split_deg1(geol.planetNewMoon[i],8);
//               System.out.println(
  //             geol.sw.swe_get_planet_name(i)+":"+
    //           "\t"+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
      //             geol.numToNameZodiak(posFrac[0]+1));
           }

         geol.getHouse(geol.dateNewMoon);
           // Заполняем в соотв переменную, так как процедура заполняет в единую house[]
           for (int i=1;i<=12;i++)
           {
               geol.houseNewMoon[i]=geol.house[i];
               posFrac=geol.ztl.swe_split_deg1(geol.houseNewMoon[i],8);

//               System.out.println(
  //             ":Дом "+ i + "    "+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
    //               geol.numToNameZodiak(posFrac[0]+1));
           }
       //***************************** Блок вычисления планет и домов на юлианскую дату

       nl =mf.getMoonPhase(yearFrac,2,true);
       // Проверка корректности вычисленной фазы - была ошибка, например на
        // 13092004 ближайшее новолуние перед событием высчитывал как
        // 14092004 - то есть уже после события
         ii=0;
        while((geol.dateInputJuli-nl)>29.53)
        {
         //System.out.println("С коррекцией по полнолунию - в первый проход поймал (дату полнолуния-входная дата)>25.53  ");
         //System.out.println("Ошибочная (не предыдущая а предпредыдущая) дата полнолуния   "+ formatter.format(geol.sd.getDate(nl)));
         ii++;
         nl =mf.getMoonPhase(yearFrac+(ii/365.25),2,true);
        }
       // Проверка корректности завершена
       geol.dateFullMoon=nl;
      //System.out.println("Дата полнолуния   "+ formatter.format(geol.sd.getDate(nl)));
       if (forJAR==false)
       w.write("\njavaw -cp "+classPath+";.; ru.liveplanet.zt.ZT " +
               formatDateTimeInBatFile.format(geol.sd.getDate(nl))+
               " -l "+longi+" -s "+lat+"  -c "+"Полнолуние_перед_"+
               comment+" "+addKey(drAspPlan,drAspPlZT,drDomTab,fixDraw,catalogMan,interpr));
       else
           w.write("\njavaw -jar Geolika.jar " +
                   formatDateTimeInBatFile.format(geol.sd.getDate(nl))+
                   " -l "+longi+" -s "+lat+"  -c "+"Полнолуние_перед_"+
                   comment+" "+addKey(drAspPlan,drAspPlZT,drDomTab,fixDraw,catalogMan,interpr));



       //***************************** Блок вычисления планет и домов на юлианскую дату
//System.out.println("Планеты и дома на полнолуние перед событием  ");
        geol.planetFullMoon=geol.getPlanet(geol.dateFullMoon);
           for (int i=0;i<=20;i++)
           {
               posFrac=geol.ztl.swe_split_deg1(geol.planetFullMoon[i],8);
//               System.out.println(
  //             geol.sw.swe_get_planet_name(i)+":"+
    //           "\t"+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
      //             geol.numToNameZodiak(posFrac[0]+1));
           }
         geol.getHouse(geol.dateFullMoon);
           // Заполняем в соотв переменную, так как процедура заполняет в единую house[]
           for (int i=1;i<=12;i++)
           {
               geol.houseFullMoon[i]=geol.house[i];
                posFrac=geol.ztl.swe_split_deg1(geol.houseFullMoon[i],8);

//               System.out.println(
  //             ":Дом "+ i + "    "+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
    //               geol.numToNameZodiak(posFrac[0]+1));
           }
       //***************************** Блок вычисления планет и домов на юлианскую дату

//**********Для новолуния и полнолуния  *************************

       w.flush(); //Завершает запись в файл
       w.close(); //Завершает запись в файл

   }
 }
