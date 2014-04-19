package ru.liveplanet.zt;

/**
 * Created by IntelliJ IDEA.
 * User: damir
 * Date: 08.08.2006
 * Time: 12:51:49
 * To change this template use File | Settings | File Templates.
 */

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

//import swisseph.*;


public class PaintZT extends java.applet.Applet

  {
      static final double DEGTORAD=0.0174532925199433;
      static final double RADTODEG=57.2957795130823;
      double[] planetKoord;
      int planetCount=13;
      static final  double juliDate=2451545;
//      planetKoord=new double[25];//

      double[] koordHouses;
      double[] koordHousesRelativ;
      double[] koordZTRelativ;
      boolean drawAspectPlanet=false;
      boolean drawDomInTable= false;
      boolean drawAspectZTPlanet= false;
  //    koordHouses=new double[13];
  //    koordHousesRelativ=new double[13];

      double[] koordZT;
    //  koordZT=new double[4];
      int deltaXFont=0,deltaYFont=0;
      int fontW=0,fontH=0;
      int x1,y1,x2,y2,x0,y0,x00,y00;
      int diametrMinCircle=300; //Диаметр меньшего круга
      int delta=60; // Прирост каждого следующего круга
      int x=150, y=150; // Координаты левого верхнего угла квадрата, куда вписан меньший круг
      int diametr2Circle=diametrMinCircle+delta;
      int diametr3Circle=diametrMinCircle+delta*2;
      int diametr4Circle=diametrMinCircle+delta*3;
      double[][] gradusPlanetPlanet;
      double[][] gradusPlanetZT;
      double[][] aspectPlanetPlanet;
      double[][] aspectPlanetCuspidDom;
      double[][] aspectPlanetZT;
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

      //Graphics screen;

      public void init()
        {
           //setBackground(Color.yellow);

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
      };
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
         //FileInputStream fis = new FileInputStream("font\\ZT.ttf");
         //System.out.println(System.getProperty("user.dir"));
        File f = new File("");
        String path = f.getAbsolutePath();
              System.out.println(path);

         FileInputStream fis = new FileInputStream("ZT.ttf");   


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
                         System.out.println(" Выявлен аспект "+i+" - "+j+" " + gradusPlanetPlanet[i][j]);
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

    stringAspectZTPlanet=setSymbolZT(n);
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


         stringAspectPlanetPlanet=setSymbolPlanet(n);
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
                        System.out.println(" Выявлен аспект Планета - Дом "+i+" - "+j+" " + deltaPlanetDom);
                    }
                }
             }
         }
     }
    public void calcAspectPlanetZT()
    {
        aspectPlanetZT= new double[planetCount][koordZT.length];
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
                       System.out.println(" Выявлен аспект Планета - ZT "+i+" - "+j+" " + gradusPlanetZT[i][j]);
                   }
               }
            }
        }

    }

   public void getHousePlanetZT()
   {
//Получение массивов домов, землеточек и планет
            planetKoord=new double[25];//
            koordHouses=new double[13];
            koordHousesRelativ=new double[13];
            koordZT=new double[4];
            koordZTRelativ=new double[4];


              strName="И. В. Сталин - Петербург";
              drawDomInTable=false;  // Выводим ли координаты домов в таблицу
              drawAspectPlanet=false; // Рисуем ли аспекты планет
              drawAspectZTPlanet=true;
              day=21; month=12;year=1879;
              h=4;m=20;
              lonGr=30.250;lonMin=0;latGr=59.917; latMin=0;
              if (lonGr>0)       longi=lonGr+lonMin/60; else longi=lonGr-lonMin/60;
              if (latGr>0) lat=latGr+latMin/60; else lat=latGr-latMin/60;
              hour=h+m/60;

               geol = new Geolika(year,month,day,hour,longi,lat);
               //int[] posFrac=new int[4];
               geol.getZT(geol.naclEkl);
               koordZT=geol.ztoch;
                  for (int i=0;i<=3;i++)
                  {
                      posFrac=geol.ztl.swe_split_deg1(koordZT[i],8);
                      System.out.println("\n***** "+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
                              geol.numToNameZodiak(posFrac[0]+1));
                  }
               planetKoord=geol.getPlanet(geol.dateInputJuli);
                  for (int i=0;i<=20;i++)
                  {
                      posFrac=geol.ztl.swe_split_deg1(planetKoord[i],8);
                      System.out.println(
                      geol.sw.swe_get_planet_name(i)+":"+"\t"+
                              posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
                              geol.numToNameZodiak(posFrac[0]+1));
                  }
                geol.getHouse(geol.dateInputJuli);
               koordHouses=geol.house;
                  // Заполняем в соотв переменную, так как процедура заполняет в единую house[]
                  for (int i=1;i<=12;i++)
                  {
                   koordHouses[i]=geol.house[i];
                   posFrac=geol.ztl.swe_split_deg1(koordHouses[i],8);
                      System.out.println(
                      ":Дом "+ i + "    "+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
                              geol.numToNameZodiak(posFrac[0]+1));
                  }
   }
  public void paint(Graphics screen)

    {
//        screen.drawString("Название события",10,10); // Название события
//        screen.drawString(day+"."+month+"."+year+" "+h+":"+m+" GT ",10,30); //

        screen.setFont(myFont());
        FontMetrics fm= getFontMetrics(myFont());

        fontW=fm.stringWidth("A");  //Ширина фонта
        fontH=fm.getHeight();  //Высота фонта
        // смещения для определения центра фонта

        deltaXFont=fontW/2;
        deltaYFont=fontH/2;

//Получение массивов домов, землеточек и планет
     getHousePlanetZT();
     calcGradusAspectPlanetPlanet();
     calcAspectPlanetDom();
     calcAspectPlanetZT();
      //drawAspectPlanet();
     //System.exit(0);


        //Координата асц - koordHouses[0], линия асц рисуется отдельно - входная
        // толчковая точка,
        // остальные координаты koordHouses[n] следовательно равны koordHouses[n]-koordHouses[0],
        // приведенные в диапазон 0-360 koordHouses[i]=koordHouses[i]-koordHouses[1];

        screen.setColor(Color.red);

        screen.drawOval(x,y,diametrMinCircle,diametrMinCircle);
        screen.drawOval(x-delta/2,y-delta/2,diametr2Circle,diametr2Circle);
        screen.drawOval(x-delta,y-delta,diametr3Circle,diametr3Circle);
        screen.drawOval(x-delta*3/2,y-delta*3/2,diametr4Circle,diametr4Circle);


    // Координаты нулевой линии асцендента на 3 и 4 круге
        x1=x0=x-delta;
        y1=y0=y+(diametrMinCircle)/2;
        x2=x00=x-3*delta/2;
        y2=y00=y+(diametrMinCircle)/2;
        screen.drawLine(x1,y1,x2,y2); // Рисует ASC

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
                Math.cos(halfHouse*DEGTORAD)));
        y1= (int) (yCentrDom+((diametr3Circle/2+delta/4)*
                Math.sin(halfHouse*DEGTORAD)));
        x1=(x1-deltaXFont); y1=(y1+deltaYFont);
      //  screen.drawString("\u0031", (x1), (y1)); // 1 дом
          */

        for (int i=0;i<=3;i++)  // Рисует  линии для остальных домов
        {
            //koordHousesRelativ[i]=Math.abs(ZTLib.swe_degnorm(koordHouses[i]-koordHouses[1]));
            koordZTRelativ[i]=Math.abs(ZTLib.swe_degnorm(koordZT[i]-koordZT[1]));
        x1=(int) (x0+(diametr3Circle/2)-((diametr3Circle/2)*
                Math.cos(koordZTRelativ[i]*DEGTORAD)));
        y1= (int) (y0+((diametr3Circle/2)*
                Math.sin(koordZTRelativ[i]*DEGTORAD)));
        x2=(int) (x00+(diametr4Circle/2)-((diametr4Circle/2)*
                Math.cos(koordZTRelativ[i]*DEGTORAD)));
        y2= (int) (y00+((diametr4Circle/2)*
                Math.sin(koordZTRelativ[i]*DEGTORAD)));
        screen.drawLine(x1,y1,x2,y2);

            switch(i)
        {
            case 0:screen.drawString("\u00C1", x2-fontW/2, y2-fontH/2); break;//   Землецель
            case 1:screen.drawString("\u00D0", x2-2*fontW, y2+fontH/2); break;  //     Землеявность
            case 2:screen.drawString("\u005B", x2-fontW/2, (int)(y2+(1.25*fontH))); break;    //   Землебаза
            case 3:screen.drawString("\u00B5", x2+fontW/2, y2+fontH/2); break;//            Землетыл
        }

        // Наносит Землеточки на внутренний круг
            // Координаты нулевой линии на внутреннем круге

               int y2=y+(diametrMinCircle)/2;

            x1=(int) (x+(diametrMinCircle/2)-((diametrMinCircle/2)*
                    Math.cos(koordZTRelativ[i]*DEGTORAD)));
            y1= (int) (y2+((diametrMinCircle/2)*
                    Math.sin(koordZTRelativ[i]*DEGTORAD)));

            screen.setColor(Color.red);
            screen.fillOval(x1,y1,5,5);// Наносит Землеточки на внутренний круг кругом
            //screen.drawOval(x1,y1,3,3); //// Наносит Землеточки на внутренний круг кругом

        }



        // Координаты нулевой линии - градус асцендента на промежуточном
        // между 2 и 3 кругом для вывода Знаков Зодиака в центре
            int xCentrZnak,yCentrZnak;
            xCentrZnak=(int) (x-0.75*delta);
            yCentrZnak=y+(diametrMinCircle)/2;
            //screen.drawOval(xCentrZnak,yCentrZnak,2,2);

        // Координаты нулевой линии - промежуточном
        // между 1 и 2 кругом для вывода Планет в центре
            int xCentrPlanet,yCentrPlanet;
            xCentrPlanet=(int) (x-0.25*delta);
            yCentrPlanet=y+(diametrMinCircle)/2;
            //screen.drawOval(xCentrPlanet,yCentrPlanet,2,2);


        // Координаты нулевой линии - градус асцендента на 2 и 3 круге (Знаки Зодиака)
            x1=x0=x-delta;
            y1=y0=y+(diametrMinCircle)/2;

            x2=x00=x-delta/2;
            y2=y00=y+(diametrMinCircle)/2;
            screen.setColor(Color.black);
            //screen.drawLine(x1,y1,x2,y2);

       // Находим смещение начала овна (360) от асц - koordHouses[0]
        //double deltaAries=360-koordHouses[1]; // не koordHouses[0] так как первый дом это koordHouses[1]
        double deltaAries=360-koordZT[1]; //

        //Координаты линий для 0 овна
        x1=(int) (x1+(diametr3Circle/2)-((diametr3Circle/2)*
                Math.cos(deltaAries*DEGTORAD)));
        y1= (int) (y1+((diametr3Circle/2)*
                Math.sin(deltaAries*DEGTORAD)));
        x2=(int) (x2+(diametr2Circle/2)-((diametr2Circle/2)*
                Math.cos(deltaAries*DEGTORAD)));
        y2= (int) (y2+((diametr2Circle/2)*
                Math.sin(deltaAries*DEGTORAD)));
        screen.setColor(Color.cyan);
        screen.drawLine(x1,y1,x2,y2); // Рисует 0 Овна

        // Выводит символ Овна
        x1=(int) (xCentrZnak+(diametr3Circle/2-delta/4)-((diametr3Circle/2-delta/4)*
                Math.cos((deltaAries+15)*DEGTORAD)));
        y1= (int) (yCentrZnak+((diametr3Circle/2-delta/4)*
                Math.sin((deltaAries+15)*DEGTORAD)));
        screen.drawString("\u00A4", (x1-deltaXFont), (y1+deltaYFont)); //Овен


        double[] znacZodiak;
        znacZodiak=new double[] {0,30,60,90,120,150,180,210,240,270,300,330};

        for (int i=1;i<=11;i++)  // Рисует  линии для остальных Знаков
        {
            znacZodiak[i]=znacZodiak[i]-(360-deltaAries);
        x1=(int) (x0+(diametr3Circle/2)-((diametr3Circle/2)*
                Math.cos(znacZodiak[i]*DEGTORAD)));
        y1= (int) (y0+((diametr3Circle/2)*
                Math.sin(znacZodiak[i]*DEGTORAD)));
        x2=(int) (x00+(diametr2Circle/2)-((diametr2Circle/2)*
                Math.cos(znacZodiak[i]*DEGTORAD)));
        y2= (int) (y00+((diametr2Circle/2)*
                Math.sin(znacZodiak[i]*DEGTORAD)));
            screen.setColor(Color.blue);
        screen.drawLine(x1,y1,x2,y2);
        // Наносим знаки зодиака
        x1=(int) (xCentrZnak+(diametr3Circle/2-delta/4)-((diametr3Circle/2-delta/4)*
                Math.cos((znacZodiak[i]+15)*DEGTORAD)));
        y1= (int) (yCentrZnak+((diametr3Circle/2-delta/4)*
                Math.sin((znacZodiak[i]+15)*DEGTORAD)));
         //screen.drawOval(x1,y1,2,2);
           x1=x1-deltaXFont;y1=y1+deltaYFont; //Поправки для центрирования шрифта
         switch(i)
         {

             case 1:screen.drawString("\u00A7", x1, y1); break;//   Телец
             case 2:screen.drawString("\u00A9", x1, y1); break;  //      Близнец
             case 3:screen.drawString("\u00AD", x1, y1); break;    //           Рак
             case 4:screen.drawString("\u00AF", x1, y1); break;//Лев
             case 5:screen.drawString("\u00B3", x1, y1); break;  // Дева
             case 6:screen.drawString("\u00B6", x1, y1); break;    //   Весы
             case 7:screen.drawString("\u00B8", x1, y1); break;//           Скорпион
             case 8:screen.drawString("\u00BE", x1, y1); break;  //Стрелец
             case 9:screen.drawString("\u00BF", x1, y1); break;    //Козерог
             case 10:screen.drawString("\u00C3", x1, y1); break;//          Водолей
             case 11:screen.drawString("\u00C5", x1, y1); break;  // Рыбы

         }
        }
        //Найти нулевую точку на малом круге
        // Координаты нулевой линии  на 1 круге
            x1=x0=x;
            y1=y0=y+(diametrMinCircle/2);
            //screen.drawOval(x1,y1,2,2);
        //  Но ее реальная координата - deltaAries

        double[] planetKoordWithDelta=new double[planetCount]; //Для координат планет со смещением
        double[] planetKoordWithDeltaStellium=new double[planetCount]; //Для координат планет со смещением
                                                                       // если есть стеллиум (разводка)
                 
        //Заполняем массив для разводки планет, которые слипаются при графике
        int [][] planetGrafikKoord=new int [planetCount][2];
        for (int i=0;i<=planetCount-1;i++)  //
        {
            planetKoordWithDelta[i]=planetKoordWithDeltaStellium[i]=
                    ZTLib.swe_degnorm(planetKoord[i]-(360-deltaAries));
            x1=(int) (xCentrPlanet+(diametrMinCircle/2+delta/4)-((diametrMinCircle/2+delta/4)*
                    Math.cos((planetKoordWithDelta[i])*DEGTORAD)));
            y1= (int) (yCentrPlanet+((diametrMinCircle/2+delta/4)*
                    Math.sin((planetKoordWithDelta[i])*DEGTORAD)));

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

        for (int i=0;i<=planetCount-1;i++)  //
            for (int j = i + 1; j <= planetCount - 1; j++)  //
            {
                //Разница в дианазоне до 180 для учета перехода рыбы-овен
                deltaKoord = (int) ZTLib.swe_difdeg2n(planetKoordWithDeltaStellium[i], planetKoordWithDeltaStellium[j]);
                if (Math.abs(deltaKoord) < normaForDrawing)
                {
                    System.out.println(i + "- " + j + " deltaKoord=" + deltaKoord);
                    plSort[i]=plNonSort[i]=planetKoordWithDeltaStellium[i];
                    plSort[j]=plNonSort[j]=planetKoordWithDeltaStellium[j];
                }
            }
          Arrays.sort(plSort);
        //Имееется два массива - один отсортированный, другой нет
        // Массив для записи номеров планет, предназначенных к разводке, отсортированный начиная с меньшей координаты
        int[] numPlanetforBreak= new int[planetCount];
        int n=0;
        for (int i=0;i<=plSort.length-1;i++)
        {
            if (plSort[i] !=0)
            {
            System.out.println("** plSort[i] "+i+ "  "+plSort[i] );
           for (int j=0;j<=plNonSort.length-1;j++)
           {

               if (plSort[i]==plNonSort[j])
               {
               System.out.println("* plNonSort[j] "+j+ "  "+planetKoord[j]);

               // Заполняем массив номеров планет, предназначенных к разводке (отсортирован с меньшей координаты)
                   numPlanetforBreak[n]=j;
                   n++;
               }
           }
            }
        }

        double deltaKoordDoub=0;
        // Но основе отсортированного массива разводим планеты
       for(int i=0;i<=numPlanetforBreak.length-2;i++)
       {
            int j= numPlanetforBreak[i];
            int next=numPlanetforBreak[i+1];
           if (next!=j)// иначе идут нули (next=0 j=0) а это приводит к смещению солнца
           {
           //Разница в дианазоне до 180 для учета перехода рыбы-овен
           deltaKoordDoub =  ZTLib.swe_difdeg2n(planetKoordWithDeltaStellium[j], planetKoordWithDeltaStellium[next]);

           if (Math.abs(deltaKoordDoub) < normaForDrawing)
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
                       Math.cos((planetKoordWithDeltaStellium[next]) * DEGTORAD)));
               y1 = (int) (yCentrPlanet + ((diametrMinCircle / 2 + delta / 4) *
                       Math.sin((planetKoordWithDeltaStellium[next]) * DEGTORAD)));

               planetGrafikKoord[next][0] = x1;
               planetGrafikKoord[next][1] = y1;
           }
           }
} //// Но основе отсортированного массива разводим планеты
           for (int i=0;i<=planetCount-1;i++)  //
           {
               //planetKoordWithDelta[i]=planetKoord[i]-(360-deltaAries);       !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
               //Наносит точки на внутренний круг

               x1=(int) (x0+(diametrMinCircle/2)-((diametrMinCircle/2)*
                       Math.cos(planetKoordWithDelta[i]*DEGTORAD)));
               y1= (int) (y0+((diametrMinCircle/2)*
                       Math.sin(planetKoordWithDelta[i]*DEGTORAD)));

               screen.setColor(Color.black);
               screen.drawOval(x1,y1,2,2);
               x1=planetGrafikKoord[i][0];
               y1=planetGrafikKoord[i][1];
               screen.setColor(Color.black);
               //screen.drawOval(x1,y1,2,2);
               x1=x1-deltaXFont;y1=y1+deltaYFont; //Поправки для центрирования шрифта
               switch(i)
               {
                   case 0:screen.drawString("\u005D", x1, y1); break;//   Солнце
                   case 1:screen.drawString("\u005E", x1, y1); break;//   Луна
                   case 2:screen.drawString("\u0061", x1, y1); break;  //     Меркурий
                   case 3:screen.drawString("\u0063", x1, y1); break;    //           Венера
                   case 4:screen.drawString("\u0067", x1, y1); break;//Марс
                   case 5:screen.drawString("\u006A", x1, y1); break;  // Юпитер
                   case 6:screen.drawString("\u006D", x1, y1); break;    //   Сатурн
                   case 7:screen.drawString("\u006F", x1, y1); break;//           Уран
                   case 8:screen.drawString("\u0072", x1, y1); break;  //Нептун
                   case 9:screen.drawString("\u0076", x1, y1); break;  //Плутон
                       //case 10:screen.drawString("\u00F1", x1, y1); break;    //Средний узел
                   case 11:screen.drawString("\u00F1", x1, y1); break;//         Истинный узел
                   case 12:screen.drawString("\u0178", x1, y1); break;  // Черная луна
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


         if (drawAspectPlanet)
         {
        // Рисуем аспекты планет
        screen.setColor(Color.CYAN);
        for(int i=0;i<=planetCount-1;i++)
        {
            for(int j=0;j<=planetCount-1;j++)
            {

             if (aspectPlanetPlanet[i][j]!=0)
             {
                 x1=(int) (x0+(diametrMinCircle/2)-((diametrMinCircle/2)*
                         Math.cos(planetKoordWithDelta[i]*DEGTORAD)));
                 y1= (int) (y0+((diametrMinCircle/2)*
                         Math.sin(planetKoordWithDelta[i]*DEGTORAD)));
                 x2=(int) (x0+(diametrMinCircle/2)-((diametrMinCircle/2)*
                         Math.cos(planetKoordWithDelta[j]*DEGTORAD)));
                 y2= (int) (y0+((diametrMinCircle/2)*
                         Math.sin(planetKoordWithDelta[j]*DEGTORAD)));
               screen.drawLine(x1,y1,x2,y2);
             }
            }
        }// Рисуем аспекты планет
          }


        // Рисуем аспекты планеты-землеточки
        if (drawAspectZTPlanet)
        {
        for(int i=0;i<=planetCount-1;i++)
        {
            for(int j=0;j<=3;j++)
            {

             if (aspectPlanetZT[i][j]!=0)
             {
                 String symbolAspect=setSymbolAspect(gradusPlanetZT[i][j]);
                 x1=(int) (x0+(diametrMinCircle/2)-((diametrMinCircle/2)*
                         Math.cos(planetKoordWithDelta[i]*DEGTORAD)));
                 y1= (int) (y0+((diametrMinCircle/2)*
                         Math.sin(planetKoordWithDelta[i]*DEGTORAD)));
                 x2=(int) (x0+(diametrMinCircle/2)-((diametrMinCircle/2)*
                         Math.cos(koordZTRelativ[j]*DEGTORAD)));
                 y2= (int) (y0+((diametrMinCircle/2)*
                         Math.sin(koordZTRelativ[j]*DEGTORAD)));
                screen.drawLine(x1,y1,x2,y2);
                setKoordSymbolAspect(x1,y1,x2,y2); // находит центр линии для вывода туда символа аспекта
                screen.setColor(Color.green);
                screen.drawString(symbolAspect,x1-fontW/2,y1+fontH/2);
             }
            }
        }// Рисуем аспекты планеты-землеточки
        }
        screen.setColor(Color.blue);

        xTab=x-delta+diametr4Circle;
        yTab=y-delta;
        for (int i=0;i<=3;i++)  // Выводим информацию по Землеточкам
        {
            posFrac=geol.ztl.swe_split_deg1(koordZT[i],8);
            switch(i)
            {
                case 0:
                {
                    screen.drawString
                    ("\u00C1 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+
                    posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1),xTab,yTab);
                    screen.drawString(setStringAspectZTPlanet(i),xTab+(11*fontW),yTab);// перечисляются аспекты землеточки
                }
                    break;
                case 1:
                {
                    screen.drawString
                        ("\u00d0 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+
                                posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1),xTab,yTab+i*fontH);
                    screen.drawString(setStringAspectZTPlanet(i),xTab+(11*fontW),yTab+i*fontH);// перечисляются аспекты землеточки

                }
                    break;
                case 2:
                {
                    screen.drawString
                        ("\u005b "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"
                                + posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1),xTab,yTab+i*fontH);
                    screen.drawString(setStringAspectZTPlanet(i),xTab+(11*fontW),yTab+i*fontH);// перечисляются аспекты землеточки
                }
                    break;
                case 3:
                {
                    screen.drawString
                        ("\u00b5 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+
                                posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1),xTab,yTab+i*fontH);
                    screen.drawString(setStringAspectZTPlanet(i),xTab+(11*fontW),yTab+i*fontH);// перечисляются аспекты землеточки
                }
                    break;
            }
        }

        n=5; // Выведено уже 4 землеточки
        for (int i=0;i<=20;i++)
        {
            posFrac=geol.ztl.swe_split_deg1(planetKoord[i],8);
            System.out.println(
            geol.sw.swe_get_planet_name(i)+":"+"\t"+
                    posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
                    geol.numToNameZodiak(posFrac[0]+1));

            positionPlanet(i,n); // переопределяет координаты  x1 y1

            switch(i)
            {
                case 0:
                {
                    screen.drawString("\u005D "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                    screen.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты

                }
                    break;//   Солнце
                case 1:
                {
                    screen.drawString("\u005E "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                    screen.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                }
                    break;//   Луна
                case 2:
                {
                    screen.drawString("\u0061 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                    screen.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                }
                    break;  //     Меркурий
                case 3:
                {
                    screen.drawString("\u0063 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                    screen.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                }
                    break;    //           Венера
                case 4:
                {
                    screen.drawString("\u0067 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                    screen.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                }
                    break;//Марс
                case 5:
                {
                    screen.drawString("\u006A "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                    screen.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                }
                    break;  // Юпитер
                case 6:
                {
                    screen.drawString("\u006D "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                    screen.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                }
                    break;    //   Сатурн
                case 7:
                {
                    screen.drawString("\u006F "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                    screen.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                }
                    break;//           Уран
                case 8:
                {
                    screen.drawString("\u0072 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                    screen.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                }
                    break;  //Нептун
                case 9:
                {
                    screen.drawString("\u0076 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                    screen.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                }
                    break;  //Плутон
                case 10:
                {
                    screen.drawString("\u00F1 M"+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                    screen.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                }
                    break;    //Средний узел
                case 11:
                {
                    screen.drawString("\u00F1 T"+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                    screen.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                }
                    break;//         Истинный узел
                case 12:
                {
                    screen.drawString("\u0178 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1);
                    screen.drawString(setStringAspectPlanetPlanet(i),x1+(11*fontW),y1);// перечисляются аспекты планеты
                }
                    break;  // Черная луна
                    /*
             case 12:screen.drawString("\u00FE "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;  // osc Apogee
             case 13:screen.drawString("\u0064 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;  // Земля
             case 14:screen.drawString("\u0078 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;  // Хирон
             case 15:screen.drawString("\u00C5 " +posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;  // Фолус
             case 16:screen.drawString("\u00C5 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;  // Церрера
             case 17:screen.drawString("\u00C5 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;  // Паллада
             case 18:screen.drawString("\u00C5 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;  // Юнона
             case 19:screen.drawString("\u00C5 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;  // Веста
               */
            }
        }

        if (drawDomInTable)
        {
        n=n+13+1;// Выводилось еще 13 планет
        // Заполняем в соотв переменную, так как процедура заполняет в единую house[]
        for (int i=1;i<=6;i++)
        {
         koordHouses[i]=geol.house[i];
         posFrac=geol.ztl.swe_split_deg1(koordHouses[i],8);
            System.out.println(
            ":Дом "+ i + "    "+ posFrac[1]+" гр."+posFrac[2]+" мин."+posFrac[3]+"сек "+
                    geol.numToNameZodiak(posFrac[0]+1));
            positionPlanet(i,n); // переопределяет координаты  x1 y1

            switch(i)
            {
                case 1:screen.drawString("\u201E "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;//
                case 2:screen.drawString("\u00eb "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;  //
                case 3:screen.drawString("\u00ec "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;    //
                case 4:screen.drawString("\u02c6 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;//
                case 5:screen.drawString("\u2030 "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;  //
                case 6:screen.drawString("\u00ab "+posFrac[1]+"\u00B0"+posFrac[2]+"\u0027"+ posFrac[3]+"''"+numToSymbolZodiak(posFrac[0]+1), x1, y1); break;    //
            }
        }
        }

        Font strongFont = new Font("Arial", Font.BOLD|Font.ITALIC, 18);

        screen.setFont(strongFont);
        //screen.setColor(Color.CYAN);
        screen.drawString(strName,10,18); // Название события
        strongFont = new Font("Arial", Font.BOLD|Font.ITALIC, 15);
        screen.setFont(strongFont);
        screen.drawString(day+"."+month+"."+year+" "+(int)h+":"+(int)m+" GT ",10,38); //
        if (lonMin==0)
        screen.drawString("Долгота "+lonGr+" гр.; ",10,58); //
        else
        screen.drawString("Долгота "+Math.round(lonGr)+" гр. "+Math.round(lonMin)+" мин",10,58); //
        if (latMin==0)
        screen.drawString("Широта "+ latGr+" гр.",10,78); //
        else
        screen.drawString("Широта "+ Math.round(latGr)+" гр. "+Math.round(latMin)+" мин.",10,78); //


        }
    }

