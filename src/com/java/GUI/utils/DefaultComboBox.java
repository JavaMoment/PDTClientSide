package com.java.GUI.utils;

import javax.swing.DefaultComboBoxModel;

public class DefaultComboBox extends DefaultComboBoxModel<Object> {
	
	private static final String DEFAULT_OPTION = "Elija una opción: ";
	
	public DefaultComboBox() {
		addElement(DEFAULT_OPTION);
	}
	
	public DefaultComboBox(Object[] entities) {
		addElement(DEFAULT_OPTION);
		for(Object o : entities) {
			addElement(o);
		}
	}
	
	@Override
    public Object getSelectedItem() {
        Object selectedItem = super.getSelectedItem();
        if (selectedItem != null && selectedItem.equals(DEFAULT_OPTION)) {
            return null; // Return null for the default option
        }
        return selectedItem;
    }

}
