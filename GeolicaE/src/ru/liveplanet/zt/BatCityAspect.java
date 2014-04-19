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
//KvZC22.txt(вых файл) zc(ZT) 9(№ Планеты)  90(Аспект) G20.xls(Вх файл) 1.1.1700(дата с) 1.1.2000(даьа по) * На выходе - текстовый файл, кторый запскают из под Swiss Epemer, у резильтате расчитываются даты когда в городах
 * к данной землеточке происходил указанный аспект
 *
 */
public class BatCityAspect
{

     private String nameAttachFile;
    String cellValue;
    static int typeStrForAnalyz=0;   //тип анализируемой строки
    int typeStrForAnalyzOld=0;   //тип анализируемой строки
    static boolean metka=false;
    //CityAspect.txt zc 9 10 КвадратПлутона
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

  for (int i0 = 0; i0 < wb1.getNumberOfSheets(); i0++) //цикл перебора листов
  {
    System.out.println(" ***************Лист************ "  + i0+1);
              HSSFSheet sheet = wb1.getSheetAt(i0); // перебор листов
// Количество строк
         System.out.println(" Первая строка "     + sheet.getFirstRowNum());
         System.out.println(" Последняя строка "  + sheet.getLastRowNum());
      out1.println  (" echo ------------- > "+ BatCityAspect.fileName);
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

                   }
               //System.out.println("$$$$$$$$$$$$ Пред тип данных"+old);
               //System.out.println("$$$$$$$$$$$$ Текущий тип данных"+Excel.typeStrForAnalyz);
               //System.out.println("_______________________________________________Ячейка");

           //}
              // Здесь имется объект Capital и его заполненные поля
              //System.out.println("Страна: " +cap.stranaName+" " + "Код страны " +cap.kodStrani+" ");
             // System.out.println("Страна: " +cap.stranaName+" " + "Столица: " +cap.capitalName);
              //out1.println("Страна: " +cap.stranaName+" " + "Столица: " +cap.capitalName);
              //System.out.println("Код страны " +cap.kodStrani+" " );
              //System.out.println("Столица: " +cap.capitalName+" " );
              //System.out.println("Широта: " +cap.geoSirota+" " +"Долгота: " +cap.geoDolgota+" "+ "Входит в G20? " +cap.g20+" ");
              //System.out.println("Широта: " +cap.geoSirota+" " +"Долгота: " +cap.geoDolgota+" ");//+ "Входит в G20? " +cap.g20+" ");
              //out1.println("Широта: " +cap.geoSirota+" " +"Долгота: " +cap.geoDolgota+" ");//+ "Входит в G20? " +cap.g20+" ");
             // System.out.println("Долгота " +cap.geoDolgota+" " );
              //System.out.println("Входит в G20? " +cap.g20+" " );


               int[] posFrac=new int[4];
              int[] posFracZJav=new int[4];
              int[] posFracZCel=new int[4];
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
              // Эту функцию применить!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
              String namePlanet, nameAspect;
              namePlanet=ZTLib.PlanetFromNum(BatCityAspect.numPlanet);
              nameAspect=ZTLib.AspektFromNum(BatCityAspect.aspectForCity);
              // Конец Эту функцию применить!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

              double eclipShir=geol.getEclShir();
               posFrac=geol.ztl.swe_split_deg1(zpoints[1],8);// ЗемлеЯвность массив
               posFracZJav=posFrac;
              double minFrac=posFrac[1]+ (double)posFrac[2]/60;
                //System.out.println(" ------- ЗемлеТочки ------------");
              //out1.println(" ------- ЗемлеТочки ------------");
               //if (metka)
              //{
               //   System.out.println ("|ЗемлеЯвность "+ minFrac+"  "+
               //                     geol.numToNameZodiak(posFrac[0]+1)+" |");
                 //out1.println ("|ЗемлеЯвность "+ minFrac+"  "+
                 //                   geol.numToNameZodiak(posFrac[0]+1)+" |");
                          //+"\t"+"Эклипт. широта"+((int)eclipShir+"гр"+(Math.abs((int)((eclipShir-(int)eclipShir)*60)))+"мин"+"\t"+"\n"));

                  posFrac=geol.ztl.swe_split_deg1(zpoints[0],8);//ЗемлеЦель Массив
                  posFracZCel=posFrac;
               //   double[] zt = new double[4];
                 // zt=ZTLib.fracAbsTZT(posFracZJav,posFracZCel);


                  minFrac=posFrac[1]+ (double)posFrac[2]/60;
                  //System.out.println   ("|ЗемлеЦель "+minFrac+"  "+
                                                                    //geol.numToNameZodiak(posFrac[0]+1)+" |");
                 //out1.println("|ЗемлеЦель "+minFrac+"  "+
                                                                    //geol.numToNameZodiak(posFrac[0]+1)+" |");
                 //System.out.println  (" -------------------------------");

                 //out1.println  (" -------------------------------");
              //out1.println  (" -------------------------------");
              
              double[] zt = new double[4];
              zt=ZTLib.fracAbsTZT(posFracZJav,posFracZCel); //Возвращает массив, где 0-ЗЯ,1-Зц,2-ЗТ,3-ЗБ

              //out1.println  (" ЗЯ ="+zt[0]);
              //out1.println  (" ЗЦ ="+zt[1]);
              //out1.println  (" ЗТ ="+zt[2]);
              //out1.println  (" ЗБ ="+zt[3]);
              String tipZT;

              tipZT=ZTLib.tipZT(BatCityAspect.zemleTochka); // Определяет по аббревиатуре ЗемлеТОчки на рууском  "zj"="ЗемлеЯвность"

              //out1.println  (" Тип ЗемлеТочки - "+tipZT);
              //System.out.println  (" Тип ЗемлеТочки - "+tipZT);

              double[] ztAsp = new double[2];
              //выбираем тип ЗемлеТочки
              double typeZT=0;
              if (BatCityAspect.zemleTochka.equalsIgnoreCase("zj")) typeZT=zt[0]; else
              if (BatCityAspect.zemleTochka.equalsIgnoreCase("zc")) typeZT=zt[1]; else
              if (BatCityAspect.zemleTochka.equalsIgnoreCase("zt")) typeZT=zt[2]; else
              if (BatCityAspect.zemleTochka.equalsIgnoreCase("zb")) typeZT=zt[3];

                 // расчет массива координат с учетом аспекта - 2 коорд если аспект не = 0 или 180
              ztAsp=ZTLib.returnAspect(BatCityAspect.zemleTochka,typeZT,BatCityAspect.aspectForCity);

              //out1.println  (" Координаты с учетом аспекта Первый- " + ztAsp[0]+"  Второй- "+ ztAsp[1]);
              //System.out.println  (" Координаты с учетом аспекта Первый- " + ztAsp[0]+"  Второй- "+ ztAsp[1]);

             //out1.println  (" echo ------------- > "+ BatCityAspect.fileName);
             // echo Квадрат Плутона к ЗемлеЯвности Лондона АспектКЗТМеста >> PlKvZyaLondon.txt ИмяФайла
             out1.println  ("echo ***************************** >> "+ BatCityAspect.fileName);
             out1.println  (" echo "+ nameAspect+"  "+ namePlanet+" "+ tipZT+" "+typeZT +".гр  "+cap.capitalName+" >> "+BatCityAspect.fileName );
             out1.println  ("echo ***************************** >> "+ BatCityAspect.fileName);

              if (BatCityAspect.orb==0)
              {
              out1.println  (" echo Нет орба------------- >> "+ BatCityAspect.fileName);
              if (ztAsp[1]!=1000)
              {
              out1.println  ("echo #####  Расходящийся аспект >> "+ BatCityAspect.fileName);
              out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                      ztAsp[0]+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);
                  out1.println  ("echo #####  Схоодящийся аспект >> "+ BatCityAspect.fileName);
              out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                       ztAsp[1]+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);

              } else
              out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                          ztAsp[0]+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);
              }
              else
              {
                  out1.println  ("echo #####  Есть орб!!! >> "+ BatCityAspect.fileName);

                  if (ztAsp[1]!=1000)
                  {
                  out1.println  ("echo #####  Расходящийся аспект минус орб " + BatCityAspect.orb+" град. >> "+ BatCityAspect.fileName);
                  out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                         ZTLib.diap360(ztAsp[0]-BatCityAspect.orb)+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);

                   out1.println  ("echo #####  Cходящийся аспект минус орб " + BatCityAspect.orb+" град. >> "+ BatCityAspect.fileName);
                   out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                          ZTLib.diap360(ztAsp[1]-BatCityAspect.orb)+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);

                      out1.println  ("echo #####  Расходящийся аспект точный  >> "+ BatCityAspect.fileName);
                      out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                             ZTLib.diap360(ztAsp[0])+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);

                       out1.println  ("echo #####  Cходящийся аспект точный >> "+ BatCityAspect.fileName);
                       out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                              ZTLib.diap360(ztAsp[1])+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);

                      out1.println  ("echo #####  Расходящийся аспект плюс орб " + BatCityAspect.orb+" град. >> "+ BatCityAspect.fileName);
                      out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                             ZTLib.diap360(ztAsp[0]+BatCityAspect.orb)+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);

                       out1.println  ("echo #####  Cходящийся аспект плюс орб " + BatCityAspect.orb+" град. >> "+ BatCityAspect.fileName);
                       out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                              ZTLib.diap360(ztAsp[1]+BatCityAspect.orb)+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);




                  } else
                   out1.println  ("echo #####  Аспект минус орб " + BatCityAspect.orb+" град. >> "+ BatCityAspect.fileName);
                  out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                             ZTLib.diap360(ztAsp[0]-BatCityAspect.orb)+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);
                  out1.println  ("echo #####  Аспект точный  >> "+ BatCityAspect.fileName);
                  out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                              ztAsp[0]+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);
                  out1.println  ("echo #####  Аспект плюс орб " + BatCityAspect.orb+" град. >> "+ BatCityAspect.fileName);
                  out1.println  ("java Transits -p"+BatCityAspect.numPlanet+" -lon"+
                              ZTLib.diap360(ztAsp[0]+BatCityAspect.orb)+" -b"+startDate+" -b"+endDate+" -utnow -oloc >> "+BatCityAspect.fileName);


              }

        //java Transits -p9 -lon74.08333333333334 -b1.1.1800 -b1.1.2050   -utnov -oloc > Kv.txt
              //java Transits -p9НомерПланеты -lon1.72ЗемлеТочка -btoday -r -n12НаСколькоЦикловНазад -utnow -oloc >> PlKvZyaLondon.txt ИмяФайла


              //}
              /*else
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


                       //+"\t"+((int)eclipShir+"гр"+(Math.abs((int)((eclipShir-(int)eclipShir)*60)))+"мин"+"\t"+"\n"));
               }
          } //if   (sheet.getRow(i) !=null )

           */



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
  }
    private void jbInit() throws Exception {
  }

public static void main(String[] args) throws Exception
  {
      //KvZC22.txt zc 9 10 90 G20.xls 1.1.1700 1.1.2000
      //KvZC22.txt(вых файл) zc(ZT) 9(№ Планеты)  90(Аспет) G20.xls(Вх файл) 1.1.1700(дата с) 1.1.2000(даьа по)
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

