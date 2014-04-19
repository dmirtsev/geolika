package ru.liveplanet.zt;

/**
 * Created by IntelliJ IDEA.
 * User: damir
 * Date: 17.08.2006
 * Time: 17:42:47
 * To change this template use File | Settings | File Templates.
 */
import java.awt.image.*;
import java.awt.*;
import java.applet.*;

public class CreateAnImage extends Frame{
  Image myImage;

  public CreateAnImage() {
    int x = 100;
    int y = 100;
    myImage = createImage(x,y);
    Graphics g = myImage.getGraphics();
    g.drawLine(0,0,x,y);
    g.drawLine(x,0,0,y);
    for(int i=0; i < x; i+=2){
       setPixel(myImage, 50, i, new Color(0).blue);
       setPixel(myImage, i, 50, new Color(0).green);
       }
    }

  public void paint(Graphics g) {
    g.drawImage(myImage,0,0,this);
    }

  public void setPixel
      (Image image, int x, int y, Color color ) {
    Graphics g = image.getGraphics( );
    g.setColor( color );
    g.fillRect( x, y, 1, 1 );
    g.dispose( );
    }

    public static void main(String[] args) {
        CreateAnImage ca= new CreateAnImage();
        
    }
}