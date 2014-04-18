package com.example.examples;

//import com.google.gwt.user.client.ui.Panel;
import com.vaadin.annotations.Theme;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.BorderStyle;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("examples")
public class Button_Label_LinkUI extends UI {

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
		//*******************
		Panel panel = new Panel("Panel Containing a Label");
		panel.setWidth("600px");

		panel.setContent(
		    new Label("This is a Label inside a Panel. There is " +
		              "enough text in the label to make the text " +
		              "wrap when it exceeds the width of the panel."));
		layout.addComponent(panel);
		
		GridLayout labelgrid = new GridLayout (2,1);
		//*******************
		labelgrid.addComponent (new Label ("PREFORMATTED"));
		labelgrid.addComponent (
		    new Label (" This is a preformatted label.\n"+
		               "The newline character \n breaks the line.",
		               ContentMode.PREFORMATTED));

		labelgrid.addComponent (new Label ("TEXT"));
		labelgrid.addComponent (
		    new Label (" This is a label in (plain) text mode",
		               ContentMode.TEXT));

		labelgrid.addComponent (new Label ("HTML"));
		labelgrid.addComponent (
		    new Label ("<i>This</i> is an <b>HTML</b> formatted label",
		    		ContentMode.HTML));
		 
		layout.addComponent(labelgrid); 
		//*******************
		// Textual link
		Link link = new Link("Click Me!",
		        new ExternalResource("http://vaadin.com/"));
		layout.addComponent(link);
		
		// Image link
		Link iconic = new Link(null,
		        new ExternalResource("http://vaadin.com/"));
		iconic.setIcon(new ThemeResource("img/favorites_4122.ico"));
		layout.addComponent(iconic);
		// Image + caption
		Link combo = new Link("To appease both literal and visual",
		        new ExternalResource("http://vaadin.com/"));
		combo.setIcon(new ThemeResource("img/favorites_4122.ico"));
		// Фактически за иконкой обращается сюда:
		// http://localhost:8080/Examples/VAADIN/themes/examples/res/img/favorites_4122.ico
		layout.addComponent(combo);
		//*******************
		// Hyperlink to a given URL
		Link link1 = new Link("Take me a away to a faraway land",
		        new ExternalResource("http://vaadin.com/"));

		// Open the URL in a new window/tab
		link1.setTargetName("_blank");
		        
		// Indicate visually that it opens in a new window/tab
		link1.setIcon(new ThemeResource("img/favorites_4122.ico"));
		link1.addStyleName("icon-after-caption"); // Сам стиль добавляем сюда 
		// WebContent/VAADIN/themes/examples/styles.scss!!!
		layout.addComponent(link1);
		//**************************
		// Open the URL in a popup
		Link link2 = new Link("Open the URL in a popup",
		        new ExternalResource("http://vaadin.com/"));

		link2.setTargetName("_blank");
		link2.setTargetBorder(BorderStyle.NONE);
		link2.setTargetHeight(300);
		link2.setTargetWidth(400);
		layout.addComponent(link2);
	}
	}