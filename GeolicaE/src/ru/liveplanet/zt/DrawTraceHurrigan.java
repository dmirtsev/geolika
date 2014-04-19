package ru.liveplanet.zt;

/**
 * Created by IntelliJ IDEA.
 * User: DBashkirtsev
 * Date: 03.08.2009
 * Time: 15:16:19
 * To change this template use File | Settings | File Templates.
 */
import java.awt.*;
import java.io.*;

import com.bbn.openmap.Layer;
import com.bbn.openmap.omGraphics.*;
import com.bbn.openmap.event.ProjectionEvent;

    public class DrawTraceHurrigan extends Layer
    {


        String data="",dolg="",shir="",pressue="",speed="";
        String fileName ="";
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

        public DrawTraceHurrigan(String fileName) {


            this.fileName=fileName;
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



      String[][] findInFile()
    {
               String str="";
         String[] al=new String[50];
        String[][]nabor=new String[400][5];
                   try {

                      // String fileString="";
	             	                 InputStreamReader isr=new InputStreamReader(
                           new FileInputStream(fileName),"windows-1251");
	                    BufferedReader in = new BufferedReader(isr);


         int t=0;

       while (((str = in.readLine()) != null))
        {
            try
            {
            if ((str.indexOf("#")!=-1)||(str.trim().equals(""))) continue;    
            al=str.split("\\t");

             for  (int i=0;i<al.length;i++)
        {
               data=al[0];shir=al[1];dolg=al[2];pressue=al[3];speed=al[4];
            nabor[t][i]=al[i];

        }
            }
            catch (Exception e)
            {
                          System.out.println("Конец файла ");
            }
             t++;
        }



        in.close();

    } catch (IOException e)
                   {
                        System.out.println("----");
                   }
        return nabor;
    } ;




    void arrTraceHurFromFile()
    {

    }


        public  OMGraphicList createGraphics(OMGraphicList graphics)
        {
            String[][] arg= findInFile();
            float sh1=0,sh2=0,dol1=0,dol2=0;

            for (int j=0;j<arg.length;j++)
            {
               // if

                try
                {
              sh1=Float.parseFloat(arg[j][1]);
              dol1=Float.parseFloat(arg[j][2]);
              sh2=Float.parseFloat(arg[j+1][1]);
              dol2=Float.parseFloat(arg[j+1][2]);

              graphics.addOMGraphic(createLine(sh1,-dol1,sh2,-dol2,Color.CYAN));
                System.out.println(sh1+" "+dol1+" --- "+sh2+" "+dol2);
                }
                catch (Exception e)
                {
                    System.out.println("Конец массива");
                    break;
                }
                  //}
            }




                   // koordOutputLabel(lzt,planet,aspect,j);
                //double rr= Double.parseDouble();
             //   float dolg=Float.parseFloat(koordOutputLabel(lzt,planet,aspect,j)[0]);
              //  float shir=Float.parseFloat(koordOutputLabel(lzt,planet,aspect,j)[1]);
              //  String str=koordOutputLabel(lzt,planet,aspect,j)[2];
                        //( );
                //int.koordOutputLabel(lzt,j)[1].in;


                   //graphics.add(createLabel(shir,dolg,str));

            //}
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
            return graphics; // #####
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

        public static void main (String[] argStr)
        {
           DrawTraceHurrigan dth= new DrawTraceHurrigan("");
            dth.findInFile();
            //dth.createGraphics();
        }

}
