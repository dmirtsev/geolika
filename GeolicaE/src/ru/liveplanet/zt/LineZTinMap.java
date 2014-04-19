package ru.liveplanet.zt;
import java.lang.Math;
import java.awt.Color;
import java.util.ArrayList;

import com.bbn.openmap.Layer;
import com.bbn.openmap.omGraphics.OMGraphic;
import com.bbn.openmap.omGraphics.OMGraphicList;
import com.bbn.openmap.omGraphics.OMLine;
import com.bbn.openmap.event.ProjectionEvent;
import com.bbn.openmap.examples.simple.SimpleMap2;
import com.bbn.openmap.examples.simple.RouteLayer;



//import ru.liveplanet.zt.Geolika;

/**
 * Created by IntelliJ IDEA.
 * User: bashkirtsev
 * Date: 23.12.2008
 * Time: 13:16:09
 * To change this template use File | Settings | File Templates.
 */



/**
 * Created by IntelliJ IDEA.
 * User: damir
 * Date: 08.08.2006
 * Time: 12:51:15
 * To change this template use File | Settings | File Templates.
 * –асчитывает массив географических координат дл€ заданного градуса землецели - земле€вности с указанной точностью
 * капризные параметры mp.skanZC(300,1-шаг, 0.5-итерационный шаг, 0.2f -необходима€ точность);
 * капризные параметры mp.skanZJ(300,1-шаг, 0.5-итерационный шаг, 0.2f -необходима€ точность);
 */
public class LineZTinMap
{
    double gr=29;
     double[] zt =new double[4] ;
    double gDolg;
    float exactDolg;
    int exactShir;
    double exactZT;
    //ArrayList arrListDolg  = new ArrayList();
    //ArrayList arrListShir  = new ArrayList();
     int arrShir[]=new int[565];
     float arrDolg[]=new float[565];
    SimpleMap2 map= new SimpleMap2();
    double naklE;

      LineZTinMap()
    {
           // –асчитываетс€ наклон эклиптики на конкретную дату
                NaclEclipt ne =new NaclEclipt(2009,11,1,2);
                //ne.ne(2009,11,1);
                this.naklE=ne.naklEcl;
                //zpoints=geol.getZT(ne.naklEcl);
    }

     void skanZJ(double findGr, double step, double iterStep, float tochnost)
     {
         Geolika geol=new Geolika();
          double eclipShir, esh=0;
                int ii=0;
          System.out.println("«емлеявность:");
            for (int j=90;j>=-90;j=j-3) // ёжна€ широта
        {
            for (int i=0;i<=359;i++)   //Ўаг по долготе

            {
                geol.setLong(i);
                geol.setLat(j);
                zt=geol.getZT(naklE);
                eclipShir=esh=geol.getEclShir();

                if ((Math.abs(findGr-zt[1])<step) & (Math.abs(eclipShir)<=66.5)
                  )
                {

                //boolean b=plusTrue(j,i,zt[1],false,findGr);
                  exactValue(j,i,iterStep,tochnost, zt[1],false,findGr);
                    if (exactDolg>180) exactDolg=-(360-exactDolg);
                    arrShir[ii]=j;
                  arrDolg[ii]=exactDolg;
                  //System.out.println(i+" "+exactDolg+"<-дол-шир->"+j+"::::«емлеявность:::::"+zt[1]+" Ёкл шир -"+eclipShir);
                  //System.out.println(arrDolg[ii]+"<-дол-шир->"+arrShir[ii]);
                    //System.out.print(arrDolg[ii]+","+arrShir[ii]+" ");

                }
               // i++;
            }
           // j++;
            if (exactDolg!=0 & (Math.abs(esh)<=66.5))
             System.out.print(exactDolg+","+j+" ");
             //System.out.print(exactDolg+","+j+" "+"Ёкл широта "+esh+" ");
        }
     }

    void skanZC(double findGr, double step, double iterStep, float tochnost)
    {
        Geolika geol=new Geolika();
           int ii=0;
        System.out.println("");
        System.out.println("«емле÷ель:");
           for (int j=90;j>=-90;j=j-9) // ёжна€ широта

       {
           exactShir=j;
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
                //ii++;
               //boolean b=plusTrue(j,i,zt[0],true,findGr);
                 exactValue(j,i,iterStep,tochnost, zt[0],true,findGr);
                   if (exactDolg>180) exactDolg=-(360-exactDolg);
                   //arrListDolg.add(exactDolg);
                 arrShir[ii]=j;
                 arrDolg[ii]=exactDolg;
              //System.out.println(ii+" "+exactDolg+"<-дол-шир->"+exactShir+"::::«емле÷ель:::::"+zt[0]+" Ёкл шир -"+eclipShir);
              //System.out.println(arrDolg[ii]+"<-дол-шир->"+arrShir[ii]);
                  //System.out.print(arrDolg[ii]+","+arrShir[ii]+" ");


               }
            //    i=i+45;
           }
           //System.out.print(exactDolg+","+j+" ");
         //   System.out.print(j+","+exactDolg+" ");

            System.out.print(exactDolg+","+j+" ");
       }
    }

    //* “очное значение землеточек
    void exactValue(double lat, float lon, double iterStep, float tochnost, double ztOrZc, boolean zc, double findGR)
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
        while(Math.abs(findGR-exactZTOrZc)>=tochnost)
        {
            Geolika geol=new Geolika();
            if (b)
            {
                exactDolg=(exactDolg+(float)iterStep);//шаг итерации
                geol.setLong(exactDolg);
            } else
            {
                exactDolg=exactDolg-(float)iterStep;//шаг итерации
                geol.setLong(exactDolg);
            }
            geol.setLat(lat);
            //zt=geol.getZT(geol.naclEkl);
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
        LineZTinMap mp= new LineZTinMap();

        mp.skanZJ(330,1, 0.002, 0.01f);
        //mp.skanZC(330,1, 0.005, 0.005f );

    }
}