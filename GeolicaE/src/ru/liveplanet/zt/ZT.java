package ru.liveplanet.zt;

/**
 * Рисует карту и выводит интерпретяцию
-d 25.2.1972 -t 8:35.0 -l 135.754807 -s 35.009129 -c Борис_Шорнинг_Киото -z -m -b -i
 -d 12.1.1967 -t 12:0.0 -l 37.833 -s 55.75 -c Рената_Литвинова_Москва -z -m -b -i
 */
import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiException;
import com.sun.jimi.core.JimiWriter;
import com.sun.jimi.core.raster.JimiRasterImage;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Arrays;


//import swisseph.*;


//public class ZT extends java.applet.Applet
public class ZT extends Frame

  {
      ///
      private Graphics graphics;
      private Image image;
      boolean interpret=false;
      ///

      //static final double DEGTORAD=0.0174532925199433;
      //static final double RADTODEG=57.2957795130823;
      double[] planetKoord;
      int planetCount=13;
//      planetKoord=new double[25];//

      double[] koordHouses;
      double[] koordHousesRelativ;
      double[] koordZTRelativ;
      boolean drawAspectPlanet=false;
      boolean drawDomInTable= false;
      boolean drawAspectZTPlanet= false;
      boolean fixDraw;
  //    koordHouses=new double[13];
  //    koordHousesRelativ=new double[13];

      double[] koordZT;
    //  koordZT=new double[4];
      int deltaXFont=0,deltaYFont=0;
      int fontW=0,fontH=0;
      int x1,y1,x2,y2,x0,y0,x00,y00;
      int diametrMinCircle=300; //Диаметр меньшего круга
      int delta=60; // Прирост каждого следующего круга
      int x=170, y=170; // Координаты левого верхнего угла квадрата, куда вписан меньший круг
      int diametr2Circle=diametrMinCircle+delta;
      int diametr3Circle=diametrMinCircle+delta*2;
      int diametr4Circle=diametrMinCircle+delta*3;
      double[][] gradusPlanetPlanet;
      double[][] gradusPlanetZT;
      double[][] aspectPlanetPlanet;
      double[][] aspectPlanetCuspidDom;
      double[][] aspectPlanetZT;
      int[][] aspectPlanetZTTochn;
      double orb=2;
      double[] allAspect =new double[]
              {0.0,18.0,24.0,30.0,36.0,40.0,45.0,51.5,60.0,72.0,80.0,
              90.0,100,102.9,108.0,120.0,135.0,144.0,150.0,154.33,160.0,180.0};
      double[] majorAspect =new double[]
              {0.0,60.0,72.0,90.0,120.0,150.0,180.0};
      String strName;
      int day, month,year;
      double hour,h,m;
      double  longi,lat,lonGr,lonMin,latGr, latMin;
      int[] posFrac=new int[4];
      Geolika geol;
      int xTab,yTab; //Координата левая верхняя чтобы начать выводить таблицу планет;
      int xCentrZnak,yCentrZnak;
      int xCentrPlanet,yCentrPlanet;
      double deltaAries;
      double[] planetKoordWithDelta=new double[planetCount]; //Для координат планет со смещением
      int n=0;
      public static String comment="";
      public static String commentName="";
      public static String dateErthq="";
      public boolean catalogMan=false;

      //Graphics screen;

      public static String day1,month1,year1,hour1,min1;

      ///
      public void createImage(OutputStream stream) throws IOException
        {
          try
          {
            JimiWriter writer = Jimi.createJimiWriter("image/png", stream);
            writer.setSource(image);
            writer.putImage(stream);
          }
          catch (JimiException je)
          {
            throw new IOException(je.getMessage());
          }
        }

      public void drawPieGraph()
      {
        graphics.setColor(Color.blue);
        graphics.drawOval(200,200,60,60);
      }
      ///

      public ZT(String comment, int day,int month, int year,double h,
                double m,double longi, double lat,
                boolean drawAspectPlanet, boolean drawAspectZTPlanet,
                boolean drawDomInTable, boolean fixDraw,boolean catalogMan, boolean interpr) //Конструктор
        {
           //setBackground(Color.yellow);
            ///
            this.catalogMan=catalogMan; // если был ключ -b то рисунки складывать в каталог Man
            Frame f = new Frame();
            f.setVisible(true);
            image = f.createImage(1000, 700);
            graphics = image.getGraphics();
            f.setVisible(false);
            //drawPieGraph();

            // Переменная  фиксировать ли рисунок после вывода или сразу закрывать его
            this.fixDraw=fixDraw;

            ///

            setTitle("Анализ Землеточек");
            setSize(1000,700);

            strName=comment;
            this.drawDomInTable=drawDomInTable;  // Выводим ли координаты домов в таблицу
            this.drawAspectPlanet=drawAspectPlanet; // Рисуем ли аспекты планет
            this.drawAspectZTPlanet=drawAspectZTPlanet;
            this.day=day; this.month=month;this.year=year;
            this.h=h;this.m=m;
            this.longi=longi;
            this.lat=lat;
            this.interpret=interpr;

            //M_5.4__New_Britain_region__Papua_New_Guinea
            //Солнечное_затмение_1_перед_M_5.4__New_Britain_region__Papua_New_Guinea
            if (strName.indexOf("перед")!=-1)
            commentName=strName.trim().substring(strName.indexOf("перед")+6);
            else
            commentName=strName.trim();
            //Извлекаем дату землетрясения чтобы вставить в название каталога
            if (strName.indexOf("@")!=-1)
            dateErthq=strName.trim().substring(strName.indexOf("@")+1);

            //this.lonGr=lonGr;this.lonMin=lonMin;this.latGr=latGr; this.latMin=latMin;
//geol = new Geolika(year,month,day,hour,longi,lat);

            addWindowListener(new WindowAdapter()
            {
                public void windowClosing( WindowEvent e)
                {
                    System.exit(0);
                }
            });

            setVisible(true);
            //setVisible(false);


        }

      /**
       *  Определяет координаты для вывода на экран
       * @param i - номер по итерации
       * @param n - смещение - например выведено уже 4 землеточки
       */
      public void positionPlanet(int i,int n)
      {
          x1=xTab;
          y1=yTab+fontH*(i+n);
      }

      /**
       * Позиционирует символ аспекта в центр линии
       * @param x1
       * @param y1
       * @param x2
       * @param y2
       */
      public void setKoordSymbolAspect(int x1,int y1,int x2,int y2)
      {
         if (x1<=x2) this.x1=x1+(x2-x1)/2; else
             this.x1=x2+(x1-x2)/2;
          if (y1<=y2) this.y1=y1+(y2-y1)/2; else
              this.y1=y2+(y1-y2)/2;
      }
      /**
       * Вычисляет символ аспекта по градусу
       * @param grad градус аспекта
       * @return символ аспекта
       */

      public String setSymbolAspect(double grad)
      {
          String strSymbol="";
          grad=Math.abs(grad);
          if ((Math.abs(0-grad)<=orb)) strSymbol="\u00ca"; else
          if ((Math.abs(18-grad)<=orb)) strSymbol="\u00fe"; else //?????????
          if((Math.abs(24-grad)<=orb)) strSymbol="\u00fe"; else  //?????????
          if((Math.abs(36-grad)<=orb)) strSymbol="\u00fe"; else//?????????
          if((Math.abs(40-grad)<=orb)) strSymbol="\u00e2"; else//?????????
          if((Math.abs(45-grad)<=orb)) strSymbol="\u00d7"; else
          if((Math.abs(51.5-grad)<=orb)) strSymbol="\u00fe"; else//?????????
          if((Math.abs(60-grad)<=orb)) strSymbol="\u00cb"; else
          if((Math.abs(72-grad)<=orb)) strSymbol="\u00DF"; else//?????????
          if((Math.abs(80-grad)<=orb)) strSymbol="\u00fe"; else//?????????
          if((Math.abs(90-grad)<=orb)) strSymbol="\u00cd"; else
          if((Math.abs(100-grad)<=orb)) strSymbol="\u00fe"; else//?????????
          if((Math.abs(102.9-grad)<=orb)) strSymbol="\u00fe"; else//?????????
          if((Math.abs(108-grad)<=orb)) strSymbol="\u00fe"; else//?????????
          if((Math.abs(120-grad)<=orb)) strSymbol="\u00cf"; else
          if((Math.abs(135-grad)<=orb)) strSymbol="\u00d8"; else
          if((Math.abs(144-grad)<=orb)) strSymbol="\u00fe"; else//?????????
          if((Math.abs(150-grad)<=orb)) strSymbol="\u00d5"; else
          if((Math.abs(154.33-grad)<=orb)) strSymbol="\u00fe"; else//?????????
          if((Math.abs(160-grad)<=orb)) strSymbol="\u00fe"; else//?????????
          if((Math.abs(180-grad)<=orb)) strSymbol="\u00d2";
          return strSymbol;
      }
      public double setAspect(double grad)
      {
          double strAspect=0;
          grad=Math.abs(grad);
          if ((Math.abs(0-grad)<=orb)) strAspect=Aspect.CONJ0; else
          if ((Math.abs(18-grad)<=orb)) strAspect=Aspect.VIG18; else //?????????
          if((Math.abs(24-grad)<=orb)) strAspect=Aspect.KVIND24; else  //?????????
          if((Math.abs(36-grad)<=orb)) strAspect=Aspect.DECL36; else//?????????
          if((Math.abs(40-grad)<=orb)) strAspect=Aspect.NOVL40; else//?????????
          if((Math.abs(45-grad)<=orb)) strAspect=Aspect.SKVAD45; else
          if((Math.abs(51.5-grad)<=orb)) strAspect=Aspect.SEPT51; else//?????????
          if((Math.abs(60-grad)<=orb)) strAspect=Aspect.SEXT60; else
          if((Math.abs(72-grad)<=orb)) strAspect=Aspect.KVINT72; else//?????????
          if((Math.abs(80-grad)<=orb)) strAspect=Aspect.BNOV80; else//?????????
          if((Math.abs(90-grad)<=orb)) strAspect=Aspect.KVDR90; else
          if((Math.abs(100-grad)<=orb)) strAspect=Aspect.SESKN100; else//?????????
          if((Math.abs(102.9-grad)<=orb)) strAspect=Aspect.BSERT102; else//?????????
          if((Math.abs(108-grad)<=orb)) strAspect=Aspect.TRDEC108; else//?????????
          if((Math.abs(120-grad)<=orb)) strAspect=Aspect.TRIN120; else
          if((Math.abs(135-grad)<=orb)) strAspect=Aspect.PLKVDR135; else
          if((Math.abs(144-grad)<=orb)) strAspect=Aspect.BIKV144; else//?????????
          if((Math.abs(150-grad)<=orb)) strAspect=Aspect.KVIK150; else
          if((Math.abs(154.33-grad)<=orb)) strAspect=Aspect.TRSEPT154; else//?????????
          if((Math.abs(160-grad)<=orb)) strAspect=Aspect.TTRNVL160; else//?????????
          if((Math.abs(180-grad)<=orb)) strAspect=Aspect.OPP180;
          return strAspect;
      }

      public String numToSymbolZodiak(int num)
      {
          String symbolZodiak="";
          switch (num)
                  {case (1): symbolZodiak="\u00a4";break;
                  case (2): symbolZodiak="\u00a8";break;
                  case (3): symbolZodiak="\u00a9";break;
                  case (4): symbolZodiak="\u00ad";break;
                  case (5): symbolZodiak="\u00af";break;
                  case (6): symbolZodiak="\u00b3";break;
                  case (7): symbolZodiak="\u00b6";break;
                  case (8): symbolZodiak="\u00b8";break;
                  case (9): symbolZodiak="\u00bc";break;
                  case (10): symbolZodiak="\u00c0";break;
                  case (11): symbolZodiak="\u00c3";break;
                  case (12): symbolZodiak="\u00c6";break;
          }
          return symbolZodiak;
      }


      public Font myFont()
      {
         Font snowFont=null;
          try {
          //FileInputStream fis = new FileInputStream("C:/ZT.ttf");
        //  URL classLocation= getClass().getProtectionDomain().getCodeSource().getLocation();
          //String classLocationStr=classLocation.toString();
          //String classLocationStr=classLocation.toString().substring("file:/".length())+"/ZT.ttf";
          //FileInputStream fis;
          File f = new File("");
        String path = f.getAbsolutePath();
              System.out.println(path);

           InputStream fis = getClass().getResourceAsStream("ZT.ttf");
               //fis = new FileInputStream(classLocationStr);
          Font fnt=Font.createFont(Font.TRUETYPE_FONT,fis);//
                  fis.close();
          snowFont = fnt.deriveFont(Font.PLAIN,18);
          } catch (FontFormatException e) {
              e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
          } catch (IOException e) {
              e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
          }
         return snowFont;
          /*
          //Вариант получения шрифта по url
          try {
          URL url = new URL("http://www.liveplanet.ru/almagest.ttf");
          InputStream is = url.openStream();

         Font fnt=Font.createFont(Font.TRUETYPE_FONT,is);//
         is.close();

         Font snowFont = fnt.deriveFont(Font.PLAIN,26);
         screen.setFont(snowFont);
          } catch (FontFormatException e) {
              e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
          } catch (IOException e) {
              e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
          }
          */

      }

      /**
       * Процедура расчета аспектов и заполнения массива градусов между планетами и массива аспектов
       *
       */
      public void calcGradusAspectPlanetPlanet()
      {
          gradusPlanetPlanet=new double[planetCount][planetCount];
          aspectPlanetPlanet=new double[planetCount][planetCount];
          for (int i=0;i<=planetCount-1;i++)
          {
              for (int j=i+1;j<=planetCount-1;j++)
              {
                 gradusPlanetPlanet[i][j]=ZTLib.swe_difdeg2n(planetKoord[i],planetKoord[j]);
                //System.out.println(" gradusPlanetPlanet[i][j] "+i+" - "+j+" " + gradusPlanetPlanet[i][j]);
                 for (int as=0;as<=majorAspect.length-1;as++)
                 {
                     if (Math.abs(Math.abs(gradusPlanetPlanet[i][j])-majorAspect[as])<=orb)
                     {
                         aspectPlanetPlanet[i][j]=gradusPlanetPlanet[i][j];
                         //System.out.println(" Выявлен аспект "+i+" - "+j+" " + gradusPlanetPlanet[i][j]);
                     }
                 }
              }
          }
      }

      public String setSymbolZT(int i)
      {
          String symbolZT="";
          switch(i)
          {
              case 0:symbolZT="\u00c1"; break;
              case 1:symbolZT="\u00d0"; break;
              case 2:symbolZT="\u005b"; break;
              case 3:symbolZT="\u00b5"; break;
          }

          return symbolZT;
      }

      public String setSymbolPlanet(int i)
      {
          String symbolPlanet="";
          switch(i)
          {
              case 0:symbolPlanet="\u005D"; break;//   Солнце
              case 1:symbolPlanet="\u005E"; break;//Луна
              case 2:symbolPlanet="\u0061"; break;//Меркурий
              case 3:symbolPlanet="\u0063"; break;//           Венера
              case 4:symbolPlanet="\u0067"; break;//Марс
              case 5:symbolPlanet="\u006A"; break;  // Юпитер
              case 6:symbolPlanet="\u006D"; break;    //   Сатурн
              case 7:symbolPlanet="\u006F"; break;//           Уран
              case 8:symbolPlanet="\u0072"; break;  //Нептун
              case 9:symbolPlanet="\u0076"; break;  //Плутон
             case 10:symbolPlanet="\u00F1 M"; break;    //Средний узел
              case 11:symbolPlanet="\u00F1 T"; break;//         Истинный узел
              case 12:symbolPlanet="\u0178"; break;  // Черная луна
                  /*
           case 12:symbolPlanet="\u00FE"; break;  // osc Apogee
           case 13:symbolPlanet="\u0064"; break;  // Земля
           case 14:symbolPlanet="\u0078"; break;  // Хирон
           case 15:symbolPlanet="\u00C5"; break;  // Фолус
           case 16:symbolPlanet="\u00C5"; break;  // Церрера
           case 17:symbolPlanet="\u00C5"; break;  // Паллада
           case 18:symbolPlanet="\u00C5"; break;  // Юнона
           case 19:symbolPlanet="\u00C5"; break;  // Веста
                      */
          }

          return symbolPlanet;
      }

      /**
       *  Определяет и превр в строку все аспекты к данной Землеточке
       * @param n номер землеточки
       * @return сформимрованная строка для вывода аспектов справа от координат землеточки
       */
      public String setStringAspectZTPlanet(int n)
     {
       String stringAspectZTPlanet="";

    //stringAspectZTPlanet=setSymbolZT(n);
         for (int i=0;i<=planetCount-1;i++)
         {
                    if (aspectPlanetZT[i][n]!=0)
                    {
                        stringAspectZTPlanet=stringAspectZTPlanet+" "+
                                setSymbolAspect(aspectPlanetZT[i][n])+setSymbolPlanet(i)+"; ";
                    }
         }
         return stringAspectZTPlanet;
     }

      /**
       *
       * @param n номер планеты
       * @return строка для вывода аспектов справа от координат планеты
       */
      public String setStringAspectPlanetPlanet(int n)
     {
       String stringAspectPlanetPlanet="";


        // stringAspectPlanetPlanet=setSymbolPlanet(n);
         for (int i=0;i<=planetCount-1;i++)
         {
                    if (aspectPlanetPlanet[n][i]!=0)
                    {
                        stringAspectPlanetPlanet=stringAspectPlanetPlanet+" "+
                                setSymbolAspect(aspectPlanetPlanet[n][i])+setSymbolPlanet(i)+"; ";
                    }
         }
         return stringAspectPlanetPlanet;
     }



     public void calcAspectPlanetDom()
     {
         aspectPlanetCuspidDom=new double[planetCount][13];
         for (int i=0;i<=planetCount-1;i++)
         {
             for (int j=0;j<=12;j++)
             {
                 double deltaPlanetDom=ZTLib.swe_difdeg2n(planetKoord[i],koordHouses[j]);
                for (int as=0;as<=majorAspect.length-1;as++)
                {
                    if (Math.abs(Math.abs(deltaPlanetDom)-majorAspect[as])<=orb)
                    {
                        aspectPlanetCuspidDom[i][j]=deltaPlanetDom;
                        //System.out.println(" Выявлен аспект Планета - Дом "+i+" - "+j+" " + deltaPlanetDom);
                    }
                }
             }
         }
     }
    public void calcAspectPlanetZT()
    {
        aspectPlanetZT= new double[planetCount][koordZT.length];
        aspectPlanetZTTochn= new int [planetCount][koordZT.length];
        for (int i=0; i<planetCount;i++)
        {
            for (int j=0; j<koordZT.length;j++)
            {
             aspectPlanetZTTochn[i][j]=-1;   
            }

        }

        gradusPlanetZT= new double[planetCount][koordZT.length];
        for (int i=0;i<=planetCount-1;i++)
        {
            for (int j=0;j<=koordZT.length-1;j++)
            {
               gradusPlanetZT[i][j]=ZTLib.swe_difdeg2n(planetKoord[i],koordZT[j]);

               for (int as=0;as<=majorAspect.length-1;as++)
               {
                   if (Math.abs(Math.abs(gradusPlanetZT[i][j])-majorAspect[as])<=orb)
                   {
                       aspectPlanetZT[i][j]=gradusPlanetZT[i][j];
                       aspectPlanetZTTochn[i][j]=(int)majorAspect[as];
                       //System.out.println(" Выявлен аспект Планета - ZT "+i+" - "+j+" " + gradusPlanetZT[i][j]);
                       //Тут нужно бежать в блок интерпретации
                   }
               }
            }
        }

    }

   String interpString(double[][] aspectPlanetZT) throws IOException
   {
       String interp="";
        for(int i=0;i<=planetCount-1;i++)
          {
              for(int j=0;j<=3;j++)
              {

               if (aspectPlanetZT[i][j]!=0)
               {
                   //setAspect(aspectPlanetZT[i][j]);
                   System.out.println(" Аспект Планета - ZT "+i+" - "+Aspect.ZTOCH[j]+" " + setAspect(aspectPlanetZT[i][j]));
               }
                   try {
                       String str="";
                        
                       //String readPage(String filename, String fileString)               ;
                       String filename="interpret\\interpr.txt";

                       String fileString="";
	                   FileInputStream fis=new FileInputStream(filename);
	                   InputStreamReader isr=new InputStreamReader(
                            new FileInputStream(filename),"windows-1251");
	                    BufferedReader in = new BufferedReader(isr);
        while (((str = in.readLine()) != null))
        {
          fileString=fileString+"\n"+str;
            System.out.println(fileString);
        }
        in.close();
    } catch (IOException e)
                   {
                      System.out.println(e.getLocalizedMessage());
                   }
              }
          }

       return interp;
   }

   public void getHousePlanetZT()
   {
//Получение массивов домов, землеточек и планет
            planetKoord=new double[25];//
            koordHouses=new double[13];
            koordHousesRelativ=new double[13];
            koordZT=new double[4];
            koordZTRelativ=new double[4];

         /*
              strName="И. В. Сталин - Петербург";
              drawDomInTable=false;  // Выводим ли координаты домов в таблицу
              drawAspectPlanet=false; // Рисуем ли аспекты планет
              drawAspectZTPlanet=true;
              day=21; month=12;year=1879;
              h=4;m=20;
              lonGr=30.250;lonMin=0;latGr=59.917; latMin=0;

              if (lonGr>0)       longi=lonGr+lonMin/60; else longi=lonGr-lonMin/60;
              if (latGr>0) lat=latGr+latMin/60; else lat=latGr-latMin/60;
       */
              hour=h+m/60;

               geol = new Geolika(year,month,day,hour,longi,lat);
               //int[] posFrac=new int[4];
               geol.getZT(geol.naclEkl);
               koordZT=geol.ztoch;
                  for (int i=0;i<=3;i++)
                  {
                      posFrac=geol.ztl.swe_split_deg1(koordZT[i],8);
                      //System.out.println("\n***** "+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
                        //      geol.numToNameZodiak(posFrac[0]+1));
                  }
               planetKoord=geol.getPlanet(geol.dateInputJuli);
                  for (int i=0;i<=20;i++)
                  {
                      posFrac=geol.ztl.swe_split_deg1(planetKoord[i],8);
                      //System.out.println(
                      //geol.sw.swe_get_planet_name(i)+":"+"\t"+
                        //      posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
                          //    geol.numToNameZodiak(posFrac[0]+1));
                  }
                geol.getHouse(geol.dateInputJuli);
               koordHouses=geol.house;
                  // Заполняем в соотв переменную, так как процедура заполняет в единую house[]
                  for (int i=1;i<=12;i++)
                  {
                   koordHouses[i]=geol.house[i];
                   posFrac=geol.ztl.swe_split_deg1(koordHouses[i],8);
                      //System.out.println(
                      //":Дом "+ i + "    "+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
                        //      geol.numToNameZodiak(posFrac[0]+1));
                  }
   }

      /**
       * Рисование пунктирной или сплошной линии определенного цвета
       * @param g
       * @param x1
       * @param y1
       * @param x2
       * @param y2
       * @param dashlength величина
       * @param spacelength
       * @param c - цвет линии
       */
      public void drawDashedLine(Graphics g,int x1,int y1,int x2,int y2,
                           double dashlength, double spacelength,Color c)
      {
          g.setColor(c);
          graphics.setColor(c);
  if((x1==x2)&&(y1==y2))
  {
    g.drawLine(x1,y1,x2,y2);
    graphics.drawLine(x1,y1,x2,y2);
    return;
    }
  double linelength=Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
  double yincrement=(y2-y1)/(linelength/(dashlength+spacelength));
  double xincdashspace=(x2-x1)/(linelength/(dashlength+spacelength));
  double yincdashspace=(y2-y1)/(linelength/(dashlength+spacelength));
  double xincdash=(x2-x1)/(linelength/(dashlength));
  double yincdash=(y2-y1)/(linelength/(dashlength));
  int counter=0;
  for (double i=0;i<linelength-dashlength;i+=dashlength+spacelength){
      g.drawLine((int) (x1+xincdashspace*counter),
                 (int) (y1+yincdashspace*counter),
                 (int) (x1+xincdashspace*counter+xincdash),
                 (int) (y1+yincdashspace*counter+yincdash));
      graphics.drawLine((int) (x1+xincdashspace*counter),
                 (int) (y1+yincdashspace*counter),
                 (int) (x1+xincdashspace*counter+xincdash),
                 (int) (y1+yincdashspace*counter+yincdash));

      counter++;
      }
  if ((dashlength+spacelength)*counter<=linelength)
     g.drawLine((int) (x1+xincdashspace*counter),
                (int) (y1+yincdashspace*counter),
                x2,y2);
          graphics.drawLine((int) (x1+xincdashspace*counter),
                     (int) (y1+yincdashspace*counter),
                     x2,y2);

  }

      /**
       * Наносит землеточки
       * @param g
       */
      public void drawZT(Graphics g)
      {
         g.drawLine(x1,y1,x2,y2); // Рисует Землеявность
         graphics.drawLine(x1,y1,x2,y2); // Рисует Землеявность

        // Координаты нулевой линии - градус асцендента на промежуточном
        // между 2 и 3 кругом для вывода Знаков Зодиака в центре
            int xCentrDom,yCentrDom;
            xCentrDom=(int) (x-1.25*delta);
            yCentrDom=y+(diametrMinCircle)/2;
            //screen.drawOval(xCentrDom,yCentrDom,2,2);

        /*
        //находим половину величины в градусах первого дома для центровки
        int halfHouse;
        halfHouse=Math.abs((int)ZTLib.swe_difdeg2n(koordHouses[1],koordHouses[2]))/2;
        x1=(int) (xCentrDom+(diametr3Circle/2+delta/4)-((diametr3Circle/2+delta/4)*
                Math.cos(halfHouse*ZTLib.DEGTORAD)));
        y1= (int) (yCentrDom+((diametr3Circle/2+delta/4)*
                Math.sin(halfHouse*ZTLib.DEGTORAD)));
        x1=(x1-deltaXFont); y1=(y1+deltaYFont);
      //  screen.drawString("\u0031", (x1), (y1)); // 1 дом
          */

        for (int i=0;i<=3;i++)  // Рисует  линии для остальных домов
        {
            //koordHousesRelativ[i]=Math.abs(ZTLib.swe_degnorm(koordHouses[i]-koordHouses[1]));
            koordZTRelativ[i]=Math.abs(ZTLib.swe_degnorm(koordZT[i]-koordZT[1]));
        x1=(int) (x0+(diametr3Circle/2)-((diametr3Circle/2)*
                Math.cos(koordZTRelativ[i]*ZTLib.DEGTORAD)));
        y1= (int) (y0+((diametr3Circle/2)*
                Math.sin(koordZTRelativ[i]*ZTLib.DEGTORAD)));
        x2=(int) (x00+(diametr4Circle/2)-((diametr4Circle/2)*
                Math.cos(koordZTRelativ[i]*ZTLib.DEGTORAD)));
        y2= (int) (y00+((diametr4Circle/2)*
                Math.sin(koordZTRelativ[i]*ZTLib.DEGTORAD)));
        g.drawLine(x1,y1,x2,y2);
        graphics.drawLine(x1,y1,x2,y2);

            switch(i)
        {
            case 0:{g.drawString("\u00C1", x2-fontW/2, y2-fontH/2);graphics.drawString("\u00C1", x2-fontW/2, y2-fontH/2)
                    ; break;} //   Землецель
            case 1:{g.drawString("\u00D0", x2-2*fontW, y2+fontH/2);graphics.drawString("\u00D0", x2-2*fontW, y2+fontH/2)
                    ; break;}  //     Землеявность
            case 2:{g.drawString("\u005B", x2-fontW/2, (int)(y2+(1.25*fontH)));graphics.drawString("\u005B", x2-fontW/2, (int)(y2+(1.25*fontH)))
                    ; break;}    //   Землебаза
            case 3:{g.drawString("\u00B5", x2+fontW/2, y2+fontH/2);graphics.drawString("\u00B5", x2+fontW/2, y2+fontH/2)
                    ; break;}//            Землетыл
        }

        // Наносит Землеточки на внутренний круг
            // Координаты нулевой линии на внутреннем круге

               int y2=y+(diametrMinCircle)/2;

            x1=(int) (x+(diametrMinCircle/2)-((diametrMinCircle/2)*
                    Math.cos(koordZTRelativ[i]*ZTLib.DEGTORAD)));
            y1= (int) (y2+((diametrMinCircle/2)*
                    Math.sin(koordZTRelativ[i]*ZTLib.DEGTORAD)));

            g.setColor(Color.red);
            graphics.setColor(Color.red);
            g.fillOval(x1,y1,5,5);// Наносит Землеточки на внутренний круг кругом
            graphics.fillOval(x1,y1,5,5);// Наносит Землеточки на внутренний круг кругом
            //screen.drawOval(x1,y1,3,3); //// Наносит Землеточки на внутренний круг кругом

        }
      }

      public void drawZodiak(Graphics g)
      {
          // Координаты нулевой линии - градус асцендента на промежуточном
          // между 2 и 3 кругом для вывода Знаков Зодиака в центре
//              int xCentrZnak,yCentrZnak;
  //            xCentrZnak=(int) (x-0.75*delta);
    //          yCentrZnak=y+(diametrMinCircle)/2;
              //screen.drawOval(xCentrZnak,yCentrZnak,2,2);

          // Координаты нулевой линии - промежуточном
          // между 1 и 2 кругом для вывода Планет в центре
              //int xCentrPlanet,yCentrPlanet;
              xCentrPlanet=(int) (x-0.25*delta);
              yCentrPlanet=y+(diametrMinCircle)/2;
              //screen.drawOval(xCentrPlanet,yCentrPlanet,2,2);


          // Координаты нулевой линии - градус асцендента на 2 и 3 круге (Знаки Зодиака)
              x1=x0=x-delta;
              y1=y0=y+(diametrMinCircle)/2;

              x2=x00=x-delta/2;
              y2=y00=y+(diametrMinCircle)/2;
              g.setColor(Color.black);
              graphics.setColor(Color.black);
              //screen.drawLine(x1,y1,x2,y2);

         // Находим смещение начала овна (360) от асц - koordHouses[0]
          //double deltaAries=360-koordHouses[1]; // не koordHouses[0] так как первый дом это koordHouses[1]
          deltaAries=360-koordZT[1]; //

          //Координаты линий для 0 овна
          x1=(int) (x1+(diametr3Circle/2)-((diametr3Circle/2)*
                  Math.cos(deltaAries*ZTLib.DEGTORAD)));
          y1= (int) (y1+((diametr3Circle/2)*
                  Math.sin(deltaAries*ZTLib.DEGTORAD)));
          x2=(int) (x2+(diametr2Circle/2)-((diametr2Circle/2)*
                  Math.cos(deltaAries*ZTLib.DEGTORAD)));
          y2= (int) (y2+((diametr2Circle/2)*
                  Math.sin(deltaAries*ZTLib.DEGTORAD)));
          g.setColor(Color.blue);
          graphics.setColor(Color.blue);
          g.drawLine(x1,y1,x2,y2); // Рисует 0 Овна
          graphics.drawLine(x1,y1,x2,y2); // Рисует 0 Овна

          // Выводит символ Овна
          x1=(int) (xCentrZnak+(diametr3Circle/2-delta/4)-((diametr3Circle/2-delta/4)*
                  Math.cos((deltaAries+15)*ZTLib.DEGTORAD)));
          y1= (int) (yCentrZnak+((diametr3Circle/2-delta/4)*
                  Math.sin((deltaAries+15)*ZTLib.DEGTORAD)));
          g.drawString("\u00A4", (x1-deltaXFont), (y1+deltaYFont)); //Овен
          graphics.drawString("\u00A4", (x1-deltaXFont), (y1+deltaYFont)); //Овен


          double[] znacZodiak;
          znacZodiak=new double[] {0,30,60,90,120,150,180,210,240,270,300,330};

          for (int i=1;i<=11;i++)  // Рисует  линии для остальных Знаков
          {
              znacZodiak[i]=znacZodiak[i]-(360-deltaAries);
          x1=(int) (x0+(diametr3Circle/2)-((diametr3Circle/2)*
                  Math.cos(znacZodiak[i]*ZTLib.DEGTORAD)));
          y1= (int) (y0+((diametr3Circle/2)*
                  Math.sin(znacZodiak[i]*ZTLib.DEGTORAD)));
          x2=(int) (x00+(diametr2Circle/2)-((diametr2Circle/2)*
                  Math.cos(znacZodiak[i]*ZTLib.DEGTORAD)));
          y2= (int) (y00+((diametr2Circle/2)*
                  Math.sin(znacZodiak[i]*ZTLib.DEGTORAD)));
              //screen.setColor(Color.blue);
          g.drawLine(x1,y1,x2,y2);
          graphics.drawLine(x1,y1,x2,y2);
          // Наносим знаки зодиака
          x1=(int) (xCentrZnak+(diametr3Circle/2-delta/4)-((diametr3Circle/2-delta/4)*
                  Math.cos((znacZodiak[i]+15)*ZTLib.DEGTORAD)));
          y1= (int) (yCentrZnak+((diametr3Circle/2-delta/4)*
                  Math.sin((znacZodiak[i]+15)*ZTLib.DEGTORAD)));
           //screen.drawOval(x1,y1,2,2);
             x1=x1-deltaXFont;y1=y1+deltaYFont; //Поправки для центрирования шрифта
           switch(i)
           {

               case 1:{g.drawString("\u00A7", x1, y1);graphics.drawString("\u00A7", x1, y1)
                       ; break;}//   Телец
               case 2:{g.drawString("\u00A9", x1, y1);graphics.drawString("\u00A9", x1, y1)
                       ; break;}  //      Близнец
               case 3:{g.drawString("\u00AD", x1, y1);graphics.drawString("\u00AD", x1, y1)
                       ; break;}    //           Рак
               case 4:{g.drawString("\u00AF", x1, y1);graphics.drawString("\u00AF", x1, y1)
                       ; break;}//Лев
               case 5:{g.drawString("\u00B3", x1, y1);graphics.drawString("\u00B3", x1, y1)
                       ; break;}  // Дева
               case 6:{g.drawString("\u00B6", x1, y1);graphics.drawString("\u00B6", x1, y1)
                       ; break;}    //   Весы
               case 7:{g.drawString("\u00B8", x1, y1);graphics.drawString("\u00B8", x1, y1)
                       ; break;}//           Скорпион
               case 8:{g.drawString("\u00BE", x1, y1);graphics.drawString("\u00BE", x1, y1)
                       ; break;}  //Стрелец
               case 9:{g.drawString("\u00BF", x1, y1);graphics.drawString("\u00BF", x1, y1)
                       ; break;}    //Козерог
               case 10:{g.drawString("\u00C3", x1, y1);graphics.drawString("\u00C3", x1, y1)
                       ; break;}//          Водолей
               case 11:{g.drawString("\u00C5", x1, y1);graphics.drawString("\u00C5", x1, y1)
                       ; break;}  // Рыбы

           }
          }

      }

  public void drawPlanet(Graphics g)
  {
      //Найти нулевую точку на малом круге
      // Координаты нулевой линии  на 1 круге
          x1=x0=x;
          y1=y0=y+(diametrMinCircle/2);
          //screen.drawOval(x1,y1,2,2);
      //  Но ее реальная координата - deltaAries

      //double[] planetKoordWithDelta=new double[planetCount]; //Для координат планет со смещением
      double[] planetKoordWithDeltaStellium=new double[planetCount]; //Для координат планет со смещением
                                                                     // если есть стеллиум (разводка)

      //Заполняем массив для разводки планет, которые слипаются при графике
      int [][] planetGrafikKoord=new int [planetCount][2];
      for (int i=0;i<=planetCount-1;i++)  //
      {
          planetKoordWithDelta[i]=planetKoordWithDeltaStellium[i]=
                  ZTLib.swe_degnorm(planetKoord[i]-(360-deltaAries));
          x1=(int) (xCentrPlanet+(diametrMinCircle/2+delta/4)-((diametrMinCircle/2+delta/4)*
                  Math.cos((planetKoordWithDelta[i])*ZTLib.DEGTORAD)));
          y1= (int) (yCentrPlanet+((diametrMinCircle/2+delta/4)*
                  Math.sin((planetKoordWithDelta[i])*ZTLib.DEGTORAD)));

         planetGrafikKoord[i][0]=x1;
         planetGrafikKoord[i][1]=y1;
      }
      //Разводка
      int normaForDrawing=6
              ; //Норма разницы между планетами чтобы они рисовались раздельно подбирается экспер
      int deltaKoord;
      //int n=0;
      double plNonSort[]= new double[25];
      double plSort[]= new double[25];
/*
      for (int i=0;i<=planetCount-1;i++)  //
          for (int j = i + 1; j <= planetCount - 1; j++)  //
          {
              //Разница в дианазоне до 180 для учета перехода рыбы-овен
            //  deltaKoord = (int) ZTLib.swe_difdeg2n(planetKoordWithDeltaStellium[i], planetKoordWithDeltaStellium[j]);
            //  if (Math.abs(deltaKoord) < normaForDrawing)
            //  {
             //     System.out.println(i + "- " + j + " deltaKoord=" + deltaKoord);
                  if (i!=10)
                  {
                  plSort[i]=plNonSort[i]=planetKoordWithDeltaStellium[i];
                  plSort[j]=plNonSort[j]=planetKoordWithDeltaStellium[j];
                  }
             // }
          }
  */
      for (int i=0;i<=planetCount-1;i++)  //
      {
                  if (i!=10)
                  plSort[i]=plNonSort[i]=planetKoordWithDeltaStellium[i];

          }

        Arrays.sort(plSort);
      //Имееется два массива - один отсортированный, другой нет
      // Массив для записи номеров планет, предназначенных к разводке, отсортированный начиная с меньшей координаты
      int[] numPlanetforBreak= new int[planetCount+6];
      n=0;
      for (int i=0;i<=plSort.length-1;i++)
      {
          if (plSort[i] !=0)
          {
          //System.out.println("** plSort[i] "+i+ "  "+plSort[i] );
         for (int j=0;j<=plNonSort.length-1;j++)
         {

             if (plSort[i]==plNonSort[j])
             {
             //System.out.println("* plNonSort[j] "+j+ "  "+planetKoord[j]);

             // Заполняем массив номеров планет, предназначенных к разводке (отсортирован с меньшей координаты)
                 // В массив не вставляем 10 - ср.восх узел - так как он не выводится
///                 if (j!=10)
                 numPlanetforBreak[n]=j; //номер планеты, предназначенный к разводке
                 n++;
             }
         }
          }
      }

      double deltaKoordDoub=0;

      //Преобразуем numPlanetforBreak если есть случай что стеллиум имеет начало в последних гр круга и захватывает первые
      //Разность между первой упоряд планетой и последней
      deltaKoordDoub = Math.abs(ZTLib.swe_difdeg2n(planetKoordWithDeltaStellium[numPlanetforBreak[0]],planetKoordWithDeltaStellium[numPlanetforBreak[11]]));


      if (deltaKoordDoub< normaForDrawing+3) //если эта разница меньше наступает //1,3 ...  355,357
      {
          // Если первый элемент numPlanetforBreak меньше чем normaForDrawing+3 то такой случай может быть
          //1,3 ...  355,357
          //а должно быть в отссорт 355,357,1,3... тогда разведутся корректно
          // Начинаем проверку с 3 с конца
          double first,next;
          int [] newStell=new int[]{55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55};
          int i=0;
          first=planetKoordWithDeltaStellium[numPlanetforBreak[numPlanetforBreak.length-7-3]];
          next=planetKoordWithDeltaStellium[numPlanetforBreak[numPlanetforBreak.length-7-2]];
          deltaKoordDoub =  ZTLib.swe_difdeg2n(next, first);
          if (Math.abs(deltaKoordDoub) < normaForDrawing+3)
          {
              //входит в стеллиум
              newStell[i]=numPlanetforBreak[numPlanetforBreak.length-3];i++;
              newStell[i]=numPlanetforBreak[numPlanetforBreak.length-2];i++;
          }
          first=planetKoordWithDeltaStellium[numPlanetforBreak[numPlanetforBreak.length-7-2]];
          next=planetKoordWithDeltaStellium[numPlanetforBreak[numPlanetforBreak.length-7-1]];
          deltaKoordDoub =  ZTLib.swe_difdeg2n(next, first);
          if (Math.abs(deltaKoordDoub) < normaForDrawing+3)
          {
              //входит в стеллиум
              newStell[i]=numPlanetforBreak[numPlanetforBreak.length-7-2];i++;
              newStell[i]=numPlanetforBreak[numPlanetforBreak.length-7-1];i++;

          }
          /*
          first=planetKoordWithDeltaStellium[numPlanetforBreak[numPlanetforBreak.length-1]];
          next=planetKoordWithDeltaStellium[numPlanetforBreak[numPlanetforBreak.length]];
          deltaKoordDoub =  ZTLib.swe_difdeg2n(next, first);
          if (Math.abs(deltaKoordDoub) < normaForDrawing+3)
          {
              //входит в стеллиум
              newStell[4]=numPlanetforBreak[numPlanetforBreak.length-1];
              newStell[5]=numPlanetforBreak[numPlanetforBreak.length];

          } */
          first=planetKoordWithDeltaStellium[numPlanetforBreak[0]];
          next=planetKoordWithDeltaStellium[numPlanetforBreak[1]];
          deltaKoordDoub =  ZTLib.swe_difdeg2n(next, first);
          if (Math.abs(deltaKoordDoub) < normaForDrawing+3)
          {
              //входит в стеллиум
              newStell[i]=numPlanetforBreak[0];i++;
              newStell[i]=numPlanetforBreak[1];i++;

          }
          first=planetKoordWithDeltaStellium[numPlanetforBreak[1]];
          next=planetKoordWithDeltaStellium[numPlanetforBreak[2]];
          deltaKoordDoub =  ZTLib.swe_difdeg2n(next, first);
          if (Math.abs(deltaKoordDoub) < normaForDrawing+3)
          {
              //входит в стеллиум
              newStell[i]=numPlanetforBreak[1];i++;
              newStell[i]=numPlanetforBreak[2];i++;

          }
          first=planetKoordWithDeltaStellium[numPlanetforBreak[2]];
          next=planetKoordWithDeltaStellium[numPlanetforBreak[3]];
          deltaKoordDoub =  ZTLib.swe_difdeg2n(next, first);
          if (Math.abs(deltaKoordDoub) < normaForDrawing+3)
          {
              //входит в стеллиум
              newStell[i]=numPlanetforBreak[2];i++;
              newStell[i]=numPlanetforBreak[3];
              i++;

          }
           int[] numPlanetForBreakNew=new int[numPlanetforBreak.length];
          //массив newStell
          int j=0;
          for (int i1=0;i1<=newStell.length-1;i1++)
          {

              if (newStell[i1]!=55)
              {
                numPlanetForBreakNew[i1]=newStell[i1];
                  j++;
              }

          }   //for (int i1=0;i1<=newStell.length;i1++)

          for (int i1=0;i1<=numPlanetForBreakNew.length-1-j;i1++)
          {
                numPlanetForBreakNew[i1+j]=numPlanetforBreak[i1];

          }
          for (int i1=0;i1<=numPlanetforBreak.length-1;i1++) numPlanetforBreak[i1]=55;

         numPlanetforBreak=numPlanetForBreakNew;

         //Выкидываем парные
          int [] newStell1=new int[numPlanetforBreak.length-1];//{55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55,55};
          boolean exist=false;
          for (int i1=0;i1<=numPlanetforBreak.length-1;i1++)
          {

              for (int j1=0;j1<=newStell1.length-1;j1++)
              {
                 if (numPlanetforBreak[i1]==newStell1[j1])
                  {
                     exist=true; //есть
                     break;
                  }  else
                     exist=false;
                      //newStell1[j1]=numPlanetforBreak[j1];
              }

              if (exist==false)
              newStell1[i1]=numPlanetforBreak[i1];

          }

         numPlanetforBreak=newStell1;
      } //  //Преобразуем numPlanetforBreak если есть случай что стеллиум имеет начало в последних гр круга и захватывает первые



      // Но основе отсортированного массива разводим планеты
     for(int i=0;i<=numPlanetforBreak.length-2;i++)
     {
         //if (numPlanetforBreak[i]==55) continue;
         int j= numPlanetforBreak[i];
          int next=numPlanetforBreak[i+1];

         if (next!=j)// иначе идут нули (next=0 j=0) а это приводит к смещению солнца
         {
          /// if (((j!=10))) //Чтобы не разводить средний и истинный узлы - так как ср. не выводится на графику
           ///{
           ///    if (((next!=10))) //Чтобы не разводить средний и истинный узлы - так как ср. не выводится на графику
              /// {
         //Разница в дианазоне до 180 для учета перехода рыбы-овен
         deltaKoordDoub =  ZTLib.swe_difdeg2n(planetKoordWithDeltaStellium[j], planetKoordWithDeltaStellium[next]);

         if (Math.abs(deltaKoordDoub) < normaForDrawing+3)
         {
             // Разводим слипшуюся планету
             if (deltaKoordDoub>=0) // Это значит при расталкивании стелиума пред. планета передвинулась
                                 // за эту и ее нужно толкнуть еще дальше
             {
                 deltaKoordDoub=Math.abs(deltaKoordDoub)+normaForDrawing;
             planetKoordWithDeltaStellium[next] = planetKoordWithDeltaStellium[next] + Math.abs(deltaKoordDoub);
             } else
               planetKoordWithDeltaStellium[next] = planetKoordWithDeltaStellium[next] + (normaForDrawing-Math.abs(deltaKoordDoub))
                 ;
             // Переопределяем ее координаты
             x1 = (int) (xCentrPlanet + (diametrMinCircle / 2 + delta / 4) - ((diametrMinCircle / 2 + delta / 4) *
                     Math.cos((planetKoordWithDeltaStellium[next]) * ZTLib.DEGTORAD)));
             y1 = (int) (yCentrPlanet + ((diametrMinCircle / 2 + delta / 4) *
                     Math.sin((planetKoordWithDeltaStellium[next]) * ZTLib.DEGTORAD)));

             planetGrafikKoord[next][0] = x1;
             planetGrafikKoord[next][1] = y1;
         }
        ///   }
       ///    }
         }
  } //// На основе отсортированного массива разводим планеты


         for (int i=0;i<=planetCount-1;i++)  //
         {
             //planetKoordWithDelta[i]=planetKoord[i]-(360-deltaAries);       !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
             //Наносит точки на внутренний круг

             x1=(int) (x0+(diametrMinCircle/2)-((diametrMinCircle/2)*
                     Math.cos(planetKoordWithDelta[i]*ZTLib.DEGTORAD)));
             y1= (int) (y0+((diametrMinCircle/2)*
                     Math.sin(planetKoordWithDelta[i]*ZTLib.DEGTORAD)));

             g.setColor(Color.black);
             graphics.setColor(Color.black);
             if (i!=10)  //не рисуем ср лунный узел
             {
             g.fillOval(x1,y1,4,4);  //Рисует истинную позицию планеты на внутреннем круге
             graphics.fillOval(x1,y1,4,4);  //Рисует истинную позицию планеты на внутреннем круге
             x1=planetGrafikKoord[i][0];
             y1=planetGrafikKoord[i][1];
             }
             //screen.drawOval(x1,y1,2,2);
             x1=x1-deltaXFont;y1=y1+deltaYFont; //Поправки для центрирования шрифта
             switch(i)
             {
                 case 0:{g.drawString("\u005D", x1, y1);graphics.drawString("\u005D", x1, y1)
                         ; break;}//   Солнце
                 case 1:{g.drawString("\u005E", x1, y1);graphics.drawString("\u005E", x1, y1)
                         ; break;}//   Луна
                 case 2:{g.drawString("\u0061", x1, y1);graphics.drawString("\u0061", x1, y1)
                         ; break;}  //     Меркурий
                 case 3:{g.drawString("\u0063", x1, y1);graphics.drawString("\u0063", x1, y1)
                         ; break;}    //           Венера
                 case 4:{g.drawString("\u0067", x1, y1);graphics.drawString("\u0067", x1, y1)
                         ; break;}//Марс
                 case 5:{g.drawString("\u006A", x1, y1);graphics.drawString("\u006A", x1, y1)
                         ; break;}  // Юпитер
                 case 6:{g.drawString("\u006D", x1, y1);graphics.drawString("\u006D", x1, y1)
                         ; break;}    //   Сатурн
                 case 7:{g.drawString("\u006F", x1, y1);graphics.drawString("\u006F", x1, y1)
                         ; break;}//           Уран
                 case 8:{g.drawString("\u0072", x1, y1);graphics.drawString("\u0072", x1, y1)
                         ; break;}  //Нептун
                 case 9:{g.drawString("\u0076", x1, y1);graphics.drawString("\u0076", x1, y1)
                         ; break;}  //Плутон
               //case 10:screen.drawString("\u00F1", x1, y1); break;    //Средний узел
                 case 11:{g.drawString("\u00F1", x1, y1);graphics.drawString("\u00F1", x1, y1)
                         ; break;}//         Истинный узел
                 case 12:{g.drawString("\u0178", x1, y1);graphics.drawString("\u0178", x1, y1)
                         ; break;}  // Черная луна
                     /*
              case 12:screen.drawString("\u00FE", x1, y1); break;  // osc Apogee
              case 13:screen.drawString("\u0064", x1, y1); break;  // Земля
              case 14:screen.drawString("\u0078", x1, y1); break;  // Хирон
              case 15:screen.drawString("\u00C5", x1, y1); break;  // Фолус
              case 16:screen.drawString("\u00C5", x1, y1); break;  // Церрера
              case 17:screen.drawString("\u00C5", x1, y1); break;  // Паллада
              case 18:screen.drawString("\u00C5", x1, y1); break;  // Юнона
              case 19:screen.drawString("\u00C5", x1, y1); break;  // Веста
                */
             }
         }
  }
      public void drawAspectPl(Graphics g)
      {
          // Рисуем аспекты планет
          g.setColor(Color.CYAN);
          graphics.setColor(Color.CYAN);
          for(int i=0;i<=planetCount-1;i++)
          {
              for(int j=0;j<=planetCount-1;j++)
              {

               if (aspectPlanetPlanet[i][j]!=0)
               {
                   x1=(int) (x0+(diametrMinCircle/2)-((diametrMinCircle/2)*
                           Math.cos(planetKoordWithDelta[i]*ZTLib.DEGTORAD)));
                   y1= (int) (y0+((diametrMinCircle/2)*
                           Math.sin(planetKoordWithDelta[i]*ZTLib.DEGTORAD)));
                   x2=(int) (x0+(diametrMinCircle/2)-((diametrMinCircle/2)*
                           Math.cos(planetKoordWithDelta[j]*ZTLib.DEGTORAD)));
                   y2= (int) (y0+((diametrMinCircle/2)*
                           Math.sin(planetKoordWithDelta[j]*ZTLib.DEGTORAD)));
                 g.drawLine(x1,y1,x2,y2);
                 graphics.drawLine(x1,y1,x2,y2);
               }
              }
          }

      }
      public void drawAspectPlZt(Graphics g)
      {
          for(int i=0;i<=planetCount-1;i++)
          {
              for(int j=0;j<=3;j++)
              {

               if (aspectPlanetZT[i][j]!=0)
               {
                   String symbolAspect=setSymbolAspect(gradusPlanetZT[i][j]);
                   x1=(int) (x0+(diametrMinCircle/2)-((diametrMinCircle/2)*
                           Math.cos(planetKoordWithDelta[i]*ZTLib.DEGTORAD)));
                   y1= (int) (y0+((diametrMinCircle/2)*
                           Math.sin(planetKoordWithDelta[i]*ZTLib.DEGTORAD)));
                   x2=(int) (x0+(diametrMinCircle/2)-((diametrMinCircle/2)*
                           Math.cos(koordZTRelativ[j]*ZTLib.DEGTORAD)));
                   y2= (int) (y0+((diametrMinCircle/2)*
                           Math.sin(koordZTRelativ[j]*ZTLib.DEGTORAD)));
                  //screen.setColor(Color.green);
                  //screen.drawLine(x1,y1,x2,y2);
                  drawDashedLine(g,x1,y1,x2,y2,6,5,Color.green);
                  setKoordSymbolAspect(x1,y1,x2,y2); // находит центр линии для вывода туда символа аспекта

                  g.drawString(symbolAspect,x1-fontW/2,y1+fontH/2);
                  graphics.drawString(symbolAspect,x1-fontW/2,y1+fontH/2);
               }
              }
          }// Рисуем аспекты планеты-землеточки

      }
      public void drawPosInTable(Graphics g)
      {

          Font strongFont = new Font("Arial", Font.BOLD, 15);
          g.setColor(Color.blue);
          graphics.setColor(Color.blue);

          xTab=x-delta+diametr4Circle+7;
          yTab=y-delta;

          g.setFont(strongFont);
          graphics.setFont(strongFont);

          g.drawString("Землеточки            Аспекты к ним",xTab,yTab);
          graphics.drawString("Землеточки            Аспекты к ним",xTab,yTab);

          g.drawLine(xTab,yTab-16,getSize().width-12,yTab-16); //Линию рисует только 1 раз
          graphics.drawLine(xTab,yTab-16,getSize().width-12,yTab-16); //Линию рисует только 1 раз
          g.drawLine(xTab,yTab+6,getSize().width-12,yTab+6); //Линию рисует только 1 раз
          graphics.drawLine(xTab,yTab+6,getSize().width-12,yTab+6); //Линию рисует только 1 раз

          yTab=yTab+fontH+6; //переопределили yTab

          g.setFont(myFont());
          graphics.setFont(myFont());



          g.drawLine(xTab-6,yTab-(2*fontH)-2,xTab-6,getSize().height-12);
          graphics.drawLine(xTab-6,yTab-(2*fontH)-2,xTab-6,getSize().height-12);
          g.drawLine(xTab-6+fontW*11,yTab-(2*fontH)-2,xTab-6+fontW*11,getSize().height-12);
          graphics.drawLine(xTab-6+fontW*11,yTab-(2*fontH)-2,xTab-6+fontW*11,getSize().height-12);

          Font strongFont2 = new Font("Arial", Font.BOLD, 10);
          for (int i=0;i<=3;i++)  // Выводим информацию по Землеточкам
          {
              posFrac=geol.ztl.swe_split_deg1(koordZT[i],8);
              switch(i)
              {
                  case 0:
                  {
                      g.setFont(myFont());
                      graphics.setFont(myFont());
                      g.drawString
                      ("\u00C1 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+
                      posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1),xTab,yTab);
                      graphics.drawString
                      ("\u00C1 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+
                      posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1),xTab,yTab);
                      g.drawString(setStringAspectZTPlanet(i),xTab+(11*fontW),yTab);// перечисляются аспекты землеточки
                      graphics.drawString(setStringAspectZTPlanet(i),xTab+(11*fontW),yTab);// перечисляются аспекты землеточки
                      g.setFont(strongFont2);
                      graphics.setFont(strongFont2);
                      g.drawString("З-цель ",xTab-57,yTab);// перечисляются аспекты землеточки
                      graphics.drawString("З-цель ",xTab-57,yTab);// перечисляются аспекты землеточки

//                      g.drawString(setStringAspectZTPlanet(i),xTab+(11*fontW),yTab);// перечисляются аспекты землеточки
  //                    graphics.drawString(setStringAspectZTPlanet(i),xTab+(11*fontW),yTab);// перечисляются аспекты землеточки
    //                  g.setFont(strongFont2);
      //                graphics.setFont(strongFont2);
        //              g.drawString("З-цель ",xTab-57,yTab);// перечисляются аспекты землеточки
          //            graphics.drawString("З-цель ",xTab-57,yTab);// перечисляются аспекты землеточки


                  }
                      break;
                  case 1:
                  {
                      g.setFont(myFont());
                      graphics.setFont(myFont());
                      g.drawString
                          ("\u00d0 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+
                                  posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1),xTab,yTab+i*fontH);
                      graphics.drawString
                          ("\u00d0 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+
                                  posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1),xTab,yTab+i*fontH);

                      g.drawString(setStringAspectZTPlanet(i),xTab+(11*fontW),yTab+i*fontH);// перечисляются аспекты землеточки
                      graphics.drawString(setStringAspectZTPlanet(i),xTab+(11*fontW),yTab+i*fontH);// перечисляются аспекты землеточки
                      g.setFont(strongFont2);
                      graphics.setFont(strongFont2);
                      g.drawString("З-явность ",xTab-57,yTab+i*fontH);// перечисляются аспекты землеточки
                      graphics.drawString("З-явность ",xTab-57,yTab+i*fontH);// перечисляются аспекты землеточки


                  }
                      break;
                  case 2:
                  {
                      g.setFont(myFont());
                     graphics.setFont(myFont());
                      g.drawString
                          ("\u005b "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"
                                  + posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1),xTab,yTab+i*fontH);
                      graphics.drawString
                          ("\u005b "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"
                                  + posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1),xTab,yTab+i*fontH);
                      g.drawString(setStringAspectZTPlanet(i),xTab+(11*fontW),yTab+i*fontH);// перечисляются аспекты землеточки
                      graphics.drawString(setStringAspectZTPlanet(i),xTab+(11*fontW),yTab+i*fontH);// перечисляются аспекты землеточки
                      g.setFont(strongFont2);
                      graphics.setFont(strongFont2);
                      g.drawString("З-база ",xTab-57,yTab+i*fontH);// перечисляются аспекты землеточки
                      graphics.drawString("З-база ",xTab-57,yTab+i*fontH);// перечисляются аспекты землеточки

                  }
                      break;
                  case 3:
                  {
                      g.setFont(myFont());
                      graphics.setFont(myFont());
                      g.drawString
                          ("\u00b5 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+
                                  posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1),xTab,yTab+i*fontH);
                      graphics.drawString
                          ("\u00b5 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+
                                  posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1),xTab,yTab+i*fontH);

                      g.drawString(setStringAspectZTPlanet(i),xTab+(11*fontW),yTab+i*fontH);// перечисляются аспекты землеточки
                      graphics.drawString(setStringAspectZTPlanet(i),xTab+(11*fontW),yTab+i*fontH);// перечисляются аспекты землеточки
                      g.setFont(strongFont2);
                      graphics.setFont(strongFont2);
                      g.drawString("З-тыл ",xTab-57,yTab+i*fontH);// перечисляются аспекты землеточки
                      graphics.drawString("З-тыл ",xTab-57,yTab+i*fontH);// перечисляются аспекты землеточки

                  }
                      break;
              }
          }


          n=5; // Выведено уже 4 землеточки
          g.setFont(strongFont);
          graphics.setFont(strongFont);
          positionPlanet(0,n); // переопределяет координаты  x1 y1

          g.drawLine(x1,y1-15,getSize().width-12,y1-15); //Линию рисует только 1 раз
          graphics.drawLine(x1,y1-15,getSize().width-12,y1-15); //Линию рисует только 1 раз
          g.drawString("Планеты                 Аспекты между планетами",x1,y1);
          graphics.drawString("Планеты                 Аспекты между планетами",x1,y1);
          n=n+1;//Плюс надпись

          g.setFont(myFont());
          graphics.setFont(myFont());

          for (int i=0;i<=20;i++)
          {
              posFrac=geol.ztl.swe_split_deg1(planetKoord[i],8);
              //System.out.println(
              //geol.sw.swe_get_planet_name(i)+":"+"\t"+
                //      posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
                  //    geol.numToNameZodiak(posFrac[0]+1));

              positionPlanet(i,n); // переопределяет координаты  x1 y1
              if (i==0)
              g.drawLine(x1,y1-15,getSize().width-12,y1-15); //Линию рисует только 1 раз
              graphics.drawLine(x1,y1-15,getSize().width-12,y1-15); //Линию рисует только 1 раз

              switch(i)
              {
                  case 0:
                  {
                      g.drawString("\u005D "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      g.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                      graphics.drawString("\u005D "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      graphics.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты


                  }
                      break;//   Солнце
                  case 1:
                  {
                      g.drawString("\u005E "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      g.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                      graphics.drawString("\u005E "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      graphics.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты

                  }
                      break;//   Луна
                  case 2:
                  {
                      g.drawString("\u0061 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      g.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                      graphics.drawString("\u0061 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      graphics.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты

                  }
                      break;  //     Меркурий
                  case 3:
                  {
                      g.drawString("\u0063 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      g.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                      graphics.drawString("\u0063 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      graphics.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты

                  }
                      break;    //           Венера
                  case 4:
                  {
                      g.drawString("\u0067 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      g.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                      graphics.drawString("\u0067 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      graphics.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты

                  }
                      break;//Марс
                  case 5:
                  {
                      g.drawString("\u006A "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      g.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                      graphics.drawString("\u006A "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      graphics.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты

                  }
                      break;  // Юпитер
                  case 6:
                  {
                      g.drawString("\u006D "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      g.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                      graphics.drawString("\u006D "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      graphics.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты

                  }
                      break;    //   Сатурн
                  case 7:
                  {
                      g.drawString("\u006F "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      g.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                      graphics.drawString("\u006F "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      graphics.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                  }
                      break;//           Уран
                  case 8:
                  {
                      g.drawString("\u0072 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      g.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                      graphics.drawString("\u0072 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      graphics.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                  }
                      break;  //Нептун
                  case 9:
                  {
                      g.drawString("\u0076 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      g.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                      graphics.drawString("\u0076 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      graphics.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                  }
                      break;  //Плутон
                  case 10:
                  {
                      g.drawString("\u00F1 M"+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      g.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                      graphics.drawString("\u00F1 M"+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      graphics.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                  }
                      break;    //Средний узел
                  case 11:
                  {
                      g.drawString("\u00F1 T"+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      g.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                      graphics.drawString("\u00F1 T"+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      graphics.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                  }
                      break;//         Истинный узел
                  case 12:
                  {
                      g.drawString("\u0178 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      g.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                      graphics.drawString("\u0178 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      graphics.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                  }
                      break;  // Черная луна
                      /*
               case 12:g.drawString("\u00FE "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;  // osc Apogee
               case 13:g.drawString("\u0064 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;  // Земля
               case 14:g.drawString("\u0078 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;  // Хирон
               case 15:g.drawString("\u00C5 " +posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;  // Фолус
               case 16:g.drawString("\u00C5 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;  // Церрера
               case 17:g.drawString("\u00C5 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;  // Паллада
               case 18:g.drawString("\u00C5 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;  // Юнона
               case 19:g.drawString("\u00C5 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;  // Веста
                 */
              }
          }

          if (drawDomInTable)
          {
          n=n+13;// Выводилось еще 13 планет
              g.setFont(strongFont);
              graphics.setFont(strongFont);
              positionPlanet(1,n); // переопределяет координаты  x1 y1

              g.drawLine(x1,y1-15,getSize().width-12,y1-15);
              g.drawString("Дома",x1,y1);
              graphics.drawLine(x1,y1-15,getSize().width-12,y1-15);
              graphics.drawString("Дома",x1,y1);

              n=n+1;//Плюс надпись

              g.setFont(myFont());
              graphics.setFont(myFont());

          // Заполняем в соотв переменную, так как процедура заполняет в единую house[]
          for (int i=1;i<=6;i++)
          {
           koordHouses[i]=geol.house[i];
           posFrac=geol.ztl.swe_split_deg1(koordHouses[i],8);
//              System.out.println(
  //            ":Дом "+ i + "    "+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
    //                  geol.numToNameZodiak(posFrac[0]+1));
              positionPlanet(i,n); // переопределяет координаты  x1 y1
              if (i==1)
              g.drawLine(x1,y1-15,getSize().width-12,y1-15);
              graphics.drawLine(x1,y1-15,getSize().width-12,y1-15);
              switch(i)
              {
                  case 1:
                  {
                      g.drawString("\u201E "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      graphics.drawString("\u201E "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                  }
                           break;//
                  case 2:
                  {
                      g.drawString("\u00eb "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                     graphics.drawString("\u00eb "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                  }
                      break;  //
                  case 3:
                  {
                      g.drawString("\u00ec "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      graphics.drawString("\u00ec "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                  }
                      break;    //
                  case 4:
                  {
                      g.drawString("\u02c6 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      graphics.drawString("\u02c6 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                  }
                      break;//
                  case 5:
                  {
                      g.drawString("\u2030 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      graphics.drawString("\u2030 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                  }
                  break;  //
                  case 6:
                  {
                      g.drawString("\u00ab "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                      graphics.drawString("\u00ab "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                  }
                   break;    //
              }
          }
          }

      }
      //Из comment вытаскиваем name
      //Солнечное_затмение_1_перед_M_5.4__New_Britain_region__Papua_New_Guinea


      public void drawHeader(Graphics g)
      {

          Font strongFont1 = new Font("Arial", Font.BOLD|Font.ITALIC, 18);
          g.setFont(strongFont1);
          g.drawString(strName,10,38); // Название события
          graphics.setFont(strongFont1);
          graphics.drawString(strName,10,38); // Название события

          //screen.drawString("Тест",10,28); // Название события
          Font strongFont = new Font("Arial", Font.BOLD|Font.ITALIC, 15);
          g.setFont(strongFont);
          g.drawString(day+"."+month+"."+year+" "+(int)h+":"+(int)m+":"+Math.round((m-(int)m)*60)+" GT ",10,58); //

          g.drawString("Долгота "+longi+"\u00b0",10,78); //
          g.drawString("Широта "+ lat+"\u00b0",10,98); //
          //g.drawString("\u0040 ZP  ver. 0.25"+ " http://www.liveplanet.ru  e-mail: mail@liveplanet.ru",
            //      12,getSize().height-12); //
            g.drawString("\u0040 ZP  ver. 0.25",12,getSize().height-12);


          graphics.setFont(strongFont);
          graphics.drawString(day+"."+month+"."+year+" "+(int)h+":"+(int)m+":"+Math.round((m-(int)m)*60)+" GT ",10,58); //

          graphics.drawString("Долгота "+longi+"\u00b0",10,78); //
          graphics.drawString("Широта "+ lat+"\u00b0",10,98); //
          //graphics.drawString("\u0040 ZP  ver. 0.25"+ " http://www.liveplanet.ru  e-mail: mail@liveplanet.ru",
            //      12,getSize().height-12); //
          graphics.drawString("\u0040 ZP  ver. 0.25",12,getSize().height-12);
      }


  public void paint(Graphics screen)
     {

          //  drawPieGraph();

        screen.setFont(myFont());
         graphics.setFont(myFont());
        FontMetrics fm= getFontMetrics(myFont());
        fontW=fm.stringWidth("A");  //Ширина фонта
        fontH=fm.getHeight();  //Высота фонта
        // смещения для определения центра фонта
        deltaXFont=fontW/2;
        deltaYFont=fontH/2;
     //Получение массивов домов, землеточек и планет

 //BufferedImage image = new BufferedImage(
   //    1000,700,BufferedImage.TYPE_INT_RGB);

     getHousePlanetZT();
     calcGradusAspectPlanetPlanet();
     calcAspectPlanetDom();

     calcAspectPlanetZT(); // На выходе массив aspectPlanetZT


        /*
         try {
             interpString(aspectPlanetZT) ;// Блок интерпретации
         } catch (IOException e) {
             e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
         }
      */
         //Координата асц - koordHouses[0], линия асц рисуется отдельно - входная
        // толчковая точка,
        // остальные координаты koordHouses[n] следовательно равны koordHouses[n]-koordHouses[0],
        // приведенные в диапазон 0-360 koordHouses[i]=koordHouses[i]-koordHouses[1];

        screen.setColor(Color.red);
        graphics.setColor(Color.red);

        screen.drawOval(x,y,diametrMinCircle,diametrMinCircle);
         graphics.drawOval(x,y,diametrMinCircle,diametrMinCircle);
        screen.drawOval(x-delta/2,y-delta/2,diametr2Circle,diametr2Circle);
         graphics.drawOval(x-delta/2,y-delta/2,diametr2Circle,diametr2Circle);
        screen.drawOval(x-delta,y-delta,diametr3Circle,diametr3Circle);
         graphics.drawOval(x-delta,y-delta,diametr3Circle,diametr3Circle);
        screen.drawOval(x-delta*3/2,y-delta*3/2,diametr4Circle,diametr4Circle);
         graphics.drawOval(x-delta*3/2,y-delta*3/2,diametr4Circle,diametr4Circle);

    // Координаты нулевой линии асцендента на 3 и 4 круге
        x1=x0=x-delta;
        y1=y0=y+(diametrMinCircle)/2;
        x2=x00=x-3*delta/2;
        y2=y00=y+(diametrMinCircle)/2;

        drawZT(screen); // Рисует Землеточки
 //        int xCentrZnak,yCentrZnak;

        xCentrZnak=(int) (x-0.75*delta);
        yCentrZnak=y+(diametrMinCircle)/2;

        drawZodiak(screen);//Рисует Зодиак
        drawPlanet(screen);//Рисует планеты

        // Рисуем аспекты планет
         if (drawAspectPlanet) drawAspectPl(screen);

        // Рисуем аспекты планеты-землеточки
        if (drawAspectZTPlanet) drawAspectPlZt(screen);

        drawPosInTable(screen); // Выводит позиции в таблицу
        drawHeader(screen); // Выводит шапку - наименование, дата, координаты
         String pathStr="";
         if (!catalogMan)
            pathStr="ErthCart/"+dateErthq+" "+commentName;
         else
            pathStr="Man/"+commentName;

         try {
             //day+"."+month+"."+year+" "+(int)hour+" "+(int)min+
             //Создание каталога для землетрясений
             if (comment.trim().substring(0,1).equalsIgnoreCase("M"))
             {
                 //pathStr="ErthCart1/"+day1+"."+month1+"."+year1+" "+hour1+" "+min1;

                 File td = new File(pathStr);
             td.mkdirs();
             }
             //Создание каталога для Man, каталог создается 1 раз - если нет слова "перед"
             if (comment.trim().indexOf("перед")==-1)
             {
                 //pathStr="ErthCart1/"+day1+"."+month1+"."+year1+" "+hour1+" "+min1;

                 File td = new File(pathStr);
             td.mkdirs();
             }


             //File f = new File(day1+"."+month1+"."+year1+" "+hour1+" "+min1+" "+comment+".png");

             File f = new File(pathStr+"/"+comment+".png");
             JimiRasterImage jrf = Jimi.createRasterImage(image.getSource());
             //jrf.
             try
             {
                Jimi.putImage("image/png",jrf,new FileOutputStream(f));

             }
             catch(Exception e)
             {
                System.out.println("Проблема с выводом графического файла "+ e.toString());
             }
            }
         catch (JimiException je) {je.printStackTrace();}
         if (fixDraw==true)

       // Блок интерпретации  
        if (interpret)
      {
     FileWork fw=  new FileWork(aspectPlanetZTTochn,13,comment);
         fw.findInFile(fw.readFile());
      }

         System.exit(0);

     }
      static void doHelp(int returnValue)
      {
          //h-помощь d дата t время l долгота s широта c название p рисовать аспекты планет
          // z рисовать аспекты землеточек m выводить таблицы домов на графику

          System.err.println("Usage: Geolika [-h][-d][-t][-l][-s][-p][-z][-m][-w][-j][-b][-x]");
          System.err.println(" [-h] - help");
          System.err.println(" [-d] - date format day.month.year - example:21.12.2007");
          System.err.println(" [-t] - time format hour:min:sec or hour:min - example:19:23:56 or 19:24");
          System.err.println(" [-l] - geo.longitude decimal format - example: 45.34 ");
          System.err.println("                West -; East+");
          System.err.println(" [-s] - geo.latitude decimal format - example: -12.34 ");
          System.err.println("                South -; Nouth+");
          System.err.println(" [-p] - draw aspect Planet-Planet");
          System.err.println(" [-z] - draw aspect Planet-ZT");
          System.err.println(" [-m] - draw houses in table");
          System.err.println(" [-b] - if catalog Man"); // например человек - рисунки будут в каталог Man
         System.err.println(" [-x] - fix draw"); // например человек - рисунки будут в каталог Man
          System.err.println(" [-w] - classpath");
          System.err.println(" [-j] - if create bat-file for JAR");
          System.err.println(" [-i] - interpret in text file");
          System.err.println(" Пример параметров с интерпретацией");
          System.err.println(" -d 25.2.1972 -t 8:35.0 -l 135.754807 -s 35.009129 -c Борис_Шорнинг_Киото -z -m -b -i");

          System.exit(returnValue);

    }

      /**
       *
       * @param date дата в формате день.месяц.год
       * @return разделенные день-месяц-год
       */
      static String[] parsDate(String date)
      {
          String d="",m="",y="",tmp="";
          String[] dateArray=new String[3];
          d=date.substring(0,date.indexOf("."));
          tmp=date.substring(date.indexOf(".")+1);
          m=tmp.substring(0,tmp.indexOf("."));
          y=tmp.substring(tmp.indexOf(".")+1);
          dateArray[0]=d;dateArray[1]=m;dateArray[2]=y;
          return dateArray;
      }

      /**
       *
       * @param time время в формате h:m:s
       * @return массив разделенный час минута секунда
       */
      static String[] parsTime(String time)
      {
          String tmp="";
          String[] timeArray=new String[3];
          timeArray[0]=time.substring(0,time.indexOf(":"));
          tmp=time.substring(time.indexOf(":")+1);
          if (tmp.indexOf(":")!=-1)
          {
          timeArray[1]=tmp.substring(0,tmp.indexOf(":"));
          timeArray[2]=tmp.substring(tmp.indexOf(":")+1);
          } else

          {
          timeArray[1]=tmp.substring(0);
          timeArray[2]="0";
          }

          return timeArray;
      }

      /**
       *
       * @param argStr
       */
      public static void main(String[] argStr)
      {
          String hlp="",dat="",tim="",lon="",shir="";
          boolean drAspPlan=false, drAspPlZT=true,drDomTab=false, fixDraw=true,catalogMan=false, interpret=false;
          String[] strArrDate=new String[3];
          String[] strArrTime=new String[3];

          GetOpt go = new GetOpt("hd:t:l:s:c:pzmxbi");

          //h-помощь d дата t время l долгота s широта c название p рисовать аспекты планет
          // z рисовать аспекты землеточек m выводить таблицы домов на графику

          char c;
          while ((c = go.getopt(argStr)) != GetOpt.DONE)
          {
			switch(c)
            {
			case 'h':
				doHelp(0);
				break;
			case 'd':
				dat=go.optarg();
				break;
			case 't':
				tim = go.optarg();
				break;
            case 'l':
				lon = go.optarg();
				break;
                case 's':
                 shir = go.optarg();
                 break;
            case 'c':
				comment = go.optarg();
				break;
            case 'p':
                    drAspPlan=true;
                    break;
            case 'z':
                    drAspPlZT=true;
                    break;
            case 'm':
                    drDomTab=true;
                    break;
             case 'x':
                    fixDraw=false;
                     break;
                case 'b':
                       catalogMan=true;
                        break;
                case 'i':
                       interpret=true;
                        break;



            }
		}
          strArrDate=parsDate(dat);
          strArrTime=parsTime(tim);

          double min=Double.valueOf(strArrTime[1]).doubleValue();
          double sec=(Double.valueOf(strArrTime[2]).doubleValue())/60;
          double minSec=min+sec;

          day1=strArrDate[0]; month1=strArrDate[1];year1=strArrDate[2];
          hour1=strArrTime[0]; min1=strArrTime[1];

         ZT zt= new ZT(comment,Integer.valueOf(strArrDate[0]).intValue(),Integer.valueOf(strArrDate[1]).intValue(),
                  Integer.valueOf(strArrDate[2]).intValue(),
                 Double.valueOf(strArrTime[0]).doubleValue(),minSec,Double.valueOf(lon).doubleValue(),
                  Double.valueOf(shir).doubleValue(),drAspPlan,drAspPlZT,drDomTab,fixDraw,catalogMan,interpret);
          /*
          try
          {
          FileOutputStream stream = new FileOutputStream("piechart.png");
          zt.createImage(stream);
          stream.close();
          //System.exit(1);
          }
          catch(Exception e)
          {
              System.out.println("e.toString() = " + e.toString());
          }
          */

      }
    }

