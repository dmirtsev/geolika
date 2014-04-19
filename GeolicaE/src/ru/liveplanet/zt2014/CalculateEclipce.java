package ru.liveplanet.zt2014;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import swisseph.SweConst;
import swisseph.SweDate;
import swisseph.SwissEph;
/**
 * ������������ ��������� � ������ ��������
 *  * @author dbashkirtsev
 *
 */
public class CalculateEclipce 
{
	SwissEph sw=new SwissEph();
	/**
	 * ������ ���������� �������� 
	 * @param dateJuli
	 * @return
	 * ���������� ������
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
	        double eclipsPar[]={0,0,0,0,0,0,0,0,0,0}; //������������ ���� ��� �������� eclipsPar[0] - time of maximum eclipse.
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
	 * ������ ������� ��������
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
	        double eclipsPar[]={0,0,0,0,0,0,0,0,0,0};//������������ ���� ��� �������� eclipsPar[0] - time of maximum eclipse.
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
     SimpleDateFormat formatDateTime=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"); //��� �������������� ����
     formatDateTime.setTimeZone(TimeZone.getTimeZone("UTC")); // �����! ����� ������ ����� ������� � ������ ������� ����� � ��������!
     CalculateEclipce ecl=new CalculateEclipce();
     // ������ ������� ���������� ��������
     System.out.println("��������� ��������");
     eclipsPar=ecl.getDateSolEclips(sd.getJulDay());
	 System.out.println("������������ ���� �������� "+formatDateTime.format(sd.getDate(eclipsPar[3]))); // ������ ���������� ������ � ����
	 //System.out.println("������������ ���� �������� "+sd.getDate(eclipsPar[3])); // ������ ���������� ������ � ����
     for (int i=0; i<eclipsPar.length; i++)
     {
    	 System.out.println(formatDateTime.format(sd.getDate(eclipsPar[i])));
    	 
     }
     // ������ ������� ������� ��������   
     System.out.println("������ ��������");
          
     eclipsPar=ecl.getDateMoonEclips(sd.getJulDay());
     System.out.println("������ ���� �������� "+formatDateTime.format(sd.getDate(eclipsPar[5]))); // ������ ���������� ������ � ����
     for (int i=0; i<eclipsPar.length; i++)
     {
    	 System.out.println(formatDateTime.format(sd.getDate(eclipsPar[i])));
    	 
     }

	}

}
