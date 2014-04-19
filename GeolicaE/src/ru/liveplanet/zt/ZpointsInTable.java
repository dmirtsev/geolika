package ru.liveplanet.zt;

/**
 * —оздает файл таблиц «емле“очек с шагом долготы - широты в один градус
 */
public class ZpointsInTable {

    public static void main(String[] str)
    {
        double[] zpoints=new double[4] ;
        int[] posFrac=new int[4];
        Geolika geol=new Geolika();

        /*   geol.setLong(-16.1);
        geol.setLat(56);
        double[] zt;
        zt = new double[4];
        zt=geol.getZT();
        System.out.println(zt[2]);
        System.out.println(zt[1]);
                      System.out.println("√еографическа€ ");
                         System.out.print("«≈ћЋ≈÷≈Ћ№ "+posFrac[1]+" гр."+posFrac[2]+" мин. "+
                                 geol.numToNameZodiak(posFrac[0]+1)+"\n\n"+"«≈ћЋ≈я¬Ќќ—“№\tЁкл. широта\t√еогр. широта\n");
    */
        for (int i=0;i<=359;i++)   //Ўаг по долготе
        {
            /**
             * ¬начале вычислени€ дл€ —еверной широты, потом закомментировать ее
             * и вычислить дл€ южной широты
             */


            //for (int j=0;j<=90;j++) // —еверна€ широта
            for (int j=0;j>=-90;j--) // ёжна€ широта
            {
                //System.out.println("ƒл€ широты ="+j+" гр.");
                geol.setLong(i);
                geol.setLat(j);
                zpoints=geol.getZT(geol.naclEkl);
                double eclipShir=geol.getEclShir();
                 for (int n=0;n<=1;n++)
                {
                     if ((j==0)&(n==0))
                     {
                         posFrac=geol.ztl.swe_split_deg1(zpoints[n],8);
                         int z=0;
                         if (i<180) z=i; else z=-(360-i);
                         System.out.println("√еографическа€ долгота="+z+" гр.");
                         System.out.print("«≈ћЋ≈÷≈Ћ№ "+posFrac[1]+" гр."+posFrac[2]+" мин. "+
                                 geol.numToNameZodiak(posFrac[0]+1)+"\n\n"+"«≈ћЋ≈я¬Ќќ—“№\tЁкл. широта\t√еогр. широта\n");
                     }
                     if ((n==1)&(Math.abs(eclipShir)<=60))
                     {
                         posFrac=geol.ztl.swe_split_deg1(zpoints[n],8);

                         System.out.print(posFrac[1]+" гр."+posFrac[2]+" мин. "+
                                 geol.numToNameZodiak(posFrac[0]+1)+"\t"+
                         ((int)eclipShir+"гр"+(Math.abs((int)((eclipShir-(int)eclipShir)*60)))+"мин"+"\t"+j+"\n"));
                     }
                 }
                if (Math.abs(j)==90) System.out.print("\n\n\n");
            }
        }
        }
    }

