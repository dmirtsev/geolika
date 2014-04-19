package ru.liveplanet.zt2014.appl;

/**
 * Хранилище для градусов между планетами и датой этого события
 * @author dbashkirtsev
 *
 */
public class ValueDateTransitPlanetPlanet 
{
	protected int planet1,planet2;
	protected double degreeDistance, jDateTransit;
	
	/**
	 * 
	 * @param planet1 планета 1
	 * @param planet2 планета 2
	 * @param degreeDistance градус между планетами
	 * @param jDateTransit юлианская дата этого события
	 */
	public ValueDateTransitPlanetPlanet(int planet1,int planet2, double degreeDistance, double jDateTransit)
	{
		this.planet1=planet1;this.planet2=planet2; this.degreeDistance=degreeDistance;
		this.jDateTransit=jDateTransit;
		
	}
	

}
