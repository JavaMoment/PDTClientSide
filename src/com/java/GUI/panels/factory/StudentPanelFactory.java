package com.java.GUI.panels.factory;

import javax.swing.JPanel;

import com.entities.Usuario;
import com.java.GUI.panels.EventsPanel;
import com.java.GUI.panels.UserManagementPanel;
import com.java.GUI.panels.students.StudentHomePanel;
import com.java.GUI.panels.students.StudentUserManagementPanel;

public class StudentPanelFactory implements UserPanelFactory {

	@Override
	public JPanel createHomePanel() {
		return new StudentHomePanel();
	}

	@Override
	public EventsPanel createEventsPanel(Usuario user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JPanel createReportsPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserManagementPanel createUserManagement(Usuario user) {
		return new StudentUserManagementPanel(user);
	}

}
