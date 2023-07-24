package com.java.GUI.panels.factory;

import javax.swing.JPanel;

import com.entities.Estudiante;
import com.entities.Usuario;
import com.java.GUI.panels.EventsPanel;
import com.java.GUI.panels.ReportsPanel;
import com.java.GUI.panels.UserManagementPanel;
import com.java.GUI.panels.tutores.TeacherEventManagementPanel;
import com.java.GUI.panels.tutores.TeacherHomePanel;
import com.java.GUI.panels.tutores.TeacherUserManagementPanel;

public class TeacherPanelFactory implements UserPanelFactory {

	@Override
	public JPanel createHomePanel() {
		return new TeacherHomePanel();
	}

	@Override
	public EventsPanel createEventsPanel() {
		return new TeacherEventManagementPanel();
	}

	@Override
	public JPanel createReportsPanel() {
		return null;
	}

	@Override
	public UserManagementPanel createUserManagement(Usuario user) {
		return new TeacherUserManagementPanel(user);
	}

	@Override
	public ReportsPanel createReportsPanel(Estudiante e) {
		// TODO Auto-generated method stub
		return null;
	}

}
