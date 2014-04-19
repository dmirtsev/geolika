package ru.liveplanet.zt2014.appl;

import ru.liveplanet.zt2014.CalculateNaclEclipt;
import ru.liveplanet.zt2014.ElementZt;
import swisseph.SweDate;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double findGeoDolg;
		double gradZT=1, geoShir=0;
		int typeZt=2;
		SweDate sd=new SweDate(1964,9,16,6+52/60.);
		GeoDolgotaKoordForZt a = new GeoDolgotaKoordForZt();
		
		findGeoDolg=a.getArrGeoKoordOpt(gradZT, typeZt, geoShir, 0.05, 0.03,0 );
		ElementZt zt1 = new ElementZt(findGeoDolg,geoShir,new CalculateNaclEclipt(sd.getJulDay()).naklEcl, typeZt);
		
		System.out.println("Ќайденна€ долгота: "+findGeoDolg+ " при заданной широте: "+geoShir);
		
		double ztKoord1=zt1.getLongitude();
		System.out.println("ѕроверка найденной долготы "+findGeoDolg+ " искомый гр: "+gradZT+" провер. рез-т "+ztKoord1);
	}

}
