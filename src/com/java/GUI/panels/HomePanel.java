package com.java.GUI.panels;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ImageIcon;

public class HomePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public HomePanel() {
		setBackground(new Color(255, 255, 255));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{85, 298, 100, 298, 100, 298, 89, 89, 0};
		gridBagLayout.rowHeights = new int[]{92, 110, 92, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton btnUserMngmnt = new JButton("Gestión de Usuarios");
		btnUserMngmnt.setBackground(new Color(125, 229, 251));
		btnUserMngmnt.setForeground(new Color(40, 40, 40));   
		btnUserMngmnt.setIcon(new ImageIcon(HomePanel.class.getResource("/com/java/resources/images/management.png")));
		GridBagConstraints gbc_btnUserMngmnt = new GridBagConstraints();
		gbc_btnUserMngmnt.fill = GridBagConstraints.BOTH;
		gbc_btnUserMngmnt.insets = new Insets(0, 0, 5, 5);
		gbc_btnUserMngmnt.gridx = 1;
		gbc_btnUserMngmnt.gridy = 0;
		add(btnUserMngmnt, gbc_btnUserMngmnt);
		
		JButton btnEventsMngmnt = new JButton("Gestión de Eventos");
		btnEventsMngmnt.setBackground(new Color(125, 229, 251));
		btnEventsMngmnt.setForeground(new Color(40, 40, 40));   
		btnEventsMngmnt.setIcon(new ImageIcon(HomePanel.class.getResource("/com/java/resources/images/calendar.png")));
		GridBagConstraints gbc_btnEventsMngmnt = new GridBagConstraints();
		gbc_btnEventsMngmnt.fill = GridBagConstraints.BOTH;
		gbc_btnEventsMngmnt.insets = new Insets(0, 0, 5, 5);
		gbc_btnEventsMngmnt.gridx = 3;
		gbc_btnEventsMngmnt.gridy = 0;
		add(btnEventsMngmnt, gbc_btnEventsMngmnt);
		
		JButton btnIssuesMngmnt = new JButton("Gestión de Reclamos");
		btnIssuesMngmnt.setBackground(new Color(125, 229, 251));
		btnIssuesMngmnt.setForeground(new Color(40, 40, 40));   
		btnIssuesMngmnt.setIcon(new ImageIcon(HomePanel.class.getResource("/com/java/resources/images/complain.png")));
		GridBagConstraints gbc_btnIssuesMngmnt = new GridBagConstraints();
		gbc_btnIssuesMngmnt.fill = GridBagConstraints.BOTH;
		gbc_btnIssuesMngmnt.insets = new Insets(0, 0, 5, 5);
		gbc_btnIssuesMngmnt.gridx = 5;
		gbc_btnIssuesMngmnt.gridy = 0;
		add(btnIssuesMngmnt, gbc_btnIssuesMngmnt);
		
		JButton btnCertificatesMngmnt = new JButton("Gestión de Constancias");
		btnCertificatesMngmnt.setBackground(new Color(125, 229, 251));
		btnCertificatesMngmnt.setForeground(new Color(40, 40, 40));   
		btnCertificatesMngmnt.setIcon(new ImageIcon(HomePanel.class.getResource("/com/java/resources/images/stamp.png")));
		GridBagConstraints gbc_btnCertificatesMngmnt = new GridBagConstraints();
		gbc_btnCertificatesMngmnt.fill = GridBagConstraints.BOTH;
		gbc_btnCertificatesMngmnt.insets = new Insets(0, 0, 0, 5);
		gbc_btnCertificatesMngmnt.gridx = 1;
		gbc_btnCertificatesMngmnt.gridy = 2;
		add(btnCertificatesMngmnt, gbc_btnCertificatesMngmnt);
		
		JButton btnAbsenceMgnmt = new JButton("Gestión de Justificación de Inasistencias");
		btnAbsenceMgnmt.setBackground(new Color(125, 229, 251));
		btnAbsenceMgnmt.setForeground(new Color(40, 40, 40));   
		btnAbsenceMgnmt.setIcon(new ImageIcon(HomePanel.class.getResource("/com/java/resources/images/absence.png")));
		GridBagConstraints gbc_btnAbsenceMgnmt = new GridBagConstraints();
		gbc_btnAbsenceMgnmt.fill = GridBagConstraints.BOTH;
		gbc_btnAbsenceMgnmt.insets = new Insets(0, 0, 0, 5);
		gbc_btnAbsenceMgnmt.gridx = 3;
		gbc_btnAbsenceMgnmt.gridy = 2;
		add(btnAbsenceMgnmt, gbc_btnAbsenceMgnmt);
		
		JButton btnDashboard = new JButton("Ánalisis y Gestión de Reportes");
		btnDashboard.setBackground(new Color(125, 229, 251));
		btnDashboard.setForeground(new Color(40, 40, 40));   
		btnDashboard.setSelectedIcon(null);
		btnDashboard.setIcon(new ImageIcon(HomePanel.class.getResource("/com/java/resources/images/administrator.png")));
		GridBagConstraints gbc_btnDashboard = new GridBagConstraints();
		gbc_btnDashboard.fill = GridBagConstraints.BOTH;
		gbc_btnDashboard.insets = new Insets(0, 0, 0, 5);
		gbc_btnDashboard.gridx = 5;
		gbc_btnDashboard.gridy = 2;
		add(btnDashboard, gbc_btnDashboard);
	}
}
