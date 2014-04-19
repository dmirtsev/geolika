package ru.liveplanet.zt;

/**
 * Created by IntelliJ IDEA.
 * User: damir
 * Date: 16.08.2006
 * Time: 11:14:10
 * To change this template use File | Settings | File Templates.
 */
import java.net.*;
import java.io.*;
import java.util.*;
//import org.apache.regexp.*;

/**
 * Title:    Закачивает xml файл на локальную машину, обрабатывает и на выходе - бак файл
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

public class URLReader
{
  public URLReader() throws Exception
  {
       //PrintStream outUR=new PrintStream(
    //new BufferedOutputStream(
    //new FileOutputStream("_URLReader.txt")));
    //System.setOut(out);  // перенаправление стандартного ввода-вывода
    //System.setErr(out);
  }
  /**
   * На входе URL, на выходе - считанная с источника строка
   *
   */
  public String readURL(String startURL, String addPathURL )throws Exception{

	URL MyURL = new URL(startURL);
	HttpURLConnection MyURLConnection = (HttpURLConnection) MyURL.openConnection();
/*
    System.getProperties().put("proxySet", "true");
    System.getProperties().put("proxyHost", "192.168.0.2");
    System.getProperties().put("proxyPort", "3128");
    */
    //System.getProperty()


    DataInputStream din = new DataInputStream(  //загружаем настройки с файла
      new BufferedInputStream(
        new FileInputStream("prop.dat")));
      Properties connInfo = new Properties();
      connInfo.load(din);
      String ps=connInfo.getProperty("proxySet");
      String ph=connInfo.getProperty("proxyHost");
      String pp=connInfo.getProperty("proxyPort");
      //System.getProperties().put("proxySet",ps);
      //System.getProperties().put("proxyHost",ph);
      //System.getProperties().put("proxyPort",pp);

    PrintWriter gr=new PrintWriter(new BufferedWriter(new FileWriter("eqs7day-M5.txt")));
    PrintWriter gr1=new PrintWriter(new BufferedWriter(new FileWriter("eqs7day-M5.xml")));
    BufferedReader in
          = new BufferedReader(new InputStreamReader(MyURLConnection.getInputStream()));
	String inputLine,inputLine1="";

	while ((inputLine = in.readLine()) != null)
	{
          gr1.println(inputLine);
      //    System.out.println(inputLine);
          inputLine1=inputLine1+"\r"+inputLine;
        }
       gr.println(inputLine1);
       gr.close();gr1.close();
      return inputLine1;
  };
  public static void main(String[] args) throws Exception{
  URLReader ur=new URLReader();
  //ur.readURL("http://www.nix.ru/autocatalog/cc/cases_atx.html","http://www.nix.ru/autocatalog/");


    System.out.print("@@@@@@@@@@ "+ur.readURL("http://earthquake.usgs.gov/eqcenter/recenteqsww/catalogs/eqs7day-M5.xml","http://earthquake.usgs.gov/eqcenter/recenteqsww/catalogs/"));  
                                               //http://earthquake.usgs.gov/eqcenter/recenteqsww/catalogs/eqs7day-M5.xml
      Lister ls=new Lister();
              ls.list( );


  };
}