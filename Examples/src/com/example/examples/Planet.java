package com.example.examples;

import java.io.Serializable;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;

public class Planet implements Serializable 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
    int id;
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Planet (){};
    
    public Planet(int id, String name) {
        this.name = name;
        this.id=id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    
    public  void propertyModeExample(VerticalLayout layout) {
	    // Have a bean container to put the beans in
	    BeanItemContainer<Planet> container =
	        new BeanItemContainer<Planet>(Planet.class);

	    // Put some example data in it
	    container.addItem(new Planet(1, "Меркурий"));
	    container.addItem(new Planet(2, "Венера"));
	    container.addItem(new Planet(3, "Земля"));
	    container.addItem(new Planet(4, "Марс"));

	    // Create a selection component bound to the container
	    ComboBox select = new ComboBox("Планеты из класса", container);

	    // Set the caption mode to read the caption directly
	    // from the 'name' property of the bean
	    select.setItemCaptionMode(ItemCaptionMode.PROPERTY);
	    select.setItemCaptionPropertyId("name");
	    layout.addComponent(select);
		//layout.addComponent(new Label("Планета "+pl.getName()));
	    
}
}