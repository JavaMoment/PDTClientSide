package com.java.GUI.panels.tutores;

import com.entities.Usuario;
import com.java.GUI.panels.AuxiliaryListofEventStatesPanel;
import com.java.GUI.panels.CreateEventPanel;
import com.java.GUI.panels.EventModeAuxiliaryListPanel;
import com.java.GUI.panels.EventsPanel;
import com.java.GUI.panels.ListEventPanel;
import com.java.GUI.panels.ModifyCallsEventPanel;
import com.java.GUI.panels.ModifyEventPanel;
import com.java.GUI.panels.RegisterAssistsEventPanel;
import com.java.GUI.panels.RegisterCallsEventPanel;
import com.java.GUI.panels.RemoveEventPanel;

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
	}

}
