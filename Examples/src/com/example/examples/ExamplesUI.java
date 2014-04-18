package com.example.examples;

//import com.google.gwt.user.client.ui.Panel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;








//import com.vaadin.data.Property.ConversionException;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
//import com.google.gwt.dev.cfg.Properties;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Property;
import com.vaadin.server.ClassResource;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.BorderStyle;
import com.vaadin.shared.ui.MultiSelectMode;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.data.Item;
//import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertyFormatter;
import com.vaadin.data.validator.DoubleValidator;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnResizeEvent;
import com.vaadin.ui.Table.HeaderClickEvent;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings({ "serial", "deprecation" })
@Theme("examples")
public class ExamplesUI extends UI {

	public static final String basepath = VaadinService.getCurrent()
            .getBaseDirectory().getAbsolutePath(); // файловый путь до /web_inf
												   // туда в img лучше положить такие ресурсы, как image
	
	@SuppressWarnings("deprecation")
	@Override
	protected void init(VaadinRequest request) {
	
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
		//******************
		// A top-level menu item that opens a submenu
		MenuBar menubar = new MenuBar();
		layout.addComponent(menubar);
		// Define a common menu command for all the menu items.
				MenuBar.Command mycommand = new MenuBar.Command() {
				    public void menuSelected(MenuItem selectedItem) 
				    {
				        Label selection =new Label();
						selection .setValue("Ordered a " +
				                           selectedItem.getText() +
				                           " from menu.");
				    }  
				};
		
		MenuItem drinks = menubar.addItem("Beverages", null, null);

		// Submenu item with a sub-submenu
		MenuItem hots = drinks.addItem("Hot", null, null);
		hots.addItem("Tea",
		    new ThemeResource("img/application-community_5242.ico"),    mycommand);
		hots.addItem("Coffee",
		    new ThemeResource("img/application-ogg_2361.ico"), mycommand);

		// Another submenu item with a sub-submenu
		MenuItem colds = drinks.addItem("Cold", null, null);
		colds.addItem("Milk",      null, mycommand);
		colds.addItem("Weissbier", null, mycommand);

		// Another top-level item
		MenuItem snacks = menubar.addItem("Snacks", null, null);
		snacks.addItem("Weisswurst", null, mycommand);
		snacks.addItem("Bratwurst",  null, mycommand);
		snacks.addItem("Currywurst", null, mycommand);
		        
		// Yet another top-level item
		MenuItem servs = menubar.addItem("Services", null, null);
		servs.addItem("Car Service", null, mycommand);
		
		// A feedback component
		final Label selection = new Label("-");
		layout.addComponent(selection);
		//*******************
		//Embedded Resources
		//Image image = new Image("Yes, logo:",
			//    new ClassResource(basepath+"/WEB-INF/img/application-community_6674.ico"));
		Image image = new Image("Yes, logo:",
		new ClassResource("application-community_6674.ico"));

			layout.addComponent(image);
			//**************
			HorizontalLayout barbar = new HorizontalLayout();
			layout.addComponent(barbar);
			        
			// Create the indicator, disabled until progress is started
			final ProgressBar progress = new ProgressBar(new Float(0.0));
			progress.setEnabled(false);
			barbar.addComponent(progress);
			        
			final Label status = new Label("not running");
			barbar.addComponent(status);

			// A button to start progress
			final Button button = new Button("Click to start");
			layout.addComponent(button);

			// A thread to do some work
			class WorkThread extends Thread {
			    // Volatile because read in another thread in access()
			    volatile double current = 0.0;

			    @Override
			    public void run() {
			        // Count up until 1.0 is reached
			        while (current < 1.0) {
			            current += 0.01;

			            // Do some "heavy work"
			            try {
			                sleep(50); // Sleep for 50 milliseconds
			            } catch (InterruptedException e) {}

			            // Update the UI thread-safely
			            UI.getCurrent().access(new Runnable() {
			                @Override
			                public void run() {
			                    progress.setValue(new Float(current));
			                    if (current < 1.0)
			                        status.setValue("" +
			                            ((int)(current*100)) + "% done");
			                    else
			                        status.setValue("all done");
			                }
			            });
			        }
			        
			        // Show the "all done" for a while
			        try {
			            sleep(2000); // Sleep for 2 seconds
			        } catch (InterruptedException e) {}

			        // Update the UI thread-safely
			        UI.getCurrent().access(new Runnable() {
			            @Override
			            public void run() {
			                // Restore the state to initial
			                progress.setValue(new Float(0.0));
			                progress.setEnabled(false);
			                        
			                // Stop polling
			                UI.getCurrent().setPollInterval(-1);
			                
			                button.setEnabled(true);
			                status.setValue("not running");
			            }
			        });
			    }
			}

			// Clicking the button creates and runs a work thread
			button.addClickListener(new Button.ClickListener() {
			    public void buttonClick(ClickEvent event) {
			        final WorkThread thread = new WorkThread();
			        thread.start();

			        // Enable polling and set frequency to 0.5 seconds
			        UI.getCurrent().setPollInterval(500);

			        // Disable the button until the work is done
			        progress.setEnabled(true);
			        button.setEnabled(false);

			        status.setValue("running...");
			    }
			});
		//*******************
			BrowserFrame browser = new BrowserFrame("Browser",
				    new ExternalResource("http://demo.vaadin.com/sampler/"));
				browser.setWidth("600px");
				browser.setHeight("400px");
				layout.addComponent(browser);
			//*****************
		//tree
		final Object[][] planets = new Object[][]{
		        new Object[]{"Mercury"}, 
		        new Object[]{"Venus"},
		        new Object[]{"Earth", "The Moon"},    
		        new Object[]{"Mars", "Phobos", "Deimos"},
		        new Object[]{"Jupiter", "Io", "Europa", "Ganymedes",
		                                "Callisto"},
		        new Object[]{"Saturn",  "Titan", "Tethys", "Dione",
		                                "Rhea", "Iapetus"},
		        new Object[]{"Uranus",  "Miranda", "Ariel", "Umbriel",
		                                "Titania", "Oberon"},
		        new Object[]{"Neptune", "Triton", "Proteus", "Nereid",
		                                "Larissa"}};
		        
		Tree tree = new Tree("The Planets and Major Moons");

		/* Add planets as root items in the tree. */
		for (int i=0; i<planets.length; i++) {
		    String planet = (String) (planets[i][0]);
		    tree.addItem(planet);
		    
		    if (planets[i].length == 1) {
		        // The planet has no moons so make it a leaf.
		        tree.setChildrenAllowed(planet, false);
		    } else {
		        // Add children (moons) under the planets.
		        for (int j=1; j<planets[i].length; j++) {
		            String moon = (String) planets[i][j];
		            
		            // Add the item as a regular item.
		            tree.addItem(moon);
		            
		            // Set it to be a child.
		            tree.setParent(moon, planet);
		            
		            // Make the moons look like leaves.
		            tree.setChildrenAllowed(moon, false);
		        }

		        // Expand the subtree.
		        tree.expandItemsRecursively(planet);
		    }
		}
		 
		layout.addComponent(tree);
		
		//*************************

		
	
	}
	}