package com.java.GUI.panels.factory;

import javax.swing.JPanel;

import com.entities.Usuario;
import com.java.GUI.panels.UserManagementPanel;

public interface UserPanelFactory {
	
	JPanel createHomePanel();
	
	JPanel createEventsPanel();
	
	JPanel createReportsPanel();

	UserManagementPanel createUserManagement(Usuario user);

}
