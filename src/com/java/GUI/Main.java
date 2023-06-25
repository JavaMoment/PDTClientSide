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
import javax.swing.JTabbedPane;

import com.java.GUI.panels.ContentHomePanel;
import com.java.GUI.panels.ContentLoginPanel;
import com.java.GUI.panels.HomePanel;
import com.java.GUI.panels.LoginPanel;
import com.java.GUI.panels.SignUpPanel;
import com.java.GUI.panels.UsersListPanel;
import com.java.controller.BeansFactory;
import com.java.enums.Beans;
import com.services.AnalistaBeanRemote;
import com.services.AreaBeanRemote;
import com.services.DepartamentoBeanRemote;
import com.services.EstudianteBeanRemote;
import com.services.ItrBeanRemote;
import com.services.LocalidadBeanRemote;
import com.services.TutorBeanRemote;
import com.services.UsuarioBeanRemote;


import java.awt.Toolkit;


public class Main extends JFrame {

	private JPanel contentPane;
	private JPanel loginPanel;
	private JPanel signupPanel; 
	private static UsuarioBeanRemote usuarioBean;
	private static DepartamentoBeanRemote depaBean;
	private static LocalidadBeanRemote localidadBean;
	private static ItrBeanRemote itrBean;
	private static AnalistaBeanRemote analiBean;
	private static EstudianteBeanRemote estudBean;
	private static AreaBeanRemote areaBean;
	private static TutorBeanRemote tutorBean;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					usuarioBean = BeansFactory.getBean(Beans.Usuario, UsuarioBeanRemote.class);
					depaBean = BeansFactory.getBean(Beans.Departamentos, DepartamentoBeanRemote.class);
					localidadBean = BeansFactory.getBean(Beans.Localidades, LocalidadBeanRemote.class);
					itrBean = BeansFactory.getBean(Beans.Itr, ItrBeanRemote.class);
					analiBean = BeansFactory.getBean(Beans.Analista, AnalistaBeanRemote.class);
					estudBean = BeansFactory.getBean(Beans.Estudiante, EstudianteBeanRemote.class);
					areaBean = BeansFactory.getBean(Beans.Area, AreaBeanRemote.class);
					tutorBean = BeansFactory.getBean(Beans.Tutor, TutorBeanRemote.class);
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
		//initUserMngmnt();
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
		loginPanel = new LoginPanel(cardPanel, usuarioBean);
		signupPanel = new SignUpPanel(cardPanel, usuarioBean, depaBean, localidadBean, itrBean, analiBean, estudBean, areaBean, tutorBean);
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
		HomePanel homePanel = new HomePanel();
		
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
		contentPane = new JPanel();
		setContentPane(contentPane);
		getContentPane().setLayout(new CardLayout(0, 0));
		
		JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabs, "userManagement");
		
		JPanel panel = new UsersListPanel(usuarioBean, itrBean);
		tabs.addTab("Listado de usuarios", null, panel, null);
		
		JPanel panel2 = new JPanel();
		tabs.addTab("Modificación de usuarios", null, panel2, null);
		
		JPanel panel3 = new JPanel();
		tabs.addTab("Modificación de usuarios", null, panel3, null);
		
		JPanel panel4 = new JPanel();
		tabs.addTab("Modificación de datos propios", null, panel4, null);
		
		JPanel panel5 = new JPanel();
		tabs.addTab("Baja de usuarios", null, panel5, null);
	}

}
