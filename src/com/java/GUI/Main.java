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
import javax.swing.border.EmptyBorder;

import com.java.GUI.panels.ContentHomePanel;
import com.java.GUI.panels.ContentLoginPanel;
import com.java.GUI.panels.HomePanel;
import com.java.GUI.panels.LoginPanel;
import com.java.GUI.panels.SignUpPanel;
import com.java.controller.BeansFactory;
import com.java.enums.Beans;
import com.services.DepartamentoBeanRemote;
import com.services.ItrBeanRemote;
import com.services.LocalidadBeanRemote;
import com.services.UsuarioBeanRemote;

import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class Main extends JFrame {

	private JPanel contentPane;
	private JPanel loginPanel;
	private JPanel signupPanel;
	private static UsuarioBeanRemote usuarioBean;
	private static DepartamentoBeanRemote depaBean;
	private static LocalidadBeanRemote localidadBean;
	private static ItrBeanRemote itrBean;

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
		
		initLogin();
	}
	
	public void initLogin() {
		contentPane = new ContentLoginPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(255, 255, 255));

		setContentPane(contentPane);

		CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        loginPanel = new LoginPanel(cardPanel, usuarioBean);
        signupPanel = new SignUpPanel(cardPanel, usuarioBean, depaBean, localidadBean, itrBean);
        contentPane.add(cardPanel);
        cardPanel.add(loginPanel, "login");
        cardPanel.add(signupPanel, "signup");

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.CENTER)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(640)
					.addComponent(cardPanel, GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
					.addGap(630))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.CENTER)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(271, Short.MAX_VALUE)
					.addComponent(cardPanel, GroupLayout.DEFAULT_SIZE, 499, GroupLayout.DEFAULT_SIZE)
					.addGap(261))
		);
		
		contentPane.setLayout(gl_contentPane);
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
        constraints.insets = new Insets(0, 0, 0, 0); // Add any desired padding
        constraints.anchor = GridBagConstraints.CENTER;
        wrapperPanel.add(cardPanel, constraints);
        
        cardPanel.add(homePanel, "home");

	}
}
