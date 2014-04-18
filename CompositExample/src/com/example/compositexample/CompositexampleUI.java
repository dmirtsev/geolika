package com.example.compositexample;

//import com.gargoylesoftware.htmlunit.javascript.host.Window;
//import com.google.gwt.user.client.ui.HorizontalSplitPanel;
//import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
//import com.vaadin.Application;
//import com.vaadin.

@SuppressWarnings("serial")
@Theme("compositexample")
public class CompositexampleUI extends UI{

	CompWindows cv= new CompWindows();
	
	@SuppressWarnings("deprecation")
	@Override
	protected void init(VaadinRequest request) 
	{
		AbsoluteLayout layout = new AbsoluteLayout();
		layout.setWidth("900px");
		layout.setHeight("550px");
		        
		// A component with coordinates for its top-left corner
		//TextField text = new TextField("Somewhere someplace!!!");
		//layout.addComponent(text, "left: 50px; top: 50px;");
		layout.addComponent(cv);
		//panel.setContent(layout);
		setContent(layout);
		
		
	}

}