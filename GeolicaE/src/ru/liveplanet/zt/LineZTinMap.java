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
 * ����������� ������ �������������� ��������� ��� ��������� ������� ��������� - ������������ � ��������� ���������
 * ��������� ��������� mp.skanZC(300,1-���, 0.5-������������ ���, 0.2f -����������� ��������);
 * ��������� ��������� mp.skanZJ(300,1-���, 0.5-������������ ���, 0.2f -����������� ��������);
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
           // ������������� ������ ��������� �� ���������� ����
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
          System.out.println("������������:");
            for (int j=90;j>=-90;j=j-3) // ����� ������
        {
            for (int i=0;i<=359;i++)   //��� �� �������

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
                  //System.out.println(i+" "+exactDolg+"<-���-���->"+j+"::::������������:::::"+zt[1]+" ��� ��� -"+eclipShir);
                  //System.out.println(arrDolg[ii]+"<-���-���->"+arrShir[ii]);
                    //System.out.print(arrDolg[ii]+","+arrShir[ii]+" ");

                }
               // i++;
            }
           // j++;
            if (exactDolg!=0 & (Math.abs(esh)<=66.5))
             System.out.print(exactDolg+","+j+" ");
             //System.out.print(exactDolg+","+j+" "+"��� ������ "+esh+" ");
        }
     }

    void skanZC(double findGr, double step, double iterStep, float tochnost)
    {
        Geolika geol=new Geolika();
           int ii=0;
        System.out.println("");
        System.out.println("���������:");
           for (int j=90;j>=-90;j=j-9) // ����� ������

       {
           exactShir=j;
         // System.out.println("���->"+j);
           for (int i=0;i<=359;i++)   //��� �� �������
           {
             //   System.out.println("����->"+i);
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
              //System.out.println(ii+" "+exactDolg+"<-���-���->"+exactShir+"::::���������:::::"+zt[0]+" ��� ��� -"+eclipShir);
              //System.out.println(arrDolg[ii]+"<-���-���->"+arrShir[ii]);
                  //System.out.print(arrDolg[ii]+","+arrShir[ii]+" ");


               }
            //    i=i+45;
           }
           //System.out.print(exactDolg+","+j+" ");
         //   System.out.print(j+","+exactDolg+" ");

            System.out.print(exactDolg+","+j+" ");
       }
    }

    //* ������ �������� ����������
    void exactValue(double lat, float lon, double iterStep, float tochnost, double ztOrZc, boolean zc, double findGR)
    {
//        this.zt = zt;
        double exactZTOrZc=0;
        exactDolg=lon;
        boolean b=false;
        if (zc)
        {
            exactZTOrZc=zt[0];
           // ��� ������� - ���������� ��� ��������
            b=plusTrue(lat,lon,ztOrZc,true,findGR); //���� true, �� ���������� ��������
        }
           else
        {
            exactZTOrZc=zt[1];
            // ��� ������� - ���������� ��� ��������
            b=plusTrue(lat,lon,ztOrZc,false,findGR); //���� true, �� ���������� ��������
        }
        while(Math.abs(findGR-exactZTOrZc)>=tochnost)
        {
            Geolika geol=new Geolika();
            if (b)
            {
                exactDolg=(exactDolg+(float)iterStep);//��� ��������
                geol.setLong(exactDolg);
            } else
            {
                exactDolg=exactDolg-(float)iterStep;//��� ��������
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
    // ������� - ���������� ��� �������� ��� ��������
    // ���� true, �� ����������
    boolean plusTrue(double lat, double lon, double zt, boolean zc, double findGR)
    {
        boolean pt=false;
        double[] exactZT = new double[4];
        Geolika geol=new Geolika();
        geol.setLong(lon+0.01);
        // ��� ������� - ���������� ��� ��������
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