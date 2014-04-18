package com.example.examples;

//import com.google.gwt.user.client.ui.Panel;

import com.vaadin.annotations.Theme;
//import com.vaadin.data.Property;
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
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertyFormatter;
import com.vaadin.data.validator.DoubleValidator;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings({ "serial", "deprecation" })
@Theme("examples")
public class TextField_Text_area_PasswField_BigArea_RichTextUI extends UI {

	@SuppressWarnings("deprecation")
	@Override
	protected void init(VaadinRequest request) {
	
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
		//*******************
		// Create a text field
		TextField tf = new TextField("Это поле");
		        
		// Put some initial content in it
		tf.setValue("Начальное значение, измените его");
		layout.addComponent(tf);
		//*******************
		// Обработчик смены значения поля
		tf.addValueChangeListener((new Property.ValueChangeListener() {
		    private static final long serialVersionUID = -6549081726526133772L;

		    public void valueChange(ValueChangeEvent event) {
		        // Assuming that the value type is a String
		        String value = (String) event.getProperty().getValue();
		        // Do something with the value
		        Notification.show("Введенное значение: " + value);
		    }
		}));
		// Fire value changes immediately when the field loses focus
		tf.setImmediate(true);
		//*********************************
		
		// Have an initial data model. As Double is unmodificable and
		// doesn't support assignment from String, the object is
		// reconstructed in the wrapper when the value is changed.
		Double trouble = 42.0;
		        
		// Wrap it in a property data source
		final ObjectProperty<Double> property =
		    new ObjectProperty<Double>(trouble);
		        
		// Create a text field bound to it
		// (StringToDoubleConverter is used automatically)
		TextField tf2 = new TextField("Проверяет на double", property);
		tf2.setImmediate(true);
		 
		// Show that the value is really written back to the
		// data source when edited by user.
		Label feedback = new Label(property);
		feedback.setCaption("Считывает обратное значение");
		layout.addComponent(tf2);
		layout.addComponent(feedback);
		//**************
		// Create a text field without setting its value
		TextField tf3 = new TextField("Field Energy (J)");
		tf3.setNullRepresentation("-- null-point energy --");

		// The null value is actually the default
		tf3.setValue(null);
		        
		// Allow user to input the null value by
		// its representation
		tf3.setNullSettingAllowed(true);
		 
		// Feedback to see the value
		Label value = new Label(tf3);
		value.setCaption("Текущее значение:");
		layout.addComponent(tf3);
		layout.addComponent(value);
		//****************************
		 //Текстовое поле с макс числом символов
		 final TextField tf4 = new TextField("Ограниченное число символов");
		 tf4.addStyleName("v-textfield-dashing");// Сам стиль добавляем сюда 
			// WebContent/VAADIN/themes/examples/styles.scss!!!
		 tf4.setValue("Начальное число");
		 tf4.setMaxLength(25);

		 // Counter for input length
		 final Label counter = new Label();
		 counter.setValue(tf4.getValue().length() +
		                  " из " + tf4.getMaxLength());

		 // Display the current length interactively in the counter
		 tf4.addTextChangeListener(new TextChangeListener() {
		     public void textChange(TextChangeEvent event) 
		     {
		         int len = event.getText().length();
		         counter.setValue(len + " из " + tf4.getMaxLength());
		         if (len>=25) tf4.addStyleName("v-textfield-red");
		         if (len<25) tf4.addStyleName("v-textfield-dashing");
		     }
		 });
		  
		 // The lazy mode is actually the default
		 tf4.setTextChangeEventMode(TextChangeEventMode.LAZY);
		 layout.addComponent(tf4);
			layout.addComponent(counter);
			//**********************
			// Create the area
			TextArea area = new TextArea("Big Area");
			        
			// Put some content in it
			area.setValue("A row\n"+
			              "Another row\n"+
			              "Yet another row");
			layout.addComponent(area);
			//*************
			TextArea area1 = new TextArea("Перенос");
			area1.setWordwrap(true); // The default
			area1.setValue("A quick brown fox jumps over the lazy dog");

			TextArea area2 = new TextArea("Без переноса");
			area2.setWordwrap(false);
			area2.setWidth("256");
			area2.setValue("Victor jagt zw&ouml;lf Boxk&auml;mpfer quer "+
			               "&uuml;ber den Sylter Deich");
			layout.addComponent(area1);
			layout.addComponent(area2);
			//*************
			PasswordField pf = new PasswordField("Введите пароль");
			layout.addComponent(pf);
			//******************
			// Create a rich text area
			final RichTextArea rtarea = new RichTextArea();
			rtarea.setCaption("My Rich Text Area");

			// Set initial content as HTML
			rtarea.setValue("<h1>Привет</h1>\n" +
			    "<p>Это \"rich text area\" с некоторым текстом.</p>");
			layout.addComponent(rtarea);
	}
	}