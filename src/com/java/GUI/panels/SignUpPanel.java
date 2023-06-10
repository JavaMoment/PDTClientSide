package com.java.GUI.panels;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

import com.entities.Usuario;
import com.java.enums.Genres;
import com.services.DepartamentoBeanRemote;
import com.services.ItrBeanRemote;
import com.services.LocalidadBeanRemote;
import com.services.UsuarioBeanRemote;
import com.toedter.calendar.JDateChooser;

import javax.swing.JPanel;
import javax.swing.JPasswordField;

@SuppressWarnings("serial")
public class SignUpPanel extends JPanel {

	private JLabel lblSignUpTitle;
	private JLabel lblPassword;
	private JPasswordField txtfldPassword;
	private JButton btnSignup;
	private JButton btnGoBack;
	private JLabel lblPassw2;
	private JPasswordField txtFldPassw2;
	private JLabel lblEmail;
	private JTextField txtFieldEmail;
	private JLabel lblUsername;
	private JTextField txtFieldUsername;
	private JLabel lblName1;
	private JTextField txtFieldName1;
	private JLabel lblName2;
	private JTextField txtFieldName2;
	private JLabel lblLastName1;
	private JTextField txtFieldLastName1;
	private JLabel lblLastName2;
	private JTextField txtFieldLastname2;
	private JLabel lblBirthdate;
	private JDateChooser dcBirthdate;
	private JLabel lblGenre;
	private JComboBox<Genres> comboBoxGenre;
	private JLabel lblDepartamento;
	private JComboBox<String> comboBoxDepas;
	private JLabel lblCity;
	private JComboBox<String> comboBoxCity;
	private JLabel lblItr;
	private JComboBox comboBoxItr;
	private JLabel lblPhone;
	private JTextField txtFieldPhone;
	private JLabel lblCi;
	private JTextField txtFieldCi;
	

	/**
	 * Create the panel.
	 */
	public SignUpPanel(JPanel contentPane, UsuarioBeanRemote usuarioBean, DepartamentoBeanRemote depaBean,
			LocalidadBeanRemote localidadBean, ItrBeanRemote itrBean) {
		
        lblUsername = new JLabel("Nombre de usuario (*):");
        lblSignUpTitle = new JLabel("Registro");
        lblBirthdate = new JLabel("Fecha de nacimiento (*):");
        lblCi = new JLabel("Cédula de identidad (*):");
        lblCity = new JLabel("Ciudad de residencia (*):");
        lblDepartamento = new JLabel("Departamento de residencia (*):");
        lblEmail = new JLabel("Correo (*):");
        lblGenre = new JLabel("Genero (*):");
        lblItr = new JLabel("ITR a la que pertenece (*):");
        lblLastName1 = new JLabel("Primer apellido (*):");
        lblLastName2 = new JLabel("Segundo apellido (*):");
        lblPassw2 = new JLabel("Re-ingrese su contraseña (*):");
        lblName1 = new JLabel("Primer nombre (*):");
        lblName2 = new JLabel("Segundo nombre:");
        lblPassword = new JLabel("Contraseña (*):");
        lblPhone = new JLabel("Télefono:");
        
        txtfldPassword = new JPasswordField();
        txtFldPassw2 = new JPasswordField();
        txtFieldUsername = new JTextField();
        txtFieldEmail = new JTextField();
        txtFieldName1 = new JTextField();
        txtFieldName2 = new JTextField();
        txtFieldCi = new JTextField();
        txtFieldEmail = new JTextField();
        txtFieldLastName1 = new JTextField();
        txtFieldLastname2 = new JTextField();
        txtFieldPhone = new JTextField();
        
        var txtFields = List.of(txtFieldUsername, txtFieldEmail, txtFieldName1, txtFieldLastname2, txtFieldCi,
        		txtFieldEmail, txtFieldLastName1);
        
        comboBoxCity = new JComboBox(localidadBean.selectAllNames().toArray());
        comboBoxGenre = new JComboBox<Genres>(Genres.values());
        comboBoxItr = new JComboBox(itrBean.selectAllNames().toArray());
        comboBoxDepas = new JComboBox(depaBean.selectAllNames().toArray());

        btnSignup = new JButton("Registrarme!");
        btnGoBack = new JButton("Back to login");
        
        dcBirthdate = new JDateChooser();

        setBackground(new Color(255, 255, 255));

        lblSignUpTitle.setFont(new Font("sansserif", 1, 48)); // NOI18N
        lblSignUpTitle.setForeground(new Color(69, 68, 68));
        lblSignUpTitle.setHorizontalAlignment(SwingConstants.CENTER);

        btnSignup.setBackground(new Color(125, 229, 251));
        btnSignup.setForeground(new Color(40, 40, 40));

        btnGoBack.setFont(new Font("sansserif", 1, 12)); // NOI18N
        btnGoBack.setForeground(new Color(30, 122, 236));
        btnGoBack.setContentAreaFilled(false);
        btnGoBack.setBorder(null);
        btnGoBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGoBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.show(contentPane, "login");
			}
		});

        btnSignup.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String email = txtFieldEmail.getText();
				char[] passwArr = txtfldPassword.getPassword();
				char[] passwArr2 = txtFldPassw2.getPassword();
				String passw = new String(passwArr);
				String passw2 = new String(passwArr2);
				
				for(JTextField txtField : txtFields) {
					if(txtField.getText().isEmpty()) {
						JOptionPane.showMessageDialog(SignUpPanel.this, "Existen campos obligatorios vacíos.", "¡Error!", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				
				try {
					InternetAddress correoInternet = new InternetAddress(email);
					correoInternet.validate();
				} catch (AddressException ex) {
					// Muestra un mensaje de error si el correo electrónico no es válido
					JOptionPane.showMessageDialog(null, "Por favor ingrese una dirección de correo electrónico válida.");
					return;
				}
				
				if(!passw.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+-=]).{8,}$") || !passw2.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+-=]).{8,}$")) {
					// Muestra un mensaje de error si la contraseña no cumple con los requisitos mínimos
					JOptionPane.showMessageDialog(null,
							"Por favor ingrese una contraseña válida que contenga al menos una letra mayúscula, una letra minúscula, un número y un carácter especial, y tenga una longitud de al menos 8 caracteres.");
					return;
				}
				
				if(!passw.equals(passw2)) {
					JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.");
					return;
				}
				
				if(!isAnUruguayanCI(txtFieldCi.getText())) {
					JOptionPane.showMessageDialog(SignUpPanel.this, "La cedula ingresada no es válida.");
				}
				
				if(!txtFieldPhone.getText().matches("^\\d{8}$") && !txtFieldPhone.getText().isEmpty()) {
					JOptionPane.showMessageDialog(SignUpPanel.this, "El número de telefono ingresado no contiene sólo números y/o tiene menos o mas de 8 digitos");
				}
				
				Usuario newUser = new Usuario(txtFieldUsername.getText(), txtFieldLastName1.getText(), txtFieldLastname2.getText(), 
						passw, txtFieldCi.getText(), Date.valueOf(dcBirthdate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()), 
						comboBoxGenre.getSelectedItem().equals(Genres.Femenino) ? 'F' : comboBoxGenre.getSelectedItem().equals(Genres.Masculino) ? 'M' : 'O',
						comboBoxDepas.getSelectedIndex(), comboBoxItr.getSelectedIndex(), comboBoxCity.getSelectedIndex(), 
		        		txtFieldEmail.getText(), txtFieldName1.getText());
				
				if(!txtFieldPhone.getText().isEmpty()) {
					newUser.setTelefono(txtFieldPhone.getText());
				}
				
				if(!txtFieldName2.getText().isEmpty()) {
					newUser.setNombre2(txtFieldName2.getText());
				}
				
				int exitCode = usuarioBean.create(newUser);
				if(exitCode == 0) {
					JOptionPane.showMessageDialog(SignUpPanel.this, "El usuario ha sido correctamente creado.\nEspere la habilitación del analista para poder ingresar.");
				} else {
					JOptionPane.showMessageDialog(SignUpPanel.this, "Ha ocurrido un error mientras se intentaba crear el usuario.\nPor favor, intente de nuevo.");
				}

				for(JTextField txtField : txtFields) {
					txtField.setText("");
				}
				dcBirthdate.setDate(Date.from(Instant.now()));
				comboBoxCity.setSelectedIndex(0);
				comboBoxDepas.setSelectedIndex(0);
				comboBoxGenre.setSelectedIndex(0);
				comboBoxItr.setSelectedIndex(0);
				txtFieldLastname2.setText("");
				txtFieldPhone.setText("");
				txtfldPassword.setText("");
				txtFldPassw2.setText("");
			}
		});

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblEmail)
                    .addComponent(lblPassword)
                    .addComponent(lblUsername)
                    .addComponent(lblBirthdate)
                    .addComponent(dcBirthdate)
                    .addComponent(lblCi)
                    .addComponent(lblCity)
                    .addComponent(comboBoxCity)
                    .addComponent(lblGenre)
                    .addComponent(comboBoxGenre)
                    .addComponent(lblItr)
                    .addComponent(comboBoxItr)
                    .addComponent(lblLastName1)
                    .addComponent(txtFieldLastName1)
                    .addComponent(lblLastName2)
                    .addComponent(txtFieldLastname2)
                    .addComponent(lblName1)
                    .addComponent(lblName2)
                    .addComponent(lblPhone)
                    .addComponent(txtFieldPhone)
                    .addComponent(lblDepartamento)
                    .addComponent(comboBoxDepas)
                    .addComponent(lblSignUpTitle, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                    .addComponent(txtfldPassword, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFldPassw2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSignup, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGoBack, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPassw2)
                    .addComponent(txtFieldUsername, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFieldEmail, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(txtFieldName1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(txtFieldName2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(txtFieldCi, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(dcBirthdate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(lblSignUpTitle)
                .addGap(18, 18, 18)
                .addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtFieldUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEmail, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtFieldEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtfldPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPassw2, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtFldPassw2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblName1, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtFieldName1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblName2, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtFieldName2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblLastName1, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtFieldLastName1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblLastName2, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtFieldLastname2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblBirthdate, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(dcBirthdate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblCi, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtFieldCi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblDepartamento, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(comboBoxDepas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblCity, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(comboBoxCity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblGenre, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(comboBoxGenre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblItr, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(comboBoxItr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblPhone, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtFieldPhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGap(30, 30, 30)
                .addComponent(btnSignup, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(btnGoBack)
                .addGap(30, 30, 30))
        );
	}
	
	/**
	 * Checks if a given CI (Cédula de Identidad) number is valid according to the requirements of a Uruguayan CI.
	 * 
	 * Source: https://efactura.puntoexe.com.uy/documentos/uruguay/algoritmos-de-d%C3%ADgito-verificador
	 *
	 * @param ci The CI number to validate.
	 * @return {@code true} if the CI is valid, {@code false} otherwise.
	 */
	private boolean isAnUruguayanCI(String ci) {
	    // Paso 1: Remover caracteres no numericos
	    String digitsOnly = ci.replaceAll("\\D", "");

	    // Paso 2: Chequear largo de la cedula
	    if (digitsOnly.length() != 7 && digitsOnly.length() != 8) {
	        return false;
	    }

	    // Paso 3: Agregar 0 en caso de que la cedula sea de 6 digitos
	    if (digitsOnly.length() == 7) {
	        digitsOnly = "0" + digitsOnly;
	    }

	    // Paso 4: Separar digito verificador de los demas digitos
	    String digits = digitsOnly.substring(0, digitsOnly.length()-1);
	    String checkerDigit = digitsOnly.substring(digitsOnly.length()-1, digitsOnly.length());

	    // Paso 5: Convertir los digitos a vectores y crear operador verificador
	    String[] digitsArr = digits.split("");
	    int[] verifier = {2, 9, 8, 7, 6, 3, 4};

	    // Paso 6: El modulo 10 de la multiplicacion vectorial entre digitos y vector verificador debe ser igual al digito verificador
	    int mod = 0;
	    for(int i = 0; i<digitsArr.length; i++) {
	    	mod += (Integer.parseInt(digitsArr[i]) * verifier[i]) % 10;
	    }
	    mod = mod % 10;
	    mod = (mod - 10) * -1;
	    mod %= 10;
	    
	    // Paso 7: ¿Es el modulo de la operacion igual al digito verificador?
	    return mod == Integer.parseInt(checkerDigit);
	}	    

}
