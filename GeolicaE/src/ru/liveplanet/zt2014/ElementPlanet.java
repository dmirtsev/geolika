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
	 * ��������� ���� 
	 * @param idPlanetOrFStar - ������������� ������� ��������� 
	 * @param juliDay - ��������� ����
	 * @param isPlanet - ������� �� true - ������� ��� ��������, false - ������������� ������
	 * ��� ������� � ��������� ������ �������� �������������, ��� ������ - �������� ��� ������������� 
	 * �� ����� ephe\sefstars.txt 
	 */
	public ElementPlanet(String idPlanetOrFStar, double juliDay, boolean isPlanet) {
		// TODO Auto-generated constructor stub
		double[] res=new double[6];
		if (isPlanet)  // ���� ��� �������
		{
		CalculatePlanet pl = new CalculatePlanet();
		 res=pl.getPlanetJD(Integer.parseInt(idPlanetOrFStar),juliDay);
		}
		else // ���� ������������� ������
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
	 * ���������� �� ��������
	 * @return
	 */
	public double getDistance() {
		return distance;
	}
	/**
	 * ���������� ���������� �� ��������
	 * @return
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}
	/**
	 * ������ ��������
	 * @return
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * ���������� ������ �� ��������
	 * @return
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * ������� �������� ��������
	 * @return
	 */
	public double getSpeedLong() {
		return speedLong;
	}
	/**
	 * ���������� ������� �������� ��������
	 * @return
	 */
	public void setSpeedLong(double speedLong) {
		this.speedLong = speedLong;
	}
	public static void main (String[] str )
	{
		SweDate sd=new SweDate(1964,9,16,6+52/60.);
		//������ ������� ��� ���������
		int idPlanet=SweConst.SE_MARS;
		ElementPlanet pl = new ElementPlanet(Integer.toString(idPlanet),sd.getJulDay(), true);
		System.out.println("������� "+pl.getLongitude());
		System.out.println("������ "+pl.getLatitude());
		System.out.println("��������� � AU "+pl.getDistance());
		System.out.println("������� �������� "+pl.getSpeedLong());
		
		ElementPlanet pl1 = new ElementPlanet(",thSco,",sd.getJulDay(), false);
		System.out.println("������� "+pl1.getLongitude());
		System.out.println("������ "+pl1.getLatitude());
		System.out.println("��������� � AU "+pl1.getDistance());
		System.out.println("������� �������� "+pl1.getSpeedLong());
	}
}
