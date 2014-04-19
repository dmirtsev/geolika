package ru.liveplanet.zt2014.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import ru.liveplanet.zt2014.CalculateNaclEclipt;
import ru.liveplanet.zt2014.ElementZt;
import ru.liveplanet.zt2014.appl.GeoDolgotaKoordForZt;
import ru.liveplanet.zt2014.appl.GeoKoord;
import swisseph.SweDate;

public class TestgetArrGeoKoordOptArray {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//double findGeoDolg;
		double gradZT=330;
		int typeZt=2;
		SweDate sd=new SweDate(1964,9,16,6+52/60.);
		GeoDolgotaKoordForZt a = new GeoDolgotaKoordForZt();
		ArrayList<GeoKoord> arrKoord = new ArrayList<GeoKoord>();
		
		arrKoord=a.arrayListGeoKoordForLineZT(gradZT, typeZt, 1, 0.04, 0.008);
		// Сортировка по широте, от меньшего
	
		Collections.sort(arrKoord, new Comparator<GeoKoord>() 
				{	@Override
					public int compare(GeoKoord arg0, GeoKoord arg1) 
					{
						double sh1 = arg0.getShirota();
			             double sh2 = arg1.getShirota();
			             if(sh1 > sh2) {
			                    return 1;
			             }
			             else if(sh1 < sh2) {
			                    return -1;
			             }
			             else {
			                    return 0;
			             }
						//return 0;
					}
			
		}
		
		);
		
		System.out.println("-----------------------------------------");		
		System.out.println("-----------------------------------------");
		for (int i=0;arrKoord.size()>i;i++ )
		{
			//System.out.println("Начинаем заполнение");
			System.out.println("Широта "+ arrKoord.get(i).getShirota()+" Долгота "+ arrKoord.get(i).getDolgota());
			 ElementZt zt2 = new ElementZt(arrKoord.get(i).getDolgota(),arrKoord.get(i).getShirota(),new CalculateNaclEclipt(sd.getJulDay()).naklEcl, typeZt);
			 double  ztKoord=zt2.getLongitude();
			System.out.println("Проверка найденной долготы "+arrKoord.get(i).getDolgota()+
					" для широты "+arrKoord.get(i).getShirota()+
					" искомый гр: "+gradZT+" провер. рез-т "+ztKoord);
		}
	}

}
