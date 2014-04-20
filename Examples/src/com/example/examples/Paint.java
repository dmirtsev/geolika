package com.example.examples;

//import com.google.gwt.user.client.ui.Panel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.animation.Animate;
import org.vaadin.gwtgraphics.client.shape.Circle;

import com.google.gwt.user.client.ui.RootPanel;
import com.vaadin.annotations.Theme;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.BorderStyle;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("examples")
public class Paint extends UI {


	
	
	@SuppressWarnings("deprecation")
	@Override
	protected void init(VaadinRequest request) {
	
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
		//*******************
		
		Button button = new Button("Click Me");
		button.addClickListener
		(
				new Button.ClickListener() 
		{
			public void buttonClick(ClickEvent event) {
				layout.addComponent(new Label("Thank you for clicking"));
			}
		}
		);
		layout.addComponent(button);
	
		//layout.addComponent((Component) canvas);
		
		//****************
		//DrawingArea canvas = new DrawingArea(400, 400);
		//RootPanel.get().add(canvas);
		//Circle circle = new Circle(200, 200, 0);
		//canvas.add(circle);
		//new Animate(circle, "radius", 0, 100, 3000).start();
	}
	 public void paint(Graphics g) {
		    //Here is how we used to draw a square with width
		    //of 200, height of 200, and starting at x=50, y=50.
		    g.setColor(Color.red);
		    g.drawRect(50,50,200,200);
		 
		    //Let's set the Color to blue and then use the Graphics2D
		    //object to draw a rectangle, offset from the square.
		    //So far, we've not done anything using Graphics2D that
		    //we could not also do using Graphics.  (We are actually
		    //using Graphics2D methods inherited from Graphics.)
		    Graphics2D g2d = (Graphics2D)g;
		    g2d.setColor(Color.blue);
		    g2d.drawRect(75,75,300,200);

		    //Now, let's draw another rectangle, but this time, let's
		    //use a GeneralPath to specify it segment by segment.
		    //Furthermore, we're going to translate and rotate this 
		    //rectangle relative to the Device Space (and thus, to
		    //the first two quadrilaterals) using an AffineTransform.
		    //We also will change its color.
		    GeneralPath path = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		    path.moveTo(0.0f,0.0f);
		    path.lineTo(0.0f,125.0f);
		    path.lineTo(225.0f,125.0f);
		    path.lineTo(225.0f,0.0f);
		    path.closePath();

		    AffineTransform at = new AffineTransform();
		    at.setToRotation(-Math.PI/8.0);
		    g2d.transform(at);
		    at.setToTranslation(50.0f,200.0f);
		    g2d.transform(at);

		    g2d.setColor(Color.green);
		    g2d.fill(path);
		  }
	}