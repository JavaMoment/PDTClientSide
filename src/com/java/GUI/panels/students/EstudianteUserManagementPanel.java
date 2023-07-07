package com.java.GUI.panels.students;

import com.entities.Usuario;
import com.java.GUI.panels.UserManagementPanel;

public class EstudianteUserManagementPanel extends UserManagementPanel {

	public EstudianteUserManagementPanel(Usuario user) {
		super(user);
		super.remove(1);
		super.remove(1);
		super.remove(2);
	}

}
