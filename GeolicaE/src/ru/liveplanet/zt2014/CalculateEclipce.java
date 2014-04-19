package ru.liveplanet.zt2014;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import swisseph.SweConst;
import swisseph.SweDate;
import swisseph.SwissEph;
/**
 * Рассчитывает солнечное и лунное затмение
 *  * @author dbashkirtsev
 *
 */
public class CalculateEclipce 
{
	SwissEph sw=new SwissEph();
	/**
	 * Расчет солнечного затмения 
	 * @param dateJuli
	 * @return
	 * Возвращает массив
	 * tret[0]     time of maximum eclipse
  	 * tret[1]     time, when eclipse takes place at local apparent noon
  	 * tret[2]     time of eclipse begin
  	 * tret[3]     time of eclipse end
  	 * tret[4]     time of totality begin
  	 * tret[5]     time of totality end
  	 * tret[6]     time of center line begin
  	 * tret[7]     time of center line end
  	 * tret[8]     time when annular-total eclipsfe becomes total not implemented so far
  	 * tret[9]     time when annular-total eclipse becomes annular again not implemented so far	 */
	
	public double[]  getDateSolEclips(double dateJuli)
	    {
	        double eclipsPar[]={0,0,0,0,0,0,0,0,0,0}; //записываются даты фаз затмения eclipsPar[0] - time of maximum eclipse.
	        StringBuffer sb=new StringBuffer();
	        int rc=sw.swe_sol_eclipse_when_glob(dateJuli,2,0,eclipsPar,1,sb);
		    if (sb.length()>0) {
			      System.out.println(sb.toString());
			    }
			    if (rc==SweConst.ERR) {
			      System.exit(1);
			    }
	        return eclipsPar;
	    }
	/**
	 * Расчет лунного затмения
	 * @param dateJuli
	 * @return
	 * tret[0]     time of maximum eclipse
   * tret[1] 
   * tret[2]     time of partial phase begin (indices consistent with solar eclipses)
   * tret[3]     time of partial phase end
   * tret[4]     time of totality begin
   * tret[5]     time of totality end
   * tret[6]     time of penumbral phase begin
   * tret[7]     time of penumbral phase end
	 */
	    public double[] getDateMoonEclips(double dateJuli)
	    {
	        double eclipsPar[]={0,0,0,0,0,0,0,0,0,0};//записываются даты фаз затмения eclipsPar[0] - time of maximum eclipse.
	        StringBuffer sb=new StringBuffer();
	        int rc=sw.swe_lun_eclipse_when(dateJuli,2,0,eclipsPar,1,sb);
	        
		    if (sb.length()>0) {
			      System.out.println(sb.toString());
			    }
			    if (rc==SweConst.ERR) {
			      System.exit(1);
			    }
	        return eclipsPar;
	    }
	public static void main(String[] args) 
	{
     int day=16,month=9, year=1964, hour=6, min=52;
     SweDate sd=new SweDate(year,month,day,hour+min/60.);
     double eclipsPar[];
     SimpleDateFormat formatDateTime=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"); //Для форматирования даты
     formatDateTime.setTimeZone(TimeZone.getTimeZone("UTC")); // Важно! Иначе выдает время местное с учетом часовго пояса и поправок!
     CalculateEclipce ecl=new CalculateEclipce();
     // Пример расчета солнечного затмения
     System.out.println("Солнечное затмение");
     eclipsPar=ecl.getDateSolEclips(sd.getJulDay());
	 System.out.println("Максимальная фаза затмения "+formatDateTime.format(sd.getDate(eclipsPar[3]))); // Точное координаты Солнца и Луны
	 //System.out.println("Максимальная фаза затмения "+sd.getDate(eclipsPar[3])); // Точное координаты Солнца и Луны
     for (int i=0; i<eclipsPar.length; i++)
     {
    	 System.out.println(formatDateTime.format(sd.getDate(eclipsPar[i])));
    	 
     }
     // Пример расчета лунного затмения   
     System.out.println("Лунное затмение");
          
     eclipsPar=ecl.getDateMoonEclips(sd.getJulDay());
     System.out.println("Начало фазы полутени "+formatDateTime.format(sd.getDate(eclipsPar[5]))); // Точное координаты Солнца и Луны
     for (int i=0; i<eclipsPar.length; i++)
     {
    	 System.out.println(formatDateTime.format(sd.getDate(eclipsPar[i])));
    	 
     }

	}

}
