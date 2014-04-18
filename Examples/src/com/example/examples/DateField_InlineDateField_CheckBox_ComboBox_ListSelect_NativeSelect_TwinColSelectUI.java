package com.example.examples;

//import com.google.gwt.user.client.ui.Panel;

import java.io.Serializable;
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
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
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
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings({ "serial", "deprecation" })
@Theme("examples")
public class DateField_InlineDateField_CheckBox_ComboBox_ListSelect_NativeSelect_TwinColSelectUI extends UI {

	
	@SuppressWarnings("deprecation")
	@Override
	protected void init(VaadinRequest request) {
	
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
		//*******************
		
		
		//*******************
		
		// Create a DateField with the default style
		DateField date1 = new DateField();
		 
		// Set the date and time to present
		date1.setValue(new Date());
		// Display only year, month, and day in ISO format
		date1.setDateFormat("dd.MM.yyyy");
		layout.addComponent(date1);	
		//******************
		PopupDateField date = new PopupDateField();

		// Set the prompt
		date.setInputPrompt("Select a date");
		        
		// Set width explicitly to accommodate the prompt
		date.setWidth("10em");
		layout.addComponent(date);
		//*******************
		// Create a DateField with the default style
		InlineDateField date3 = new InlineDateField();
		    
		// Set the date and time to present
		date3.setValue(new java.util.Date());
		layout.addComponent(date3);
		//**************
		Button button = new Button("Click Me!");
		button.addClickListener(new Button.ClickListener() {
		    public void buttonClick(ClickEvent event) {
		        Notification.show("Thank You!");
		    }
		});
		 
		layout.addComponent(button);
		//************
		// A check box with default state (not checked, false).
		final CheckBox checkbox1 = new CheckBox("Мой CheckBox");
		layout.addComponent(checkbox1);

		// Another check box with explicitly set checked state.
		final CheckBox checkbox2 = new CheckBox("CheckBox отмечен");
		checkbox2.setValue(true);
		layout.addComponent(checkbox2);

		// Make some application logic. We use anonymous listener
		// classes here. The above references were defined as final
		// to allow accessing them from inside anonymous classes.
		checkbox1.addValueChangeListener(new ValueChangeListener() {
		    public void valueChange(ValueChangeEvent event) {
		        // Copy the value to the other checkbox.
		        checkbox2.setValue(checkbox1.getValue());
		    }
		});
		checkbox2.addValueChangeListener(new ValueChangeListener() {
		    public void valueChange(ValueChangeEvent event) {
		        // Copy the value to the other checkbox.
		        checkbox1.setValue(checkbox2.getValue());
		    }
		});
		//************************
		// Create a selection component
		ComboBox select = new ComboBox("Мой  ComboBox");
		 
		// Add items with given item IDs
		select.addItem("Меркурий");
		select.addItem("Венера");
		select.addItem("Земля");
		layout.addComponent(select);
		//***************
		// Create a selection component
		ComboBox select1 = new ComboBox("Определенные мной");
		 
		// Add an item with a generated ID
		Object itemId = select1.addItem();
		select1.setItemCaption(itemId, "Солнце");
		 
		// Select the item
		select1.setValue(itemId);
		layout.addComponent(select1);
		//********************
		// Create a selection component
		ComboBox select3 = new ComboBox("Луны Марса");
		select3.setItemCaptionMode(ItemCaptionMode.EXPLICIT_DEFAULTS_ID);
		 
		// Use the item ID also as the caption of this item
		select3.addItem(new Integer(1));
		 
		// Set item caption for this item explicitly
		select3.addItem(2); // same as "new Integer(2)"
		select3.setItemCaption(2, "Демос");
		layout.addComponent(select3);
		//*****************
		ComboBox select4 = new ComboBox("Внутренние планеты");
		select4.setItemCaptionMode(ItemCaptionMode.ID);

		// A class that implements toString()
		class PlanetId extends Object implements Serializable {
		    private static final long serialVersionUID = -7452707902301121901L;

		    String planetName;
		    PlanetId (String name) {
		        planetName = name;
		    }
		    public String toString () {
		        return "Пленета " + planetName;
		    }
		}

		// Use such objects as item identifiers
		String planets[] = {"Меркурий", "Венера", "Земля", "Марс"};
		for (int i=0; i<planets.length; i++)
		    select4.addItem(new PlanetId(planets[i]));
		layout.addComponent(select4);
		//*************
		layout.addComponent(new Label("A bean with a \"name\" property."));
		Planet pl = new Planet();
		pl.propertyModeExample(layout);
		//***************
		
		ComboBox combobox = new ComboBox("Заполнение ComboBox массивом");

		// Set the filtering mode
		combobox.setFilteringMode(FilteringMode.CONTAINS);

		// Fill the component with some items
		final String[] planets2 = new String[] {
		        "Меркурий", "Венера", "Земля", "Марс",
		        "Юпитер", "Сатурн", "Уран", "Нептун", "Плутон" };
		for (int i = 0; i < planets2.length; i++)
		    for (int j = 0; j < planets2.length; j++) {
		        combobox.addItem(planets2[j] + " к " + planets2[i]);
		    }
		layout.addComponent(combobox);
		//*****************
		// Create the selection component
		ListSelect select6 = new ListSelect("Мой выбор");
		select6.addItem("Венера");
		select6.addItem("Земля");
		select6.addItem("Марс");
		select6.addItem("Юпитер");
		select6.addItem("Сатурн");
		select6.addItem("Уран");
		select6.addItem("Нептун");
		select6.setNullSelectionAllowed(false);
		select6.setMultiSelect(true);
		// Show 5 items and a scrollbar if there are more
		select6.setRows(6);
		layout.addComponent(select6);
		//*******************
		// Create the selection component
		final NativeSelect select7 = new NativeSelect("Native Selection - ниспадающий список ");
		// Set the width in "columns" as in TextField
		select7.addItem("Венера");
		select7.addItem("Земля");
		select7.addItem("Марс");
		select7.addItem("Юпитер");
		select7.addItem("Сатурн");
		select7.addItem("Уран");
		select7.addItem("Нептун");
		
		select7.setWidth("9em"); 
		select.setNullSelectionAllowed(false);
		layout.addComponent(select7);
		//*******************
		OptionGroup optiongroup = new OptionGroup("Мои Option Group");
		// Use the multiple selection mode.
		optiongroup.addItem("Венера");
		optiongroup.addItem("Марс");
		optiongroup.addItem("Юпитер");
		optiongroup.addItem("Сатурн");
		optiongroup.addItem("Уран");
		optiongroup.addItem("Нептун");
		// Disable one item
		optiongroup.setItemEnabled("Сатурн", false);
		
		optiongroup.setMultiSelect(false); // false то радиокнопки
		
		layout.addComponent(optiongroup);
		
		//******************
		final TwinColSelect select8 =
			    new TwinColSelect("Отсортируйте:");


			// Set the column captions (optional)
		select8.setCaption("Перемещение между наборами**");
			select8.setLeftColumnCaption("Внешние планеты");
			select8.setRightColumnCaption("Внутренние планеты");
			select8.setNewItemsAllowed(true); // позволяет добавлять новые элементы
			select8.setMultiSelect(true);

			        
			// Put some data in the select
			String planets3[] = {"Меркурий", "Венера", "Плутон", "Марс",
			        "Юпитер", "Сатурн", "Уран", "Нептун"};
			for (int pl1=0; pl1<planets3.length; pl1++)
			    select8.addItem(planets3[pl1]);
			 
			// Set the number of visible items
			select8.setRows(planets3.length);
			//select8.setWidth("25em"); ширина

			select8.setMultiSelect(true);
			// layout.addComponent(select8);
			select8.setNewItemsAllowed(true); // позволяет добавлять новые элементы
		
			// Feedback on value changes
			
			select.setImmediate(true);
			layout.addComponent(select8);
			select8.addValueChangeListener(
				    new Property.ValueChangeListener() {
				    public void valueChange(ValueChangeEvent event) {
				        // Some feedback
				        layout.addComponent(new Label("Выбран : " +
				            event.getProperty().getValue().toString()));
				    }
				});

	}
	}