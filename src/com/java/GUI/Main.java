package com.java.GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.java.GUI.panels.ContentLoginPanel;
import com.java.GUI.panels.LoginPanel;
import com.java.GUI.panels.SignUpPanel;

import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;

public class Main extends JFrame {

	private JPanel contentPane;
	private JPanel loginPanel;
	private JPanel signupPanel;

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
		//setBounds(100, 100, 800, 600);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setLocationRelativeTo(null);
		contentPane = new ContentLoginPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(255, 255, 255));

		setContentPane(contentPane);

		CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        loginPanel = new LoginPanel(cardPanel);
        signupPanel = new SignUpPanel(cardPanel);
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
}
