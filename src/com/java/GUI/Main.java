package com.java.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.java.GUI.panels.LoginPanel;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextPane;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Label;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Main extends JFrame {

	private JPanel contentPane;

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
		contentPane = new LoginPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new java.awt.Color(255, 255, 255));

		setContentPane(contentPane);
		//add(new LoginPanel());
		
		//GroupLayout contentPanelLayout = new GroupLayout(contentPane);
		//contentPane.setLayout(null);
		/*contentPanelLayout.setHorizontalGroup(
				contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(contentPanelLayout.createSequentialGroup())
				.addComponent(contentPane)
				);*/
	}
}
