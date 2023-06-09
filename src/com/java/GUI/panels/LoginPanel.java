package com.java.GUI.panels;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.services.UsuarioBeanRemote;

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
	public LoginPanel(JPanel contentPane, UsuarioBeanRemote usuarioBean) {
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
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// Implementar logica para ingreso a sistema
				String email = txtFieldMail.getText();
				char[] passwArr = passwordField.getPassword();
				String passw = new String(passwArr);
				
				if(email.isEmpty() || passw.isEmpty()) {
					JOptionPane.showMessageDialog(LoginPanel.this, "Por favor, ingrese un correo o contraseña.", "Login Error", JOptionPane.ERROR_MESSAGE);
					passwordField.setText("");
					return;
				}
				
				if (!passw.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+-=]).{8,}$")) {
					// Muestra un mensaje de error si la contraseña no cumple con los requisitos mínimos
					JOptionPane.showMessageDialog(null,
							"Por favor ingrese una contraseña válida que contenga al menos una letra mayúscula, una letra minúscula, un número y un carácter especial, y tenga una longitud de al menos 8 caracteres.");
					passwordField.setText("");
					return;
				}
				
				try {
					InternetAddress correoInternet = new InternetAddress(email);
					correoInternet.validate();
				} catch (AddressException ex) {
					// Muestra un mensaje de error si el correo electrónico no es válido
					JOptionPane.showMessageDialog(null, "Por favor ingrese una dirección de correo electrónico válida.");
					passwordField.setText("");
					return;
				}

				String passwBDD = usuarioBean.selectPasswBy(email);
				if(passwBDD == null) {
					JOptionPane.showMessageDialog(LoginPanel.this, "Salió todo mal loco no podes", "Mal ahiiii makina", JOptionPane.INFORMATION_MESSAGE);
					passwordField.setText("");
					return;
				}
				else if(!passwBDD.equals(passw)) {
					JOptionPane.showMessageDialog(LoginPanel.this, "El usuario y/o contraseña es incorrecto", "mal ahiiii makina", JOptionPane.YES_OPTION);
					passwordField.setText("");
					return;
				} else {
					JOptionPane.showMessageDialog(LoginPanel.this, "Salió todo bien loco bien ahí", "Bien ahiiii makina", JOptionPane.YES_OPTION);
				}
				txtFieldMail.setText("");
				passwordField.setText("");
				
			}
		});
		
		btnRegister = new JButton("Registrarse");
		btnRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "signup");
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
