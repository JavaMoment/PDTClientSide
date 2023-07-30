package com.java.GUI.panels.tutores;

import com.entities.Usuario;
import com.java.GUI.panels.EventsPanel;

public class TeacherEventManagementPanel extends EventsPanel {

	public TeacherEventManagementPanel(Usuario user) {
		super();
		super.setListEvent(new TeacherListEvent(user));
		super.remove(2);
		super.remove(2);
		super.remove(2);
		super.remove(2);
		super.remove(2);
		super.remove(3);
		super.remove(3);
		super.setSelectedIndex(1);
	}

}
