package ru.liveplanet.zt2014;

//import ru.liveplanet.zt.Zemletochki;


public class ZmtAll
{
static final double VERY_SMALL=1E-10;
    private double fiSefarial;

    private double sind(double x) {
      return Math.sin(x * DEGTORAD);
    }
    private double asind(double x) {
      return (Math.asin(x) *RADTODEG);
    }

    private double cosd(double x) {
      return Math.cos(x *DEGTORAD);
    }
    private double tand(double x) {
      return Math.tan(x *DEGTORAD);
    }
    private double atand(double x) {
      return (Math.atan(x) *RADTODEG);
    }

    /**
     * Главное ноу-хау теории землеточек - западная долгота нулевного меридиана
     * От нее идет отсчет, это нулевой градус - ЗемлеЦель 0 градусов Овна
     * Ни в одной школе нет подобной нулевой точки.
     * Она обеспечивает корелляции с событиями
     * Экспериментально допустимы колебания в 40 минут к западу или востоку от этой точки
     */
     static double ZTKONST=16.216666;
           //static double ZTKONST=0;

    static final double DEGTORAD=0.0174532925199433;
    static final double RADTODEG=57.2957795130823;


    /**
     * Процедура делает преобразование экваториальной щироты в эклиптическую
     * Причина - расчет ведется в эклитических координатах и нужно привести
     * и перерасчитать географическую широту в условно спроецированную плоскость эклиптики
     * на земном шаре. То есть получается новая широта - "эклиптическая географическая широта"
     * Иными словами географическая широта пересчитывается на "эклиптическую географическую широту"
     * Ввод в Теорию данного  перерасчета
     * Одно из ноу хау Теории ЗемлеТочек
     * @param shir широта местности
     * @param dol долгота местности
     * @param ecl
     * @return преобразованная широта
     * conversion between ecliptical and equatorial polar coordinates.

     */
    private double cotrans(double shir,double dol,double ecl)
    {
        dol=dol+ZTKONST;
        return ((asind((sind(shir)*cosd(ecl))-(cosd(shir)*sind(ecl)*sind(dol)))));
     }

public double degnorm(double x)
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


    /**
     * Расчет асцендента. Традиционные и открытые рассчеты
     *
     * @param x1
     * @param f
     * @param sine
     * @param cose
     * @return
     */
    private double Asc1 (double x1, double f, double sine, double cose) {
      int n;
      double ass;
      x1 = degnorm(x1);
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
      ass = degnorm(ass);
      if (Math.abs(ass - 90) < VERY_SMALL)
      {
        ass = 90;
      }
      if (Math.abs(ass - 180) < VERY_SMALL) {
        ass = 180;
      }
      if (Math.abs(ass - 270) < VERY_SMALL)
      {
        ass = 270;
      }
      if (Math.abs(ass - 360) < VERY_SMALL) {
        ass = 0;
      }
      return ass;
    }

    /**
     * Вспомогательный метод для расчета асцендента.
     * Метод известный во всех астрологических расчетах
     * Открытый
     * @param x
     * @param f
     * @param sine
     * @param cose
     * @return
     */

    private double Asc2 (double x, double f, double sine, double cose) {
   //   int n;
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

        double[] zt= {0,0,0,0};
        double  tant,fi; //tane,tanfi,
        double sine, cose;
        cose  = cosd(ekl);
        //sine  = sind(ekl);
        //tane  = tand(ekl);

          /**
           * Gреобразование географической щироты (по своей природе экваториальной) в эклиптическую
           * географическую широту
           * Это один из признаков использования теории землеточек
           */
          fi=cotrans(fiekv,geolon,ekl);
          System.out.println("Эклипт широта ="+ fi);
          fiSefarial=fiekv;
          /**
           * Ввод поправки к геграфической широте.
           * Поскольку нудевая точка на соответствующее число градусов западнее, ее значение
           * прибавляется к географической долготе
           * Главный признак теории землеТочек
           */
       geolon=geolon+ZTKONST;
        System.out.println("Истинная долгота ="+ geolon);
       // geolon=geolon+0;


        //System.out.println((int)fi+" гр"+(Math.abs((int)((fi-(int)fi)*60)))+" мин");
       // System.out.println(" Географическая широта места "+fiekv);
        // System.out.println("Эклиптическая географическая широта, преобразованная из географической широты - "+fi);
        //th=geolon; // Превратить geolon в th

          /**
           * Приведение открытые данные
           */
        if (Math.abs(Math.abs(fi) - 90) < VERY_SMALL) {
          if (fi < 0) {
            fi = -90 + VERY_SMALL;
          } else {
            fi = 90 - VERY_SMALL;
          }
        }
       // tanfi = tand(fi);
        /** Приведение ЗемлеЦели в квадрант (перобразования координат)
         * Основан на опубликованных и открытых вычислениях МС - середины небы
         *  Но, учитывая, что применяется долгота с поправкой на нулевую точку
         *  */

        if (Math.abs(geolon - 90) > VERY_SMALL
          && Math.abs(geolon - 270) > VERY_SMALL) {
          tant = tand(geolon);
          zt[0] = atand(tant / cose);
          if (Math.abs(geolon) > 90 && Math.abs(geolon) <= 270) {
            zt[0] = degnorm(zt[0] + 180);
          }
        } else {
          if (Math.abs(geolon - 90) <= VERY_SMALL) {
            zt[0] = 90;
          } else {
            zt[0] = 270;
          }
        }
            
           /*  if */
          /**
           * В массив записываются значения ЗемлеЦели и ЗемлеБазы
           */
        zt[0] = degnorm(zt[0]);
       // zt[0]=geolon;
        zt[2] = degnorm(zt[0]+180);


     System.out.println("Разность между ист долготой и чем-то... ="+(geolon - zt[0]));
        /**
         * Алгоритм расчет асцендента открытый, изестный , опубликованный и реализованный во многих программах
         * В Теории ЗемлеТочек расчитанный на основе ранее вычисленного МС (ЗемлеЦели) асцендент называется
         * ЗемлеЯвностью
         * ЗемлеЯвность вычисляется на основе ЗемлеЦели
         * */
        cose  = cosd(ekl);
        sine  = sind(ekl);

        zt[1] = Asc1 (geolon + 90, fi, sine, cose); // Это землеявнсть для ТЗТ

          /**Рассчет ВЗТ (вертекс землеТочек)
           *
           *
           */

          double koShirota=degnorm(90-fi);
          double obratnZC=degnorm(geolon-180); // оппоз МС - IC
          double ztVertex=Asc1 (degnorm(obratnZC) + 90, koShirota, sine, cose); // Это вертекс для ТЗТ
          obratnZC=degnorm(zt[0]-180);
          double ztVertex1=Asc1 (obratnZC + 90, koShirota, sine, cose); // Это вертекс для ТЗТ
          System.out.println("***ТЗВертекс="+ztVertex);
          System.out.println("***ТЗВертекс1="+ztVertex1);


          //** Для сефариала используем обратную долготу
          //**
          //   Пока все дублируем
         // double geodetAsc;
          //double geodetMC;

          System.out.println("***Расчет обратной долготы***");
          double obrDolg=0;
            /*
          if (Math.abs(Math.abs(fiSefarial) - 90) < VERY_SMALL) {
          if (fi < 0) {
            fi = -90 + VERY_SMALL;
          } else {
            fi = 90 - VERY_SMALL;
          }
        }

          if (Math.abs(geolon-ZTKONST - 90) > VERY_SMALL
          && Math.abs(geolon-ZTKONST - 270) > VERY_SMALL) {
          tant = tand(geolon-ZTKONST);
          geodetMC= atand(tant / cose);
          if (Math.abs(geolon-ZTKONST) > 90 && Math.abs(geolon-ZTKONST) <= 270) {
            geodetMC = degnorm(zt[0] + 180);
          }
        } else {
          if (Math.abs(geolon-ZTKONST - 90) <= VERY_SMALL) {
            geodetMC = 90;
          } else {
            geodetMC = 270;
          }
        }
              */
          obrDolg=(Math.atan(Math.tan((geolon-ZTKONST)*DEGTORAD)*(Math.cos(ekl*DEGTORAD))))*RADTODEG;
          obrDolg=degnorm(obrDolg); // в диапазон
          System.out.println("***Обратная долгота для методики Крис-Макрей="+obrDolg);


          double zj= Asc1 (obrDolg + 90, fiSefarial, sine, cose);
         System.out.println("Для Сефариала геодет МС="+ (geolon-ZTKONST) +" абсолютных градусов"); // новое
         System.out.println("Для Сефариала геодет АСЦ="+zj);

        zt[3] = degnorm(zt[1]+180);

          return zt;
      }
  public static void main(String[] str)
  {
      ZmtAll gp =new ZmtAll();
       //System.out.println("Пример расчета ЗемлеТочек Москвы:"); // новое
      //System.out.println("ГеоНоль(ZTKONST) (ноухау) или поправка к долготе ="+ ZTKONST+" западной долготы");
      //System.out.println("ГеоНоль (ZTKONST) в различных вариантах в Теории ЗемлеТочек может принимать значение от 15.7 до 16.8 ");
      //System.out.println("Но наилучшие результаты дает при ZTKONST=16.216666 ");
      //double [] zemlet= gp.zemletochka(30,30,23.4440459607407);
      double [] zemlet= gp.zemletochka(37.617633,55.755786,23.4440459607407);
      //double [] zemlet= gp.zemletochka(0,0,23.4440459607407);
      //double [] zemlet= gp.zemletochka(166.4703,41.33,23.4440459607407);
      //double [] zemlet= gp.zemletochka(0,41.33,23.4440459607407);
      //System.out.println(" Результат - 4 угловые землеточки");
      System.out.println("Землецель\n"+ zemlet[0]+" абсолютных градусов"); // новое
      System.out.println("ЗемлеЯвность\n"+ zemlet[1]+" абсолютных градусов"); // новое
      //System.out.println("ЗемлеБаза\n"+ zemlet[2]+" абсолютных градусов"); // новое
      //System.out.println("ЗемлеТыл\n"+ zemlet[3]+" абсолютных градусов"); // новое

      //System.out.println(" Теоретически их может быть 12 при введении общеизвестных расчетов домов, например Плацидуса и др.. ");
      //System.out.println("Подход теории ЗемлеТочек позволит, используя основные его расчеты, ввести и использовать дополнительные известные точки, например, аналоги от вертекса, антивертеса и другие");

  }
  }