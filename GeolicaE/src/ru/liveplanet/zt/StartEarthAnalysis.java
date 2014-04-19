package ru.liveplanet.zt;

import swisseph.SweDate;
import swisseph.SwissEph;
import swisseph.SwissLib;
import swisseph.SweConst;

/**
 * Этот класс - преобразованный StartInputValue
 * Предназначен для вывода значений на карты Google
 * Для проведения исследований
 -d 16.09.1964 -t 6:52 -l 41 -s 69 -a 3 -b erthqAspect
 Выводит линии Венеры для  аспектов массива erthqAspect
 -d 21.01.1141 -t 6:38 -l 41 -s 69 -a 9 -b CONJ0
 Соединения для Плутона с ЗемлеТочками

 */
public class StartEarthAnalysis
{
    double hour,min,sec,minSec;
    int year,month,day;

    double juliDate;
    double dolg;
    double shir;
    SweDate sd;
    //SpaceObject[] soPlanet;
    //SpaceObject[] soFixStar;
    //GeoObject[] geoObject;
    //public SwissEph sw;
    public SwissLib sl;
    //public Geopoint gp;


    StartEarthAnalysis(int year,int month,int day,double hour,double longi,double lat)
    {


//        sw=new SwissEph();
        this.dolg=longi;
         this.shir=lat;
         this.year=year;
         this.month=month;
         this.day=day;
         this.hour=hour;
         sd=new SweDate(year,month,day,hour);
         this.juliDate=sd.getJulDay();
    }

  

    static void doHelp(int returnValue)
       {
           //h-помощь d дата t время l долгота s широта c название p рисовать аспекты планет
           // z рисовать аспекты землеточек m выводить таблицы домов на графику

         System.err.println("Usage: java StartEarthAnalysis  [-h][-d][-t][-l][-s][-c][-a][-b]");
         System.err.println(" [-h] - help");
         System.err.println(" [-d] - date format day.month.year - example:21.12.2007");
         System.err.println(" [-t] - time format hour:min:sec or hour:min - example:19:23:56 or 19:24");
         System.err.println(" [-l] - geo.longitude decimal format - example: 45.34 ");
         System.err.println("                West -; East+");
         System.err.println(" [-s] - geo.latitude decimal format - example: -12.34 ");
         System.err.println("                South -; Nouth+");
         System.err.println("[-c] - comment");
           System.err.println("[-a] - список планет");
           System.err.println("[-b] - список аспектов");
        System.err.println("[-f] - файл трассы для урагана");
        System.err.println("Пример на дату и координаты 0 планета (Солнце)  и мажорные аспекты");
           System.err.println(" java StartEarthAnalysis -d 16.09.1964 -t 6:52 -l 41 -s 69 -a 0 -b majorAspect");
         System.exit(returnValue);

     }



    public static void main(String[] str) throws Exception
      {
         // Получение параметров командной строки
          String hlp="",dat="",tim="",lon="",shir="",comment="";
          int[] planet=new int[300] ;
          double [] aspect= new double[300] ;
          String[] strArrDate=new String[3];
          String[] strArrTime=new String[3];
          String tracHurriganFile="";
          boolean existTraceHurrFile=false;


          GetOpt go = new GetOpt("hd:t:l:s:c:a:b:f:mw:jxb");

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
            case 'a':
                planet=go.getPlanetArr();
                break;
            case 'b':aspect= go.getAspectArr();
                break;
            case 'f':
            {
                    existTraceHurrFile=true;
                    tracHurriganFile=go.optarg();
            }
                    break;
            /*
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

              */

            }
        }

          // Преобразуем массивы - даляем лишние нулевые
              int n=0;
          for (int i=0;i<planet.length;i++)
          {
              n=planet[i];
              if ((i>0) && (n==0))
              {
                  i=n; break;
              }
          }
          int pl[] = new int[n];
          pl = planet;

            double m=0;
          int s=0;
          for (int i=0;i<aspect.length;i++)
          {
              m=aspect[i];
              if ((i>0) && (m==0))
              {
                   break;
              }
              s++;
          }
          double asp[] = new double[s];
          for (int i=0;i<s;i++)
          {
           asp[i]=aspect[i];
          }








          strArrDate=ZT.parsDate(dat);
          strArrTime=ZT.parsTime(tim);

          double min=Double.valueOf(strArrTime[1]).doubleValue();
          double sec=(Double.valueOf(strArrTime[2]).doubleValue())/60;
          double minSec=min+sec;
          double hour=Double.valueOf(strArrTime[0]).doubleValue()+minSec/60;

         // Получение параметров командной строки
          //StartEarthAnalysis(int year,int month,int day,double hour,double longi,double lat)
          StartEarthAnalysis siv1 = new StartEarthAnalysis(Integer.valueOf(strArrDate[2]).intValue(),Integer.valueOf(strArrDate[1]).intValue(),
                  Integer.valueOf(strArrDate[0]).intValue(), hour,
                  Double.valueOf(lon).doubleValue(), Double.valueOf(shir).doubleValue());
          System.out.println("");

          // Получаем массив планет и звезд
          ArraySpaceObject siv =new ArraySpaceObject(siv1.juliDate);
          siv.planet();
          siv.fxStars();
//          double [] d=new double[40];
  //        d=siv.arrDolgota(siv.soPlanet);

          Aspect as =new Aspect(); // Создаются массивы аспектов

          PlantDolgotaWithAspect pd=new PlantDolgotaWithAspect(siv.arrDolgota(siv.soPlanet),as.allAspect);

          //int cntPlanet=siv.arrDolgota(siv.soPlanet).length; // Кол-во входящих планет
         // int cntAspect=as.majorAspect.length;// Кол-во входящих аспектов

          // Расчитываем долготы с учетом выбранного массива аспектов
          //pd.insertArrDolg(); // // Результат см в Массив долгот c аспектами.txt
          // Все эти долготы должны быть представлены на карте линиями как землецели так и землеявности
          // см LineZTInMap.java
          pd.insertArrDolg(); // Имеем все долготы всех планет с аспектами
          // Теперь можем росуществить выбор планеты и аспектов ее - которые в дальнейшем будут пердставлены линиями на карте

         // int[] pl=new int[1];
          //pl[0]=SweConst.SE_URANUS;
          //pl[1]=SweConst.SE_PLUTO;
          //pl[0]=SweConst.SE_SUN;
         // double [] asp = new double[1] ;
          //asp[0]=Aspect.CONJ0;

          //Aspect asp1 = new Aspect();
          //double asp[] = new double[asp1.majorAspect.length] ;
          //asp=asp1.majorAspect;


          //asp[1]=Aspect.OPP180;
          //asp[2]=Aspect.TRIN120;
          //asp[1]=Aspect.SEXT60;
          //asp[2]=-Aspect.SEXT60;
          //asp[2]=-Aspect.SEXT60;
          //asp[2]=Aspect.TRIN120;
          //asp[3]=-Aspect.TRIN120;
          //asp[4]=-Aspect.SEXT60;

          //asp[3]=-Aspect.SEXT60;

          //**
          // Заполняется значение - выбранный элемент+- аспект  только выбранные планеты и аспекты
            pd.selectPlanetAndAspect(pl,asp);      //!!!! //Доделать


          /*
           siv ArraySpaceObject
           soPlanet[i] - 22 планеты - см  Константы планет.txt
           soFixStar[i]  1001 звезда - см  FixStarCatalog.txt
           их параметры
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


           System.out.println("^^^^^^^^^^^^^^^^^");
          //DrawCart dc=new DrawCart(pd.selPlanAsp,tracHurriganFile);
          //dc.start();

          

          /*
          for (int i=0;i<=siv.arrDolgota(siv.soPlanet).length-1;i++)
          {
              System.out.println(i+"ПЛ "+siv.arrDolgota(siv.soPlanet)[i]);
          }
          
          for (int i=0;i<=as.majorAspect.length-1;i++)
          {
              System.out.println(i+" АСП "+as.majorAspect[i]);
          }


          for (int i=0;i<=pd.dolgMinus.length-1;i++)
          {
              System.out.println(i+"- "+pd.dolgMinus[i]);
          }
          for (int i=0;i<=pd.dolgPlus.length-1;i++)
          {
              System.out.println(i+"+ "+pd.dolgPlus[i]);
          }
           /*
           System.out.println("#########################");           
          /*
          for (int i=0;i<=22;i++)
          {
            System.out.println(
     "soPlanet[i].nameSpaceObject "+siv.soPlanet[i].nameSpaceObject+
     "\nsoPlanet[i].lambda "+siv.soPlanet[i].lambda  +
     "\nsoPlanet[i].betta "+siv.soPlanet[i].betta  +
     "\nsoPlanet[i].alfa "+siv.soPlanet[i].alfa  +
     "\nsoPlanet[i].sigma "+siv.soPlanet[i].sigma  +
     "\nsoPlanet[i].distans "+siv.soPlanet[i].distans  +
     "\nsoPlanet[i].speedLambda "+siv.soPlanet[i].speedLambda  +
     "\nsoPlanet[i].speedBetta "+siv.soPlanet[i].speedBetta  +
     "\nsoPlanet[i].speedDistans "+siv.soPlanet[i].speedDistans  +
     "\nsoPlanet[i].soDolgota "+siv.soPlanet[i].soDolgota  +
     "\nsoPlanet[i].soShirota "+siv.soPlanet[i].soShirota  +
     "\nsoPlanet[i].priznak "+siv.soPlanet[i].priznak);

        System.out.println("#########################");
          }


          for (int i=0;i<=1001;i++)
          {
            System.out.println(
     "soPlanet[i].nameSpaceObject "+siv.soFixStar[i].nameSpaceObject+
             "\nsoPlanet[i].lambda "+siv.soFixStar[i].lambda+
     "\nsoPlanet[i].betta "+ siv.soFixStar[i].betta  +
     "\nsoPlanet[i].alfa "+siv.soFixStar[i].alfa  +
     "\nsoPlanet[i].sigma "+siv.soFixStar[i].sigma  +
     "\nsoPlanet[i].distans "+siv.soFixStar[i].distans  +
     "\nsoPlanet[i].speedLambda "+siv.soFixStar[i].speedLambda  +
     "\nsoPlanet[i].speedBetta "+siv.soFixStar[i].speedBetta  +
     "\nsoPlanet[i].speedDistans "+siv.soFixStar[i].speedDistans  +
     "\nsoPlanet[i].soDolgota "+siv.soFixStar[i].soDolgota  +
     "\nsoPlanet[i].soShirota "+siv.soFixStar[i].soShirota  +
     "\nsoPlanet[i].priznak "+siv.soFixStar[i].priznak );

        System.out.println("#########################");
          }
             */
      }


}
