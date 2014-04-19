package ru.liveplanet.zt;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;



import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * Created by IntelliJ IDEA.
 * User: bashkirtsev                                                        A
 * Date: 01.11.2008
 * Time: 13:19:27
 * To change this template use File | Settings | File Templates.
 * �������� yes - ������� ����� ��������, ����� ������ - �� �� ������ - � ������� - �������
 */
public class Excel
{

     private String nameAttachFile;
    String cellValue;
    static int typeStrForAnalyz=0;   //��� ������������� ������
    int typeStrForAnalyzOld=0;   //��� ������������� ������
    static boolean metka=false;

     public Excel( String nameAttachFile) throws Exception
  {
     Capital cap=new Capital();

    POIFSFileSystem fs      =
    new POIFSFileSystem(new FileInputStream(nameAttachFile));
        HSSFWorkbook wb1 = new HSSFWorkbook(fs);

      PrintWriter out1 =
               new PrintWriter(
                 new BufferedWriter(
                   new FileWriter("xls\\ZTForCapitalWorld.txt")));

  for (int i0 = 0; i0 < wb1.getNumberOfSheets(); i0++) //���� �������� ������
  {
    System.out.println(" ***************����************ "  + i0+1);
              HSSFSheet sheet = wb1.getSheetAt(i0); // ������� ������
// ���������� �����
         System.out.println(" ������ ������ "     + sheet.getFirstRowNum());
         System.out.println(" ��������� ������ "  + sheet.getLastRowNum());
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

                   //}
               //System.out.println("$$$$$$$$$$$$ ���� ��� ������"+old);
               //System.out.println("$$$$$$$$$$$$ ������� ��� ������"+Excel.typeStrForAnalyz);
               //System.out.println("_______________________________________________������");

           }
              // ����� ������ ������ Capital � ��� ����������� ����
              //System.out.println("������: " +cap.stranaName+" " + "��� ������ " +cap.kodStrani+" ");
              System.out.println("������: " +cap.stranaName+" " + "�������: " +cap.capitalName);
              out1.println("������: " +cap.stranaName+" " + "�������: " +cap.capitalName);
              //System.out.println("��� ������ " +cap.kodStrani+" " );
              //System.out.println("�������: " +cap.capitalName+" " );
              //System.out.println("������: " +cap.geoSirota+" " +"�������: " +cap.geoDolgota+" "+ "������ � G20? " +cap.g20+" ");
              System.out.println("������: " +cap.geoSirota+" " +"�������: " +cap.geoDolgota+" ");//+ "������ � G20? " +cap.g20+" ");
              out1.println("������: " +cap.geoSirota+" " +"�������: " +cap.geoDolgota+" ");//+ "������ � G20? " +cap.g20+" ");
             // System.out.println("������� " +cap.geoDolgota+" " );
              //System.out.println("������ � G20? " +cap.g20+" " );

               int[] posFrac=new int[4];
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


              double eclipShir=geol.getEclShir();
               posFrac=geol.ztl.swe_split_deg1(zpoints[1],8);
              double minFrac=posFrac[1]+ (double)posFrac[2]/60;
                System.out.println(" ------- ���������� ------------");
              out1.println(" ------- ���������� ------------");
               if (metka)
              {
                  System.out.println ("|������������ "+ minFrac+"  "+
                                    geol.numToNameZodiak(posFrac[0]+1)+" |");
                 out1.println ("|������������ "+ minFrac+"  "+
                                    geol.numToNameZodiak(posFrac[0]+1)+" |");
                          //+"\t"+"������. ������"+((int)eclipShir+"��"+(Math.abs((int)((eclipShir-(int)eclipShir)*60)))+"���"+"\t"+"\n"));

                  posFrac=geol.ztl.swe_split_deg1(zpoints[0],8);
                  minFrac=posFrac[1]+ (double)posFrac[2]/60;
                  System.out.println   ("|��������� "+minFrac+"  "+
                                                                    geol.numToNameZodiak(posFrac[0]+1)+" |");
                 out1.println("|��������� "+minFrac+"  "+
                                                                    geol.numToNameZodiak(posFrac[0]+1)+" |");
                 System.out.println  (" -------------------------------");
                 out1.println  (" -------------------------------");


              }  else
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
               }
                       //+"\t"+((int)eclipShir+"��"+(Math.abs((int)((eclipShir-(int)eclipShir)*60)))+"���"+"\t"+"\n"));

          } //if   (sheet.getRow(i) !=null )





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

    private void jbInit() throws Exception {
  }

public static void main(String[] args) throws Exception
  {
      String metka=args[0];
      if (metka.equals("-yes"))
             Excel.metka=true; else
             Excel.metka=false;

    Excel ex=new Excel("xls\\CapitalWorld.xls");

      //Excel ex=new Excel("xls\\pav01012007-10102007.xls");
      //Excel ex=new Excel("xls\\il01012007-10102007.xls");
      //Excel ex=new Excel("xls\\Az01012007-10102007.xls");
      //Excel ex=new Excel("xls\\ly01012007-10102007.xls");

  }
}

