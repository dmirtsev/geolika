package ru.liveplanet.zt;

import java.io.FileOutputStream;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/** Создает полный бат файл на основе даты - времени - координат
 * Created by IntelliJ IDEA.
 * User: damir
 * Date: 17.08.2006
 * Time: 11:22:40
 * To change this template use File | Settings | File Templates.
 */
public class batSumCreator
{



    double[] planetInput=new double[25] ;
    double[] houseInput=new double[13] ;
   double[] house=new double[13] ;
    double dateSE1;
   double[] planetSE1=new double[25];
   double[] houseSE1=new double[13];
   double dateSE2;
   double[] planetSE2=new double[25];
   double[] houseSE2=new double[13];
   double dateSE3;
   double[] planetSE3=new double[25];
   double[] houseSE3=new double[13];
   double dateME;
   double[] planetME=new double[25];
   double[] houseME=new double[13];
   double dateNewMoon;
   double[] planetNewMoon=new double[25];
   double[] houseNewMoon=new double[13];
   double dateFullMoon;
   double[] planetFullMoon=new double[25];
   double[] houseFullMoon=new double[13];
     String name="";
    String dateTime="";
    String lat="",longi="";
    String batStr;

    int day=0;
    String dayStr;
    int month=0;
    int year=0;
    double min=0;
    double sec=0;
    double hour=0;

    public void separateDateTime() throws Exception  // -d 29.12.1731 -t 00:46:40
    {
        try
        {
        day=Integer.valueOf(dateTime.substring(dateTime.indexOf(".")-2,dateTime.indexOf("."))).intValue();
        if (day<10)
            dayStr = "0" + String.valueOf(day);
            else
            dayStr = String.valueOf(day);

        dateTime=dateTime.substring(dateTime.indexOf(".")+1).trim();
        month=Integer.valueOf(dateTime.substring(0,dateTime.indexOf("."))).intValue();
        dateTime=dateTime.substring(dateTime.indexOf(".")+1).trim();
        year=Integer.valueOf(dateTime.substring(dateTime.indexOf(" ")-4,dateTime.indexOf(" "))).intValue();
        dateTime=dateTime.substring(dateTime.indexOf(" ")+4).trim();
        hour=Integer.valueOf(dateTime.substring(0,2)).intValue();
        dateTime=dateTime.substring(3).trim();
        min=Integer.valueOf(dateTime.substring(0,2)).intValue();
        sec=Integer.valueOf(dateTime.substring(3)).intValue();
        }
        catch(Exception e)
        {
            System.out.println("Проблема в separateDateTime "+ e.toString());
            throw e;
        }
    }

    public batSumCreator(String name,String dateTime,String lat,String longi,
                         String batStr) throws Exception
    {
        this.name=name;
        this.dateTime=dateTime; // -d 29.12.1731 -t 00:46:40
        this.lat=lat;
        this.longi=longi;
        this.batStr=batStr;
        separateDateTime();
    }

    public static void main(String [] argStr) throws Exception
    {
        batSumCreator bs=new batSumCreator("Наименование", "-d 29.12.1731 -t 00:46:40","41","69","batStr");
        //bs.separateDateTime();
        bs.batGreator();
    }

    public void batGreator() throws Exception
    {
       // double min=52;
      //  double sec=0;
      //  double minSec=min+sec;
      //  double hour=6+minSec/60;

       // Получение параметров командной строки


        FileOutputStream fos = new FileOutputStream("Erth//"+dayStr+"."+month+"."+year+" "+(int)hour+" "+(int)min+" "+name+".bat");
        Writer w =
          new BufferedWriter(new OutputStreamWriter(fos, "Cp866"));

     Geolika geol;
        //int day=16;
        //int month=9;
        //int year=1964;


        //double longi=69;
        //double lat=41;
     //  w.write("javaw -jar Geolika.jar  -d "+day+"."
       //  +month+"."+year+" -t "+hour+":"+minSec+" -l "+longi+" -s "+
         //       lat+" -c ");
        w.write("cd ..");
        w.write("\n"+batStr);


        geol = new Geolika(year,month,day,hour,Double.valueOf(longi).doubleValue(),Double.valueOf(lat).doubleValue());
        int[] posFrac=new int[4];

        geol.getZT(geol.naclEkl); //заполняет geol.ztoch[i] Землеточки

        //***************************** Блок вычисления планет и домов на юлианскую дату
         planetInput=geol.getPlanet(geol.dateInputJuli); // Заполняет planetInput[i]

          geol.getHouse(geol.dateInputJuli);
            // Заполняем в соотв переменную, так как процедура заполняет в единую house[]
            for (int i=1;i<=12;i++)
            {

                houseInput[i]=geol.house[i];

            }
        //***************************** Блок вычисления планет и домов на юлианскую дату

           SimpleDateFormat formatter =new SimpleDateFormat("E dd.MM.yyyy 'в' HH:mm:ss  z"); //Для форматирования строки
           SimpleDateFormat formatDateTimeInBatFile =new SimpleDateFormat("'-d' dd.MM.yyyy '-t' HH:mm:ss"); //Для форматирования строки
           TimeZone tz = TimeZone.getTimeZone("GMT"); // or PST, MID, etc ...
           formatter.setTimeZone(tz);
           formatDateTimeInBatFile.setTimeZone(tz);

//**********Дата солнечного затмения 1 *************************
        geol.dateSE1=geol.getDateSolEclips(geol.dateInputJuli)[0];

//        if (forJAR==false)
  //      w.write("\njavaw -cp "+classPath+";.; ru.liveplanet.zt.ZT " +
    //            formatDateTimeInBatFile.format(geol.sd.getDate(geol.dateSE1))+
      //          " -l "+longi+" -s "+lat+"  -c "+"Солнечное_затмение_1_перед_"+
        //        comment+" "+addKey(drAspPlan,drAspPlZT,drDomTab));
       // else // для jar файла
//javaw -jar Geolika.jar  -d 14.8.2006 -t 13:9.583333333333334 -l -64.6363 -s 19.0223 -c M_5.0__Virgin_Islands_region -z
            w.write("\njavaw -jar Geolika.jar "+formatDateTimeInBatFile.format(geol.sd.getDate(geol.dateSE1))+
                    " -l "+longi+" -s "+lat+" -c Солнечное_затмение_1_перед_"+name+"@"+dayStr+"."+month+"."+year+" -z");

        //***************************** Блок вычисления планет и домов на юлианскую дату

         planetSE1=geol.getPlanet(geol.dateSE1);

          geol.getHouse(geol.dateSE1);
            // Заполняем в соотв переменную, так как процедура заполняет в единую house[]
            for (int i=1;i<=12;i++)
            {
                houseSE1[i]=geol.house[i];

            }
        //***************************** Блок вычисления планет и домов на юлианскую дату
//**********Дата солнечного затмения 1 *************************
//**********Дата солнечного затмения 2 *************************
        geol.dateSE2=geol.getDateSolEclips(geol.dateSE1)[0];

        w.write("\njavaw -jar Geolika.jar "+formatDateTimeInBatFile.format(geol.sd.getDate(geol.dateSE2))+
                " -l "+longi+" -s "+lat+" -c Солнечное_затмение_2_перед_"+name+"@"+dayStr+"."+month+"."+year+" -z");

        //***************************** Блок вычисления планет и домов на юлианскую дату
         planetSE2=geol.getPlanet(geol.dateSE2);
          geol.getHouse(geol.dateSE2);
            // Заполняем в соотв переменную, так как процедура заполняет в единую house[]
            for (int i=1;i<=12;i++)
            {
                houseSE2[i]=geol.house[i];
            }
        //***************************** Блок вычисления планет и домов на юлианскую дату
        posFrac=geol.ztl.swe_split_deg1(geol.sl.swe_degnorm(planetSE2[0]-planetSE1[0]),8);
        w.write("\nОрб оси затмений 2-1 "+ posFrac[1]+" гр."+posFrac[2]+" мин.");
//**********Дата солнечного затмения 2 *************************

//**********Дата солнечного затмения 3 *************************
        geol.dateSE3=geol.getDateSolEclips(geol.dateSE2)[0];


        //***************************** Блок вычисления планет и домов на юлианскую дату

         planetSE3=geol.getPlanet(geol.dateSE3);

          geol.getHouse(geol.dateSE3);
            // Заполняем в соотв переменную, так как процедура заполняет в единую house[]
            for (int i=1;i<=12;i++)
            {
                houseSE3[i]=geol.house[i];
            }

//**********Дата лунного затмения  *************************
        geol.dateME=geol.getDateMoonEclips(geol.dateInputJuli)[0];
        //System.out.println("Дата лунного затмения перед событием  "+ formatter.format(geol.sd.getDate(geol.dateME)));
//        if (forJAR==false)
  //
    //    w.write("\njavaw -cp "+classPath+";.; ru.liveplanet.zt.ZT " +
      //          formatDateTimeInBatFile.format(geol.sd.getDate(geol.dateME))+
        //        " -l "+longi+" -s "+lat+" -c "+"Лунное_затмение_перед_"+
          //      comment+" "+addKey(drAspPlan,drAspPlZT,drDomTab));
        //else

        w.write("\njavaw -jar Geolika.jar "+formatDateTimeInBatFile.format(geol.sd.getDate(geol.dateME))+
                " -l "+longi+" -s "+lat+" -c Лунное_затмение_перед_"+name+"@"+dayStr+"."+month+"."+year+" -z");


        //***************************** Блок вычисления планет и домов на юлианскую дату

         planetME=geol.getPlanet(geol.dateME);

          geol.getHouse(geol.dateME);
            // Заполняем в соотв переменную, так как процедура заполняет в единую house[]
            for (int i=1;i<=12;i++)
            {
                houseME[i]=geol.house[i];
            }

//**********Дата лунного затмения  *************************
//**********Для новолуния и полнолуния  *************************
       double yearFrac=geol.yearFrac(day,month,year);
       //SimpleDateFormat formatter =new SimpleDateFormat("E dd.MM.yyyy 'в' HH:mm:ss  z"); //Для форматирования строки
       MoonPhase mf=new MoonPhase();
       double nl =mf.getMoonPhase(yearFrac,0,true);
       // Проверка корректности вычисленной фазы - была ошибка, например на
        // 13092004 ближайшее новолуние перед событием высчитывал как
        // 14092004 - то есть уже после события
        int ii=0;
        while(nl>geol.dateInputJuli)
        {
//        System.out.println("С коррекцией по новолунию - в превый проход поймал дату новолуния больше чем входная дата  ");
   //      System.out.println("Ошибочная (после события) дата новолуния   "+ formatter.format(geol.sd.getDate(nl)));
         ii++;
         nl =mf.getMoonPhase(yearFrac-(ii/365.25),0,true);

        }
       // Проверка корректности завершена
       geol.dateNewMoon=nl;
//      System.out.println("Дата новолуния   "+ formatter.format(geol.sd.getDate(nl)));
//        if (forJAR==false)
  //      w.write("\njavaw -cp "+classPath+";.; ru.liveplanet.zt.ZT " +
    //            formatDateTimeInBatFile.format(geol.sd.getDate(nl))+
      //          " -l "+longi+" -s "+lat+" -c "+"Новолуние_перед_"+
        //        comment+" "+addKey(drAspPlan,drAspPlZT,drDomTab));
        //else
            //w.write("\njavaw -jar Geolika.jar Новолуние_перед_");
        w.write("\njavaw -jar Geolika.jar "+formatDateTimeInBatFile.format(geol.sd.getDate(nl))+
                " -l "+longi+" -s "+lat+" -c Новолуние_перед_"+name+"@"+dayStr+"."+month+"."+year+" -z");

        //***************************** Блок вычисления планет и домов на юлианскую дату

         planetNewMoon=geol.getPlanet(geol.dateNewMoon);

          geol.getHouse(geol.dateNewMoon);
            // Заполняем в соотв переменную, так как процедура заполняет в единую house[]
            for (int i=1;i<=12;i++)
            {
                houseNewMoon[i]=geol.house[i];
            }
        //***************************** Блок вычисления планет и домов на юлианскую дату

        nl=mf.getMoonPhase(yearFrac,2,true);
        // Проверка корректности вычисленной фазы - была ошибка, например на
         // 13092004 ближайшее новолуние перед событием высчитывал как
         // 14092004 - то есть уже после события
          ii=0;
         while((geol.dateInputJuli-nl)>29.53)
         {
          //System.out.println("С коррекцией по полнолунию - в первый проход поймал (дату полнолуния-входная дата)>25.53  ");
          //System.out.println("Ошибочная (не предыдущая а предпредыдущая) дата полнолуния   "+ formatter.format(geol.sd.getDate(nl)));
          ii++;
          nl =mf.getMoonPhase(yearFrac+(ii/365.25),2,true);
         }
        // Проверка корректности завершена
        geol.dateFullMoon=nl;
       //System.out.println("Дата полнолуния   "+ formatter.format(geol.sd.getDate(nl)));
       // if (forJAR==false)
        //w.write("\njavaw -cp "+classPath+";.; ru.liveplanet.zt.ZT " +
          //      formatDateTimeInBatFile.format(geol.sd.getDate(nl))+
            //    " -l "+longi+" -s "+lat+"  -c "+"Полнолуние_перед_"+
              //  comment+" "+addKey(drAspPlan,drAspPlZT,drDomTab));
        //else
            //w.write("\njavaw -jar Geolika.jar Полнолуние_перед_");
        w.write("\njavaw -jar Geolika.jar "+formatDateTimeInBatFile.format(geol.sd.getDate(nl))+
                " -l "+longi+" -s "+lat+" -c Полнолуние_перед_"+name+"@"+dayStr+"."+month+"."+year+" -z");

        //***************************** Блок вычисления планет и домов на юлианскую дату
//System.out.println("Планеты и дома на полнолуние перед событием  ");
         planetFullMoon=geol.getPlanet(geol.dateFullMoon);
          geol.getHouse(geol.dateFullMoon);
            // Заполняем в соотв переменную, так как процедура заполняет в единую house[]
            for (int i=1;i<=12;i++)
            {
                houseFullMoon[i]=geol.house[i];
            }
        //***************************** Блок вычисления планет и домов на юлианскую дату

//**********Для новолуния и полнолуния  *************************

        w.flush(); //Завершает запись в файл
        w.close(); //Завершает запись в файл

    }
}
