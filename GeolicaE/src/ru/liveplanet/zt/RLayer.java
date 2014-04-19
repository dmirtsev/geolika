package ru.liveplanet.zt;

/**
 * Created by IntelliJ IDEA.
 * User: DBashkirtsev
 * Date: 03.08.2009
 * Time: 15:16:19
 * To change this template use File | Settings | File Templates.
 */
import java.awt.Color;

import com.bbn.openmap.Layer;
import com.bbn.openmap.omGraphics.OMGraphic;
import com.bbn.openmap.omGraphics.OMGraphicList;
import com.bbn.openmap.omGraphics.OMLine;
import com.bbn.openmap.event.ProjectionEvent;

    public class RLayer extends Layer {

        /**
         * A list of graphics to be painted on the map.
         */
        private OMGraphicList omgraphics;

        /**
         * Construct a default route layer. Initializes omgraphics to a
         * new OMGraphicList, and invokes createGraphics to create the
         * canned list of routes.
         */
        public RLayer() {

            omgraphics = new OMGraphicList();
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
            int y=0;
            for (int u=0;u<=12;u++)
            {
                y=u*30;

                lzt.skanZJ(y,0.3,0.05,0.3f);



          /*  graphics.addOMGraphic(createLine(90,
                    //37-180,
                    37,
                    -90,
                    37 ,
                    //37-180,
                    Color.red));*/
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
            for (int u=0;u<=12;u++)
            {
                y=u*30;

                lzt.skanZC(y,0.5,0.05,0.2f);



          /*  graphics.addOMGraphic(createLine(90,
                    //37-180,
                    37,
                    -90,
                    37 ,
                    //37-180,
                    Color.red));*/
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
