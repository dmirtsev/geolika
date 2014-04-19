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
 * Пареметр yes - дробные части градусов, любой другой - но не пустой - в гадусах - минутах
 */
public class Excel
{

     private String nameAttachFile;
    String cellValue;
    static int typeStrForAnalyz=0;   //тип анализируемой строки
    int typeStrForAnalyzOld=0;   //тип анализируемой строки
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

  for (int i0 = 0; i0 < wb1.getNumberOfSheets(); i0++) //цикл перебора листов
  {
    System.out.println(" ***************Лист************ "  + i0+1);
              HSSFSheet sheet = wb1.getSheetAt(i0); // перебор листов
// Количество строк
         System.out.println(" Первая строка "     + sheet.getFirstRowNum());
         System.out.println(" Последняя строка "  + sheet.getLastRowNum());
      int old=0;
         for (int i = 0; i <= sheet.getLastRowNum(); i++) //цикл перебора строк
         {
     //System.out.println(" Нач. строки--> "+ i );
//System.out.println("    --- №"+ i +" ---" );
             if   (sheet.getRow(i) !=null ) // или строка информационная а не шапка или что то иное
          {

           HSSFRow row1 = sheet.getRow(i); // перебор строк
           for (int ii = 0; ii < row1.getLastCellNum(); ii++) //цикл перебора ячеек
           {

                 if   (row1.getCell((short)ii) !=null )
                 {
                   HSSFCell cell1 = row1.getCell((short)ii); // перебор ячеек в строке
                      cellValue=cell1.getStringCellValue();
                    switch (ii)
                    {
                        case 0:
                        {
                            //System.out.println("Широта " +cellValue+" " );
                            cap.geoSirota=Double.valueOf(cellValue).doubleValue();
                        break;
                        }
                        case 1:
                        {
                             //System.out.println("Долгота "+ cellValue+" " );
                            cap.geoDolgota=Double.valueOf(cellValue).doubleValue();
                        break;
                        }
                        case 2:
                        {
                             //System.out.println("Код страны  "+ cellValue+" " );
                            cap.kodStrani=cellValue;
                        break;
                        }
                        case 3:
                        {
                             //System.out.println("Столица  "+cellValue+" " );
                            cap.capitalName=cellValue;
                        break;
                        }
                        case 4:
                        {
                             //System.out.println("Страна "+cellValue+" " );
                            cap.stranaName=cellValue;
                         break;
                        }
                        case 5:
                        {
                             //System.out.println(" ***G20*** "+cellValue+" " );
                            if (cellValue.equalsIgnoreCase("Да"))  cap.g20=true;
                             else  cap.g20=false;
                         break;
                        }



                    }
                   //  System.out.println("Широта"+(ii-1)+" "+cellValue+" " );
                   /*
                   switch (cell1.getCellType())
                   {
                      case 1:
                     {
                      cellValue=cell1.getStringCellValue();
                      System.out.println("Широта"+(ii-1)+" "+cellValue+" " );
                       break;
                     }
                       case 3:
                       {
                        cellValue=cell1.getStringCellValue();
                        //System.out.print(" Пустая ячейка--> "+cellValue );
                         break;
                       }
                       case 0:
                        cellValue=Double.toString(cell1.getNumericCellValue());
                        System.out.println("Число-->"+cellValue );
                     break;
                   }
                     */
                 //System.out.print("N : "+(ii-1)+"-" );
                     //old=typeStrForAnalyz;
                 }//if   (row1.getCell((short)ii) !=null )

               int nn=1;

                   //}
               //System.out.println("$$$$$$$$$$$$ Пред тип данных"+old);
               //System.out.println("$$$$$$$$$$$$ Текущий тип данных"+Excel.typeStrForAnalyz);
               //System.out.println("_______________________________________________Ячейка");

           }
              // Здесь имется объект Capital и его заполненные поля
              //System.out.println("Страна: " +cap.stranaName+" " + "Код страны " +cap.kodStrani+" ");
              System.out.println("Страна: " +cap.stranaName+" " + "Столица: " +cap.capitalName);
              out1.println("Страна: " +cap.stranaName+" " + "Столица: " +cap.capitalName);
              //System.out.println("Код страны " +cap.kodStrani+" " );
              //System.out.println("Столица: " +cap.capitalName+" " );
              //System.out.println("Широта: " +cap.geoSirota+" " +"Долгота: " +cap.geoDolgota+" "+ "Входит в G20? " +cap.g20+" ");
              System.out.println("Широта: " +cap.geoSirota+" " +"Долгота: " +cap.geoDolgota+" ");//+ "Входит в G20? " +cap.g20+" ");
              out1.println("Широта: " +cap.geoSirota+" " +"Долгота: " +cap.geoDolgota+" ");//+ "Входит в G20? " +cap.g20+" ");
             // System.out.println("Долгота " +cap.geoDolgota+" " );
              //System.out.println("Входит в G20? " +cap.g20+" " );

               int[] posFrac=new int[4];
              double[] zpoints=new double[4] ;
               Geolika geol=new Geolika();
               geol.setLong(cap.geoDolgota);
                geol.setLat(cap.geoSirota);
                /*  Далее извлекаем
                Землецель " +zt[0] 
                Землеявность "+zt[1]
                Землебаза "+zt[2] )
                Землетыл "+zt[3] )
                */
              // Расчитывается наклон эклиптики на конкретную дату
                NaclEclipt ne =new NaclEclipt(2009,11,1,2);
                //ne.ne(2009,11,1);
                zpoints=geol.getZT(ne.naklEcl);


              double eclipShir=geol.getEclShir();
               posFrac=geol.ztl.swe_split_deg1(zpoints[1],8);
              double minFrac=posFrac[1]+ (double)posFrac[2]/60;
                System.out.println(" ------- ЗемлеТочки ------------");
              out1.println(" ------- ЗемлеТочки ------------");
               if (metka)
              {
                  System.out.println ("|ЗемлеЯвность "+ minFrac+"  "+
                                    geol.numToNameZodiak(posFrac[0]+1)+" |");
                 out1.println ("|ЗемлеЯвность "+ minFrac+"  "+
                                    geol.numToNameZodiak(posFrac[0]+1)+" |");
                          //+"\t"+"Эклипт. широта"+((int)eclipShir+"гр"+(Math.abs((int)((eclipShir-(int)eclipShir)*60)))+"мин"+"\t"+"\n"));

                  posFrac=geol.ztl.swe_split_deg1(zpoints[0],8);
                  minFrac=posFrac[1]+ (double)posFrac[2]/60;
                  System.out.println   ("|ЗемлеЦель "+minFrac+"  "+
                                                                    geol.numToNameZodiak(posFrac[0]+1)+" |");
                 out1.println("|ЗемлеЦель "+minFrac+"  "+
                                                                    geol.numToNameZodiak(posFrac[0]+1)+" |");
                 System.out.println  (" -------------------------------");
                 out1.println  (" -------------------------------");


              }  else
               {
               System.out.println ("|ЗемлеЯвность "+ posFrac[1]+" гр."+posFrac[2]+" мин. "+
                                 geol.numToNameZodiak(posFrac[0]+1)+" |");
              out1.println ("|ЗемлеЯвность "+ posFrac[1]+" гр."+posFrac[2]+" мин. "+
                                 geol.numToNameZodiak(posFrac[0]+1)+" |");
                       //+"\t"+"Эклипт. широта"+((int)eclipShir+"гр"+(Math.abs((int)((eclipShir-(int)eclipShir)*60)))+"мин"+"\t"+"\n"));

               posFrac=geol.ztl.swe_split_deg1(zpoints[0],8);
               System.out.println   ("|ЗемлеЦель "+posFrac[1]+" гр."+posFrac[2]+" мин.    "+
                                                                 geol.numToNameZodiak(posFrac[0]+1)+" |");
              out1.println("|ЗемлеЦель "+posFrac[1]+" гр."+posFrac[2]+" мин.    "+
                                                                 geol.numToNameZodiak(posFrac[0]+1)+" |");
              System.out.println  (" -------------------------------");
              out1.println  (" -------------------------------");
               }
                       //+"\t"+((int)eclipShir+"гр"+(Math.abs((int)((eclipShir-(int)eclipShir)*60)))+"мин"+"\t"+"\n"));

          } //if   (sheet.getRow(i) !=null )





         }//for (int i = 0; i < sheet.getLastRowNum(); i++) //цикл перебора строк
  } //for (int i0 = 0; i0 < wb1.getNumberOfSheets(); i0++) //цикл перебора листов
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

