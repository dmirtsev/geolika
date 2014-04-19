package ru.liveplanet.zt;

import com.sun.jimi.core.JimiException;
import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.raster.JimiRasterImage;

import java.io.File;
import java.io.FileOutputStream;
import java.awt.image.BufferedImage;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: damir
 * Date: 17.08.2006
 * Time: 16:38:00
 * To change this template use File | Settings | File Templates.
 */
public class ImageInFile {
public static void main(String[] argStr)   throws Exception
{
    double w = 200.0;
    double h = 200.0;

    BufferedImage image = new BufferedImage(
       (int)w,(int)h,BufferedImage.TYPE_INT_RGB);
    image.setRGB(100,0,0);

    Graphics2D g = (Graphics2D)image.getGraphics();
    g.setColor(Color.blue);
    g.drawLine(0,0,(int)w,(int)h);

    try {
       File f = new File("myimage.jpg");
       JimiRasterImage jrf = Jimi.createRasterImage(image.getSource());
       Jimi.putImage("image/jpeg",jrf,new FileOutputStream(f));
       }
    catch (JimiException je) {je.printStackTrace();}

}

}
