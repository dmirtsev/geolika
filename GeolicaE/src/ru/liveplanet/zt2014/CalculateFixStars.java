package ru.liveplanet.zt2014;

import swisseph.SweConst;
import swisseph.SweDate;
import swisseph.SwissEph;

/**
 * Вычисляет координаты неподвихной звезды
 * метод getFixStar(...)
 * @author dbashkirtsev
 *
 */
public class CalculateFixStars {
  static final double AU2km=SweConst.AUNIT/1000;
  String starNameFinded="";
  SwissEph sw=new SwissEph();
  /**
   * Расчет координаты звезды по входящей гражданской  дате
   * @param starID - название зведды из каталога ephe\sefstars.txt
   * Может быть имя - "Algol" или идентификатор, огранич запятыми - ",bePer,"
   * @param day
   * @param month
   * @param year
   * @param hour
   * @param min
   * @return
   */
  public double[] getFixStar(String starID, int day, int month, int year,int hour, int min ) 
  {
	
	    SweDate sd=new SweDate(year,month,day,hour+min/60.);

	  double[] res=new double[6];
	    StringBuffer sbErr=new StringBuffer();
	    int flags = SweConst.SEFLG_SPEED; //|	                SweConst.SEFLG_TOPOCTR;
	    StringBuffer sb=new StringBuffer().append(starID);
	    int fc=sw.swe_fixstar_ut(sb, sd.getJulDay(), flags, res, sbErr);
	    if (sbErr.length()>0) {
		      System.out.println(sbErr.toString());
		    }
		    if (fc==SweConst.ERR) {
		      System.exit(1);
		    }
		   starNameFinded=sb.toString(); 
	  return res;
	
}
/**
 * Расчет координаты звезды по входящей юлианской дате
 * @param starID - название зведды из каталога ephe\sefstars.txt
 * Может быть имя - "Algol" или идентификатор, огранич запятыми - ",bePer,"
 * @param juliDate
 * @return
 */
  public double[] getFixStarJD(String starID, double juliDate) 
  {
	
	    //SweDate sd=new SweDate(year,month,day,hour+min/60.);

	  double[] res=new double[6];
	    StringBuffer sbErr=new StringBuffer();
	    int flags = SweConst.SEFLG_SPEED ;//|;//	                SweConst.SEFLG_TOPOCTR;
	    StringBuffer sb=new StringBuffer().append(starID);
	    int fc=sw.swe_fixstar_ut(sb, juliDate, flags, res, sbErr);
	    if (sbErr.length()>0) {
		      System.out.println(sbErr.toString());
		    }
		    if (fc==SweConst.ERR) {
		      System.exit(1);
		    }
	    
		    starNameFinded=sb.toString();
	  return res;
} 
  public static void main(String[] p) 
  {
	  double[] res=new double[6];
	  CalculateFixStars pl=new CalculateFixStars();
	  String nameStar=",thSco,";
	  // Первый вариант расчета звезды - по входящей гражданской дате
	  res=pl.getFixStar(nameStar,16, 9,1964 , 6, 52); 
	    System.out.println(
		        " Звезда "+pl.starNameFinded+
		        "\n\tLongitude:          "+res[0]+
		        "\n\tLatitude:           "+res[1]+
		        "\n\tDistance:           "+res[2]+" AU"+
		        "\n\t                   ("+(res[2]*AU2km)+" km)"+
		        "\n\tLongitudinal speed: "+res[3]+" degs/day");
	    
	    // Второй  вариант расчета планет - по входящей юлианкской дате
	    SweDate sd=new SweDate(1964,9,16,6+52/60.);
	    
	     res=pl.getFixStarJD(nameStar,sd.getJulDay());
		    System.out.println(
		    		"Юлианская дата "+sd.getJulDay()+
			        " Звезда "+ pl.starNameFinded+
			        "\n\tLongitude:          "+res[0]+
			        "\n\tLatitude:           "+res[1]+
			        "\n\tDistance:           "+res[2]+" AU"+
			        "\n\t                   ("+(res[2]*AU2km)+" km)"+
			        "\n\tLongitudinal speed: "+res[3]+" degs/day");
			        	  
  }
}
