/**
 * 
 */
package ru.liveplanet.zt2014;

import swisseph.SweConst;
import swisseph.SweDate;

/**
 * @author dbashkirtsev
 *
 */
public class ElementPlanet extends Element {
	/**
	 * Заполняет поля 
	 * @param idPlanetOrFStar - идентификатор объекта строковый 
	 * @param juliDay - юлианская дата
	 * @param isPlanet - планета ли true - планета или астероид, false - фиксированная звезда
	 * Для планеты и астероида входит числовой идентификатор, для звезды - название или идентификатор 
	 * из файла ephe\sefstars.txt 
	 */
	public ElementPlanet(String idPlanetOrFStar, double juliDay, boolean isPlanet) {
		// TODO Auto-generated constructor stub
		double[] res=new double[6];
		if (isPlanet)  // если это планета
		{
		CalculatePlanet pl = new CalculatePlanet();
		 res=pl.getPlanetJD(Integer.parseInt(idPlanetOrFStar),juliDay);
		}
		else // если фиксированная звезда
		{
			CalculateFixStars cfs = new CalculateFixStars();
			res=cfs.getFixStarJD(idPlanetOrFStar, juliDay);
		}
		this.setLongitude(res[0]);
		this.setLatitude(res[1]);
		this.setDistance(res[2]);
		this.setSpeedLong(res[3]);
		}
	private double latitude,  speedLong;
	private double distance;
	/**
	 * Расстояние до элемента
	 * @return
	 */
	public double getDistance() {
		return distance;
	}
	/**
	 * Записывает расстояние до элемента
	 * @return
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}
	/**
	 * Широта элемента
	 * @return
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * Записывает широту до элемента
	 * @return
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * Угловая скорость элемента
	 * @return
	 */
	public double getSpeedLong() {
		return speedLong;
	}
	/**
	 * Записывает угловую скорость элемента
	 * @return
	 */
	public void setSpeedLong(double speedLong) {
		this.speedLong = speedLong;
	}
	public static void main (String[] str )
	{
		SweDate sd=new SweDate(1964,9,16,6+52/60.);
		//расчет планеты или астероида
		int idPlanet=SweConst.SE_MARS;
		ElementPlanet pl = new ElementPlanet(Integer.toString(idPlanet),sd.getJulDay(), true);
		System.out.println("Долгота "+pl.getLongitude());
		System.out.println("Широта "+pl.getLatitude());
		System.out.println("Дистанция в AU "+pl.getDistance());
		System.out.println("Угловая скорость "+pl.getSpeedLong());
		
		ElementPlanet pl1 = new ElementPlanet(",thSco,",sd.getJulDay(), false);
		System.out.println("Долгота "+pl1.getLongitude());
		System.out.println("Широта "+pl1.getLatitude());
		System.out.println("Дистанция в AU "+pl1.getDistance());
		System.out.println("Угловая скорость "+pl1.getSpeedLong());
	}
}
