package com.java.GUI.panels;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.java.enums.Genres;
import com.toedter.calendar.JDateChooser;

import javax.swing.JPanel;
import javax.swing.JPasswordField;

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
	private JComboBox<String> comboBoxItr;
	private JLabel lblPhone;
	private JTextField txtFieldPhone;
	private JLabel lblCi;
	private JTextField txtFieldCi;
	
	private JPanel contentPane;

	/**
	 * Create the panel.
	 */
	public SignUpPanel(JPanel contentPane) {
		this.contentPane = contentPane;
        lblUsername = new JLabel("Nombre de usuario:");
        lblSignUpTitle = new JLabel("Registro");
        lblBirthdate = new JLabel("Fecha de nacimiento:");
        lblCi = new JLabel("Cédula de identidad (sin puntos ni guiones):");
        lblCity = new JLabel("Ciudad de residencia:");
        lblDepartamento = new JLabel("Departamento de residencia:");
        lblEmail = new JLabel("Correo:");
        lblGenre = new JLabel("Genero:");
        lblItr = new JLabel("ITR a la que pertenece:");
        lblLastName1 = new JLabel("Primer apellido:");
        lblLastName2 = new JLabel("Segundo apellido:");
        lblPassw2 = new JLabel("Re-ingrese su contraseña:");
        lblName1 = new JLabel("Primer nombre:");
        lblName2 = new JLabel("Segundo nombre (opcional):");
        lblPassword = new JLabel("Contraseña:");
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
        
        comboBoxCity = new JComboBox<String>();
        comboBoxGenre = new JComboBox<Genres>();
        comboBoxItr = new JComboBox<String>();
        comboBoxDepas = new JComboBox<String>();

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

}
