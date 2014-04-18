package com.example.examples;

//import com.google.gwt.user.client.ui.Panel;

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
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
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
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnResizeEvent;
import com.vaadin.ui.Table.HeaderClickEvent;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings({ "serial", "deprecation" })
@Theme("examples")
public class TableUI extends UI {

	
	@SuppressWarnings("deprecation")
	@Override
	protected void init(VaadinRequest request) {
	
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
		//*******************
		/* Create the table1 with a caption. */
		final Table table1 = new Table("Это моя таблица");
		table1.setFooterVisible(true);
		
		table1.setHeight("20em"); //????????????????

		/* Define the names and data types of columns.
		 * The "default value" parameter is meaningless here. */
		table1.addContainerProperty("First Name", String.class,  null);
		table1.addContainerProperty("Last Name",  String.class,  null);
		table1.addContainerProperty("Year",       Integer.class, null);

		/* Add a few items in the table1. */
		table1.addItem(new Object[] {
		    "Nicolaus","Copernicus",new Integer(1473)}, new Integer(1));
		table1.addItem(new Object[] {
		    "Tycho",   "Brahe",     new Integer(1546)}, new Integer(2));
		table1.addItem(new Object[] {
		    "Giordano","Bruno",     new Integer(1548)}, new Integer(3));
		table1.addItem(new Object[] {
		    "Galileo", "Galilei",   new Integer(1564)}, new Integer(4));
		table1.addItem(new Object[] {
		    "Johannes","Kepler",    new Integer(1571)}, new Integer(5));
		table1.addItem(new Object[] {
		    "Isaac",   "Newton",    new Integer(1643)}, new Integer(6));
		
		// Allow selecting items from the table1.
		table1.setSelectable(true);

		// Send changes in selection immediately to server.
		table1.setImmediate(true);

		// Shows feedback from selection.
		final Label current = new Label("Selected: -");
		layout.addComponent(table1);
		layout.addComponent(current);
		
		// Handle selection change.
		table1.addValueChangeListener(new Property.ValueChangeListener() {
		    public void valueChange(ValueChangeEvent event) {
		        current.setValue("Selected: " + table1.getValue());
		    }
		});
		table1.setMultiSelect(true);
		//table1.setMultiSelectMode(MultiSelectMode.DEFAULT); // Мультиселект по CTRL 
		table1.setMultiSelectMode(MultiSelectMode.SIMPLE); // Мультиселект просто по выбору
		
		table1.addColumnResizeListener(new Table.ColumnResizeListener(){
			//@Override
		    public void columnResize(ColumnResizeEvent event) {
		        // Get the new width of the resized column
		        int width = event.getCurrentWidth();
		        
		        // Get the property ID of the resized column
		        String column = (String) event.getPropertyId();

		        // Do something with the information
		        table1.setColumnFooter(column, String.valueOf(width) + "px");
		    }

			//@Override
			//public void columnResize(ColumnResizeEvent event) {
				// TODO Auto-generated method stub
			//}
		});
		// Must be immediate to send the resize events immediately
		table1.setImmediate(true);
		
		// Allow the user to collapse and uncollapse columns
		table1.setColumnCollapsingAllowed(true);

		// Collapse this column programmatically
		try {
		    table1.setColumnCollapsed("born", true);
		} catch (Exception e) {
		    // Can't occur - collapsing was allowed above
		    System.err.println("Something horrible occurred");
		}
		        
		// Give enough width for the table1 to accommodate the
		// initially collapsed column later
		table1.setWidth("250px");
		
		//*******************
		// Create a table2 and add a style to allow setting the row height in theme.
		final Table table2 = new Table();
		table2.addStyleName("components-inside");

		/* Define the names and data types of columns.
		 * The "default value" parameter is meaningless here. */
		table2.addContainerProperty("Sum",            Label.class,     null);
		table2.addContainerProperty("Is Transferred", CheckBox.class,  null);
		table2.addContainerProperty("Comments",       TextField.class, null);
		table2.addContainerProperty("Details",        Button.class,    null);
		/* Add a few items in the table2. */
		for (int i=0; i<100; i++) 
		{
		    // Create the fields for the current table2 row
		    Label sumField = new Label(String.format(
		                   "Sum is <b>$%04.2f</b><br/><i>(VAT incl.)</i>",
		                   new Object[] {new Double(Math.random()*1000)}),
		                               ContentMode.HTML);
		    CheckBox transferredField = new CheckBox("is transferred");
		    
		    // Multiline text field. This required modifying the 
		    // height of the table2 row.
		    TextField commentsField = new TextField();
		    //commentsField.setRows(3);
		    commentsField.setHeight("5em");;
		    
		    // The Table item identifier for the row.
		    Integer itemId = new Integer(i);
		    
		    // Create a button and handle its click. A Button does not
		    // know the item it is contained in, so we have to store the
		    // item ID as user-defined data.
		    Button detailsField = new Button("show details");
		    detailsField.setData(itemId);
		    detailsField.addClickListener(new Button.ClickListener() 
		    {
		        public void buttonClick(ClickEvent event) 
		        {
		            // Get the item identifier from the user-defined data.
		            Integer iid = (Integer)event.getButton().getData();
		            Notification.show("Link " +
		                              iid.intValue() + " clicked.");
		        } 
		    });
		    detailsField.addStyleName("link");
		    // Create the table2 row.
		    table2.addItem(new Object[] {sumField, transferredField,
		                                commentsField, detailsField},
		                  itemId);
		}
		// Show just three rows because they are so high.
		table2.setPageLength(3);
	layout.addComponent(table2);
	//*****************************
	// Create a table. It is by default not editable.
	final Table table = new Table();

	// Define the names and data types of columns.
	table.addContainerProperty("Date",     Date.class,  null);
	table.addContainerProperty("Work",     Boolean.class, null);
	table.addContainerProperty("Comments", String.class,  null);

	// Add a few items in the table.
	for (int i=0; i<100; i++) {
	    Calendar calendar = new GregorianCalendar(2008,0,1);
	    calendar.add(Calendar.DAY_OF_YEAR, i);
	    
	    // Create the table row.
	    table.addItem(new Object[] {calendar.getTime(),
	                                new Boolean(false),
	                                ""},
	                  new Integer(i)); // Item identifier
	}
	 
	table.setPageLength(8);
	layout.addComponent(table);
	
	final CheckBox switchEditable = new CheckBox("Editable");
	switchEditable.addValueChangeListener(
	        new Property.ValueChangeListener() {
	    public void valueChange(ValueChangeEvent event) {
	        table.setEditable(((Boolean)event.getProperty()
	                             .getValue()).booleanValue());
	    }
	});
	switchEditable.setImmediate(true);
	layout.addComponent(switchEditable);
	
	// Keyboard navigation
	// Не получилось. Горячие клавиши не прицепил!!!!!!!!!!!!!!!!!!!!!
	class KbdHandler implements Handler {
	    Action tab_next = new ShortcutAction("Tab",
	            ShortcutAction.KeyCode.TAB, null);
	    Action tab_prev = new ShortcutAction("Shift+Tab",
	            ShortcutAction.KeyCode.TAB,
	            new int[] {ShortcutAction.ModifierKey.SHIFT});
	    Action cur_down = new ShortcutAction("Down",
	            ShortcutAction.KeyCode.ARROW_DOWN, null);
	    Action cur_up   = new ShortcutAction("Up",
	            ShortcutAction.KeyCode.ARROW_UP,   null);
	    Action enter   = new ShortcutAction("Enter",
	            ShortcutAction.KeyCode.ENTER,      null);
	    public Action[] getActions(Object target, Object sender) {
	        return new Action[] {tab_next, tab_prev, cur_down,
	                             cur_up, enter};
	    }

	    public void handleAction(Action action, Object sender,
	                             Object target) {
	        if (target instanceof TextField) {
	            // Move according to keypress
	            int itemid = (Integer) ((TextField) target).getData();
	            if (action == tab_next || action == cur_down)
	                itemid++;
	            else if (action == tab_prev || action == cur_up)
	                itemid--;
	            // On enter, just stay where you were. If we did
	            // not catch the enter action, the focus would be
	            // moved to wrong place.
	            
	            if (itemid >= 0 && itemid < table.size()) {
	                //TextField newTF = valueFields.get(itemid);
	            	TextField newTF = (TextField)target;
	                if (newTF != null)
	                    newTF.focus();
	            }
	        }
	    }
	}
	 
	// Panel that handles keyboard navigation
	Panel navigator = new Panel();
	navigator.addStyleName(Reindeer.PANEL_LIGHT);
	//navigator.addComponent(table);
	navigator.addActionHandler(new KbdHandler());
	
	// *************************************
	// Have a table with a numeric column
	Table table3 = new Table("Настройка Footer таблицы");
	table3.addContainerProperty("Имя", String.class, null);
	table3.addContainerProperty("Возраст смерти", Integer.class, null);
	        
	// Insert some data
	Object people[][] = {{"Galileo",  77},
	                     {"Monnier",  83},
	                     {"Vaisala",  79},
	                     {"Oterma",   86}};
	for (int i=0; i<people.length; i++)
	    table3.addItem(people[i], new Integer(i));
	        
	// Calculate the average of the numeric column
	double avgAge = 0;
	for (int i=0; i<people.length; i++)
	    avgAge += (Integer) people[i][1];
	avgAge /= people.length;
	 
	// Set the footers
	table3.setFooterVisible(true);
	table3.setColumnFooter("Имя", "Средн.");
	table3.setColumnFooter("Возраст смерти", String.valueOf(avgAge));
	 
	// Adjust the table height a bit
	table3.setPageLength(table3.size());
	layout.addComponent(table3);
	
	// Handle the header clicks
	table3.addHeaderClickListener(new Table.HeaderClickListener() {
	    public void headerClick(HeaderClickEvent event) {
	        String column = (String) event.getPropertyId();
	        Notification.show("Clicked " + column +
	                " with " + event.getButtonName());
	    }
	});
	        
	// Disable the default sorting behavior
	table3.setSortEnabled(false);
	
	}
	}