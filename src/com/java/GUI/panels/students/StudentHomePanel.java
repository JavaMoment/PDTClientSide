package com.java.GUI.panels.students;

import java.awt.GridBagConstraints;

import com.java.GUI.panels.HomePanel;

public class StudentHomePanel extends HomePanel {

	private GridBagConstraints gbc_btnAbsenceMgnmt;

	public StudentHomePanel() {
		super();
		super.getBtnEventsMngmnt().setEnabled(false);
		super.getBtnEventsMngmnt().setVisible(false);
		gbc_btnAbsenceMgnmt = super.getGbc_btnAbsenceMgnmt();
		gbc_btnAbsenceMgnmt.gridy = 1;
		gbc_btnAbsenceMgnmt.gridx = 3;
		add(super.getBtnAbsenceMgnmt(), gbc_btnAbsenceMgnmt);
	}
	
}
