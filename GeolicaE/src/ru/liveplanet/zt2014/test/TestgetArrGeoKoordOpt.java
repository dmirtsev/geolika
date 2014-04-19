package ru.liveplanet.zt2014.test;

import ru.liveplanet.zt2014.CalculateNaclEclipt;
import ru.liveplanet.zt2014.ElementZt;
import ru.liveplanet.zt2014.ZTLib;
import ru.liveplanet.zt2014.appl.GeoDolgotaKoordForZt;
import swisseph.SweDate;

public class TestgetArrGeoKoordOpt {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double findGeoDolg;
		double gradZT=180, geoShir=-41;
		int typeZt=2;
		SweDate sd=new SweDate(1964,9,16,6+52/60.);
		GeoDolgotaKoordForZt a = new GeoDolgotaKoordForZt();
		//gradZT, typeZt, geoShir, 0.02, 0.005,0 
		findGeoDolg=a.getArrGeoKoordOpt(gradZT, typeZt, geoShir, 0.02, 0.003,0.0);
		if (findGeoDolg>180) findGeoDolg=-(360-findGeoDolg);
		ElementZt zt1 = new ElementZt(findGeoDolg,geoShir,new CalculateNaclEclipt(sd.getJulDay()).naklEcl, typeZt);
		System.out.println("Ќайденна€ долгота: "+findGeoDolg+ " при заданной широте: "+geoShir);
		double ztKoord1=zt1.getLongitude();
		System.out.println("ѕроверка найденной долготы "+findGeoDolg+ " искомый гр: "+gradZT+" провер. рез-т "+ztKoord1);
	}

}
