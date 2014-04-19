package ru.liveplanet.zt;

/**
 * Created by IntelliJ IDEA.
 * User: DBashkirtsev
 * Date: 03.08.2009
 * Time: 15:12:49
 * To change this template use File | Settings | File Templates.
 * Рисует линии всех землеточек на карту
 */
import java.util.Properties;

import com.bbn.openmap.LatLonPoint;
import com.bbn.openmap.LayerHandler;
import com.bbn.openmap.MapBean;
import com.bbn.openmap.MapHandler;
import com.bbn.openmap.MultipleSoloMapComponentException;
import com.bbn.openmap.examples.simple.RouteLayer;
import com.bbn.openmap.gui.BasicMapPanel;
import com.bbn.openmap.gui.MapPanel;
import com.bbn.openmap.gui.OMToolSet;
import com.bbn.openmap.gui.OpenMapFrame;
import com.bbn.openmap.gui.ToolPanel;
import com.bbn.openmap.layer.GraticuleLayer;
import com.bbn.openmap.layer.shape.ShapeLayer;
import com.bbn.openmap.layer.location.LocationLayer;

public class LineInCart1 {
public static void main(String args[]) {

        // Create a Swing frame. The OpenMapFrame knows how to use
        // the MapHandler to locate and place certain objects.
        OpenMapFrame frame = new OpenMapFrame("Simple Map 2");
        // Size the frame appropriately
        frame.setSize(640, 480);
        //LocationLayer ll=new LocationLayer();
        //

        try {

            /*
             * The BasicMapPanel automatically creates many default
             * components, including the MapBean and the MapHandler.
             * You can extend the BasicMapPanel class if you like to
             * add different functionality or different types of
             * objects.
             */
            MapPanel mapPanel = new BasicMapPanel();

            // Get the default MapHandler the BasicMapPanel created.
            MapHandler mapHandler = mapPanel.getMapHandler();
            mapHandler.add(frame);

            // Get the default MapBean that the BasicMapPanel created.
            MapBean mapBean = mapPanel.getMapBean();

            // Set the map's center
            mapBean.setCenter(new LatLonPoint(43.0f, -95.0f));

            // Set the map's scale 1:120 million
            mapBean.setScale(120000000f);

            /*
             * Create and add a LayerHandler to the MapHandler. The
             * LayerHandler manages Layers, whether they are part of
             * the map or not. layer.setVisible(true) will add it to
             * the map. The LayerHandler has methods to do this, too.
             * The LayerHandler will find the MapBean in the
             * MapHandler.
             */
            mapHandler.add(new LayerHandler());

            // Add a route layer.
            // Ниже начнутся расчеты
            RLayer routeLayer = new RLayer();
            routeLayer.setVisible(true);
            // The LayerHandler will find the Layer in the MapHandler.

            /*
             * Create a ShapeLayer to show world political boundaries.
             * Set the properties of the layer. This assumes that the
             * datafiles "dcwpo-browse.shp" and "dcwpo-browse.ssx" are
             * in a path specified in the CLASSPATH variable. These
             * files are distributed with OpenMap and reside in the
             * toplevel "share" subdirectory.
             */
            ShapeLayer shapeLayer = new ShapeLayer();

            // Since this Properties object is being used just for
            // this layer, the properties do not have to be scoped
            // with marker name, like the layer properties in the
            // ../hello/HelloWorld.properties file.
            Properties shapeLayerProps = new Properties();
            shapeLayerProps.put("prettyName", "Political Solid");
            shapeLayerProps.put("lineColor", "000000");
            shapeLayerProps.put("fillColor", "BDDE83");
            shapeLayerProps.put("shapeFile", "shareOpenMap\\data\\shape\\vmap_area_thin.shp");
            shapeLayerProps.put("spatialIndex", "shareOpenMap\\data\\shape\\data/shape/dcwpo-browse.ssx");
            shapeLayer.setProperties(shapeLayerProps);
            shapeLayer.setVisible(true);


            // Last on top.
            mapHandler.add(shapeLayer);
            mapHandler.add(new GraticuleLayer());
            mapHandler.add(routeLayer);

            // Create the directional and zoom control tool
            OMToolSet omts = new OMToolSet();
            // Create an OpenMap toolbar
            ToolPanel toolBar = new ToolPanel();

            /*
             * Add the ToolPanel and the OMToolSet to the MapHandler.
             * The OpenMapFrame will find the ToolPanel and attach it
             * to the top part of its content pane, and the ToolPanel
             * will find the OMToolSet and add it to itself.
             */
            mapHandler.add(omts);
            mapHandler.add(toolBar);
            // Display the frame
            frame.setVisible(true);

        } catch (MultipleSoloMapComponentException msmce) {
            // The MapHandler is only allowed to have one of certain
            // items. These items implement the SoloMapComponent
            // interface. The MapHandler can have a policy that
            // determines what to do when duplicate instances of the
            // same type of object are added - replace or ignore.

            // In this example, this will never happen, since we are
            // controlling that one MapBean, LayerHandler,
            // MouseDelegator, etc is being added to the MapHandler.
        }
}
}
