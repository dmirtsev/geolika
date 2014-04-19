package ru.liveplanet.zt;

/**
 * Created by IntelliJ IDEA.
 * User: DBashkirtsev
 * Date: 03.08.2009
 * Time: 15:16:19
 * To change this template use File | Settings | File Templates.
 */
import java.awt.*;
import java.io.InputStream;
import java.io.IOException;

import com.bbn.openmap.Layer;
import com.bbn.openmap.omGraphics.*;
import com.bbn.openmap.event.ProjectionEvent;

    public class DrawRLayerZJ extends Layer {

        /**
         * A list of graphics to be painted on the map.
         */
        private OMGraphicList omgraphics;

        /**
         * Construct a default route layer. Initializes omgraphics to a
         * new OMGraphicList, and invokes createGraphics to create the
         * canned list of routes.
         */
        InsPlArrForKart[] sel;
        boolean noZC;

        public DrawRLayerZJ(InsPlArrForKart[] sel, boolean noZC) {

            this.sel=sel;
            this.noZC=noZC;

            omgraphics = new OMGraphicList();

            //Graphics g=new Graphics();

            createGraphics(omgraphics);


        }

        /**
         * Creates an OMLine from the given parameters.
         *
         * @param lat1 The line's starting latitude
         * @param lon1 The line's starting longitude
         * @param lat2 The line's ending latitude
         * @param lon2 The line's ending longitude
         * @param color The line's color
         *
         * @return An OMLine with the given properties
         */
        public OMLine createLine(float lat1, float lon1, float lat2, float lon2,
                                 Color color) {
            OMLine line = new OMLine(lat1, lon1, lat2, lon2, OMGraphic.LINETYPE_GREATCIRCLE);
            line.setLinePaint(color);
            return line;
        }


              public Font myFont()
              {
                 Font snowFont=null;
                  try {

                   InputStream fis = getClass().getResourceAsStream("ZT.ttf" );
                      //C:\\Users\\DBashkirtsev\\Documents\\GEO\\OpenMap\\openmap-4.6.5\\openmap-4.6.5\\share\\data\\shape\\data/shape/dcwpo-browse.ssx"
                      //"C:\Users\DBashkirtsev\Documents\GEO\Project Java\Geolika\zt\src\ZT.ttf"
                       //fis = new FileInputStream(classLocationStr);
                  Font fnt=Font.createFont(Font.TRUETYPE_FONT,fis);//

                          fis.close();
                  snowFont = fnt.deriveFont(Font.PLAIN,21);

                  } catch (FontFormatException e) {
                      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                  } catch (IOException e) {
                      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                  }
                 return snowFont;
                      }

        public OMText createLabel(float lat1, float lon1, String str)
                                  {
            OMText text = new OMText(lat1, lon1, str,1 );
              //Font font =
                      //new Font("TimesRoman", Font.BOLD + Font.ITALIC, 48);
             text.setFont(myFont());
                                      if (noZC)
           text.setLineColor(java.awt.Color.RED); else
            text.setLineColor(java.awt.Color.BLUE);

            //text.setLinePaint(color);
            return text;
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
         *
         * @param lst
         * @return
         */

        String[] koordOutputLabel(LineZTinMap lst, int planet, double asp, int j)
        {
            String str[]=new String[3];
           str[0]=lst.arrDolg[20+j*4]+"";
         str[1]=lst.arrShir[20+j*4]+"";
           // str[0]=lst.arrDolg[j*4]+"";
            //str[1]=lst.arrShir[j*4]+"";

           str[2]= setSymbolPlanet(planet)+" -  "+asp;



            return str;
        }

        /**
         * Clears and then fills the given OMGraphicList. Creates three
         * lines for display on the map.
         *
         * @param graphics The OMGraphicList to clear and populate
         * @return the graphics list, after being cleared and filled
         */


        public OMGraphicList createGraphics(OMGraphicList graphics)
        {
            LineZTinMap lzt= new LineZTinMap();
            //lzt.skanZC(120,1);
            graphics.clear();
               int planet;
               double  aspect;
            for (int j=0;j<=sel.length-1;j++)
            {
                if ((sel[j].aspect==0) && (sel[j].plName==0) && (sel[j].dolgota==0))
                    continue;//Пропукаем если значение массива пустое 
                planet=sel[j].plName;
                aspect=sel[j].aspect;

                if (noZC)
                lzt.skanZJ(sel[j].dolgota,0.3,0.05,0.2f);
                else
                lzt.skanZC(sel[j].dolgota,0.5,0.05,0.2f);

                for (int i=2;i<=350;i++)
                {
                    if ((lzt.arrDolg[i+1]!=0) && (lzt.arrShir[i+1]!=0))
                    {
                     //Функция - что выводить (строка) и где выводить (координаты)
                    //Выводит текст
                    if (noZC)
                    graphics.addOMGraphic(createLine(lzt.arrShir[i],
                        (lzt.arrDolg[i]),
                        lzt.arrShir[i+1],
                        (lzt.arrDolg[i+1]),
                        Color.RED));
                    else
                        graphics.addOMGraphic(createLine(lzt.arrShir[i],
                            (lzt.arrDolg[i]),
                            lzt.arrShir[i+1],
                            (lzt.arrDolg[i+1]),
                            Color.BLUE)); 
                    }


                }
                    koordOutputLabel(lzt,planet,aspect,j);
                //double rr= Double.parseDouble();
                float dolg=Float.parseFloat(koordOutputLabel(lzt,planet,aspect,j)[0]);
                float shir=Float.parseFloat(koordOutputLabel(lzt,planet,aspect,j)[1]);
                String str=koordOutputLabel(lzt,planet,aspect,j)[2];
                        //( );
                //int.koordOutputLabel(lzt,j)[1].in;


                   graphics.add(createLabel(shir,dolg,str));

            }
              /*
            int y=0;
            for (int u=0;u<=12;u++)
            {
                y=u*30;
                lzt.skanZJ(y,0.3);
            for (int i=2;i<=350;i++)
            {
                if ((lzt.arrDolg[i+1]!=0) && (lzt.arrDolg[i+1]!=0))

                graphics.addOMGraphic(createLine(lzt.arrShir[i],
                    (lzt.arrDolg[i]),
                    lzt.arrShir[i+1],
                    (lzt.arrDolg[i+1]),
                    Color.BLUE));
            }
            }
            */
            /*
            for (int u=0;u<=12;u++)
            {
                y=u*30;

                lzt.skanZC(y,0.5);


            for (int i=2;i<=350;i++)
            {
                if ((lzt.arrDolg[i+1]!=0) && (lzt.arrDolg[i+1]!=0))

                graphics.addOMGraphic(createLine(lzt.arrShir[i],
                    (lzt.arrDolg[i]),
                    lzt.arrShir[i+1],
                    (lzt.arrDolg[i+1]),
                    Color.RED));
            }
            }
              */
            return graphics;
        }

        //----------------------------------------------------------------------
        // Layer overrides
        //----------------------------------------------------------------------

        /**
         * Renders the graphics list. It is important to make this routine
         * as fast as possible since it is called frequently by Swing, and
         * the User Interface blocks while painting is done.
         */
        public void paint(java.awt.Graphics g) {


            omgraphics.render(g);
        }

        //----------------------------------------------------------------------
        // ProjectionListener interface implementation
        //----------------------------------------------------------------------

        /**
         * Handler for <code>ProjectionEvent</code>s. This function is
         * invoked when the <code>MapBean</code> projection changes. The
         * graphics are reprojected and then the Layer is repainted.
         * <p>
         *
         * @param e the projection event
         */
        public void projectionChanged(ProjectionEvent e) {
            omgraphics.project(e.getProjection(), true);
            repaint();
        }

}
