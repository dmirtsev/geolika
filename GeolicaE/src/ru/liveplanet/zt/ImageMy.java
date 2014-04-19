package ru.liveplanet.zt;

/**
 * Created by IntelliJ IDEA.
 * User: damir
 * Date: 17.08.2006
 * Time: 16:20:56
 * To change this template use File | Settings | File Templates.
 */
import java.awt.image.ImageProducer;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.awt.*;
import java.io.*;
import java.util.Arrays;

import com.sun.jimi.core.decoder.pcx.PCXDecoder;
import com.sun.jimi.core.decoder.pcx.PCXDecoderFactory;
import com.sun.jimi.core.encoder.jpg.JPGEncoder;
import com.sun.jimi.core.encoder.jpg.JPGEncoderFactory;
import com.sun.jimi.core.*;
import com.sun.jimi.core.raster.JimiRasterImage;

import javax.imageio.ImageIO;

public class ImageMy {

    public static boolean DEBUG=true;

    public static void out(String str){
        System.out.println(str);
    }

    public static void main(String[] args){

        my1("putin-4.gif", "putin-2_1.jpg");

    }

    public static void my1(String sourcename,String destname){

        FileOutputStream out = null;
        try{
            out = new FileOutputStream(destname);
        } catch (FileNotFoundException e) {
            out("Error! FileNotFoundException ");
            return;
        }

//        String[] arr= ImageIO.getWriterMIMETypes();
//        System.out.println(Arrays.asList(arr));

        JPGEncoder jpg=new JPGEncoder();
        JimiReader jir = null;

        try{
            jir=Jimi.createJimiReader(sourcename);
        }  catch (JimiException e) {
            out("Error! JimiException1111 ");
            return;
        }


        try{

            jpg.encodeImage(jir.getRasterImage(),out);
            jir.close();
        }  catch (JimiException e) {
            out("Error! JimiException ");
            return;
        }

        try{
             out.close();
        }  catch (Exception e) {
             out("Error! Exception ");
             return;
        }


    }

}