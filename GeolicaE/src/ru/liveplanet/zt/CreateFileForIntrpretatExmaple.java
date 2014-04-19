package ru.liveplanet.zt;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: Дамир
 * Date: 22.11.2010
 * Time: 13:51:40
 * To change this template use File | Settings | File Templates.
 * Класс создает файл примеров из базы ZET для вставки в текст интепретации
 * В каталог ZETBaseInput размещаются файлы баз данных ZET
 * В каталог ZETBaseOutput размещаются результирующие файлы с расширением   "ex"
 */
public class CreateFileForIntrpretatExmaple
{

     int year;
        int month;
        int day;
    String  fileName="";
    String date="",time="";
    String rodRating="",sadc="";

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
                            new FileInputStream("ZETBaseInput\\"+fileName),"windows-1251");
	                    BufferedReader in = new BufferedReader(isr);
                       PrintWriter out1 =
               new PrintWriter(
                 new BufferedWriter(
                   new FileWriter("ZETBaseOutput\\"+fileName+".txt")));

             // cntArr(findStr);
               boolean readStr=false;
        String[] al;//=new String[50];
        String[] palka;//=new String[50];
        //String[] prim=new String[50];


                       out1.println("********************************************************************");
                       out1.println("Пример аналогичного взаимодействия с вышеназванной ЗемлеТочкой Москвы:");
        out1.println("********************************************************************");

       while (((str = in.readLine()) != null))
        {
            try
            {
            al=str.split("; ");
            palka=al[8].split("\\|");

            out1.println(al[0]+"| - на г. Москва - |"+ al[1]+"|"+ palka[0]);

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
                        System.out.println("----Ошибка");
                   }
        return str;
    } ;


    /**
     * перебирает директорию и подставляет имена файлов на обработку
     */

    public void createFileIndir()
  {
   String list[] = new File("ZETBaseInput\\").list();
     for(int i = 0; i < list.length; i++)
     {
       System.out.println(list[i]);
         fileName=list[i];
         findInFile();
     }
  }
    


    public static void main (String[] argStr)
    {
              // String fileName="";
        //fileName=argStr[0];
          CreateFileForIntrpretatExmaple cf = new CreateFileForIntrpretatExmaple();
       //         cf.fileName=fileName;
        //cf.findInFile();
        cf.createFileIndir();
    }
}
