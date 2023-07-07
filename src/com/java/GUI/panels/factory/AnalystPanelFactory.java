package com.java.GUI.panels.factory;

import javax.swing.JPanel;

import com.entities.Usuario;
import com.java.GUI.panels.HomePanel;
import com.java.GUI.panels.UserManagementPanel;

public class AnalystPanelFactory implements UserPanelFactory {

	@Override
	public JPanel createHomePanel() {
		return new HomePanel();
	}

	@Override
	public UserManagementPanel createUserManagement(Usuario user) {
		return new UserManagementPanel(user);
	}

	@Override
	public JPanel createEventsPanel() {
		return null;
	}

	@Override
	public JPanel createReportsPanel() {
		return null;
	}

}
