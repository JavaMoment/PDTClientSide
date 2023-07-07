package com.java.GUI.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.time.Instant;
import java.time.Year;
import java.time.ZoneId;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.entities.Analista;
import com.entities.Area;
import com.entities.Departamento;
import com.entities.Estudiante;
import com.entities.Itr;
import com.entities.Localidad;
import com.entities.Tutor;
import com.entities.Usuario;
import com.enums.Roles;
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

public class UserDataModificationPanel extends ContentPanel {

	private JLabel lblMail1;
	private JLabel lblSignUpTitle;
	private JLabel lblBirthdate;
	private JLabel lblCi;
	private JLabel lblCity;
	private JLabel lblDepartamento;
	private JLabel lblEmail;
	private JLabel lblGenre;
	private JLabel lblItr;
	private JLabel lblLastName1;
	private JLabel lblLastName2;
	private JLabel lblName1;
	private JLabel lblName2;
	private JLabel lblPhone;
	private JLabel lblUserType;
	private JLabel lblGen;
	private JLabel lblArea;
	private JLabel lblRol;
	private JTextField txtFieldMail1;
	private JTextField txtFieldEmail;
	private JTextField txtFieldName1;
	private JTextField txtFieldName2;
	private JTextField txtFieldCi;
	private JTextField txtFieldLastName1;
	private JTextField txtFieldLastname2;
	private JTextField txtFieldPhone;
	private JComboBox comboBoxCity;
	private JComboBox<Genres> comboBoxGenre;
	private JComboBox comboBoxItr;
	private JComboBox comboBoxDepas;
	private JComboBox<String> comboBoxUserType;
	private JComboBox comboBoxRol;
	private JComboBox comboBoxArea;
	private JSpinner spinnGen;
	private JButton btnModify;
	private JDateChooser dcBirthdate;
	private JButton btnCancel;

	private LocalidadBeanRemote localidadBean = BeansFactory.getBean(Beans.Localidades, LocalidadBeanRemote.class);
	private TutorBeanRemote tutorBean = BeansFactory.getBean(Beans.Tutor, TutorBeanRemote.class);;
	private EstudianteBeanRemote estudBean = BeansFactory.getBean(Beans.Estudiante, EstudianteBeanRemote.class);;
	private AnalistaBeanRemote analiBean = BeansFactory.getBean(Beans.Analista, AnalistaBeanRemote.class);;
	private UsuarioBeanRemote usuarioBean = BeansFactory.getBean(Beans.Usuario, UsuarioBeanRemote.class);;
	private ItrBeanRemote itrBean = BeansFactory.getBean(Beans.Itr, ItrBeanRemote.class);;
	private DepartamentoBeanRemote depaBean = BeansFactory.getBean(Beans.Departamentos, DepartamentoBeanRemote.class);;
	private AreaBeanRemote areaBean = BeansFactory.getBean(Beans.Area, AreaBeanRemote.class);
	
	private Usuario user;
	
	public UserDataModificationPanel() {
		
        lblMail1 = new JLabel("Correo personal (*):");
        lblSignUpTitle = new JLabel("Modificación de los datos del usuario");
        lblBirthdate = new JLabel("Fecha de nacimiento (*):");
        lblCi = new JLabel("Cédula de identidad (*):");
        lblCity = new JLabel("Ciudad de residencia (*):");
        lblDepartamento = new JLabel("Departamento de residencia (*):");
        lblEmail = new JLabel("Correo institucional (*):");
        lblGenre = new JLabel("Genero (*):");
        lblItr = new JLabel("ITR a la que pertenece (*):");
        lblLastName1 = new JLabel("Primer apellido (*):");
        lblLastName2 = new JLabel("Segundo apellido (*):");
        lblName1 = new JLabel("Primer nombre (*):");
        lblName2 = new JLabel("Segundo nombre:");
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
        
        txtFieldMail1 = new JTextField();
        txtFieldEmail = new JTextField();
        txtFieldName1 = new JTextField();
        txtFieldName2 = new JTextField();
        txtFieldCi = new JTextField();
        txtFieldLastName1 = new JTextField();
        txtFieldLastname2 = new JTextField();
        txtFieldPhone = new JTextField();
        
        var txtFields = List.of(txtFieldMail1, txtFieldEmail, txtFieldName1, txtFieldLastname2, txtFieldCi,
        		txtFieldEmail, txtFieldLastName1);
        
        comboBoxCity = new JComboBox(localidadBean.selectAllBy((long) 1).toArray());
        comboBoxGenre = new JComboBox<Genres>(Genres.values());
        comboBoxItr = new JComboBox(itrBean.selectAll().toArray());
        comboBoxDepas = new JComboBox(depaBean.selectAll().toArray());
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
			}
        });

        btnModify = new JButton("Modificar");
        btnCancel = new JButton("Cancelar");
        
        dcBirthdate = new JDateChooser();
        dcBirthdate.setDate(Date.from(Instant.now()));

        setBackground(new Color(255, 255, 255));

        lblSignUpTitle.setFont(new Font("sansserif", 1, 48));
        lblSignUpTitle.setForeground(new Color(69, 68, 68));
        lblSignUpTitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        btnCancel.setBackground(new Color(244, 113, 116));
        btnCancel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		JTabbedPane jtp = (JTabbedPane) SwingUtilities.getAncestorOfClass(JTabbedPane.class, UserDataModificationPanel.this);
        		jtp.setSelectedIndex(1);
        		jtp.revalidate();
        		txtFields.stream().forEach(box -> box.setText(""));
        		comboBoxes.stream().forEach(combo -> combo.setSelectedIndex(0));
        		dcBirthdate.setDate(Date.from(Instant.now()));
        		txtFieldPhone.setText("");
        	}
        });

        btnModify.setBackground(new Color(125, 229, 251));
        btnModify.setForeground(new Color(40, 40, 40));
        btnModify.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				String email = txtFieldEmail.getText().trim();
				String lastName1 = txtFieldLastName1.getText().trim();
				String lastName2 = txtFieldLastname2.getText().trim();
				String name1 = txtFieldName1.getText().trim();
				Departamento depa = (Departamento) comboBoxDepas.getSelectedItem();
				String ci = txtFieldCi.getText();
				Date birthdate = (Date.valueOf(dcBirthdate.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
				char genre = comboBoxGenre.getSelectedItem().equals(Genres.Femenino) ? 'F' : comboBoxGenre.getSelectedItem().equals(Genres.Masculino) ? 'M' : 'O';
				Itr itr = (Itr) comboBoxItr.getSelectedItem();
				Localidad city = (Localidad) comboBoxCity.getSelectedItem();
				String personalMail = txtFieldMail1.getText().trim();
				
				if(txtFields.stream().anyMatch(t -> t.getText().isEmpty())) {
					JOptionPane.showMessageDialog(UserDataModificationPanel.this, "Existen campos obligatorios vacíos.", "¡Error!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				try {
					InternetAddress correoInternet = new InternetAddress(email);
					InternetAddress personalEmailInet = new InternetAddress(personalMail);
					correoInternet.validate();
					personalEmailInet.validate();
				} catch (AddressException ex) {
					// Muestra un mensaje de error si el correo electrónico no es válido
					JOptionPane.showMessageDialog(UserDataModificationPanel.this, "Por favor ingrese una dirección de correo electrónico válida.");
					return;
				}
				
				if((txtFieldName1.getText().length() > 50) || (txtFieldName2.getText().length() > 50)) {
					JOptionPane.showMessageDialog(UserDataModificationPanel.this, "El largo del nombre es mayor al máximo permitido (50 caracteres)");
					return;
				}
				
				if(!dcBirthdate.getDate().before(Date.from(Instant.now()))) {
					JOptionPane.showMessageDialog(UserDataModificationPanel.this, "Por favor, ingrese una fecha de nacimiento anterior a la fecha actual.");
					return;
				}
				
				if(!isAnUruguayanCI(txtFieldCi.getText())) {
					JOptionPane.showMessageDialog(UserDataModificationPanel.this, "La cedula ingresada no es válida y/o no contiene 8 digitos de largo.");
					return;
				}
				
				if(!txtFieldPhone.getText().matches("^\\d{9}$") && !txtFieldPhone.getText().isEmpty()) {
					JOptionPane.showMessageDialog(UserDataModificationPanel.this, "El número de telefono ingresado no contiene sólo números y/o tiene menos o mas de 8 digitos");
					return;
				}
				
				user.setApellido1(lastName1);
				user.setApellido2(lastName2);
				user.setDepartamento(depa);
				user.setDocumento(ci);
				user.setFechaNacimiento(birthdate);
				user.setGenero(genre);
				user.setItr(itr);
				user.setLocalidad(city);
				user.setMailInstitucional(email);
				user.setMailPersonal(personalMail);
				user.setNombre1(name1);
				
				if(!txtFieldPhone.getText().isEmpty()) {
					user.setTelefono(txtFieldPhone.getText().trim());
				}
				
				if(!txtFieldName2.getText().isEmpty()) {
					user.setNombre2(txtFieldName2.getText().trim());
				}
				
				switch((String) comboBoxUserType.getSelectedItem()) {
					case "Analista":
						Analista analista = analiBean.selectUserBy(user.getNombreUsuario());
						Tutor tutor = tutorBean.selectUserBy(user.getNombreUsuario());
						Estudiante estud = estudBean.selectUserBy(user.getNombreUsuario());
						
						user.removeAnalista(analista);
						user.removeEstudiante(estud);
						user.removeTutor(tutor);
						
						if(analista == null) {
							analista = new Analista(user);
						}
						
						user.addAnalista(analista);
						break;
					case "Estudiante":
						if((Integer) spinnGen.getValue() > Year.now().getValue()) {
							JOptionPane.showMessageDialog(UserDataModificationPanel.this, "El año de la generación no puede ser mayor al año actual, intente nuevamente.", "Año de la generación incorrecto", JOptionPane.WARNING_MESSAGE);
							return;
						}
						String gen = spinnGen.getValue().toString();
						
						Estudiante estudi = estudBean.selectUserBy(user.getNombreUsuario());
						Tutor tutor2 = tutorBean.selectUserBy(user.getNombreUsuario());
						Analista anali2 = analiBean.selectUserBy(user.getNombreUsuario());
						
						user.removeAnalista(anali2);
						user.removeEstudiante(estudi);
						user.removeTutor(tutor2);
						
						if(estudi == null) {
							estudi = new Estudiante(user, gen);
						}
						
						user.addEstudiante(estudi);
						break;
					case "Tutor":
						Roles rol = (Roles) comboBoxRol.getSelectedItem();
						Area area = (Area) comboBoxArea.getSelectedItem();
						
						Analista anali3 = analiBean.selectUserBy(user.getNombreUsuario());
						Tutor tutor3 = tutorBean.selectUserBy(user.getNombreUsuario());
						Estudiante estud3 = estudBean.selectUserBy(user.getNombreUsuario());
						
						user.removeAnalista(anali3);
						user.removeEstudiante(estud3);
						user.removeTutor(tutor3);
						
						if(tutor3 == null) {
							tutor3 = new Tutor(user, area, rol);
						}
						
						user.addTutor(tutor3);
						break;
					default:
						break;
				}
				
				int answerCode = JOptionPane.showConfirmDialog(UserDataModificationPanel.this, "El usuario será modificado.\n¿Está de acuerdo?", "¡Atención!", JOptionPane.YES_NO_OPTION);
				if(answerCode == 1) {
					return;
				}
				
				int exitCode = usuarioBean.update(user);
				
				if(exitCode == 0) {
					JOptionPane.showMessageDialog(UserDataModificationPanel.this, "El usuario ha sido correctamente modificado.");
					UsersListPanel.fireUpdateTableContentFromDB();
				} else {
					JOptionPane.showMessageDialog(UserDataModificationPanel.this, "Ha ocurrido un error mientras se intentaba modificar el usuario.\nPor favor, intente de nuevo.");
				}
				
				txtFields.stream().forEach(txt -> txt.setText(""));
				comboBoxes.stream().forEach(box -> box.setSelectedIndex(0));
				dcBirthdate.setDate(Date.from(Instant.now()));
				txtFieldLastname2.setText("");
				txtFieldPhone.setText("");
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
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.CENTER,layout.createSequentialGroup()
            		.addContainerGap(200, Short.MAX_VALUE)
            		.addComponent(btnModify, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            		.addComponent(btnCancel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            		.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            		.addContainerGap(170, Short.MAX_VALUE)
            		)
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
                .addGap(1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(layout.createBaselineGroup(false, false)
                		.addComponent(btnModify)
                		.addComponent(btnCancel)
                		.addGap(30))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                		)
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

	public void setUser(Usuario user) {
		this.user = user;
	}
	
	public void populateComponents(Usuario user) {
		this.user = user;

		txtFieldMail1.setText(user.getMailPersonal());
		txtFieldEmail.setText(user.getMailInstitucional());
		txtFieldName1.setText(user.getNombre1());
		txtFieldName2.setText(user.getNombre2());
		txtFieldCi.setText(user.getDocumento());;
        txtFieldLastName1.setText(user.getApellido1());;
        txtFieldLastname2.setText(user.getApellido2());;
        txtFieldPhone.setText(user.getTelefono());;
        
        dcBirthdate.setDate(user.getFechaNacimiento());
        
        comboBoxGenre.getModel().setSelectedItem(user.getGenero() == 'M' ? Genres.Masculino : user.getGenero() == 'F' ? Genres.Femenino : Genres.Otro);
        comboBoxItr.getModel().setSelectedItem(user.getItr());;
        comboBoxDepas.getModel().setSelectedItem(user.getDepartamento());;
        comboBoxCity.getModel().setSelectedItem(user.getLocalidad());
        comboBoxUserType.getModel().setSelectedItem(user.getTipoUsuario());
        
        if(user.getTipoUsuario().toUpperCase().equals("ESTUDIANTE")) {
        	spinnGen.setValue(!user.getGeneracion().equals("") ? Integer.valueOf(user.getGeneracion()) : Year.now().getValue());
        } else if(user.getTipoUsuario().toUpperCase().equals("TUTOR")) {
        	comboBoxRol.getModel().setSelectedItem(user.getRol());;
        	comboBoxArea.getModel().setSelectedItem(user.getArea());;
        }
        
	}
}
	