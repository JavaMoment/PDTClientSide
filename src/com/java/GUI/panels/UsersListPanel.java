package com.java.GUI.panels;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.RowFilter;
import javax.swing.SwingWorker;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.entities.Itr;
import com.entities.Usuario;
import com.java.GUI.utils.DefaultComboBox;
import com.java.GUI.utils.EntityTableModel;
import com.services.ItrBeanRemote;
import com.services.UsuarioBeanRemote;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UsersListPanel extends JPanel {

	private JTable table;
	private JTextField textFieldSearch;
	private HashMap<String, RowFilter<Object, Object>> filters = new HashMap<>();

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
		String[] transientCols = {"tipoUsuario"};
		
		List<Itr> itrs = itrBean.selectAll();
		
		table = new JTable();
		TableModel usersTableModel = new EntityTableModel<>(usersColNames, users, transientCols);
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
		JComboBox comboBoxUserType = new JComboBox(new DefaultComboBox(userTypes));
		lblUserType.setLabelFor(comboBoxUserType);
		add(comboBoxUserType, "cell 0 2,growx");
		
		JLabel lblItr = new JLabel("ITR:");
		add(lblItr, "flowy,cell 0 2");
		
		JComboBox comboBoxItr = new JComboBox(new DefaultComboBox(itrs.toArray()));
		lblItr.setLabelFor(comboBoxItr);
		add(comboBoxItr, "cell 0 2,growx");
		
		JLabel lblGeneration = new JLabel("Generación:");
		add(lblGeneration, "flowy,cell 0 2");
		
		JSpinner spinnerYearGen = new JSpinner();
		lblGeneration.setLabelFor(spinnerYearGen);
		spinnerYearGen.setValue(Year.now().getValue());
		add(spinnerYearGen, "cell 0 2,growx");
		
		String[] status = {"0: Inactivo", "1: Activo"};
		
		JLabel lblStatus = new JLabel("Estado:");
		add(lblStatus, "flowy,cell 0 2");
		JComboBox comboBoxStatus = new JComboBox(new DefaultComboBox(status));
		lblStatus.setLabelFor(comboBoxStatus);
		add(comboBoxStatus, "cell 0 2,growx");

		List<JComboBox> comboBoxes = List.of(comboBoxItr, comboBoxStatus, comboBoxUserType);
		
		comboBoxUserType.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(comboBoxes.stream().allMatch(box -> box.getSelectedItem() == null)) {
					filters.clear();
					TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(usersTableModel);
					sorter.setRowFilter(null);
					table.setRowSorter(sorter);
					return;
				}
				if(comboBoxUserType.getSelectedItem() == null & comboBoxes.stream().anyMatch(box -> box.getSelectedItem() != null)) {
					filters.remove("userType");
					RowFilter<Object, Object> filter = RowFilter.andFilter(filters.values());
					TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(usersTableModel);
					sorter.setRowFilter(filter);
					table.setRowSorter(sorter);
					return;
				}
				String selectedUserType = (String) comboBoxUserType.getSelectedItem();
				RowFilter<Object, Object> userTypeFilter = new RowFilter<Object, Object>() {
					public boolean include(Entry entry) {
						int userTypeColIdx = table.getColumn("TipoUsuario").getModelIndex();
						String userType = (String) entry.getValue(userTypeColIdx);
						return userType.toUpperCase().equals(selectedUserType.toUpperCase());
					}
				};
				filters.put("userType", userTypeFilter);
				RowFilter<Object, Object> filter = RowFilter.andFilter(filters.values());
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(usersTableModel);
				sorter.setRowFilter(filter);
				table.setRowSorter(sorter);
			}
		});
		
		comboBoxItr.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(comboBoxes.stream().allMatch(box -> box.getSelectedItem() == null)) {
					filters.clear();
					TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(usersTableModel);
					sorter.setRowFilter(null);
					table.setRowSorter(sorter);
					return;
				}
				if(comboBoxItr.getSelectedItem() == null & comboBoxes.stream().anyMatch(box -> box.getSelectedItem() != null)) {
					filters.remove("itr");
					RowFilter<Object, Object> filter = RowFilter.andFilter(filters.values());
					TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(usersTableModel);
					sorter.setRowFilter(filter);
					table.setRowSorter(sorter);
					return;
				}
				Itr selectedItr = (Itr) comboBoxItr.getSelectedItem();
				RowFilter<Object, Object> itrFilter = new RowFilter<Object, Object>() {
					public boolean include(Entry entry) {
						int itrColIdx = table.getColumn("Itr").getModelIndex();
						Itr itr = (Itr) entry.getValue(itrColIdx);
						String itrName = itr.getNombre();
						return itrName.toUpperCase().equals(selectedItr.getNombre().toUpperCase());
					}
				};
				filters.put("itr", itrFilter);
				RowFilter<Object, Object> filter = RowFilter.andFilter(filters.values());
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(usersTableModel);
				sorter.setRowFilter(filter);
				table.setRowSorter(sorter);
			}
		});
		
		comboBoxStatus.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(comboBoxes.stream().allMatch(box -> box.getSelectedItem() == null)) {
					filters.clear();
					TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(usersTableModel);
					sorter.setRowFilter(null);
					table.setRowSorter(sorter);
					return;
				}
				if(comboBoxStatus.getSelectedItem() == null & comboBoxes.stream().anyMatch(box -> box.getSelectedItem() != null)) {
					filters.remove("status");
					RowFilter<Object, Object> filter = RowFilter.andFilter(filters.values());
					TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(usersTableModel);
					sorter.setRowFilter(filter);
					table.setRowSorter(sorter);
					return;
				}
				String selectedStatus = (String) comboBoxStatus.getSelectedItem();
				RowFilter<Object, Object> statusFilter = new RowFilter<Object, Object>() {
					public boolean include(Entry entry) {
						int statusColIdx = table.getColumn("Activo").getModelIndex();
						byte status = (byte) entry.getValue(statusColIdx);
						return status == Byte.valueOf(selectedStatus.substring(0, 1));
					}
				};
				filters.put("status", statusFilter);
				RowFilter<Object, Object> filter = RowFilter.andFilter(filters.values());
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(usersTableModel);
				sorter.setRowFilter(filter);
				table.setRowSorter(sorter);
			}
		});
		
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
						int name1ColIdx = table.getColumn("Nombre1").getModelIndex();
						int name2ColIdx = table.getColumn("Nombre2").getModelIndex();
						String name1 = (String) entry.getValue(name1ColIdx);
						String name2 = entry.getValue(name2ColIdx) != null ? (String) entry.getValue(11) : "";
						return name1.toUpperCase().contains(searchText.toUpperCase()) || name2.toUpperCase().contains(searchText.toUpperCase());
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
				comboBoxes.stream().forEach(box -> box.setSelectedIndex(0));
			}
		});
		add(btnRefresh, "cell 1 0");
		
		JButton btnEditUser = new JButton("");
		btnEditUser.setIcon(new ImageIcon(UsersListPanel.class.getResource("/com/java/resources/images/edit-user.png")));
		btnEditUser.setFont(new Font("sansserif", 1, 12));
        btnEditUser.setForeground(new Color(30, 122, 236));
        btnEditUser.setContentAreaFilled(false);
        btnEditUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEditUser.setBorder(null);
        btnEditUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
					@Override
                    protected String doInBackground() throws Exception {

                        if(table.getSelectionModel().isSelectionEmpty()) {
                        	int resultCode = JOptionPane.showConfirmDialog(UsersListPanel.this, "Elija un nombre de usuario que desee modificar.", "¡Atención!", JOptionPane.OK_CANCEL_OPTION);
                        	if(resultCode == JOptionPane.CANCEL_OPTION) {
                        		return null;
                        	}
                        }
						
						while (table.getSelectedRow() < 0 || table.getSelectedColumn() < 0) {
                            Thread.sleep(100); // Wait para no sobreecargar cpu
                        }
						
						int selectedRow = table.getSelectedRow();
						int selectedColumn = table.getSelectedColumn();

						if(!table.getColumnName(selectedColumn).equals("NombreUsuario")) {
							table.clearSelection();
                        	return null;
                        }
                        
                        table.clearSelection();
                        String cellValue = table.getValueAt(selectedRow, selectedColumn).toString();
                        return cellValue;
					}
					@Override
                    protected void done() {
						try {
                    		String cellValue = get();
                    		if(cellValue == null) {
                    			JOptionPane.showMessageDialog(UsersListPanel.this, "Ups! La columna seleccionada no es nombre de usuario. Intente de nuevo,");
                    			return;
                    		}
                    		JOptionPane.showMessageDialog(UsersListPanel.this, "El nombre de usuario seleccionado es: " + cellValue);
                    	} catch (Exception e) {
                    		e.printStackTrace();
                    	}
					}
				};
				worker.execute();
			}
		});
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
