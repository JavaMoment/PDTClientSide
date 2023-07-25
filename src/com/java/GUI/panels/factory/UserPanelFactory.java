package com.java.GUI.panels.factory;

import java.awt.Component;

import javax.swing.JPanel;

import com.entities.Usuario;
import com.java.GUI.panels.EventsPanel;
import com.java.GUI.panels.UserManagementPanel;

public interface UserPanelFactory {
	
	JPanel createHomePanel();
	
	EventsPanel createEventsPanel(Usuario user);
  
	UserManagementPanel createUserManagement(Usuario user);

	Component createReportsPanel(Usuario u);

}
