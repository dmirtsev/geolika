package ru.liveplanet.zt;

/**
 * Created by IntelliJ IDEA.
 * User: damir
 * Date: 16.08.2006
 * Time: 14:05:59
 * To change this template use File | Settings | File Templates.
 */
import java.io.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class Lister {
    String name="";
    String dateTime="",dateTimeErtq="";

    String lat="",longi="";
    String batStr;
    String monthStr="",dayStr="",yearStr="",timeStr="";
    /**
     * ѕровер€ет, если все параметры заполнены, то формирует одержимое bat-файла
     */
    public void checkFullItems()
    {
        if (!longi.equalsIgnoreCase(""))
         if (!lat.equalsIgnoreCase(""))
          if (!name.equalsIgnoreCase(""))
            if (!dateTime.equalsIgnoreCase(""))
            {
            batStr="java -jar Geolika.jar "+dateTime+" -l "+longi+" -s "+lat + " -c "+
                     name+"@"+dayStr+"."+monthStr+"."+yearStr+" -z  -j ";
            dateTimeErtq=dateTime; // ƒата и врем€ землетр€сени€, передать как параметр
          //System.out.println(batStr);
            try
            {
            batSumCreator bs=new batSumCreator(name, dateTime,lat,longi,batStr); // класс, формирующий полный бат файл
            bs.batGreator();
            }
            catch(Exception e)
            {
                System.out.println(e.toString());

            }
                name="";
               dateTime="";
               lat="";longi="";
            }

    }


  class Handler extends DefaultHandler {
    boolean title = false;
    boolean link   = false;
    boolean description   = false;
    boolean geoLat   = false;
    boolean geoLong   = false;
    String batStr;

    public void startElement(String nsURI, String strippedName,
                            String tagName, Attributes attributes)
       throws SAXException {
     if (tagName.equalsIgnoreCase("title"))
        title = true;
     if (tagName.equalsIgnoreCase("link"))
        link = true;
        if (tagName.equalsIgnoreCase("description"))
           description = true;
        if (tagName.equalsIgnoreCase("geo:lat"))
           geoLat = true;
        if (tagName.equalsIgnoreCase("geo:long"))
           geoLong = true;

    }

    public void characters(char[] ch, int start, int length)
    {
        String valueStr;
     if (title) {
         valueStr=new String(ch, start, length);
         separateValue("Title",valueStr);
       //System.out.println("Title: " + valueStr);
       title = false;
       }
     else if (link) {
         valueStr=new String(ch, start, length);
         //batCreator("Description",valueStr);
       //System.out.println("Link: " + valueStr);
       link = false;
       }
     else if (geoLat) {
         valueStr=new String(ch, start, length);
         separateValue("Lat",valueStr);
       //System.out.println("geoLat: " + valueStr);
       geoLat = false;
       }
     else if (description) {
         valueStr=new String(ch, start, length);
         separateValue("Description",valueStr);
       //System.out.println("description: " + valueStr);
       description = false;
       }
     else if (geoLong) {
         valueStr=new String(ch, start, length);
         separateValue("Long",valueStr);
       //System.out.println("geoLong: " + valueStr);
       geoLong = false;
       }
        checkFullItems();
     }


      /**
       * «амен€ет пробел и зап€тую на "_"
       * @param str
       * @return преобраз. строка
       */
      public String replSpace(String str)
      {
          String str1;
          str1=str;
          str1=str1.replace(" ","_");
          str1=str1.replace(",","_");

          return str1;
      }
//      public void batCreate()
  //    {
    //     batStr="java -jar GeolikaBatCreator.jar "+dateTime+" -l "+longi+" -s "+lat + " -c "+name+ " -z  -j ";
      //}



      /**
       * ¬ходит строка, например August 15, 2006 23:53:47 GMT
       *
       * @param str
       * @return -d 16.9.1964 -t 6:52.6
       */
      public String formatDateTime(String str)
      {
          String str1="";
          str1=str;
          try
          {
          monthStr=str1.substring(0,str1.trim().indexOf(" "));
          str1=str1.substring(str1.trim().indexOf(" ")+1);

          if (monthStr.equalsIgnoreCase("January")) monthStr="1"; else
          if (monthStr.equalsIgnoreCase("February")) monthStr="2"; else
          if (monthStr.equalsIgnoreCase("March")) monthStr="3";else
          if (monthStr.equalsIgnoreCase("April")) monthStr="4";else
          if (monthStr.equalsIgnoreCase("May")) monthStr="5";else
          if (monthStr.equalsIgnoreCase("June")) monthStr="6";else
          if (monthStr.equalsIgnoreCase("July")) monthStr="7";else
          if (monthStr.equalsIgnoreCase("August")) monthStr="8";else
          if (monthStr.equalsIgnoreCase("September")) monthStr="9";else
          if (monthStr.equalsIgnoreCase("October")) monthStr="10";else
          if (monthStr.equalsIgnoreCase("November")) monthStr="11";else
          if (monthStr.equalsIgnoreCase("December")) monthStr="12";
          dayStr=str1.trim().substring(str1.trim().indexOf(",")-2,str1.trim().indexOf(","));
          str1=str1.trim().substring(str1.trim().indexOf(",")+1);
          yearStr=str1.trim().substring(0,str1.trim().indexOf(" ")+1);
          str1=str1.trim().substring(str1.trim().indexOf(" ")+1);
          timeStr=str1.trim().substring(0,str1.trim().indexOf(" "));
          str1="-d "+dayStr+"."+monthStr+"."+yearStr+" -t "+timeStr;
          }
          catch(Exception e)
          {
              System.out.println(e.toString());
          }
          return str1;
      }

      public void separateValue(String tagStr,String valStr)
      {
//          String name="";
  //        String dateTime="";
    //      String lat="",longi="";
//          String batStr;
          if (tagStr.equalsIgnoreCase("Title"))
                        name=replSpace(valStr);
          if (tagStr.equalsIgnoreCase("Description"))
                        dateTime=formatDateTime(valStr);
          if (tagStr.equalsIgnoreCase("Lat"))
                        lat=valStr;

          if (tagStr.equalsIgnoreCase("Long"))
                        longi=valStr;
          //java -jar GeolikaBatCreator.jar -d 13.8.2006 -t 21:54:11 -l -139.772 -s -58.241 -c Magnitude_5.4_-_PACIFIC-ANTARCTIC_RIDGE -z  -j
  //
    //     System.out.print(batStr);

      }

    }

    public void list( ) throws Exception {
//       XMLReader parser =
  //        XMLReaderFactory.createXMLReader
    //        ("org.apache.crimson.parser.XMLReaderImpl");
        XMLReader parser = XMLReaderFactory.createXMLReader(
            "org.apache.xerces.parsers.SAXParser");	// should load properties


       parser.setContentHandler(new Handler( ));
       parser.parse("eqs7day-M5.xml");
       }

    public static void main(String[] args) throws Exception {
       Lister ls=new Lister();
               ls.list( );
 //          ls.batStr="java -jar GeolikaBatCreator.jar "+ls.dateTime+" -l "+ls.longi+" -s "+ls.lat + " -c "+
     //              ls.name+ " -z  -j ";
   //     System.out.println(ls.batStr);

       }
}