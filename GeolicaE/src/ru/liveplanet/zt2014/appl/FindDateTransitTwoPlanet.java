/**
 * 
 */
package ru.liveplanet.zt2014.appl;
import java.util.ArrayList;
import ru.liveplanet.zt2014.CalculateTransitTwoPlanet;
import swisseph.SweDate;
/**
 * @author dbashkirtsev
 * ѕредназначен дл€ заполнени€ массива дат искомых транзитных аспектов между двум€ планетами
 */
public class FindDateTransitTwoPlanet {
	protected ArrayList<ValueDateTransitPlanetPlanet> arrDateTransit = new ArrayList<ValueDateTransitPlanetPlanet>();
	public ArrayList<ValueDateTransitPlanetPlanet> getArrDateTransit() {
		return arrDateTransit;
	}
	/**
	 *  онструктор
	 * @param planet1 перва€ планета
	 * @param planet2 втора€ планета
	 * @param degreeDistanc искомы градус между ними (транзитный аспект)
	 * @param jDateBegin юлианска€ дата начала дипазона поиска
	 * @param jDateEnd юлианска€ дата конца дипазона поиска
	 * @param backwards - если true, то считаем назад
	 */
	public FindDateTransitTwoPlanet(int planet1,int planet2,double degreeDistanc, double jDateBegin, double jDateEnd,boolean backwards ) 
	{
		this.arrDateTransit=FindDateTransitTwoPlanet.start(planet1,planet2,degreeDistanc, jDateBegin, jDateEnd, backwards);
	}
	/**
	 * —татичный метод используетс€ в конструкторе класса FindDateTransitTwoPlanet дл€ заполнени€  ArrayList<ValueDateTransitPlanetPlanet>
	 * при создании класса
	 * @param planet1
	 * @param planet2
	 * @param degreeDistanc
	 * @param jDateBegin
	 * @param jDateEnd
	 * @param backwards
	 * @return
	 */
	protected static ArrayList<ValueDateTransitPlanetPlanet>  start(int planet1, int planet2, double degreeDistanc, double jDateBegin, double jDateEnd,boolean backwards)
	{
		ArrayList<ValueDateTransitPlanetPlanet> arrDate = new ArrayList<ValueDateTransitPlanetPlanet>();
		double jDate=jDateBegin;
		boolean work=true;
		while (work) 
		{
			CalculateTransitTwoPlanet calcTrPlanet = 
					new CalculateTransitTwoPlanet(planet1, planet2, jDate, false, degreeDistanc);
			jDate=calcTrPlanet.getNextTransitUT();
			arrDate.add(new ValueDateTransitPlanetPlanet(planet1, planet2, degreeDistanc, jDate));
			//jDate=jDate+10;
			if (backwards==false & jDate>jDateEnd)
			//if (jDate>jDateEnd)
			{
			work=false;
			}
			else 
			{work=true; 
			jDate=jDate+0.01;};
			
			//if (backwards==true & jDate<jDateEnd) work=true; else work=false;
		/*	
			if (backwards==true & jDate<jDateEnd)
			//if (jDate>jDateEnd)
			{
			work=false;
			}
			else 
			{work=true; 
			jDate=jDate+0.01;};
*/

		}
		return arrDate;
	}
	public static void main(String[] args) 
	{
		SweDate sd1=new SweDate(1014,9,16,6+52/60.);
		SweDate sd2=new SweDate(2015,9,16,6+52/60.);
     FindDateTransitTwoPlanet f= new FindDateTransitTwoPlanet(9, 7, 90, sd1.getJulDay(), sd2.getJulDay(), false);
      ArrayList<ValueDateTransitPlanetPlanet> arrDate = new ArrayList<ValueDateTransitPlanetPlanet>();
      arrDate=f.getArrDateTransit();
		for (int i=0;arrDate.size()>i;i++ )
		{
			System.out.println(" ѕланета1 "+ arrDate.get(i).planet1+ " ѕланета2 "+ arrDate.get(i).planet2+
					" »скомый угол "+ arrDate.get(i).degreeDistance + " ƒата "+ sd1.getDate(arrDate.get(i).jDateTransit));
		}
	}
}
