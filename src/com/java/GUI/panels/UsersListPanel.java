package com.java.GUI.panels;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.RowFilter;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.time.Year;
import java.util.Arrays;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.miginfocom.swing.MigLayout;

import com.entities.Itr;
import com.entities.Usuario;
import com.java.GUI.utils.EntityTableModel;
import com.services.ItrBeanRemote;
import com.services.UsuarioBeanRemote;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UsersListPanel extends JPanel {

	private JTable table;
	private JTextField textFieldSearch;

	/**
	 * Create the panel.
	 */
	public UsersListPanel(UsuarioBeanRemote usuarioBean, ItrBeanRemote itrBean) {
		setLayout(new MigLayout("", "[89px][176.00px,grow][]", "[][][23px,grow][][][][][][][][][][][]"));
		
		
		
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
		TableModel usersTableModel = new EntityTableModel<>(usersColNames, users);
		table.setModel(usersTableModel);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		scrollPane.setViewportView(table);
		
		textFieldSearch = new JTextField();
		add(textFieldSearch, "flowx,cell 1 0,alignx left,growy");
		textFieldSearch.setColumns(10);

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
		btnSearch.setToolTipText("Buscar por nombre");
		btnSearch.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				String searchText = textFieldSearch.getText();
				if(searchText.isBlank() || searchText.isEmpty()) {
					return;
				}
				RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
					public boolean include(Entry entry) {
						String name1 = (String) entry.getValue(10);
						String name2 = entry.getValue(11) != null ? (String) entry.getValue(11) : "";
						return name1.toUpperCase().contains(searchText.toUpperCase()) || name2.contains(searchText);
					}
				};
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(usersTableModel);
				sorter.setRowFilter(filter);
				table.setRowSorter(sorter);
			}
		});
		btnSearch.setIcon(new ImageIcon(UsersListPanel.class.getResource("/com/java/resources/images/buscar.png")));
		btnSearch.setFont(new Font("sansserif", 1, 12));
        btnSearch.setForeground(new Color(30, 122, 236));
        btnSearch.setContentAreaFilled(false);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.setBorder(null);
		add(btnSearch, "cell 1 0");
		
		JButton btnRefresh = new JButton("");
		btnRefresh.setToolTipText("Refrescar");
		btnRefresh.setIcon(new ImageIcon(UsersListPanel.class.getResource("/com/java/resources/images/actualizar.png")));
		btnRefresh.setFont(new Font("sansserif", 1, 12));
        btnRefresh.setForeground(new Color(30, 122, 236));
        btnRefresh.setContentAreaFilled(false);
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.setBorder(null);
        btnRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(usersTableModel);
				sorter.setRowFilter(null);
				table.setRowSorter(sorter);
			}
		});
		add(btnRefresh, "cell 1 0");
		
		JButton btnEditUser = new JButton("");
		btnEditUser.setIcon(new ImageIcon(UsersListPanel.class.getResource("/com/java/resources/images/user.png")));
		btnEditUser.setFont(new Font("sansserif", 1, 12));
        btnEditUser.setForeground(new Color(30, 122, 236));
        btnEditUser.setContentAreaFilled(false);
        btnEditUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEditUser.setBorder(null);
		add(btnEditUser, "flowx,cell 2 0");
		
		JButton btnDeleteUser = new JButton("");
		btnDeleteUser.setIcon(new ImageIcon(UsersListPanel.class.getResource("/com/java/resources/images/delete-user.png")));
		btnDeleteUser.setFont(new Font("sansserif", 1, 12));
        btnDeleteUser.setForeground(new Color(30, 122, 236));
        btnDeleteUser.setContentAreaFilled(false);
        btnDeleteUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDeleteUser.setBorder(null);
		add(btnDeleteUser, "cell 2 0");
		

	}

}
