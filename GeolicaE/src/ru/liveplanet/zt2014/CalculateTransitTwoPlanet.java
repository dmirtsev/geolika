package ru.liveplanet.zt2014;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import swisseph.SweConst;
import swisseph.SweDate;
import swisseph.SwissEph;
import swisseph.TCPlanetPlanet;
import swisseph.TransitCalculator;

public class CalculateTransitTwoPlanet 
{
	private double nextTransitUT;
	double maxSpeed;
	double minSpeed;
	public double getNextTransitUT() {
		return nextTransitUT;
	}
	public void setNextTransitUT(double nextTransitUT) {
		this.nextTransitUT = nextTransitUT;
	}
	//CalculateTransitTwoPlanet()
	//{};
	/**
	 * 
	 * @param planetOne - первая планета
	 * @param planetTwo - вторая планета, которая на shiftGradAsp градусов от первой  
	 * @param jdUT - юлианская дата от которой оттолкнуться 
	 * @param backwards вперед от даты - false, назад от даты - true 
	 * @param shiftGradAsp смещение в градусах. Причем на сколько градусов первая планета за второй 
	 */
	public CalculateTransitTwoPlanet(int planetOne, int planetTwo, double jdUT,boolean backwards,double shiftGradAsp)
	{
		SwissEph sw = new SwissEph();
		int flags = SweConst.SEFLG_SWIEPH |
	             SweConst.SEFLG_TRANSIT_LONGITUDE;
		//int flags = SweConst.SEFLG_TRANSIT_LONGITUDE;

	// boolean backwards = true;
	 TransitCalculator tc = new TCPlanetPlanet(
             sw,
             //SweConst.SE_MERCURY,
             //SweConst.SE_VENUS,
             planetOne,
             planetTwo,
             flags,
             shiftGradAsp);
	 /*TCPlanetPlanet tc1 = new TCPlanetPlanet(
             sw,
             //SweConst.SE_MERCURY,
             //SweConst.SE_VENUS,
             planetOne,
             planetTwo,
             flags,
             shiftGradAsp);*/
	 //this.maxSpeed=tc1.
	 
	 this.nextTransitUT = sw.getTransitUT(tc, jdUT, backwards);
	};

	public static void main(String[] args) 
	{
	     SimpleDateFormat formatDateTime=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"); //Для форматирования даты
	     formatDateTime.setTimeZone(TimeZone.getTimeZone("UTC")); // Важно! Иначе выдает время местное с учетом часовго пояса и поправок!

		
	     SweDate sd=new SweDate(1964,9,16,6+52/60.);  
		 CalculateTransitTwoPlanet ctp =new CalculateTransitTwoPlanet(3,4,sd.getJulDay(), true,0 );
		 CalculateTransitTwoPlanet ctp2 =new CalculateTransitTwoPlanet(3,4,sd.getJulDay(), true,0 );
		 System.out.println("Дата транзита "+formatDateTime.format(sd.getDate(ctp.getNextTransitUT()))+" UT");
		 System.out.println("Дата транзита "+formatDateTime.format(sd.getDate(ctp.getNextTransitUT()))+" UT");
		 
		 //ctp.getNextTransitUT();
		 

		// TODO Auto-generated method stub

	}

}
