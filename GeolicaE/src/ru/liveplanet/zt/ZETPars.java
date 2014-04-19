package ru.liveplanet.zt;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.*;
import java.text.*;


/**
 * Конвертирует базы ZET
 * Параметр - файл ZET
 * Файл должен лежать в корне
 * Пример - java ZETPars -SADC-A.zbs
 * Создает файл SADC-A.zbs.glk формата
 *  -d 10.4.1867 -t 12:25 -l -6.333333333333333 -s 54.46666666666667 -c A__E__LURGAN-_COUNTY_ARMAGH-_IRELAND-_GB -i -b -r RODDENRATING -w SADC459
 -d 1.9.1982 -t 4:51 -l -87.65 -s 41.85 -c A_F_A_N__CHICAGO-_COOK-_IL -i -b -r RODDENRATINGA -w SADC4050
................. 
 */
public class ZETPars {
    int[][] aspectPlanetZT;
    int planetCount;
    String findStr,comment;
      int year;
        int month;
        int day;
        int hour;
        int min;
    String name, fileName="";
    String city;
    String dolg;
    String shir;
    String date="",time="";
    String rodRating="",sadc="";


    ZETPars()

    {

    }

    void formDate(String dateStr,String timeStr,String deltaStr)
    {
        String [] arrDate=new String[3];
        String [] arrTime=new String[3];
        String [] arrDelt= new String[2];
        boolean plus=false;
        arrDate=dateStr.split("\\D");
        arrTime=timeStr.split(":");
        arrDelt=deltaStr.split(":");

         if(arrDelt[0].indexOf("+")!=-1 || (arrDelt[0].indexOf("-")!=-1))
        {
          arrDelt[0]=arrDelt[0].substring(1);  
        }

         year=Integer.parseInt(arrDate[2]);
         month=Integer.parseInt(arrDate[1]);
         day=Integer.parseInt(arrDate[0]);
         hour=Integer.parseInt(arrTime[0]);
         min=Integer.parseInt(arrTime[1]);
        int delHour=Math.abs(Integer.parseInt(arrDelt[0]));
        int delMin=0;
        if (arrDelt.length>1)
        {
        delMin=Integer.parseInt(arrDelt[1]);
        } else delMin=0;

        if (deltaStr.indexOf("-")!=0)
                       plus=true; else plus=false;

        min=min+delMin;
            hour=hour+delHour;
        /*

        if (plus=false)
        {
            min=min+delMin;
            hour=hour+delHour;
        } else
        {
            min=min-delMin;
            hour=hour-delHour;

        }
          */
        GregorianCalendar gk= new GregorianCalendar(year,month-1,day,hour,min);
        Date dtTrue= gk.getTime();
        //day=dtTrue.getDay();
        //month=dtTrue.getMonth();
       // year=dtTrue.getYear();
       // hour=dtTrue.getHours() ;
       // min=dtTrue.getMinutes();
        SimpleDateFormat fm1=new SimpleDateFormat("yyyy");
        SimpleDateFormat fm2=new SimpleDateFormat("MM");
        SimpleDateFormat fm3=new SimpleDateFormat("dd");
        SimpleDateFormat fm4=new SimpleDateFormat("HH");
        SimpleDateFormat fm5=new SimpleDateFormat("mm");
        year=Integer.parseInt(fm1.format(dtTrue));
         month=Integer.parseInt(fm2.format(dtTrue));
         day=Integer.parseInt(fm3.format(dtTrue));
         hour=Integer.parseInt(fm4.format(dtTrue));
         min=Integer.parseInt(fm5.format(dtTrue));;

        date=day+"."+month+"."+year;
        time = hour+":"+min;
      //  System.out.println(day+"."+month+"."+year+" "+
           //     hour+" : "+min + "  -  "+dtTrue );
    }

    //String[] readFile()

    void inFile(String strOut) throws IOException
    {
        PrintWriter out1 =
               new PrintWriter(
                 new BufferedWriter(
                   new FileWriter(fileName+".glk")));
        out1.println(strOut);
        //out1.close();
    }

    /*
    int cntArr(String[] findStr)
    {
        int cnt=0;
        for (int i=0; i<=findStr.length;i++)
        {
          if (findStr[i].equals(null))
              cnt=i+1;
        }
        return cnt;
    }
      */
    String findInFile()
    {
               String str="";
                   try {
                       //String str="";

                       //String readPage(String filename, String fileString)               ;
                       //String filename="1.Выборка из SADT Американцы - Вашингтон.zbs";

                       String fileString="";
	                   //FileInputStream fis=new FileInputStream(filename);
	                   InputStreamReader isr=new InputStreamReader(
                            new FileInputStream(fileName),"windows-1251");
	                    BufferedReader in = new BufferedReader(isr);
                       PrintWriter out1 =
               new PrintWriter(
                 new BufferedWriter(
                   new FileWriter(fileName+".glk")));

             // cntArr(findStr);
               boolean readStr=false;
        String[] al;//=new String[50];
        //String[] prim=new String[50];



       while (((str = in.readLine()) != null))
        {
            try
            {
            al=str.split("; ");
             for  (int i=0;i<al.length;i++)
        {
            if (i==8) // Примечание - ищем RaddenRating
            {
                if (al[i].indexOf("RODDEN RATING")!=-1)
                {
                    rodRating=al[i].substring(al[i].indexOf("RODDEN RATING"),al[i].indexOf("RODDEN RATING")+17);
                   // System.out.println(rodRating);
                }

                if (al[i].indexOf("SADC")!=-1)
                {
                    sadc=al[i].substring(al[i].indexOf("SADC"),al[i].indexOf("SADC")+21);
                  //  System.out.println(sadc);
                }
                  rodRating=rodRating.replaceAll("\\p{Punct}","");
             rodRating=rodRating.replaceAll("\\p{Blank}","");

            sadc=sadc.replaceAll("\\p{Punct}","");
            sadc=sadc.replaceAll("\\p{Blank}","");
            }
           // System.out.println(i+" "+al[i]);



        }
            out1.println(createGeolikaStr(al,rodRating,sadc));
            //createGeolikaStr(al,rodRating,sadc); // создает сроку в нужном формате
        }
            catch (Exception e)
            {
                          System.out.println("Конец файла ");
            }
        }



        in.close();
        out1.close();
    } catch (IOException e)
                   {
                        System.out.println("----");
                   }
        return str;
    } ;
    boolean way(String str)
    {
        boolean  endStr=true;
        if (str.endsWith("N")||str.endsWith("S")||str.endsWith("E")||str.endsWith("W")) endStr=true;
        else endStr=false ;
        //return endStr;
        return endStr;
    }
    String createGeolikaStr(String[] arr, String rRating, String sadc)
    {
      String outStr;
        //arr[0].replaceAll(" ","_");
        arr[0]=arr[0].replaceAll("\\p{Punct}","_");
        arr[0]=arr[0].replaceAll("\\p{Blank}","_");
        arr[4]=arr[4].replaceAll("\\p{Punct}","-");
        arr[4]=arr[4].replaceAll("\\p{Blank}","_");

       // System.out.println(arr[0]);
        System.out.println(arr[4]);
        name=arr[0];
        city=arr[4] ;
        if (way(arr[5])==false)
        {
        if (arr[5].indexOf("N")!=-1)
        {
            //way(arr[5]);
            String[] gradSh = arr[5].split("N");
            arr[5]= String.valueOf(Double.parseDouble(gradSh[0])+Double.parseDouble(gradSh[1])/60);
        }

        if (arr[5].indexOf("S")!=-1)
        {
            String[] gradSh = arr[5].split("S");
            arr[5]= String.valueOf(-Double.parseDouble(gradSh[0])-Double.parseDouble(gradSh[1])/60);
        }

       if (arr[6].indexOf("E")!=-1)
        {
            String[] gradSh = arr[6].split("E");
            arr[6]= String.valueOf(Double.parseDouble(gradSh[0])+Double.parseDouble(gradSh[1])/60);
        }

        if (arr[6].indexOf("W")!=-1)
        {
            String[] gradSh = arr[6].split("W");
            arr[6]= String.valueOf(-Double.parseDouble(gradSh[0])-Double.parseDouble(gradSh[1])/60);
        }
    } else
        {
            if (arr[5].indexOf("N")!=-1)
        {
            //way(arr[5]);
            String[] gradSh = arr[5].split("°");
            arr[5]= String.valueOf(Double.parseDouble(gradSh[0])+Double.parseDouble(gradSh[1].substring(0,2))/60);
        }

        if (arr[5].indexOf("S")!=-1)
        {
            String[] gradSh = arr[5].split("°");
            arr[5]= String.valueOf(-Double.parseDouble(gradSh[0])-Double.parseDouble(gradSh[1].substring(0,2))/60);
        }

       if (arr[6].indexOf("E")!=-1)
        {
            String[] gradSh = arr[6].split("°");
            arr[6]= String.valueOf(Double.parseDouble(gradSh[0])+Double.parseDouble(gradSh[1].substring(0,2))/60);
        }

        if (arr[6].indexOf("W")!=-1)
        {
            String[] gradSh = arr[6].split("°");
            arr[6]= String.valueOf(-Double.parseDouble(gradSh[0])-Double.parseDouble(gradSh[1].substring(0,2))/60);
        }
        }
        shir=arr[5];
        dolg=arr[6];
        
        formDate(arr[1],arr[2],arr[3]);
        //System.out.println(name+" "+city+" "+date+" "+time+" "+dolg+" "+shir);

        outStr=" -d "+ date+ " -t "+ time+" -l "+ dolg +" -s "+ shir +" -c "+ name+"_"+city +" -i -b -r "+rodRating+" -w "+sadc;;
        System.out.println(outStr);

        //-d 25.2.1972 -t 8:35.0 -l 135.754807 -s 35.009129 -c Борис_Шорнинг_Киото -i -b
        return outStr;
    }

    public static void main(String[] argStr)
    {
        String fileName="";
        fileName=argStr[0];
        ZETPars fw= new ZETPars();
        fw.fileName=fileName;
        //fw.readFile();
        fw.findInFile();
       // fw.formDate("28.2.2009","22:52","+8");
    }
}
