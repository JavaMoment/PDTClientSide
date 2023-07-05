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
import javax.swing.SwingUtilities;

import com.entities.Usuario;
import com.java.GUI.Main;
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
		lblMail = new JLabel("Ingrese su correo institucional o nombre de usuario:");
		lblMail.setHorizontalAlignment(SwingConstants.LEFT);
		
		txtFieldMail = new JTextField();
		txtFieldMail.setColumns(10);
		
		lblPassword = new JLabel("Ingrese su contraseña:");
		
		passwordField = new JPasswordField();
		
		btnLogin = new JButton("Ingresar");
		btnLogin.setBackground(new Color(125, 229, 251));
		btnLogin.setForeground(new Color(40, 40, 40));
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// Implementar logica para ingreso a sistema
				String emailOrUsername = txtFieldMail.getText().trim();
				String passw = new String(passwordField.getPassword());
				
				if(emailOrUsername.isEmpty() || passw.isEmpty()) {
					JOptionPane.showMessageDialog(LoginPanel.this, "Por favor, ingrese un correo o contraseña.", "Login Error", JOptionPane.WARNING_MESSAGE);
					passwordField.setText("");
					return;
				}
				
				if (!passw.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+-=]).{8,}$")) {
					// Muestra un mensaje de error si la contraseña no cumple con los requisitos mínimos
					JOptionPane.showMessageDialog(LoginPanel.this,
							"Por favor ingrese una contraseña válida que contenga al menos una letra mayúscula, una letra minúscula, un número y un carácter especial, y tenga una longitud de al menos 8 caracteres.", "¡Cuidadiiitoo!", JOptionPane.WARNING_MESSAGE);
					passwordField.setText("");
					return;
				}
				
				if(emailOrUsername.contains("@")) {
					try {
						InternetAddress correoInternet = new InternetAddress(emailOrUsername);
						correoInternet.validate();
					} catch (AddressException ex) {
						// Muestra un mensaje de error si el correo electrónico no es válido
						JOptionPane.showMessageDialog(LoginPanel.this, "Por favor ingrese una dirección de correo electrónico válida.", "Cuidadiiitooo", JOptionPane.WARNING_MESSAGE);
						passwordField.setText("");
						return;
					}
				}

				Usuario userBDD = usuarioBean.selectUserBy(emailOrUsername);
				if(userBDD == null) {
					JOptionPane.showMessageDialog(LoginPanel.this, "El correo electronico o nombre de usuario ingresado no es correcto", "¡Oh no! Oh no no no", JOptionPane.WARNING_MESSAGE);
					passwordField.setText("");
					return;
				}
				else if(!userBDD.isValidUser(passw)) {
					JOptionPane.showMessageDialog(LoginPanel.this, "La contraseña ingresada es incorrecta o usted no se encuentra habilitado para ingresar", "¡Oh no! Oh no no no", JOptionPane.ERROR_MESSAGE);
					passwordField.setText("");
					return;
				} else {
					JOptionPane.showMessageDialog(LoginPanel.this, "¡Bienvenido/a!");
					Main main = (Main) SwingUtilities.getWindowAncestor(LoginPanel.this);
			        main.initHome();
					main.revalidate();
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
					.addContainerGap(1, GroupLayout.PREFERRED_SIZE)
					.addGap(100)
					.addGroup(login_layout.createParallelGroup(Alignment.LEADING, false)
						.addGap(100)
						.addComponent(btnLogin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblPassword)
						.addComponent(lblMail)
						.addComponent(passwordField)
						.addComponent(txtFieldMail)
						.addComponent(lblLoginTitle, GroupLayout.PREFERRED_SIZE, 260, Short.MAX_VALUE)
						.addComponent(btnRegister, GroupLayout.PREFERRED_SIZE, 298, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(1, GroupLayout.PREFERRED_SIZE)
					.addGap(100))
		);
		login_layout.setVerticalGroup(
			login_layout.createParallelGroup(Alignment.LEADING)
				.addGroup(login_layout.createSequentialGroup()
					.addContainerGap(1, Short.MAX_VALUE)
					.addGap(50)
					.addComponent(lblLoginTitle)
					.addGap(150)
					.addComponent(lblMail, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addGap(0)
					.addComponent(txtFieldMail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addGap(0)
					.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(30)
					.addComponent(btnLogin)
					.addGap(100)
					.addComponent(btnRegister)
					.addGap(200))
		);
		setLayout(login_layout);

	}	

}
