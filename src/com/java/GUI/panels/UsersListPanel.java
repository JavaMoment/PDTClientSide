package com.java.GUI.panels;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.time.Year;
import java.util.Arrays;
import java.util.List;

import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;

import com.entities.Itr;
import com.entities.Usuario;
import com.java.GUI.utils.EntityTableModel;
import com.services.ItrBeanRemote;
import com.services.UsuarioBeanRemote;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class UsersListPanel extends JPanel {
	private JTable table;
	private JTextField textFieldSearch;

	/**
	 * Create the panel.
	 */
	public UsersListPanel(UsuarioBeanRemote usuarioBean, ItrBeanRemote itrBean) {
		setLayout(new MigLayout("", "[89px][89px,grow]", "[][][23px,grow][][][][][][][][][][][]"));
		
		textFieldSearch = new JTextField();
		add(textFieldSearch, "flowx,cell 1 0,alignx left,growy");
		textFieldSearch.setColumns(10);
		
		JLabel lblFilterTitle = new JLabel("Filtrar por:");
		add(lblFilterTitle, "cell 0 1");
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 1 1 1 13,grow");
		
		List<Usuario> users = usuarioBean.selectAll();
		String[] usersColNames = Arrays.stream(usuarioBean.getColsNames())
				.filter(value -> !value.equals("estudiantes") && !value.equals("tutores") && !value.equals("analistas")
						&& !value.equals("contrasenia"))
				.toArray(String[]::new);
		
		List<Itr> itrs = itrBean.selectAll();
		
		table = new JTable();
		table.setModel(new EntityTableModel<Usuario>(usersColNames, users));
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		scrollPane.setViewportView(table);
		
		JLabel lblUserType = new JLabel("Tipo de Usuario:");
		add(lblUserType, "flowy,cell 0 2");
		
		String[] userTypes = {"Analista", "Estudiante", "Tutor"};
		JComboBox comboBoxUserType = new JComboBox(userTypes);
		lblUserType.setLabelFor(comboBoxUserType);
		add(comboBoxUserType, "cell 0 2,growx");
		
		JLabel lblItr = new JLabel("ITR:");
		add(lblItr, "flowy,cell 0 2");
		
		JComboBox comboBoxItr = new JComboBox(itrs.toArray());
		lblItr.setLabelFor(comboBoxItr);
		add(comboBoxItr, "cell 0 2,growx");
		
		JLabel lblGeneration = new JLabel("Generación:");
		add(lblGeneration, "flowy,cell 0 2");
		
		JSpinner spinnerYearGen = new JSpinner();
		lblGeneration.setLabelFor(spinnerYearGen);
		spinnerYearGen.setValue(Year.now().getValue());
		add(spinnerYearGen, "cell 0 2,growx");
		
		String[] status = {"Inactivo", "Activo"};
		
		JLabel lblStatus = new JLabel("Estado:");
		add(lblStatus, "flowy,cell 0 2");
		JComboBox comboBoxStatus = new JComboBox(status);
		lblStatus.setLabelFor(comboBoxStatus);
		add(comboBoxStatus, "cell 0 2,growx");
		
		JButton btnSearch = new JButton("");
		btnSearch.setIcon(new ImageIcon(UsersListPanel.class.getResource("/com/java/resources/images/buscar.png")));
		btnSearch.setFont(new Font("sansserif", 1, 12));
        btnSearch.setForeground(new Color(30, 122, 236));
        btnSearch.setContentAreaFilled(false);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.setBorder(null);
		add(btnSearch, "cell 1 0");

	}

}
