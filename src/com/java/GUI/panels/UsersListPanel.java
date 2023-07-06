package com.java.GUI.panels;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
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
	private final int ACTUAL_YEAR = Year.now().getValue();

	/**
	 * Create the panel.
	 */
	public UsersListPanel(UsuarioBeanRemote usuarioBean, ItrBeanRemote itrBean) {
		setLayout(new MigLayout("", "[89px][176.00px,grow][]", "[][][][][23px,grow][][][][][][][][][][][]"));
		
		JButton btnEditUser = new JButton("");
		btnEditUser.setToolTipText("Modificar usuario");
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
		            		int optionCode = JOptionPane.showConfirmDialog(UsersListPanel.this, "¿Está seguro que desea modificar al usuario: " + cellValue + "?", "¡Atención!", JOptionPane.YES_NO_OPTION);
		            		if(optionCode == JOptionPane.NO_OPTION) {
		            			return;
		            		}
		            		JOptionPane.showMessageDialog(UsersListPanel.this, "El nombre de usuario seleccionado es: " + cellValue);
		            		JTabbedPane jtp = (JTabbedPane) SwingUtilities.getAncestorOfClass(JTabbedPane.class, UsersListPanel.this);
		            		jtp.setSelectedIndex(2);
		            		jtp.revalidate();
		            		
		            		Usuario user = usuarioBean.selectUserBy(cellValue);
		            		
		            		UserDataModificationPanel usrDataModPanel = ((UserDataModificationPanel) ((JScrollPane) jtp.getSelectedComponent()).getViewport().getView());
		            		usrDataModPanel.populateComponents(user);
		            		
		            	} catch (Exception e) {
		            		e.printStackTrace();
		            	}
					}
				};
				worker.execute();
			}
		});
		add(btnEditUser, "cell 2 1");
		
		JButton btnDeleteUser = new JButton("");
		btnDeleteUser.setToolTipText("Dar de baja a usuario");
		btnDeleteUser.setIcon(new ImageIcon(UsersListPanel.class.getResource("/com/java/resources/images/delete-user.png")));
		btnDeleteUser.setFont(new Font("sansserif", 1, 12));
		btnDeleteUser.setForeground(new Color(30, 122, 236));
		btnDeleteUser.setContentAreaFilled(false);
		btnDeleteUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDeleteUser.setBorder(null);
		btnDeleteUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
					@Override
		            protected String doInBackground() throws Exception {

		                if(table.getSelectionModel().isSelectionEmpty()) {
		                	int resultCode = JOptionPane.showConfirmDialog(UsersListPanel.this, "Elija un nombre de usuario que desee dar de baja.", "¡Atención!", JOptionPane.OK_CANCEL_OPTION);
		                	if(resultCode == JOptionPane.CANCEL_OPTION) {
		                		return null;
		                	}
		                }
						
						while (table.getSelectedRow() < 0 || table.getSelectedColumn() < 0) {
		                    Thread.sleep(100); // Wait para no sobreecargar cpu
		                }
						
						int selectedRow = table.getSelectedRow();
						int selectedColumn = table.getSelectedColumn();
						int activoColumn = table.getColumn("Activo").getModelIndex();

						if(!table.getColumnName(selectedColumn).equals("NombreUsuario")) {
							table.clearSelection();
		                	return null;
		                }
						
						if(!table.getValueAt(selectedRow, activoColumn).equals((byte) 1)) {
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
		            			JOptionPane.showMessageDialog(UsersListPanel.this, "Ups! La columna seleccionada no es nombre de usuario o el usuario ya se encuentra dado de baja :D.\nIntente de nuevo");
		            			return;
		            		}
		            		if(cellValue.equals("0")) {
		            			JOptionPane.showMessageDialog(UsersListPanel.this, "¡Hey! El usuario ya se encuentra dado de baja :)");
		            			return;
		            		}
		            		int optionCode = JOptionPane.showConfirmDialog(UsersListPanel.this, "¿Está seguro que desea dar de baja al usuario: " + cellValue + "?", "¡Atención!", JOptionPane.YES_NO_OPTION);
		            		if(optionCode == JOptionPane.NO_OPTION) {
		            			return;
		            		}
		            		int exitCode = usuarioBean.logicalDeleteByUsername(cellValue);
		            		if(exitCode == 0) {
		            			List<Usuario> users = usuarioBean.selectAll();
		            			String[] usersColNames = Arrays.stream(usuarioBean.getColsNames())
		            					.filter(value -> !value.equals("estudiantes") && !value.equals("tutores") && !value.equals("analistas")
		            							&& !value.equals("contrasenia"))
		            					.toArray(String[]::new);
		            			String[] transientCols = {"tipoUsuario", "generacion"};
		            			TableModel usersTableModel = new EntityTableModel<>(usersColNames, users, transientCols);
		            			table.setModel(usersTableModel);
		            			JOptionPane.showMessageDialog(UsersListPanel.this, "¡Yep! El usuario ha sido dado de baja exitosamente", "Operación completada", JOptionPane.PLAIN_MESSAGE);
		            			return;
		            		}
		            		JOptionPane.showMessageDialog(UsersListPanel.this, "¡Oh no :(! Ocurrió un error mientras intentabamos dar de baja al usuario: " + cellValue + "\n Por favor, intente de nuevo más tarde");
		            	} catch (Exception e) {
		            		e.printStackTrace();
		            	}
					}
				};
				worker.execute();
			}
		});
		add(btnDeleteUser, "cell 2 2");
		
		JButton btnAddUser = new JButton("");
		btnAddUser.setToolTipText("Activar usuario");
		btnAddUser.setIcon(new ImageIcon(UsersListPanel.class.getResource("/com/java/resources/images/add-user.png")));
		btnAddUser.setFont(new Font("sansserif", 1, 12));
		btnAddUser.setForeground(new Color(30, 122, 236));
		btnAddUser.setContentAreaFilled(false);
		btnAddUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAddUser.setBorder(null);
		btnAddUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
					@Override
		            protected String doInBackground() throws Exception {

		                if(table.getSelectionModel().isSelectionEmpty()) {
		                	int resultCode = JOptionPane.showConfirmDialog(UsersListPanel.this, "Elija un nombre de usuario que desee dar de alta.", "¡Atención!", JOptionPane.OK_CANCEL_OPTION);
		                	if(resultCode == JOptionPane.CANCEL_OPTION) {
		                		return null;
		                	}
		                }
						
						while (table.getSelectedRow() < 0 || table.getSelectedColumn() < 0) {
		                    Thread.sleep(100); // Wait para no sobreecargar cpu
		                }
						
						int selectedRow = table.getSelectedRow();
						int selectedColumn = table.getSelectedColumn();
						int activoColumn = table.getColumn("Activo").getModelIndex();

						if(!table.getColumnName(selectedColumn).equals("NombreUsuario")) {
							table.clearSelection();
		                	return null;
		                }
						
						if(!table.getValueAt(selectedRow, activoColumn).equals((byte) 0)) {
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
		            			JOptionPane.showMessageDialog(UsersListPanel.this, "Ups! La columna seleccionada no es nombre de usuario o el usuario ya se encuentra dado de alta :D.\nIntente de nuevo");
		            			return;
		            		}
		            		if(cellValue.equals("1")) {
		            			JOptionPane.showMessageDialog(UsersListPanel.this, "¡Hey! El usuario ya se encuentra dado de alta :)");
		            			return;
		            		}
		            		int optionCode = JOptionPane.showConfirmDialog(UsersListPanel.this, "¿Está seguro que desea dar de alta al usuario: " + cellValue + "?", "¡Atención!", JOptionPane.YES_NO_OPTION);
		            		if(optionCode == JOptionPane.NO_OPTION) {
		            			return;
		            		}
		            		int exitCode = usuarioBean.activeUserBy(cellValue);
		            		if(exitCode == 0) {
		            			List<Usuario> users = usuarioBean.selectAll();
		            			String[] usersColNames = Arrays.stream(usuarioBean.getColsNames())
		            					.filter(value -> !value.equals("estudiantes") && !value.equals("tutores") && !value.equals("analistas")
		            							&& !value.equals("contrasenia"))
		            					.toArray(String[]::new);
		            			String[] transientCols = {"tipoUsuario", "generacion"};
		            			TableModel usersTableModel = new EntityTableModel<>(usersColNames, users, transientCols);
		            			table.setModel(usersTableModel);
		            			JOptionPane.showMessageDialog(UsersListPanel.this, "¡Yep! El usuario ha sido dado de alta exitosamente", "Operación completada", JOptionPane.PLAIN_MESSAGE);
		            			return;
		            		}
		            		JOptionPane.showMessageDialog(UsersListPanel.this, "¡Oh no :(! Ocurrió un error mientras intentabamos dar de alta al usuario: " + cellValue + "\n Por favor, intente de nuevo más tarde");
		            	} catch (Exception e) {
		            		e.printStackTrace();
		            	}
					}
				};
				worker.execute();
			}
		});
		add(btnAddUser, "cell 2 3");
		
		JLabel lblFilterTitle = new JLabel("Filtrar por:");
		add(lblFilterTitle, "cell 0 3");
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 1 1 1 15,grow");
		
		List<Usuario> users = usuarioBean.selectAll();
		String[] usersColNames = Arrays.stream(usuarioBean.getColsNames())
				.filter(value -> !value.equals("estudiantes") && !value.equals("tutores") && !value.equals("analistas")
						&& !value.equals("contrasenia"))
				.toArray(String[]::new);
		String[] transientCols = {"tipoUsuario", "generacion"};
		
		List<Itr> itrs = itrBean.selectAll();
		
		table = new JTable();
		TableModel usersTableModel = new EntityTableModel<>(usersColNames, users, transientCols);
		table.setModel(usersTableModel);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setOpaque(false);
		scrollPane.setViewportView(table);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		
		textFieldSearch = new JTextField();
		add(textFieldSearch, "flowx,cell 1 0,alignx left,growy");
		textFieldSearch.setColumns(10);

		JLabel lblUserType = new JLabel("Tipo de Usuario:");
		add(lblUserType, "flowy,cell 0 4");
		
		String[] userTypes = {"Analista", "Estudiante", "Tutor"};
		JComboBox comboBoxUserType = new JComboBox(new DefaultComboBox(userTypes));
		lblUserType.setLabelFor(comboBoxUserType);
		add(comboBoxUserType, "cell 0 4,growx");
		
		JLabel lblItr = new JLabel("ITR:");
		add(lblItr, "flowy,cell 0 4");
		
		JComboBox comboBoxItr = new JComboBox(new DefaultComboBox(itrs.toArray()));
		lblItr.setLabelFor(comboBoxItr);
		add(comboBoxItr, "cell 0 4,growx");
		
		String[] status = {"0: Inactivo", "1: Activo"};
		
		JLabel lblStatus = new JLabel("Estado:");
		add(lblStatus, "flowy,cell 0 4");
		JComboBox comboBoxStatus = new JComboBox(new DefaultComboBox(status));
		lblStatus.setLabelFor(comboBoxStatus);
		add(comboBoxStatus, "cell 0 4,growx");
		
		JLabel lblGeneration = new JLabel("Generación:");
		add(lblGeneration, "flowy,cell 0 4");
		
		JSpinner spinnerYearGen = new JSpinner();
		lblGeneration.setLabelFor(spinnerYearGen);
		spinnerYearGen.setValue(ACTUAL_YEAR);
		add(spinnerYearGen, "cell 0 4,growx");
		lblGeneration.setEnabled(false);
		lblGeneration.setVisible(false);
		spinnerYearGen.setEnabled(false);
		spinnerYearGen.setVisible(false);
		
		spinnerYearGen.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if(ACTUAL_YEAR < (Integer) spinnerYearGen.getValue()) {
					JOptionPane.showMessageDialog(UsersListPanel.this, "¡Hey! La generación elegida no puede ser mayor al año actual");
					spinnerYearGen.setValue(ACTUAL_YEAR);
					return;
				}
				String selectedGen = String.valueOf((Integer) spinnerYearGen.getValue());
				RowFilter<Object, Object> genFilter = new RowFilter<Object, Object>() {
					public boolean include(Entry entry) {
						int genColIdx = table.getColumn("Generacion").getModelIndex();
						String gen = (String) entry.getValue(genColIdx);
						return gen.equals(selectedGen);
					}
				};
				filters.put("gen", genFilter);
				RowFilter<Object, Object> filter = RowFilter.andFilter(filters.values());
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(usersTableModel);
				sorter.setRowFilter(filter);
				table.setRowSorter(sorter);
			}
			
		});

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
				if(comboBoxUserType.getSelectedItem().equals("Estudiante")) {
					lblGeneration.setEnabled(true);
					lblGeneration.setVisible(true);
					spinnerYearGen.setEnabled(true);
					spinnerYearGen.setVisible(true);
				} else {
					lblGeneration.setEnabled(false);
					lblGeneration.setVisible(false);
					spinnerYearGen.setEnabled(false);
					spinnerYearGen.setVisible(false);
					spinnerYearGen.setValue(ACTUAL_YEAR);
					filters.remove("gen");
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
		
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g.create();
        
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        
        Color skyBlue = new Color(135, 206, 235, 250);
        
        g2d.setPaint(skyBlue);
        g2d.fillRect(0, 0, panelWidth, panelHeight);
        
        g2d.dispose();
    }
}
