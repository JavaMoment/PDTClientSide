package com.java.GUI.panels;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.java.GUI.Main;

import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

public class HomePanel extends JPanel {

	private GridBagLayout gridBagLayout;
	private JButton btnUserMngmnt;
	private GridBagConstraints gbc_btnUserMngmnt;
	private JButton btnEventsMngmnt;
	private GridBagConstraints gbc_btnEventsMngmnt;
	private JButton btnIssuesMngmnt;
	private GridBagConstraints gbc_btnIssuesMngmnt;
	private JButton btnCertificatesMngmnt;
	private JButton btnAbsenceMgnmt;
	private JButton btnDashboard;
	private GridBagConstraints gbc_btnCertificatesMngmnt;
	private GridBagConstraints gbc_btnAbsenceMgnmt;
	private GridBagConstraints gbc_btnDashboard;

	/**
	 * Create the panel.
	 */
	public HomePanel() {
		setBackground(new Color(255, 255, 255));
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{85, 298, 100, 298, 100, 298, 89, 89, 0};
		gridBagLayout.rowHeights = new int[]{92, 110, 92, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		btnUserMngmnt = new JButton("Gestión de Usuarios");
		btnUserMngmnt.setBackground(new Color(125, 229, 251));
		btnUserMngmnt.setForeground(new Color(40, 40, 40));   
		btnUserMngmnt.setIcon(new ImageIcon(HomePanel.class.getResource("/com/java/resources/images/management.png")));
		btnUserMngmnt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main main = (Main) SwingUtilities.getWindowAncestor(HomePanel.this);
		        main.initUserMngmnt();
				main.revalidate();
			}
			
		});
		gbc_btnUserMngmnt = new GridBagConstraints();
		gbc_btnUserMngmnt.fill = GridBagConstraints.BOTH;
		gbc_btnUserMngmnt.insets = new Insets(0, 0, 5, 5);
		gbc_btnUserMngmnt.gridx = 1;
		gbc_btnUserMngmnt.gridy = 0;
		add(btnUserMngmnt, gbc_btnUserMngmnt);
		
		btnEventsMngmnt = new JButton("Gestión de Eventos");
		btnEventsMngmnt.setBackground(new Color(125, 229, 251));
		btnEventsMngmnt.setForeground(new Color(40, 40, 40));   
		btnEventsMngmnt.setIcon(new ImageIcon(HomePanel.class.getResource("/com/java/resources/images/calendar.png")));
		btnEventsMngmnt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main main = (Main) SwingUtilities.getWindowAncestor(HomePanel.this);
		        main.initEventMngmnt();
				main.revalidate();
			}
			
		});
		gbc_btnEventsMngmnt = new GridBagConstraints();
		gbc_btnEventsMngmnt.fill = GridBagConstraints.BOTH;
		gbc_btnEventsMngmnt.insets = new Insets(0, 0, 5, 5);
		gbc_btnEventsMngmnt.gridx = 3;
		gbc_btnEventsMngmnt.gridy = 0;
		add(btnEventsMngmnt, gbc_btnEventsMngmnt);
		
		btnIssuesMngmnt = new JButton("Gestión de Reclamos");
		btnIssuesMngmnt.setBackground(new Color(125, 229, 251));
		btnIssuesMngmnt.setForeground(new Color(40, 40, 40));   
		btnIssuesMngmnt.setIcon(new ImageIcon(HomePanel.class.getResource("/com/java/resources/images/complain.png")));
		gbc_btnIssuesMngmnt = new GridBagConstraints();
		gbc_btnIssuesMngmnt.fill = GridBagConstraints.BOTH;
		gbc_btnIssuesMngmnt.insets = new Insets(0, 0, 5, 5);
		gbc_btnIssuesMngmnt.gridx = 5;
		gbc_btnIssuesMngmnt.gridy = 0;
		add(btnIssuesMngmnt, gbc_btnIssuesMngmnt);
		
		btnCertificatesMngmnt = new JButton("Gestión de Constancias");
		btnCertificatesMngmnt.setBackground(new Color(125, 229, 251));
		btnCertificatesMngmnt.setForeground(new Color(40, 40, 40));   
		btnCertificatesMngmnt.setIcon(new ImageIcon(HomePanel.class.getResource("/com/java/resources/images/stamp.png")));
		gbc_btnCertificatesMngmnt = new GridBagConstraints();
		gbc_btnCertificatesMngmnt.fill = GridBagConstraints.BOTH;
		gbc_btnCertificatesMngmnt.insets = new Insets(0, 0, 0, 5);
		gbc_btnCertificatesMngmnt.gridx = 1;
		gbc_btnCertificatesMngmnt.gridy = 2;
		add(btnCertificatesMngmnt, gbc_btnCertificatesMngmnt);
		
		btnAbsenceMgnmt = new JButton("Gestión de Justificación de Inasistencias");
		btnAbsenceMgnmt.setBackground(new Color(125, 229, 251));
		btnAbsenceMgnmt.setForeground(new Color(40, 40, 40));   
		btnAbsenceMgnmt.setIcon(new ImageIcon(HomePanel.class.getResource("/com/java/resources/images/absence.png")));
		gbc_btnAbsenceMgnmt = new GridBagConstraints();
		gbc_btnAbsenceMgnmt.fill = GridBagConstraints.BOTH;
		gbc_btnAbsenceMgnmt.insets = new Insets(0, 0, 0, 5);
		gbc_btnAbsenceMgnmt.gridx = 3;
		gbc_btnAbsenceMgnmt.gridy = 2;
		add(btnAbsenceMgnmt, gbc_btnAbsenceMgnmt);
		
		btnDashboard = new JButton("Ánalisis y Gestión de Reportes");
		btnDashboard.setBackground(new Color(125, 229, 251));
		btnDashboard.setForeground(new Color(40, 40, 40));   
		btnDashboard.setSelectedIcon(null);
		btnDashboard.setIcon(new ImageIcon(HomePanel.class.getResource("/com/java/resources/images/administrator.png")));
		gbc_btnDashboard = new GridBagConstraints();
		gbc_btnDashboard.fill = GridBagConstraints.BOTH;
		gbc_btnDashboard.insets = new Insets(0, 0, 0, 5);
		gbc_btnDashboard.gridx = 5;
		gbc_btnDashboard.gridy = 2;
		add(btnDashboard, gbc_btnDashboard);
	}

	public GridBagConstraints getGbc_btnCertificatesMngmnt() {
		return gbc_btnCertificatesMngmnt;
	}

	public void setGbc_btnCertificatesMngmnt(GridBagConstraints gbc_btnCertificatesMngmnt) {
		this.gbc_btnCertificatesMngmnt = gbc_btnCertificatesMngmnt;
	}

	public GridBagConstraints getGbc_btnAbsenceMgnmt() {
		return gbc_btnAbsenceMgnmt;
	}

	public void setGbc_btnAbsenceMgnmt(GridBagConstraints gbc_btnAbsenceMgnmt) {
		this.gbc_btnAbsenceMgnmt = gbc_btnAbsenceMgnmt;
	}

	public GridBagConstraints getGbc_btnDashboard() {
		return gbc_btnDashboard;
	}

	public void setGbc_btnDashboard(GridBagConstraints gbc_btnDashboard) {
		this.gbc_btnDashboard = gbc_btnDashboard;
	}

	public GridBagLayout getGridBagLayout() {
		return gridBagLayout;
	}

	public void setGridBagLayout(GridBagLayout gridBagLayout) {
		this.gridBagLayout = gridBagLayout;
	}

	public JButton getBtnUserMngmnt() {
		return btnUserMngmnt;
	}

	public void setBtnUserMngmnt(JButton btnUserMngmnt) {
		this.btnUserMngmnt = btnUserMngmnt;
	}

	public GridBagConstraints getGbc_btnUserMngmnt() {
		return gbc_btnUserMngmnt;
	}

	public void setGbc_btnUserMngmnt(GridBagConstraints gbc_btnUserMngmnt) {
		this.gbc_btnUserMngmnt = gbc_btnUserMngmnt;
	}

	public JButton getBtnEventsMngmnt() {
		return btnEventsMngmnt;
	}

	public void setBtnEventsMngmnt(JButton btnEventsMngmnt) {
		this.btnEventsMngmnt = btnEventsMngmnt;
	}

	public GridBagConstraints getGbc_btnEventsMngmnt() {
		return gbc_btnEventsMngmnt;
	}

	public void setGbc_btnEventsMngmnt(GridBagConstraints gbc_btnEventsMngmnt) {
		this.gbc_btnEventsMngmnt = gbc_btnEventsMngmnt;
	}

	public JButton getBtnIssuesMngmnt() {
		return btnIssuesMngmnt;
	}

	public void setBtnIssuesMngmnt(JButton btnIssuesMngmnt) {
		this.btnIssuesMngmnt = btnIssuesMngmnt;
	}

	public GridBagConstraints getGbc_btnIssuesMngmnt() {
		return gbc_btnIssuesMngmnt;
	}

	public void setGbc_btnIssuesMngmnt(GridBagConstraints gbc_btnIssuesMngmnt) {
		this.gbc_btnIssuesMngmnt = gbc_btnIssuesMngmnt;
	}

	public JButton getBtnCertificatesMngmnt() {
		return btnCertificatesMngmnt;
	}

	public void setBtnCertificatesMngmnt(JButton btnCertificatesMngmnt) {
		this.btnCertificatesMngmnt = btnCertificatesMngmnt;
	}

	public JButton getBtnAbsenceMgnmt() {
		return btnAbsenceMgnmt;
	}

	public void setBtnAbsenceMgnmt(JButton btnAbsenceMgnmt) {
		this.btnAbsenceMgnmt = btnAbsenceMgnmt;
	}

	public JButton getBtnDashboard() {
		return btnDashboard;
	}

	public void setBtnDashboard(JButton btnDashboard) {
		this.btnDashboard = btnDashboard;
	}
}
