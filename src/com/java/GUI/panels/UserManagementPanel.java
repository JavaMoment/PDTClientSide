package com.java.GUI.panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import com.entities.Usuario;
import com.java.GUI.Main;

public class UserManagementPanel extends JTabbedPane {

	private JButton logout;
	private UsersListPanel usersListPanel;
	private UserDataModificationPanel userDataModPanel;
	private JScrollPane scrollPane;
	private UserSelfDataModificationPanel userSelfDataPanel;
	private ItrListManteinancePanel itrListPanel;

	public UserManagementPanel(Usuario user) {
		super(JTabbedPane.TOP);
		
		logout = new JButton ();
		logout.setIcon(new ImageIcon(HomePanel.class.getResource("/com/java/resources/images/icons8-go-back-16.png")));
		logout.setBackground(new Color(125, 229, 251));
		logout.setForeground(new Color(40, 40, 40));
		logout.setContentAreaFilled(false);
		logout.setBorder(null);
		logout.setCursor(new Cursor(Cursor.HAND_CURSOR));
		logout.setToolTipText("Click aquí para volver hacia el menú de Home");
	    logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Main main = (Main) SwingUtilities.getWindowAncestor(UserManagementPanel.this);
		        main.initHome();
				main.revalidate();
				revalidate();
			}
		});

	    addTab("", null, new JPanel(), null);
	    
	    setTabComponentAt(getTabCount() - 1, logout);
		
	    usersListPanel = new UsersListPanel();
		addTab("Listado de usuarios", null, usersListPanel, null);
		
		userDataModPanel = new UserDataModificationPanel();
		scrollPane = new JScrollPane(userDataModPanel);
		addTab("Modificación de datos de usuario/s", null, scrollPane, null);
		
		userSelfDataPanel = new UserSelfDataModificationPanel();
		userSelfDataPanel.populateComponents(user);
		addTab("Modificación de datos propios", null, userSelfDataPanel, null);
		
		itrListPanel = new ItrListManteinancePanel();
		addTab("Mantenimiento de ITRs", itrListPanel);
		
		setSelectedIndex(1);
	}

	public JButton getLogout() {
		return logout;
	}

	public void setLogout(JButton logout) {
		this.logout = logout;
	}

	public UsersListPanel getUsersListPanel() {
		return usersListPanel;
	}

	public void setUsersListPanel(UsersListPanel usersListPanel) {
		this.usersListPanel = usersListPanel;
	}

	public UserDataModificationPanel getUserDataModPanel() {
		return userDataModPanel;
	}

	public void setUserDataModPanel(UserDataModificationPanel userDataModPanel) {
		this.userDataModPanel = userDataModPanel;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public UserSelfDataModificationPanel getUserSelfDataPanel() {
		return userSelfDataPanel;
	}

	public void setUserSelfDataPanel(UserSelfDataModificationPanel userSelfDataPanel) {
		this.userSelfDataPanel = userSelfDataPanel;
	}

	public ItrListManteinancePanel getItrListPanel() {
		return itrListPanel;
	}

	public void setItrListPanel(ItrListManteinancePanel itrListPanel) {
		this.itrListPanel = itrListPanel;
	}
	
}
