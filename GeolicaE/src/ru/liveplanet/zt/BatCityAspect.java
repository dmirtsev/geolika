package ru.liveplanet.zt;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: bashkirtsev                                                        A
 * Date: 01.11.2008
 * Time: 13:19:27
 * To change this template use File | Settings | File Templates.
//KvZC22.txt zc 9 10 90 G20.xls 1.1.1700 1.1.2000
//KvZC22.txt(��� ����) zc(ZT) 9(� �������)  90(������) G20.xls(�� ����) 1.1.1700(���� �) 1.1.2000(���� ��) * �� ������ - ��������� ����, ������ �������� �� ��� Swiss Epemer, � ���������� ������������� ���� ����� � �������
 * � ������ ���������� ���������� ��������� ������
 *
 */
public class BatCityAspect
{

     private String nameAttachFile;
    String cellValue;
    static int typeStrForAnalyz=0;   //��� ������������� ������
    int typeStrForAnalyzOld=0;   //��� ������������� ������
    static boolean metka=false;
    //CityAspect.txt zc 9 10 ��������������
    static String fileName="";
    static String zemleTochka="";
    static int numPlanet=0;
    static int numCycle=0;
    static int aspectForCity=0;
    static String inputFile="";

static String  startDate="";
    static String  endDate="";
    static int orb=0;
      //BatCityAspect.endDate=args[7];




     public BatCityAspect( String nameAttachFile) throws Exception
  {
     Capital cap=new Capital();

    POIFSFileSystem fs      =
    new POIFSFileSystem(new FileInputStream(nameAttachFile));
        HSSFWorkbook wb1 = new HSSFWorkbook(fs);

      PrintWriter out1 =
               new PrintWriter(
                 new BufferedWriter(
                   new FileWriter("xls\\rezult\\"+fileName)));

  for (int i0 = 0; i0 < wb1.getNumberOfSheets(); i0++) //���� �������� ������
  {
    System.out.println(" ***************����************ "  + i0+1);
              HSSFSheet sheet = wb1.getSheetAt(i0); // ������� ������
// ���������� �����
         System.out.println(" ������ ������ "     + sheet.getFirstRowNum());
         System.out.println(" ��������� ������ "  + sheet.getLastRowNum());
      out1.println  (" echo ------------- > "+ BatCityAspect.fileName);
      int old=0;
         for (int i = 0; i <= sheet.getLastRowNum(); i++) //���� �������� �����
         {
     //System.out.println(" ���. ������--> "+ i );
//System.out.println("    --- �"+ i +" ---" );
             if   (sheet.getRow(i) !=null ) // ��� ������ �������������� � �� ����� ��� ��� �� ����
          {

           HSSFRow row1 = sheet.getRow(i); // ������� �����
           for (int ii = 0; ii < row1.getLastCellNum(); ii++) //���� �������� �����
           {

                 if   (row1.getCell((short)ii) !=null )
                 {
                   HSSFCell cell1 = row1.getCell((short)ii); // ������� ����� � ������
                      cellValue=cell1.getStringCellValue();
                    switch (ii)
                    {
                        case 0:
                        {
                            //System.out.println("������ " +cellValue+" " );
                            cap.geoSirota=Double.valueOf(cellValue).doubleValue();
                        break;
                        }
                        case 1:
                        {
                             //System.out.println("������� "+ cellValue+" " );
                            cap.geoDolgota=Double.valueOf(cellValue).doubleValue();
                        break;
                        }
                        case 2:
                        {
                             //System.out.println("��� ������  "+ cellValue+" " );
                            cap.kodStrani=cellValue;
                        break;
                        }
                        case 3:
                        {
                             //System.out.println("�������  "+cellValue+" " );
                            cap.capitalName=cellValue;
                        break;
                        }
                        case 4:
                        {
                             //System.out.println("������ "+cellValue+" " );
                            cap.stranaName=cellValue;
                         break;
                        }
                        case 5:
                        {
                             //System.out.println(" ***G20*** "+cellValue+" " );
                            if (cellValue.equalsIgnoreCase("��"))  cap.g20=true;
                             else  cap.g20=false;
                         break;
                        }



                    }
                   //  System.out.println("������"+(ii-1)+" "+cellValue+" " );
                   /*
                   switch (cell1.getCellType())
                   {
                      case 1:
                     {
                      cellValue=cell1.getStringCellValue();
                      System.out.println("������"+(ii-1)+" "+cellValue+" " );
                       break;
                     }
                       case 3:
                       {
                        cellValue=cell1.getStringCellValue();
                        //System.out.print(" ������ ������--> "+cellValue );
                         break;
                       }
                       case 0:
                        cellValue=Double.toString(cell1.getNumericCellValue());
                        System.out.println("�����-->"+cellValue );
                     break;
                   }
                     */
                 //System.out.print("N : "+(ii-1)+"-" );
                     //old=typeStrForAnalyz;
                 }//if   (row1.getCell((short)ii) !=null )

               int nn=1;

                   }
               //System.out.println("$$$$$$$$$$$$ ���� ��� ������"+old);
               //System.out.println("$$$$$$$$$$$$ ������� ��� ������"+Excel.typeStrForAnalyz);
               //System.out.println("_______________________________________________������");

           //}
              // ����� ������ ������ Capital � ��� ����������� ����
              //System.out.println("������: " +cap.stranaName+" " + "��� ������ " +cap.kodStrani+" ");
             // System.out.println("������: " +cap.stranaName+" " + "�������: " +cap.capitalName);
              //out1.println("������: " +cap.stranaName+" " + "�������: " +cap.capitalName);
              //System.out.println("��� ������ " +cap.kodStrani+" " );
              //System.out.println("�������: " +cap.capitalName+" " );
              //System.out.println("������: " +cap.geoSirota+" " +"�������: " +cap.geoDolgota+" "+ "������ � G20? " +cap.g20+" ");
              //System.out.println("������: " +cap.geoSirota+" " +"�������: " +cap.geoDolgota+" ");//+ "������ � G20? " +cap.g20+" ");
              //out1.println("������: " +cap.geoSirota+" " +"�������: " +cap.geoDolgota+" ");//+ "������ � G20? " +cap.g20+" ");
             // System.out.println("������� " +cap.geoDolgota+" " );
              //System.out.println("������ � G20? " +cap.g20+" " );


               int[] posFrac=new int[4];
              int[] posFracZJav=new int[4];
              int[] posFracZCel=new int[4];
              double[] zpoints=new double[4] ;
               Geolika geol=new Geolika();
               geol.setLong(cap.geoDolgota);
                geol.setLat(cap.geoSirota);
                /*  ����� ���������
                ��������� " +zt[0]
                ������������ "+zt[1]
                ��������� "+zt[2] )
                �������� "+zt[3] )
                */
              // ������������� ������ ��������� �� ���������� ����
                NaclEclipt ne =new NaclEclipt(2009,11,1,2);
                //ne.ne(2009,11,1);
                zpoints=geol.getZT(ne.naklEcl);
              // ��� ������� ���������!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
              String namePlanet, nameAspect;
              namePlanet=ZTLib.PlanetFromNum(BatCityAspect.numPlanet);
              nameAspect=ZTLib.AspektFromNum(BatCityAspect.aspectForCity);
              // ����� ��� ������� ���������!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

              double eclipShir=geol.getEclShir();
               posFrac=geol.ztl.swe_split_deg1(zpoints[1],8);// ������������ ������
               posFracZJav=posFrac;
              double minFrac=posFrac[1]+ (double)posFrac[2]/60;
                //System.out.println(" ------- ���������� ------------");
              //out1.println(" ------- ���������� ------------");
               //if (metka)
              //{
               //   System.out.println ("|������������ "+ minFrac+"  "+
               //                     geol.numToNameZodiak(posFrac[0]+1)+" |");
                 //out1.println ("|������������ "+ minFrac+"  "+
                 //                   geol.numToNameZodiak(posFrac[0]+1)+" |");
                          //+"\t"+"������. ������"+((int)eclipShir+"��"+(Math.abs((int)((eclipShir-(int)eclipShir)*60)))+"���"+"\t"+"\n"));

                  posFrac=geol.ztl.swe_split_deg1(zpoints[0],8);//��������� ������
                  posFracZCel=posFrac;
               //   double[] zt = new double[4];
                 // zt=ZTLib.fracAbsTZT(posFracZJav,posFracZCel);


                  minFrac=posFrac[1]+ (double)posFrac[2]/60;
                  //System.out.println   ("|��������� "+minFrac+"  "+
                                                                    //geol.numToNameZodiak(posFrac[0]+1)+" |");
                 //out1.println("|��������� "+minFrac+"  "+
                                                                    //geol.numToNameZodiak(posFrac[0]+1)+" |");
                 //System.out.println  (" -------------------------------");

                 //out1.println  (" -------------------------------");
              //out1.println  (" -------------------------------");
              
              double[] zt = new double[4];
              zt=ZTLib.fracAbsTZT(posFracZJav,posFracZCel); //���������� ������, ��� 0-��,1-��,2-��,3-��

              //out1.println  (" �� ="+zt[0]);
              //out1.println  (" �� ="+zt[1]);
              //out1.println  (" �� ="+zt[2]);
              //out1.println  (" �� ="+zt[3]);
              String tipZT;

              tipZT=ZTLib.tipZT(BatCityAspect.zemleTochka); // ���������� �� ������������ ���������� �� �������  "zj"="������������"

              //out1.println  (" ��� ���������� - "+tipZT);
              //System.out.println  (" ��� ���������� - "+tipZT);

              double[] ztAsp = new double[2];
              //�������� ��� ����������
              double typeZT=0;
              if (BatCityAspect.zemleTochka.equalsIgnoreCase("zj")) typeZT=zt[0]; else
              if (BatCityAspect.zemleTochka.equalsIgnoreCase("zc")) typeZT=zt[1]; else
              if (BatCityAspect.zemleTochka.equalsIgnoreCase("zt")) typeZT=zt[2]; else
              if (BatCityAspect.zemleTochka.equalsIgnoreCase("zb")) typeZT=zt[3];

                 // ������ ������� ��������� � ������ ������� - 2 ����� ���� ������ �� = 0 ��� 180
              ztAsp=ZTLib.returnAspect(BatCityAspect.zemleTochka,typeZT,BatCityAspect.aspectForCity);

              //out1.println  (" ���������� � ������ ������� ������- " + ztAsp[0]+"  ������- "+ ztAsp[1]);
              //System.out.println  (" ���������� � ������ ������� ������- " + ztAsp[0]+"  ������- "+ ztAsp[1]);

             //out1.println  (" echo ------------- > "+ BatCityAspect.fileName);
             // echo ������� ������� � ������������ ������� �������������� >> PlKvZyaLondon.txt ��������
             out1.println  ("echo ***************************** >> "+ BatCityAspect.fileName);
             out1.println  (" echo "+ nameAspect+"  "+ namePlanet+" "+ tipZT+" "+typeZT +".��  "+cap.capitalName+" >> "+BatCityAspect.fileName );
             out1.println  ("echo ***************************** >> "+ BatCityAspect.fileName);

              if (BatCityAspect.orb==0)
              {
              out1.println  (" echo ��� ����------------- >> "+ BatCityAspect.fileName);
              if (ztAsp[1]!=1000)
              {
              out1.println  ("echo #####  ������������ ������ >> "+ BatCityAspect.fileName);
              out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                      ztAsp[0]+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);
                  out1.println  ("echo #####  ����������� ������ >> "+ BatCityAspect.fileName);
              out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                       ztAsp[1]+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);

              } else
              out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                          ztAsp[0]+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);
              }
              else
              {
                  out1.println  ("echo #####  ���� ���!!! >> "+ BatCityAspect.fileName);

                  if (ztAsp[1]!=1000)
                  {
                  out1.println  ("echo #####  ������������ ������ ����� ��� " + BatCityAspect.orb+" ����. >> "+ BatCityAspect.fileName);
                  out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                         ZTLib.diap360(ztAsp[0]-BatCityAspect.orb)+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);

                   out1.println  ("echo #####  C��������� ������ ����� ��� " + BatCityAspect.orb+" ����. >> "+ BatCityAspect.fileName);
                   out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                          ZTLib.diap360(ztAsp[1]-BatCityAspect.orb)+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);

                      out1.println  ("echo #####  ������������ ������ ������  >> "+ BatCityAspect.fileName);
                      out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                             ZTLib.diap360(ztAsp[0])+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);

                       out1.println  ("echo #####  C��������� ������ ������ >> "+ BatCityAspect.fileName);
                       out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                              ZTLib.diap360(ztAsp[1])+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);

                      out1.println  ("echo #####  ������������ ������ ���� ��� " + BatCityAspect.orb+" ����. >> "+ BatCityAspect.fileName);
                      out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                             ZTLib.diap360(ztAsp[0]+BatCityAspect.orb)+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);

                       out1.println  ("echo #####  C��������� ������ ���� ��� " + BatCityAspect.orb+" ����. >> "+ BatCityAspect.fileName);
                       out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                              ZTLib.diap360(ztAsp[1]+BatCityAspect.orb)+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);




                  } else
                   out1.println  ("echo #####  ������ ����� ��� " + BatCityAspect.orb+" ����. >> "+ BatCityAspect.fileName);
                  out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                             ZTLib.diap360(ztAsp[0]-BatCityAspect.orb)+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);
                  out1.println  ("echo #####  ������ ������  >> "+ BatCityAspect.fileName);
                  out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                              ztAsp[0]+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);
                  out1.println  ("echo #####  ������ ���� ��� " + BatCityAspect.orb+" ����. >> "+ BatCityAspect.fileName);
                  out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                              ZTLib.diap360(ztAsp[0]+BatCityAspect.orb)+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);


              }

        //java Transits -p9 -lon74.08333333333334 -b1.1.1800 -b1.1.2050   -utnov -oloc > Kv.txt
              //java Transits -p9������������ -lon1.72���������� -btoday -r -n12�������������������� -utnow -oloc >> PlKvZyaLondon.txt ��������


              //}
              /*else
               {
               System.out.println ("|������������ "+ posFrac[1]+" ��."+posFrac[2]+" ���. "+
                                 geol.numToNameZodiak(posFrac[0]+1)+" |");
              out1.println ("|������������ "+ posFrac[1]+" ��."+posFrac[2]+" ���. "+
                                 geol.numToNameZodiak(posFrac[0]+1)+" |");
                       //+"\t"+"������. ������"+((int)eclipShir+"��"+(Math.abs((int)((eclipShir-(int)eclipShir)*60)))+"���"+"\t"+"\n"));

               posFrac=geol.ztl.swe_split_deg1(zpoints[0],8);
               System.out.println   ("|��������� "+posFrac[1]+" ��."+posFrac[2]+" ���.    "+
                                                                 geol.numToNameZodiak(posFrac[0]+1)+" |");
              out1.println("|��������� "+posFrac[1]+" ��."+posFrac[2]+" ���.    "+
                                                                 geol.numToNameZodiak(posFrac[0]+1)+" |");
              System.out.println  (" -------------------------------");
              out1.println  (" -------------------------------");


                       //+"\t"+((int)eclipShir+"��"+(Math.abs((int)((eclipShir-(int)eclipShir)*60)))+"���"+"\t"+"\n"));
               }
          } //if   (sheet.getRow(i) !=null )

           */



         }//for (int i = 0; i < sheet.getLastRowNum(); i++) //���� �������� �����
  } //for (int i0 = 0; i0 < wb1.getNumberOfSheets(); i0++) //���� �������� ������
      out1.close();
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  }
    private void jbInit() throws Exception {
  }

public static void main(String[] args) throws Exception
  {
      //KvZC22.txt zc 9 10 90 G20.xls 1.1.1700 1.1.2000
      //KvZC22.txt(��� ����) zc(ZT) 9(� �������)  90(�����) G20.xls(�� ����) 1.1.1700(���� �) 1.1.2000(���� ��)
      BatCityAspect.fileName=args[0];
      BatCityAspect.zemleTochka=args[1];
      BatCityAspect.numPlanet=Integer.parseInt(args[2]);
      //BatCityAspect.numCycle=Integer.parseInt(args[3]);
      BatCityAspect.aspectForCity=Integer.parseInt(args[3]);
      BatCityAspect.inputFile=args[4];
      BatCityAspect.startDate=args[5];
      BatCityAspect.endDate=args[6];

      if (args.length>7) BatCityAspect.orb=Integer.parseInt(args[7]);


    BatCityAspect ex=new BatCityAspect ("xls\\"+inputFile);

      //Excel ex=new Excel("xls\\pav0101200.7-10102007.xls");
      //Excel ex=new Excel("xls\\il01012007-10102007.xls");
      //Excel ex=new Excel("xls\\Az01012007-10102007.xls");
      //Excel ex=new Excel("xls\\ly01012007-10102007.xls");

  }
}

