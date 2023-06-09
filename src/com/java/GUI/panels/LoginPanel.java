package com.java.GUI.panels;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginPanel extends JPanel {

	private JTextField txtFieldMail;
	private JPasswordField passwordField;
	private JLabel lblPassword;
	private JLabel lblMail;
	private JButton btnLogin;
	private JButton btnRegister;
	private JLabel lblLoginTitle;
	private JPanel contentPane;

	/**
	 * Create the panel.
	 */
	public LoginPanel(JPanel contentPane) {
		this.contentPane = contentPane;
		setBackground(new Color(255, 255, 255));
		lblMail = new JLabel("Correo:");
		lblMail.setHorizontalAlignment(SwingConstants.LEFT);
		
		txtFieldMail = new JTextField();
		txtFieldMail.setColumns(10);
		
		lblPassword = new JLabel("Contraseña:");
		
		passwordField = new JPasswordField();
		
		btnLogin = new JButton("Ingresar");
		btnLogin.setBackground(new Color(125, 229, 251));
		btnLogin.setForeground(new Color(40, 40, 40));
		
		btnRegister = new JButton("Registrarse");
		btnRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "eventos");
			}
		});
		btnRegister.setFont(new Font("sansserif", 1, 12));
        btnRegister.setForeground(new Color(30, 122, 236));
        btnRegister.setContentAreaFilled(false);
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegister.setBorder(null);
		
		lblLoginTitle = new JLabel("Inicia sesión");
		lblLoginTitle.setFont(new Font("sansserif", 1, 48)); // NOI18N
        lblLoginTitle.setForeground(new Color(69, 68, 68));
        lblLoginTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		GroupLayout login_layout = new GroupLayout(this);
		login_layout.setHorizontalGroup(
			login_layout.createParallelGroup(Alignment.LEADING)
				.addGroup(login_layout.createSequentialGroup()
					.addContainerGap(168, Short.MAX_VALUE)
					.addGroup(login_layout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnLogin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblPassword)
						.addComponent(lblMail)
						.addComponent(passwordField)
						.addComponent(txtFieldMail)
						.addComponent(lblLoginTitle, GroupLayout.PREFERRED_SIZE, 257, Short.MAX_VALUE)
						.addComponent(btnRegister, GroupLayout.PREFERRED_SIZE, 298, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(148, Short.MAX_VALUE))
		);
		login_layout.setVerticalGroup(
			login_layout.createParallelGroup(Alignment.LEADING)
				.addGroup(login_layout.createSequentialGroup()
					.addContainerGap(18, Short.MAX_VALUE)
					.addComponent(lblLoginTitle)
					.addGap(18)
					.addComponent(lblMail, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addGap(0)
					.addComponent(txtFieldMail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addGap(0)
					.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(30)
					.addComponent(btnLogin)
					.addGap(137)
					.addComponent(btnRegister)
					.addGap(200))
		);
		setLayout(login_layout);

	}	

}
