package com.java.GUI.panels.tutores;


import java.awt.GridBagConstraints;

import com.java.GUI.panels.HomePanel;

public class TeacherHomePanel extends HomePanel {

	private GridBagConstraints gbc_btnAbsenceMgnmt;
	private GridBagConstraints gbc_btnEventsMngmnt;

	public TeacherHomePanel() {
		super();
		super.getBtnIssuesMngmnt().setEnabled(false);
		super.getBtnIssuesMngmnt().setVisible(false);
		
		gbc_btnAbsenceMgnmt = super.getGbc_btnAbsenceMgnmt();
		gbc_btnAbsenceMgnmt.gridy = 1;
		gbc_btnAbsenceMgnmt.gridx = 3;
		add(super.getBtnAbsenceMgnmt(), gbc_btnAbsenceMgnmt);
		
		gbc_btnEventsMngmnt = super.getGbc_btnEventsMngmnt();
		gbc_btnEventsMngmnt.gridx = 5;
		add(super.getBtnEventsMngmnt(), gbc_btnEventsMngmnt);
	}
	
}
