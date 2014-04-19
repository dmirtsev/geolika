package ru.liveplanet.zt2014;

import swisseph.SweConst;
import swisseph.SweDate;
import swisseph.SwissEph;

/**
 * Рассчет планет, астероидов, фиктивных планет, список в конце
 * Для расчета доп. астероидов, см файл seasnam.txt , где указаны номера и названия, например 001709  Ukraina
 * @author dbashkirtsev
 *
 */
public class CalculatePlanet {
  static final double AU2km=SweConst.AUNIT/1000;
  SwissEph sw=new SwissEph();
  /**
   * Расчет координаты планеты по входящей гражданской  дате
   * @param idPlanet
   * @param day
   * @param month
   * @param year
   * @param hour
   * @param min
   * @param dolg
   * @param shir
   * @return
   */
  
  public double[] getPlanet(int idPlanet,int day, int month, int year,int hour, int min ) 
  {
	
	    SweDate sd=new SweDate(year,month,day,hour+min/60.);

	  double[] res=new double[6];
	    StringBuffer sbErr=new StringBuffer();
	    //int flags = SweConst.SEFLG_SPEED |
	      //          SweConst.SEFLG_TOPOCTR;
	    int flags = SweConst.SEFLG_SPEED ;

	    // Calculate for some place in India:
	    //sw.swe_set_topo(dolg,shir,200);

	    int rc=sw.swe_calc_ut(sd.getJulDay(),
	                          idPlanet,
	                          flags,
	                          res,
	                          sbErr);

	    if (sbErr.length()>0) {
	      System.out.println(sbErr.toString());
	    }
	   // if (rc==SweConst.ERR) {
	     // System.exit(1);
	   // }
	  return res;
	
}
/**
 * Расчет координаты планеты по входящей юлианской дате
 * @param idPlanet
 * @param juliDate
 * @param dolg
 * @param shir
 * @return
 */
  public double[] getPlanetJD(int idPlanet, double juliDate) 
  {
	
	    //SweDate sd=new SweDate(year,month,day,hour+min/60.);

	  double[] res=new double[6];
	    StringBuffer sbErr=new StringBuffer();
	    //int flags = SweConst.SEFLG_SPEED |\
	      //          SweConst.SEFLG_TOPOCTR;
	    int flags = SweConst.SEFLG_SPEED ;
	    // Calculate for some place in India:
	    //sw.swe_set_topo(dolg,shir,200);

	    int rc=sw.swe_calc_ut(juliDate,
	                          idPlanet,
	                          flags,
	                          res,
	                          sbErr);

	    if (sbErr.length()>0) {
	      System.out.println(sbErr.toString());
	    }
	   // if (rc==SweConst.ERR) {
	     // System.exit(1);
	    //}
	  return res;
	
}

  public static void main(String[] p) 
  {
	  double[] res=new double[6];
	  SwissEph sw=new SwissEph();
	  CalculatePlanet pl=new CalculatePlanet();
	  int idPlanet=SweConst.SE_MARS;
	  // Первый вариант расчета планет - по входящей гражданской дате
	  res=pl.getPlanet(idPlanet, 16, 9,1964 , 6, 52);
	    System.out.println(
		        " Планета "+sw.swe_get_planet_name(idPlanet)+":"+
		        "\n\tLongitude:          "+res[0]+
		        "\n\tLatitude:           "+res[1]+
		        "\n\tDistance:           "+res[2]+" AU"+
		        "\n\t                   ("+(res[2]*AU2km)+" km)"+
		        "\n\tLongitudinal speed: "+res[3]+" degs/day");	  
		  // Второй  вариант расчета планет - по входящей юлианкской дате
	    SweDate sd=new SweDate(1964,9,16,6+52/60.);  
	  res=pl.getPlanetJD(idPlanet,sd.getJulDay());
		    System.out.println(
		    		"Юлианская дата "+sd.getJulDay()+
			        " Планета "+sw.swe_get_planet_name(idPlanet)+":"+
			        "\n\tLongitude:          "+res[0]+
			        "\n\tLatitude:           "+res[1]+
			        "\n\tDistance:           "+res[2]+" AU"+
			        "\n\t                   ("+(res[2]*AU2km)+" km)"+
			        "\n\tLongitudinal speed: "+res[3]+" degs/day");	  
		    
// Пример рассчета малого астероида   Ukraina, международный номер 1709, SweConst.SE_AST_OFFSET =1000
		    // Список с номерами см в файле seasnam.txt 
		    int idAsteriod=SweConst.SE_AST_OFFSET+1709;
		      
			  res=pl.getPlanetJD(idAsteriod,sd.getJulDay());
				    System.out.println(
				    		
				    		"\n\tЮлианская дата "+sd.getJulDay()+
				    		
					        "\n\t Астероид "+sw.swe_get_planet_name(idAsteriod)+":"+
					        "\n\tLongitude:          "+res[0]+
					        "\n\tLatitude:           "+res[1]+
					        "\n\tDistance:           "+res[2]+" AU"+
					        "\n\t                   ("+(res[2]*AU2km)+" km)"+
					        "\n\tLongitudinal speed: "+res[3]+" degs/day");	  

	    
	  
  }
}

/*
 * SE_ECL_NUT     -1     
SE_SUN               0      
SE_MOON              1      
SE_MERCURY           2      
SE_VENUS             3      
SE_MARS              4      
SE_JUPITER           5      
SE_SATURN            6      
SE_URANUS            7      
SE_NEPTUNE           8      
SE_PLUTO             9      
SE_MEAN_NODE         10     
SE_TRUE_NODE         11
SE_MEAN_APOG         12     
SE_OSCU_APOG         13   
SE_EARTH             14
SE_CHIRON            15
SE_PHOLUS            16
SE_CERES             17
SE_PALLAS            18
SE_JUNO              19
SE_VESTA             20
SE_INTP_APOG             21
SE_INTP_PERG             22
 
SE_NPLANETS             23
SE_FICT_OFFSET       40
SE_NFICT_ELEM                15
 
* Hamburger or Uranian "planets" *
SE_CUPIDO            40
SE_HADES             41
SE_ZEUS              42
SE_KRONOS            43
SE_APOLLON           44
SE_ADMETOS           45
SE_VULKANUS          46
SE_POSEIDON          47
 
/* other fictitious bodies *
SE_ISIS              48
SE_NIBIRU            49
SE_HARRINGTON           50
SE_NEPTUNE_LEVERRIER      51
SE_NEPTUNE_ADAMS         52
SE_PLUTO_LOWELL          53
SE_PLUTO_PICKERING        54
 
SE_AST_OFFSET     10000
  */
