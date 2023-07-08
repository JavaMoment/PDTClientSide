package com.java.GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.entities.Usuario;
import com.java.GUI.panels.ContentHomePanel;
import com.java.GUI.panels.ContentLoginPanel;
import com.java.GUI.panels.ContentPanel;
import com.java.GUI.panels.EventsPanel;
import com.java.GUI.panels.HomePanel;
import com.java.GUI.panels.LoginPanel;
import com.java.GUI.panels.SignUpPanel;
import com.java.GUI.panels.UserManagementPanel;
import com.java.GUI.panels.factory.UserPanelFactory;
import com.java.GUI.panels.factory.UserPanelFactoryProvider;

import java.awt.Toolkit;


@SuppressWarnings("serial")
public class Main extends JFrame {

	private JPanel contentPane;
	private JPanel loginPanel;
	private JPanel signupPanel; 
	private Usuario user;
	private UserPanelFactory userPanelFactory;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/com/java/resources/images/uteclogo.png")));
		setLocationRelativeTo(null);
		setExtendedState(MAXIMIZED_BOTH);
		setTitle("UTEC ERP");
		initLogin();
	}
	
	public void initLogin() {
		contentPane = new ContentLoginPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(255, 255, 255));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{1340, 0, 0};
		gbl_contentPane.rowHeights = new int[]{719, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
        
		CardLayout cardLayout = new CardLayout();

		

		JPanel cardPanel = new JPanel(cardLayout);
		loginPanel = new LoginPanel(cardPanel);
		signupPanel = new SignUpPanel(cardPanel);
		JScrollPane scrollPane = new JScrollPane(signupPanel);
		cardPanel.add(loginPanel, "login");
		cardPanel.add(scrollPane, "signup");
		GridBagConstraints gbc_cardPanel = new GridBagConstraints();
		gbc_cardPanel.gridwidth = 2;
		gbc_cardPanel.anchor = GridBagConstraints.CENTER;
		gbc_cardPanel.gridx = 0;
		gbc_cardPanel.gridy = 0;

        contentPane.add(cardPanel, gbc_cardPanel);
	}
	
	public void initHome() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(255, 255, 255));
		setContentPane(contentPane);
		
		ContentHomePanel homePanelContent = new ContentHomePanel();
		HomePanel homePanel = (HomePanel) userPanelFactory.createHomePanel();
		
		CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        contentPane.add(cardPanel);
        contentPane.add(homePanelContent);
        
        contentPane.setLayout(new BorderLayout());
        contentPane.add(homePanelContent, BorderLayout.NORTH);
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setBackground(Color.WHITE);
        contentPane.add(wrapperPanel, BorderLayout.CENTER);

        // Add the cardPanel to the wrapperPanel with appropriate constraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 0, 0, 0); 
        constraints.anchor = GridBagConstraints.CENTER;
        wrapperPanel.add(cardPanel, constraints);
        
        cardPanel.add(homePanel, "home");

	}
	
	public void initUserMngmnt() {
		contentPane = new ContentPanel();
		setContentPane(contentPane);
		getContentPane().setLayout(new CardLayout(0, 0));
		
		UserManagementPanel tabs = userPanelFactory.createUserManagement(user);
		getContentPane().add(tabs, "userManagement");
	}
	
	public void initEventMngmnt() {
		contentPane = new ContentPanel();
		setContentPane(contentPane);
		getContentPane().setLayout(new CardLayout(0, 0));
		
		EventsPanel tabs = userPanelFactory.createEventsPanel();
		getContentPane().add(tabs, "eventsMngmnt");
	}

	public void setUser(Usuario user) {
		this.user = user;
	}
	
	public Usuario getUser() {
		return user;
	}
	
	public void setUserPanelFactory(Usuario user) {
		this.userPanelFactory = UserPanelFactoryProvider.getUserPanel(user);
	}
}
