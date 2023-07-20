package com.java.GUI.panels.tutores;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.time.Instant;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import com.entities.Usuario;
import com.java.GUI.Main;
import com.java.GUI.panels.UserManagementPanel;
import com.java.GUI.panels.UserSelfDataModificationPanel;

public class TeacherUserManagementPanel extends UserManagementPanel {

	private JButton btnCancel;

	public TeacherUserManagementPanel(Usuario user) {
		super(user);
		btnCancel = super.getUserSelfDataPanel().getBtnCancel();
		btnCancel.removeMouseListener(btnCancel.getMouseListeners()[0]);
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
        	public void mousePressed(MouseEvent e) {
				Main main = (Main) SwingUtilities.getWindowAncestor(TeacherUserManagementPanel.this);
		        main.initHome();
				main.revalidate();
				revalidate();
        	}
		});
		super.getUserSelfDataPanel().setBtnCancel(btnCancel);
		super.remove(1);
		super.remove(1);
		super.remove(2);
	}

}
