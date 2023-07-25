package com.java.GUI.panels;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.java.GUI.Main;

public class ContentStudentSelectionPanel extends ContentHomePanel {
	
	private JButton btnGoBack;

	public ContentStudentSelectionPanel() {
		super();
		btnGoBack = new JButton("");
		btnGoBack.setIcon(new ImageIcon(ContentHomePanel.class.getResource("/com/java/resources/images/back.png")));
		btnGoBack.setToolTipText("Click aquí para volvera menú Home");
		btnGoBack.setContentAreaFilled(false);
		btnGoBack.setBorder(null);
		
		btnGoBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Main main = (Main) SwingUtilities.getAncestorOfClass(JFrame.class, ContentStudentSelectionPanel.this);
		        main.initHome();
				main.revalidate();
			}
		});
		super.setBtnLogout(btnGoBack);
	}
}
