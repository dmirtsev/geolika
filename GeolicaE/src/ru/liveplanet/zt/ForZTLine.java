

/**
 * Created by IntelliJ IDEA.
 * User: DBashkirtsev
 * Date: 03.08.2009
 * Time: 15:27:00
 * To change this template use File | Settings | File Templates.
 */
package ru.liveplanet.zt;
import java.util.ArrayList;


public class ForZTLine
{
double gr=29;
     double[] zt =new double[4] ;
    double gDolg;
    double exactDolg;
    double exactZT;
    static ArrayList arrList  = new ArrayList();
    double naklE;

    //SimpleMap2 map= new SimpleMap2();

    ForZTLine()
    {
           // –асчитываетс€ наклон эклиптики на конкретную дату
                NaclEclipt ne =new NaclEclipt(2009,11,1,2);
                //ne.ne(2009,11,1);
                this.naklE=ne.naklEcl;
                //zpoints=geol.getZT(ne.naklEcl);
    }

    void skanZJ(double findGr, double step)
     {
         Geolika geol=new Geolika();

            for (int j=90;j>=-90;j--) // ёжна€ широта
        {
            for (int i=0;i<=359;i++)   //Ўаг по долготе

            {
                geol.setLong(i);
                geol.setLat(j);
                zt=geol.getZT(naklE);
                double eclipShir=geol.getEclShir();

                if ((Math.abs(findGr-zt[1])<step) & (Math.abs(eclipShir)<=66.5))
                {
                //boolean b=plusTrue(j,i,zt[1],false,findGr);
                exactValue(j,i,0.05,zt[1],false,findGr);
                    if (exactDolg>180) exactDolg=-(360-exactDolg);
                System.out.println(i+" "+exactDolg+"<-дол-шир->"+j+"::::«емлеявность:::::"+zt[1]+" Ёкл шир -"+eclipShir);
                }
            }
        }
     }

    void skanZC(double findGr, double step)
    {
        Geolika geol=new Geolika();
           for (int j=90;j>=-90;j--) // ёжна€ широта
        {
         // System.out.println("шир->"+j);
           for (int i=0;i<=359;i++)   //Ўаг по долготе
           {
             //   System.out.println("ƒолг->"+i);
               geol.setLong(i);
               geol.setLat(j);
               zt=geol.getZT(naklE);
               double eclipShir=geol.getEclShir();
               //if (i<180) gDolg=i; else gDolg=-(360-i);
               if (Math.abs(findGr-zt[0])<step)
               {
               //boolean b=plusTrue(j,i,zt[0],true,findGr);
                 exactValue(j,i,0.01,zt[0],true,findGr);
                   if (exactDolg>180) exactDolg=-(360-exactDolg);
              System.out.println(i+" "+exactDolg+"<-дол-шир->"+j+"::::«емле÷ель:::::"+zt[0]+" Ёкл шир -"+eclipShir);
               }
           }
       }
    }

    //* “очное значение землеточек
    void exactValue(double lat, double lon, double orb, double ztOrZc, boolean zc, double findGR)
    {
//        this.zt = zt;
        double exactZTOrZc=0;
        exactDolg=lon;
        boolean b=false;
        if (zc)
        {
            exactZTOrZc=zt[0];
           // “ут функци€ - прибавл€ть или вычитать
            b=plusTrue(lat,lon,ztOrZc,true,findGR); //если true, то прибавл€ть итерации
        }
           else
        {
            exactZTOrZc=zt[1];
            // “ут функци€ - прибавл€ть или вычитать
            b=plusTrue(lat,lon,ztOrZc,false,findGR); //если true, то прибавл€ть итерации
        }
        while(Math.abs(findGR-exactZTOrZc)>orb)
        {
            Geolika geol=new Geolika();
            if (b)
            {
                exactDolg=exactDolg+0.01;//шаг итерации
                geol.setLong(exactDolg);
            } else
            {
                exactDolg=exactDolg-0.01;//шаг итерации
                geol.setLong(exactDolg);
            }
            geol.setLat(lat);
            zt=geol.getZT(naklE);
            if (zc) exactZTOrZc=zt[0];
               else exactZTOrZc=zt[1];
            double eclipShir=geol.getEclShir();
        }//while(zt[0]-exactZT[0]<=orb)
    }
    // ‘ункци€ - прибавл€ть или вычитать при итерации
    // если true, то прибавл€ть
    boolean plusTrue(double lat, double lon, double zt, boolean zc, double findGR)
    {
        boolean pt=false;
        double[] exactZT = new double[4];
        Geolika geol=new Geolika();
        geol.setLong(lon+0.01);
        // “ут функци€ - прибавл€ть или вычитать
        geol.setLat(lat);
        exactZT=geol.getZT(naklE);
        double eclipShir=geol.getEclShir();
        if (zc)
        {
        if (Math.abs(exactZT[0]-findGR)<(Math.abs(zt-findGR)))
        pt=true; else pt=false;
        }  else
        {
            if (Math.abs(exactZT[1]-findGR)<(Math.abs(zt-findGR)))
            pt=true; else pt=false;
        }
        return pt;
    }

   public static void main(String[] str)
    {
        //SimpleMap2 map= new SimpleMap2();
        ForZTLine mp= new ForZTLine();
        mp.skanZC(56,1);

        //mp.skanZJ(120,1);
    }
}
