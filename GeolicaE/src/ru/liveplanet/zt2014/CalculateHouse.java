package ru.liveplanet.zt2014;

import swisseph.SweDate;
import swisseph.SwissEph;

public class CalculateHouse 
{
	  
	double[] house=new double[13] ;
	double[] ascmc =new double[10] ;
	SwissEph sw=new SwissEph();   
	
    public void getHouse (int day, int month, int year,int hour, int min , double dolg, double shir, int typeHouse)
    {
    	//public SwissEph sw;
    	 	
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
    	SweDate sd=new SweDate(year,month,day,hour+min/60.);
    	
      int rc=sw.swe_houses(sd.getJulDay(),0,dolg,shir,typeHouse,house,ascmc);
    }
    
    public void getHouseJD (double juliDate, double dolg, double shir, int typeHouse)
    {
    	//public SwissEph sw;
    	 	
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
    	//SweDate sd=new SweDate(year,month,day,hour+min/60.);
    	
      int rc=sw.swe_houses(juliDate,0,dolg,shir,typeHouse,house,ascmc);
    }



	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		CalculateHouse hs = new CalculateHouse();
		int[] posFrac=new int[4];
		ZTLib ztl= new ZTLib();
// Вариант расчета домов по гражданской дате		
		hs.getHouse(16,9,1964,6,52, 41, 69,80);

	       for (int i=1;i<=12;i++)
	       {

	        posFrac=ztl.swe_split_deg1(hs.house[i],8);
	           System.out.println(
	           ":Дом "+ i + "    "+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
	           	                  ztl.numToNameZodiak(posFrac[0]+1));
	       }           
// Вариант расчета домов по юлианской дате
	       	SweDate sd=new SweDate(1964,9,16,6+52/60.);	           
	   		hs.getHouseJD(sd.getJulDay(), 41, 69,80);

	   		System.out.println( "Юлианская дата "+sd.getJulDay());
		       for (int j=1;j<=12;j++)
		       {

		        posFrac=ztl.swe_split_deg1(hs.house[j],8);
		           System.out.println(
		        	
		           ":Дом "+ j + "    "+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
		                  ztl.numToNameZodiak(posFrac[0]+1));
	           
		       }
	}

}
