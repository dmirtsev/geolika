package ru.liveplanet.zt;
/**
* This class does all the calculations that are related to astrological
* houses.
* <P><I><B>You will find the complete documentation for the original
* SwissEphemeris package at <A HREF="http://www.astro.ch/swisseph/sweph_g.htm">
* http://www.astro.ch/swisseph/sweph_g.htm</A>. By far most of the information 
* there is directly valid for this port to Java as well.</B></I>
* @version 1.0.0a
*/
class SweHouse
		{

double sine, cose;
              double[] zt= {0,0,0,0};
            double  tant,tane,tanfi,fi;
  static final double MILLIARCSEC=1.0 / 3600000.0;



static final double VERY_SMALL=1E-10;

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
    public  int abs(int i) { return Math.abs(i); }
  public long abs(long l) { return Math.abs(l); }
  public float abs(float f) { return Math.abs(f); }
  public double abs(double d) { return Math.abs(d); }

    static final double DEGTORAD=0.0174532925199433;
    static final double RADTODEG=57.2957795130823;


  /**
  * Constructs a new SweHouse object.

  SweHouse() {
    sl   = new swisseph.SwissLib();
    sw   = new swisseph.SwissEph();
    swed = new swisseph.SwissData();
  }
   */
            public double degnorm(double x)
            {
                double y;
                y = x%360.0;
                if (abs( y) < 1e-13) {
                  y = 0;
                }
                if( y < 0.0 ) {
                  y += 360.0;
                }
                return(y);
              }

  /******************************/
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
    if (abs(ass - 90) < VERY_SMALL)        /* rounding, e.g.: if */ {
      ass = 90;                           /* fi = 0 & st = 0, ac = 89.999... */
    }
    if (abs(ass - 180) < VERY_SMALL) {
      ass = 180;
    }
    if (abs(ass - 270) < VERY_SMALL)        /* rounding, e.g.: if */ {
      ass = 270;                          /* fi = 0 & st = 0, ac = 89.999... */
    }
    if (abs(ass - 360) < VERY_SMALL) {
      ass = 0;
    }
    return ass;
  }  /* Asc1 */

  private double Asc2 (double x, double f, double sine, double cose) {
    int n;
    double ass, sinx;
    ass = - tand(f) * sine + cose * cosd(x);
    if (abs(ass) < VERY_SMALL) {
      ass = 0;
    }
    sinx = sind(x);
    if (abs(sinx) < VERY_SMALL) {
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

            public double[] zemletochka(double geolon,double fiekv, double ekl)
      {
          cose  = cosd(ekl);
          sine  = sind(ekl);
           zt[0]=geolon;
        zt[2] = degnorm(zt[0]+180);
          zt[1] = Asc1 (geolon + 90, fiekv, sine, cose);
         // zt[1] = Asc1 (geolon + 90, fi, sine, cose);
          zt[3] = degnorm(zt[1]+180);
          return zt;
      }

     public static void main(String[] str)
  {
      SweHouse gp =new SweHouse();
      //double [] zemlet= gp.zemletochka(30,30,23.4440459607407);
      double [] zemlet= gp.zemletochka(37.617633,55.755786,23.4440459607407);
      //double [] zemlet= gp.zemletochka(166.4703,41.33,23.4440459607407);
      //double [] zemlet= gp.zemletochka(0,41.33,23.4440459607407);
      System.out.println(" –езультат - 4 угловые землеточки");
      System.out.println("«емлецель\n"+ zemlet[0]+" абсолютных градусов"); // новое
      System.out.println("«емлеявность\n"+ zemlet[1]+" абсолютных градусов"); // новое
      System.out.println("«емлеЅаза\n"+ zemlet[2]+" абсолютных градусов"); // новое
      System.out.println("«емле“ыл\n"+ zemlet[3]+" абсолютных градусов"); // новое

      System.out.println(" “еоретически их может быть 12 при введении общеизвестных расчетов домов, например ѕлацидуса и др.. ");
      System.out.println("ѕодход теории «емле“очек позволит, использу€ основные его расчеты, ввести и использовать дополнительные известные точки, например, аналоги от вертекса, антивертеса и другие");

  }
  }
