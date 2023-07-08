package com.java.GUI.panels.factory;

import javax.swing.JPanel;

import com.entities.Usuario;
import com.java.GUI.panels.EventsPanel;
import com.java.GUI.panels.UserManagementPanel;

public interface UserPanelFactory {
	
	JPanel createHomePanel();
	
	EventsPanel createEventsPanel();
	
	JPanel createReportsPanel();

	UserManagementPanel createUserManagement(Usuario user);

}
