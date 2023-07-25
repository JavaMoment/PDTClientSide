package com.java.GUI.panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import com.entities.Estudiante;
import com.entities.Usuario;
import com.java.GUI.Main;
import com.java.controller.BeansFactory;
import com.java.enums.Beans;
import com.services.EstudianteBeanRemote;

public class ReportsPanel extends JTabbedPane {

	private JButton btnGoBack;
	private ListAttendanceEvents listAttendanceEvents;
	private Estudiante student;
	private static EstudianteBeanRemote estudBean = BeansFactory.getBean(Beans.Estudiante, EstudianteBeanRemote.class);

	public ReportsPanel(Usuario user) {
		super(JTabbedPane.TOP);

		student = estudBean.selectUserBy(user.getNombreUsuario());
		
		btnGoBack = new JButton ();
		btnGoBack.setIcon(new ImageIcon(HomePanel.class.getResource("/com/java/resources/images/icons8-go-back-16.png")));
		btnGoBack.setBackground(new Color(125, 229, 251));
		btnGoBack.setForeground(new Color(40, 40, 40));
		btnGoBack.setContentAreaFilled(false);
		btnGoBack.setBorder(null);
		btnGoBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnGoBack.setToolTipText("Click aquí para volver hacia el menú de Home");
	    btnGoBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Main main = (Main) SwingUtilities.getWindowAncestor(ReportsPanel.this);
		        main.initHome();
				main.revalidate();
				revalidate();
			}
		});

	    addTab("", null, new JPanel(), null);
	    setTabComponentAt(getTabCount() - 1, btnGoBack);
	    
	    listAttendanceEvents = new ListAttendanceEvents(student);
		addTab("Listar eventos", listAttendanceEvents);
		
		setSelectedIndex(1);
	}

	public ListAttendanceEvents getListAttendanceEvents() {
		return listAttendanceEvents;
	}

	public JButton getBtnGoBack() {
		return btnGoBack;
	}

	public void setBtnGoBack(JButton btnGoBack) {
		this.btnGoBack = btnGoBack;
		this.btnGoBack.revalidate();
		this.btnGoBack.repaint();
		setTabComponentAt(getTabCount() - getTabCount(), btnGoBack);
		revalidate();
		repaint();
	}
	
}
