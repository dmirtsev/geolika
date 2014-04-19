package ru.liveplanet.zt;

/**
 * Created by IntelliJ IDEA.
 * User: damir
 * Date: 17.08.2006
 * Time: 18:37:58
 * To change this template use File | Settings | File Templates.
 */
// $Id$
/*
 * JIMIProducer
 *
 * Copyright (c) 2000 Ken McCrary, All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL purposes and without
 * fee is hereby granted provided that this copyright notice
 * appears in all copies.
 *
 * KEN MCCRARY MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE
 * SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. KEN MCCRARY
 * SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT
 * OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *
 * Copyright (c) 2000 Перевод Виктор Смирнов (victor@uwc.ru)
 */
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Frame;
import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.FontMetrics;
import java.awt.image.ImageProducer;
import java.awt.image.ImageConsumer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiException;
import com.sun.jimi.core.JimiWriter;

/**
 *  Рисуем круговую диаграмму с использованием
 *  библиотеки JIMI toolkit
 *
 * @version $Revision$ $Date$
 */
public  class JIMIProducer //implements ImageProducer
{
  final static int ImageWidth  = 370;
  final static int ImageHeight = 370;
  final static int PieWidth    = 300;
  final static int PieHeight   = 300;
  final static int Radius      = PieWidth/2;
  final static int Inset       = 35;

  /**
   *   Создаем класс JIMIProducer и здесь же рисуем картинку
   *
   *
   */
  public JIMIProducer( )
  {
    //this.graphics = graphics;
    currentTheta = 0;

    Frame f = new Frame();
    f.setVisible(true);
    image = f.createImage(ImageWidth, ImageHeight);
    graphics = image.getGraphics();
    f.setVisible(false);

    drawPieGraph();

    drawSlice( "Pepsi",  108);
    drawSlice( "Coke", 72 );
    drawSlice( "Sprite",  36);
    drawSlice( "7-UP", 72 );
    drawSlice( "Jolt", 72);
  }

  public String getMIMEType(){
    return "image/png";
  }

  /**
   *   Создаем картинку используя JIMI toolkit
   *
   */
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

  /**
   *
   *
   */
  public void drawPieGraph()
  {
    graphics.setColor(Color.blue);
    graphics.drawOval(Inset, Inset, PieWidth, PieHeight);
  }

  /**
   *  Рисует сегмент (дольку) диаграммы.
   *
   */
  public void drawSlice(String label, int degrees)
  {
    Point edge = new Point(PieWidth + Inset, PieHeight/2 + Inset);
    Point center = new Point(PieWidth/2 + Inset, PieHeight/2 + Inset);

    // Находим центр круга
    graphics.drawLine(center.x, center.y, edge.x, edge.y);

    //***************************************************************
    // Преобразуем угол в радианы
    // 1 градус = pi/180 радиан
    //***************************************************************
    double theta = degrees * (3.14/180);
    currentTheta += theta;

    //***************************************************************
    // Переводим в декартовы координаты
    // x = r cos @
    // y = r sin @
    //***************************************************************
    double x = Radius * Math.cos(currentTheta);
    double y = Radius * Math.sin(currentTheta);

    Point mark2 = new Point(center);
    mark2.translate((int)x,(int)y);

    graphics.drawLine(center.x, center.y, mark2.x, mark2.y);

    //***************************************************************
    // Закрашиваем сегмент
    //***************************************************************
    graphics.setColor(colors[colorIndex++]);


    graphics.fillArc(Inset,
                     Inset,
                     PieWidth,
                     PieHeight,
                     -1 * lastAngle,
                     -1 * degrees);

    //***************************************************************
    // Подписываем сегмент посередине
    //***************************************************************
    double labelTheta = currentTheta - theta/2;
    double labelX = Radius * Math.cos(labelTheta);
    double labelY = Radius * Math.sin(labelTheta);

    Point labelPoint = new Point( center );
    labelPoint.translate( (int) labelX, (int) labelY);

    graphics.setColor(Color.black);
    FontMetrics metrics = graphics.getFontMetrics();
    int stringWidth = metrics.stringWidth(label);
    int charWidth = metrics.charWidth('M');

    if ( labelPoint.x > Inset + PieWidth/2 )
    {
      // надпись справа
      graphics.drawString(label,
                          labelPoint.x + charWidth,
                          labelPoint.y);
    }
    else
    {
      // надпись слева
      graphics.drawString(label,
                          labelPoint.x - stringWidth - charWidth,
                          labelPoint.y);

    }

    lastAngle += degrees;

  }

  /**
   *  Для отладки
   *
   */
  public static void main(String[] args)
  {
    try
    {
      // Создаем реализацию класс
      JIMIProducer jimiTest = new JIMIProducer();

      // Пишем картинку в файл
      FileOutputStream stream = new FileOutputStream("piechart.png");
      jimiTest.createImage(stream);
      stream.close();

      System.exit(1);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

  }

  private Graphics graphics;
  private Image image;
  private double currentTheta;
  private Color[] colors = {Color.red,
                            Color.blue,
                            Color.green,
                            Color.pink,
                            Color.orange,
                            Color.yellow};
  private int colorIndex;
  private int lastAngle = 0;


}