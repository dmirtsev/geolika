package ru.liveplanet.zt2014.appl;

public class GeoKoord {

public GeoKoord()
{};
/**
 * Записывает долготу и широту в класс при конструировании класса 
 * @param dolg
 * @param shir
 */
public GeoKoord(double dolg, double shir)
{
	this.dolgota=dolg;
	this.shirota=shir;
}
	protected double dolgota;
	protected double shirota;
	public double getDolgota() {
		return dolgota;
	}
	public void setDolgota(double dolgota) {
		this.dolgota = dolgota;
	}
	public double getShirota() {
		return shirota;
	}
	public void setShirota(double shirota) {
		this.shirota = shirota;
	}
	

}
