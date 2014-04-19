package ru.liveplanet.zt;

import swisseph.SweConst;
import swisseph.SwissEph;

/**
 * Created by IntelliJ IDEA.
 * User: DBashkirtsev
 * Date: 20.10.2009
 * Time: 9:01:29
 * To change this template use File | Settings | File Templates.
 */

// ���������� ���� �������� se00000s.se1, ��� 00000 - ����� ��������� ������� ��������� ���������
    // ���������� ����������� �������� 1000 ����������, ������ �� ������ 20000.
    // �� FTP Swiss ��������� � �������, �������� ast0, �������� ��������� � ������ 0 �� 999
    // ast1, �������� ��������� � ������ 1000 �� 1999
    // ��� ������������� ����� ����� �������� ��������� �� ��� ��������� ������
    // ���������� ������ ���������� � �������� ����� http://www.minorplanetcenter.org/iau/lists/NumberedMPs.html
    // ����� ���������� �� �������� ����� http://www.minorplanetcenter.org/iau/lists/MPNames.html
    // ������ �� 21 ������ 2011 � �������� "C:\Users\�����\Documents\GEO\Projects Java\Geolika\zt\numberAndListAsteroids.txt"
                                   
public class TestAsteroiud {
      public SwissEph sw;
      final double AU2km=SweConst.AUNIT/1000;

    void tp()
    {
        SwissEph sw=new SwissEph();
        double [] pl =new double[2225];
          double[] xx=new double[6];
        double[] xx1=new double[6];
        double[] xx2=new double[6];
        StringBuffer sbErr=new StringBuffer();
        //sbStar=i+"";

        //int num=SweConst.SE_AST_OFFSET+4;
        int num=SweConst.SE_AST_OFFSET+4;
        int realNumAsteroid=0;

 //for (int i=num;i<=20250;i++)
        for (int i=num;i<=13000;i++)  // ����������� � i<= ������ ����� �� �������� ���������+10000 
 {
     //StringBuffer sbStar=new StringBuffer(i+"");
          try
          {
        int rc=sw.swe_calc_ut(2488000.5,
                              i,
                              SweConst.SEFLG_SPEED,    //SEFLG_EQUATORIAL
                              xx,
                              sbErr);
       int rc1=sw.swe_calc_ut(2488000.5,
                              i,
                              SweConst.SEFLG_EQUATORIAL,    //
                              xx1,
                              sbErr);


         realNumAsteroid=i-SweConst.SE_AST_OFFSET; // �������� ����� ��������� � �������� ����������

        System.out.println(
            sw.swe_get_planet_name(i)+": "+realNumAsteroid+
            "\tLongitude:          "+xx[0]+
           "\n\tLatitude:           "+xx[1]+
            "\n\tDistance:           "+xx[2]+" AU"+
            "\n\t                   ("+(xx[2]*AU2km)+" km)"+
            "\n\tLongitudinal speed: "+xx[3]+" degs/day");

   //  if ((rc==SweConst.ERR)||(rc1==SweConst.ERR)) {
     //    System.exit(0);
       //  System.out.println("__________________________________________________________");
         //continue;
  //   }


   //  else
     //{

       // pl[i-10001]=xx[0];

     //}
          }
            catch (Exception e)
              {
                System.out.println("������ ��� ����� ��� ��������� "+realNumAsteroid);
              }

    }
    }

    public static void main (String argStr[])
    {
       TestAsteroiud tp = new TestAsteroiud();
        tp.tp();
    }
}
