package ru.liveplanet.zt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: DBashkirtsev
 * Date: 22.10.2009
 * Time: 14:49:47
 * To change this template use File | Settings | File Templates.
 */
public class PlantDolgotaWithAspect
{
    private double[] arrPlanetDolg;
    private double[] arrAspect;
    double[] dolgPlus;
    double[] dolgMinus;
    InsPlArrForKart[] ipa;
    InsPlArrForKart[] selPlanAsp;

    PlantDolgotaWithAspect(double [] arrPlanetDolg, double[] arrAspect)
    {
        this.arrPlanetDolg = arrPlanetDolg;
        this.arrAspect = arrAspect;

        int n=arrPlanetDolg.length*arrAspect.length;
        dolgPlus = new double[n];
        dolgMinus = new double[n];
        ipa =new InsPlArrForKart[n];
        for(int i=0; i<ipa.length; i++)
{
               ipa[i] = new InsPlArrForKart();
}

    }

    /**
     * Заполняем только выбранные планеты и аспекты
     *
     * @param plan
     * @param asp
     */
    void selectPlanetAndAspect(int[] plan, double[] asp)
    {
        selPlanAsp = new InsPlArrForKart[plan.length*asp.length];

        for(int i=0; i<selPlanAsp.length; i++)
        {
            selPlanAsp[i] = new InsPlArrForKart();
        }
           int s=0;
        for (int j=0;j<=plan.length-1;j++)
        {
            for (int j1=0;j1<=asp.length-1;j1++)
        {
        for (int i=0;i<=ipa.length-1;i++)
        {
           if ((ipa[i].plName == plan[j]) && (ipa[i].aspect == asp[j1]) && ipa[i].dolgota!=0)
           {
               selPlanAsp[s].plName=ipa[i].plName;
               selPlanAsp[s].aspect=ipa[i].aspect;
               selPlanAsp[s].dolgota=ipa[i].dolgota;

               s++;

           }
        }
        }
        }


    }

 void insertArrDolg()
 {
     int s=0;
     for (int i=0; i<arrPlanetDolg.length;i++)
     {
        //dolgPlus[0]=
          for (int j=0; j<arrAspect.length;j++)
          {
        /*
         double rez=arrPlanetDolg[i]+arrAspect[j];
         if (rez>360) rez=rez-360;
         //dolgPlus[s]=rez;
         ipa[s].dolgota=rez;
         ipa[s].aspect=arrAspect[j];
         ipa[s].plName=i;
          */
      //   if ((arrAspect[j]!=0) && (arrAspect[j]!=180))
         {
         double rez=arrPlanetDolg[i]-arrAspect[j];
         if (rez<0) rez=rez+360;
         if (rez>360) rez=rez-360;    

         //dolgMinus[s]=rez;
         ipa[s].dolgota=rez;
         ipa[s].aspect=arrAspect[j];   //Здесь разобраться
          ipa[s].plName=i;

        // ipa[s].aspect=arrAspect[j];
//         ipa[s].dolgota=arrPlanetDolg[s];
              s=s+1;
         }  //s=s+1;

          }
     }
 }

 void insertArrDolg1()
 {
     int s=0;

     for (int i=0; i<arrPlanetDolg.length;i++)
     {
        //dolgPlus[0]=
          for (int j=0; j<arrAspect.length;j++)
          {
/*              ipa.arrPlKt[s].plName=i+"";
              ipa.arrPlKt[s].aspect=arrAspect[s];
              ipa.arrPlKt[s].dolgota=arrPlanetDolg[s];
  */
              ipa[s].plName=i;
              ipa[s].aspect=arrAspect[j];
              ipa[s].dolgota=arrPlanetDolg[s];


         /*
         double rez=arrPlanetDolg[i]+arrAspect[j];
         if (rez>360) rez=rez-360;
         dolgPlus[s]=rez;
         rez=arrPlanetDolg[i]-arrAspect[j];
         if (rez<0) rez=rez+360;
         dolgMinus[s]=rez;
           */
              s=s+1;
          }
     }
 }

    public static void main(String[] argStr) {

double[] arrPlanetDolg = new double[12] ;
double[] arrAspect= new double[12];
        arrPlanetDolg[0]=120;
        arrPlanetDolg[1]=356;
        arrPlanetDolg[2]=35;
        arrPlanetDolg[3]=26;
        arrPlanetDolg[4]=156;
        arrAspect[0]=30;
        arrAspect[1]=60;
        arrAspect[2]=40;
        arrAspect[3]=120;
        arrAspect[4]=150;
        arrAspect[5]=180;




        PlantDolgotaWithAspect pl = new PlantDolgotaWithAspect(arrPlanetDolg, arrAspect);
        pl.insertArrDolg();
        System.exit(0);


    }

}
