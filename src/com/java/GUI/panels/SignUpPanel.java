package com.java.GUI.panels;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.time.Instant;
import java.time.Year;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

import com.entities.Usuario;
import com.enums.Roles;
import com.entities.Analista;
import com.entities.Area;
import com.entities.Departamento;
import com.entities.Estudiante;
import com.entities.Localidad;
import com.entities.Tutor;
import com.entities.Itr;
import com.java.controller.BeansFactory;
import com.java.enums.Beans;
import com.java.enums.Genres;
import com.services.AnalistaBeanRemote;
import com.services.AreaBeanRemote;
import com.services.DepartamentoBeanRemote;
import com.services.EstudianteBeanRemote;
import com.services.ItrBeanRemote;
import com.services.LocalidadBeanRemote;
import com.services.TutorBeanRemote;
import com.services.UsuarioBeanRemote;
import com.toedter.calendar.JDateChooser;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;

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
	private JLabel lblMail1;
	private JTextField txtFieldMail1;
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
	private JComboBox comboBoxDepas;
	private JLabel lblCity;
	private JComboBox comboBoxCity;
	private JLabel lblItr;
	private JComboBox comboBoxItr;
	private JLabel lblPhone;
	private JTextField txtFieldPhone;
	private JLabel lblCi;
	private JTextField txtFieldCi;
	private JLabel lblUserType;
	private JComboBox<String> comboBoxUserType;
	private JLabel lblGen;
	private JSpinner spinnGen;
	private JLabel lblArea;
	private JComboBox comboBoxArea;
	private JLabel lblRol;
	private JComboBox comboBoxRol;
	
	private DepartamentoBeanRemote depaBean = BeansFactory.getBean(Beans.Departamentos, DepartamentoBeanRemote.class);
	private LocalidadBeanRemote localidadBean = BeansFactory.getBean(Beans.Localidades, LocalidadBeanRemote.class);
	private UsuarioBeanRemote usuarioBean = BeansFactory.getBean(Beans.Usuario, UsuarioBeanRemote.class);
	private ItrBeanRemote itrBean = BeansFactory.getBean(Beans.Itr, ItrBeanRemote.class);
	private AnalistaBeanRemote analiBean = BeansFactory.getBean(Beans.Analista, AnalistaBeanRemote.class);
	private EstudianteBeanRemote estudBean = BeansFactory.getBean(Beans.Estudiante, EstudianteBeanRemote.class);
	private AreaBeanRemote areaBean = BeansFactory.getBean(Beans.Area, AreaBeanRemote.class);
	private TutorBeanRemote tutorBean = BeansFactory.getBean(Beans.Tutor, TutorBeanRemote.class);

	/**
	 * Create the panel.
	 */
	public SignUpPanel(JPanel contentPane) {
		
        lblMail1 = new JLabel("Correo personal (*):");
        lblSignUpTitle = new JLabel("Registro");
        lblBirthdate = new JLabel("Fecha de nacimiento (*):");
        lblCi = new JLabel("Cédula de identidad (*):");
        lblCity = new JLabel("Ciudad de residencia (*):");
        lblDepartamento = new JLabel("Departamento de residencia (*):");
        lblEmail = new JLabel("Correo institucional (*):");
        lblGenre = new JLabel("Genero (*):");
        lblItr = new JLabel("ITR a la que pertenece (*):");
        lblLastName1 = new JLabel("Primer apellido (*):");
        lblLastName2 = new JLabel("Segundo apellido (*):");
        lblPassw2 = new JLabel("Re-ingrese su contraseña (*):");
        lblName1 = new JLabel("Primer nombre (*):");
        lblName2 = new JLabel("Segundo nombre:");
        lblPassword = new JLabel("Contraseña (*):");
        lblPhone = new JLabel("Télefono:");
        lblUserType = new JLabel("Tipo de usuario (*):");
        lblGen = new JLabel("Generación de ingreso a la carrera (*): ");
        lblGen.setEnabled(false);
        lblGen.setVisible(false);
        lblArea = new JLabel("Area a la que pertenece (*):");
        lblArea.setVisible(false);
        lblArea.setEnabled(false);
        lblRol = new JLabel("Rol asignado (*):");
        lblRol.setVisible(false);
        lblRol.setEnabled(false);
        
        txtfldPassword = new JPasswordField();
        txtFldPassw2 = new JPasswordField();
        txtFieldMail1 = new JTextField();
        txtFieldEmail = new JTextField();
        txtFieldName1 = new JTextField();
        txtFieldName2 = new JTextField();
        txtFieldCi = new JTextField();
        txtFieldEmail = new JTextField();
        txtFieldLastName1 = new JTextField();
        txtFieldLastname2 = new JTextField();
        txtFieldPhone = new JTextField();
        
        var txtFields = List.of(txtFieldMail1, txtFieldEmail, txtFieldName1, txtFieldLastname2, txtFieldCi,
        		txtFieldEmail, txtFieldLastName1);
        
        comboBoxGenre = new JComboBox<Genres>(Genres.values());
        comboBoxItr = new JComboBox(itrBean.selectAll().toArray());
        comboBoxDepas = new JComboBox(depaBean.selectAll().toArray());
        comboBoxCity = new JComboBox(localidadBean.selectAllByObject((Departamento) comboBoxDepas.getSelectedItem()).toArray());
        String[] userTypes = {"Analista", "Tutor", "Estudiante"};
        comboBoxUserType = new JComboBox<String>(userTypes);
        comboBoxUserType.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				switch((String) comboBoxUserType.getSelectedItem()) {
					case "Estudiante":
						lblGen.setEnabled(true);
						lblGen.setVisible(true);
						spinnGen.setEnabled(true);
						spinnGen.setVisible(true);
				        lblRol.setVisible(false);
				        lblRol.setEnabled(false);
				        comboBoxRol.setVisible(false);
				        comboBoxRol.setEditable(false);
				        lblArea.setVisible(false);
				        lblArea.setEnabled(false);
				        comboBoxArea.setVisible(false);
				        comboBoxArea.setEnabled(false);
						break;
					case "Tutor":
						lblGen.setEnabled(false);
						lblGen.setVisible(false);
						spinnGen.setEnabled(false);
						spinnGen.setVisible(false);
						lblRol.setVisible(true);
				        lblRol.setEnabled(true);
				        comboBoxRol.setVisible(true);
				        comboBoxRol.setEnabled(true);
				        lblArea.setVisible(true);
				        lblArea.setEnabled(true);
				        comboBoxArea.setVisible(true);
				        comboBoxArea.setEnabled(true);
						break;
					default:
						lblGen.setEnabled(false);
						lblGen.setVisible(false);
						spinnGen.setEnabled(false);
						spinnGen.setVisible(false);
				        lblRol.setVisible(false);
				        lblRol.setEnabled(false);
				        comboBoxRol.setVisible(false);
				        comboBoxRol.setEditable(false);
				        lblArea.setVisible(false);
				        lblArea.setEnabled(false);
				        comboBoxArea.setVisible(false);
				        comboBoxArea.setEnabled(false);
						break;
						
				}
			}
        });
        comboBoxRol = new JComboBox(Roles.values());
        comboBoxRol.setVisible(false);
        comboBoxRol.setEnabled(false);
        comboBoxArea = new JComboBox(areaBean.selectAll().toArray());
        comboBoxArea.setVisible(false);
        comboBoxArea.setEnabled(false);
        
        var comboBoxes = List.of(comboBoxArea, comboBoxDepas, comboBoxCity, comboBoxGenre, comboBoxItr, comboBoxItr, comboBoxRol, comboBoxUserType);
        
        spinnGen = new JSpinner();
        spinnGen.setValue(Year.now().getValue());
        spinnGen.setEnabled(false);
        spinnGen.setVisible(false);
        
        Departamento depa = (Departamento) comboBoxDepas.getSelectedItem();
        comboBoxDepas.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				Departamento depa = (Departamento) comboBoxDepas.getSelectedItem();
				comboBoxCity.setModel(new DefaultComboBoxModel(localidadBean.selectAllByObject(depa).toArray()));
				comboBoxCity.revalidate();
			}
        });

        btnSignup = new JButton("Registrarme!");
        btnGoBack = new JButton("Back to login");
        
        dcBirthdate = new JDateChooser();

        setBackground(new Color(255, 255, 255));

        lblSignUpTitle.setFont(new Font("sansserif", 1, 48));
        lblSignUpTitle.setForeground(new Color(69, 68, 68));
        lblSignUpTitle.setHorizontalAlignment(SwingConstants.CENTER);

        btnSignup.setBackground(new Color(125, 229, 251));
        btnSignup.setForeground(new Color(40, 40, 40));

        btnGoBack.setFont(new Font("sansserif", 1, 12));
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
				
				if(txtFields.stream().anyMatch(t -> t.getText().isEmpty())) {
					JOptionPane.showMessageDialog(SignUpPanel.this, "Existen campos obligatorios vacíos.", "¡Error!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				String passw = new String(txtfldPassword.getPassword());
				String passw2 = new String(txtFldPassw2.getPassword());
				String lastName1 = txtFieldLastName1.getText().trim();
				String lastName2 = txtFieldLastname2.getText().trim();
				String name1 = txtFieldName1.getText().trim();
				String email = txtFieldEmail.getText().trim();
				String personalMail = txtFieldMail1.getText().trim();
				Departamento depa = (Departamento) comboBoxDepas.getSelectedItem();
				String ci = txtFieldCi.getText();
				Date birthdate;
				char genre = comboBoxGenre.getSelectedItem().equals(Genres.Femenino) ? 'F' : comboBoxGenre.getSelectedItem().equals(Genres.Masculino) ? 'M' : 'O';
				Itr itr = (Itr) comboBoxItr.getSelectedItem();
				Localidad city = (Localidad) comboBoxCity.getSelectedItem();
				
				if(dcBirthdate.getDate() == null) {
					JOptionPane.showMessageDialog(SignUpPanel.this, "El campo de fecha nacimiento no puede ser vacío.", "¡Error!", JOptionPane.ERROR_MESSAGE);
					return;
				} else {
					birthdate = (Date.valueOf(dcBirthdate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
				}
				
				try {
					InternetAddress correoInternet = new InternetAddress(email);
					InternetAddress personalEmailInet = new InternetAddress(personalMail);
					correoInternet.validate();
					personalEmailInet.validate();
				} catch (AddressException ex) {
					// Muestra un mensaje de error si el correo electrónico no es válido
					JOptionPane.showMessageDialog(SignUpPanel.this, "Por favor ingrese una dirección de correo electrónico válida.");
					return;
				}
				
				String username = email.split("@")[0];
				String mailDomain = email.split("@")[1];
				
				if(!mailDomain.endsWith(".utec.edu.uy")) {
					JOptionPane.showMessageDialog(SignUpPanel.this, "Por favor ingrese una dirección de correo electrónico institucional con terminación: \".utec.edu.uy\".");
					return;
				}
				
				if(usuarioBean.isUserRegistered(txtFieldMail1.getText())) {
					JOptionPane.showMessageDialog(SignUpPanel.this, "El nombre de usuario ingresado ya se encuentra registrado.");
					return;
				}
				
				if(!passw.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+-=]).{8,}$") || !passw2.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+-=]).{8,}$")) {
					// Muestra un mensaje de error si la contraseña no cumple con los requisitos mínimos
					JOptionPane.showMessageDialog(SignUpPanel.this,
							"Por favor ingrese una contraseña válida que contenga al menos una letra mayúscula, una letra minúscula, un número y un carácter especial, y tenga una longitud de al menos 8 caracteres.");
					return;
				}
				
				if(!passw.equals(passw2)) {
					JOptionPane.showMessageDialog(SignUpPanel.this, "Las contraseñas no coinciden.");
					return;
				}
				
				if((txtFieldName1.getText().length() > 50) || (txtFieldName2.getText().length() > 50)) {
					JOptionPane.showMessageDialog(SignUpPanel.this, "El largo del nombre es mayor al máximo permitido (50 caracteres)");
					return;
				}
				
				if(!dcBirthdate.getDate().before(Date.from(Instant.now()))) {
					JOptionPane.showMessageDialog(SignUpPanel.this, "Por favor, ingrese una fecha de nacimiento anterior a la fecha actual.");
					return;
				}
				
				if(!isAnUruguayanCI(txtFieldCi.getText())) {
					JOptionPane.showMessageDialog(SignUpPanel.this, "La cedula ingresada no es válida y/o no contiene 8 digitos de largo.");
					return;
				}
				
				if(!txtFieldPhone.getText().matches("^\\d{9}$") && !txtFieldPhone.getText().isEmpty()) {
					JOptionPane.showMessageDialog(SignUpPanel.this, "El número de telefono ingresado no contiene sólo números y/o tiene menos o mas de 8 digitos");
					return;
				}
				
				Usuario newUser = new Usuario(username, lastName1, lastName2,
						passw, ci, birthdate,
						genre, depa, itr,
						city, email, personalMail,
						name1);

				
				if(!txtFieldPhone.getText().isEmpty()) {
					newUser.setTelefono(txtFieldPhone.getText());
				}
				
				if(!txtFieldName2.getText().isEmpty()) {
					newUser.setNombre2(txtFieldName2.getText());
				}
				
				int answerCode = JOptionPane.showConfirmDialog(SignUpPanel.this, "Su solicitud será revisada antes de activar su cuenta.\n¿Está de acuerdo?", "¡Atención!", JOptionPane.YES_NO_OPTION);
				if(answerCode == 1) {
					return;
				}
				
				switch((String) comboBoxUserType.getSelectedItem()) {
					case "Analista":
						Analista analista = new Analista(newUser);
						newUser.setAnalistas(Set.of(analista));
						break;
					case "Estudiante":
						if((Integer) spinnGen.getValue() > Year.now().getValue()) {
							JOptionPane.showMessageDialog(SignUpPanel.this, "El año de la generación no puede ser mayor al año actual, intente nuevamente.", "Año de la generación incorrecto", JOptionPane.WARNING_MESSAGE);
							return;
						}
						String gen = spinnGen.getValue().toString();
						Estudiante estud = new Estudiante(newUser, gen);
						newUser.setEstudiantes(Set.of(estud));
						break;
					case "Tutor":
						Roles rol = (Roles) comboBoxRol.getSelectedItem();
						Area area = (Area) comboBoxArea.getSelectedItem();
						Tutor tutor = new Tutor(newUser, area, rol);
						newUser.setTutores(Set.of(tutor));
						break;
					default:
						break;
				}

				int exitCode = usuarioBean.create(newUser);
				
				if(exitCode == 0) {
					JOptionPane.showMessageDialog(SignUpPanel.this, "El usuario ha sido correctamente creado.\nEspere la habilitación del analista para poder ingresar.");
				} else {
					JOptionPane.showMessageDialog(SignUpPanel.this, "Ha ocurrido un error mientras se intentaba crear el usuario.\nPor favor, intente de nuevo.");
				}
				
				txtFields.stream().forEach(txt -> txt.setText(""));
				comboBoxes.stream().forEach(box -> box.setSelectedIndex(0));
				dcBirthdate.setDate(Date.from(Instant.now()));
				txtFieldLastname2.setText("");
				txtFieldPhone.setText("");
				txtfldPassword.setText("");
				txtFldPassw2.setText("");
			}
		});
        
        /**
         * UI manager creation and allignment of the Swing components
         */
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblEmail)
                    .addComponent(lblPassword)
                    .addComponent(lblMail1)
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
                    .addComponent(lblUserType)
                    .addComponent(lblSignUpTitle, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                    .addComponent(txtfldPassword, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFldPassw2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSignup, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGoBack, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPassw2)
                    .addComponent(txtFieldMail1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFieldEmail, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(txtFieldName1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(txtFieldName2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(txtFieldCi, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(dcBirthdate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(comboBoxUserType, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(lblGen)
	                .addComponent(spinnGen, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(lblRol)
	                .addComponent(comboBoxRol, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(lblArea)
	                .addComponent(comboBoxArea, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(lblSignUpTitle)
                .addGap(18, 18, 18)
                .addComponent(lblMail1, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtFieldMail1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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
                .addComponent(lblUserType, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(comboBoxUserType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblGen, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(spinnGen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblArea, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(comboBoxArea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblRol, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(comboBoxRol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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
	    if (digitsOnly.length() != 8) {
	        return false;
	    }

	    // Paso 3: Separar digito verificador de los demas digitos
	    String digits = digitsOnly.substring(0, digitsOnly.length()-1);
	    String checkerDigit = digitsOnly.substring(digitsOnly.length()-1, digitsOnly.length());

	    // Paso 4: Convertir los digitos a vectores y crear operador verificador
	    String[] digitsArr = digits.split("");
	    int[] verifier = {2, 9, 8, 7, 6, 3, 4};

	    // Paso 5: El modulo 10 de la multiplicacion vectorial entre digitos y vector verificador debe ser igual al digito verificador
	    int mod = 0;
	    for(int i = 0; i<digitsArr.length; i++) {
	    	mod += (Integer.parseInt(digitsArr[i]) * verifier[i]) % 10;
	    }
	    mod = mod % 10;
	    mod = (mod - 10) * -1;
	    mod %= 10;
	    
	    // Paso 6: ¿Es el modulo de la operacion igual al digito verificador?
	    return mod == Integer.parseInt(checkerDigit);
	}

}
