package com.java.GUI.panels.tutores;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.entities.Usuario;
import com.java.GUI.Main;
import com.java.GUI.panels.HomePanel;
import com.java.GUI.panels.ReportsPanel;

public class TeacherAnalystReportsPanel extends ReportsPanel {


	private JButton btnGoBack;

	public TeacherAnalystReportsPanel(Usuario u) {
		super(u);
		btnGoBack = new JButton ();
		btnGoBack.setIcon(new ImageIcon(HomePanel.class.getResource("/com/java/resources/images/icons8-go-back-16.png")));
		btnGoBack.setBackground(new Color(125, 229, 251));
		btnGoBack.setForeground(new Color(40, 40, 40));
		btnGoBack.setContentAreaFilled(false);
		btnGoBack.setBorder(null);
		btnGoBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnGoBack.setToolTipText("Click aquí para volver hacia el menú de selección de usuario a reportar");
		btnGoBack.addMouseListener(new MouseAdapter() {
			@Override
        	public void mousePressed(MouseEvent e) {
				Main main = (Main) SwingUtilities.getAncestorOfClass(JFrame.class, TeacherAnalystReportsPanel.this);
		        main.initReports();
				main.revalidate();
				revalidate();
        	}
		});
		super.setBtnGoBack(btnGoBack);
	}

	
}
