package com.java.GUI.panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingWorker;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.entities.Itr;
import com.java.GUI.utils.EntityTableModel;
import com.java.controller.BeansFactory;
import com.java.enums.Beans;
import com.services.ItrBeanRemote;

import net.miginfocom.swing.MigLayout;

public class ItrListManteinancePanel extends ContentPanel {

	private ItrBeanRemote itrBean = BeansFactory.getBean(Beans.Itr, ItrBeanRemote.class);
	private JTable table;
	private JTextField textFieldSearch;
	private EntityTableModel<Itr> itrTableModel;

	/**
	 * Create the panel.
	 */
	public ItrListManteinancePanel() {
		setLayout(new MigLayout("", "[89px][415.00px,grow][]", "[][][][][23px,grow][][][][][][][][][][][]"));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 1 1 2 15,grow");
		
		table = new JTable();
		fireUpdateTableContentFromDB();
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setOpaque(false);
		scrollPane.setViewportView(table);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		
		textFieldSearch = new JTextField();
		add(textFieldSearch, "flowx,cell 1 0,alignx left,growy");
		textFieldSearch.setColumns(10);
		
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
						int nameColIdx = table.getColumn("Nombre").getModelIndex();
						String name = (String) entry.getValue(nameColIdx);
						return name.toUpperCase().contains(searchText.toUpperCase());
					}
				};
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(itrTableModel);
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
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(itrTableModel);
				sorter.setRowFilter(null);
				table.setRowSorter(sorter);
				textFieldSearch.setText("");
			}
		});
		add(btnRefresh, "cell 1 0");

		JButton btnEditItr = new JButton("");
		btnEditItr.setToolTipText("Modificar ITR");
		btnEditItr.setIcon(new ImageIcon(UsersListPanel.class.getResource("/com/java/resources/images/edit-list.png")));
		btnEditItr.setFont(new Font("sansserif", 1, 12));
		btnEditItr.setForeground(new Color(30, 122, 236));
		btnEditItr.setContentAreaFilled(false);
		btnEditItr.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnEditItr.setBorder(null);
		btnEditItr.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
					@Override
		            protected String doInBackground() throws Exception {

		                if(table.getSelectionModel().isSelectionEmpty()) {
		                	int resultCode = JOptionPane.showConfirmDialog(ItrListManteinancePanel.this, "Elija un nombre de ITR que desee modificar.", "¡Atención!", JOptionPane.OK_CANCEL_OPTION);
		                	if(resultCode == JOptionPane.CANCEL_OPTION) {
		                		return null;
		                	}
		                }
						
						while (table.getSelectedRow() < 0 || table.getSelectedColumn() < 0) {
		                    Thread.sleep(100); // Wait para no sobreecargar cpu
		                }
						
						int selectedRow = table.getSelectedRow();
						int selectedColumn = table.getSelectedColumn();

						if(!table.getColumnName(selectedColumn).equals("Nombre")) {
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
		            			JOptionPane.showMessageDialog(ItrListManteinancePanel.this, "Ups! La columna seleccionada no es nombre de ITR. Intente de nuevo,");
		            			return;
		            		}
		            		int optionCode = JOptionPane.showConfirmDialog(ItrListManteinancePanel.this, "¿Está seguro que desea modificar al ITR: " + cellValue + "?", "¡Atención!", JOptionPane.YES_NO_OPTION);
		            		if(optionCode == JOptionPane.NO_OPTION) {
		            			return;
		            		}
		            		String newName = JOptionPane.showInputDialog(ItrListManteinancePanel.this, "Ingrese el nuevo nombre para el ITR:");
		            		
		            		if(newName == null) {
		            			return;
		            		}
		            		
		            		Itr itr = itrBean.selectItrBy(cellValue);
		            		
		            		itr.setNombre(newName);
		            		
		            		int exitCode = itrBean.update(itr);
		            		
		            		if(exitCode == 0) {
		            			fireUpdateTableContentFromDB();
		            			JOptionPane.showMessageDialog(ItrListManteinancePanel.this, "¡Yep! El ITR ha sido dado de alta exitosamente", "Operación completada", JOptionPane.PLAIN_MESSAGE);
		            			return;
		            		}
		            		JOptionPane.showMessageDialog(ItrListManteinancePanel.this, "¡Oh no :(! Ocurrió un error mientras intentabamos dar de alta al ITR: " + cellValue + "\n Por favor, intente de nuevo más tarde");
		            		
		            	} catch (Exception e) {
		            		e.printStackTrace();
		            	}
					}
				};
				worker.execute();
			}
		});

		add(btnEditItr, "cell 2 0");

		JButton btnDeleteItr = new JButton("");
		btnDeleteItr.setToolTipText("Dar de baja a un ITR");
		btnDeleteItr.setIcon(new ImageIcon(UsersListPanel.class.getResource("/com/java/resources/images/remove-list.png")));
		btnDeleteItr.setFont(new Font("sansserif", 1, 12));
		btnDeleteItr.setForeground(new Color(30, 122, 236));
		btnDeleteItr.setContentAreaFilled(false);
		btnDeleteItr.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDeleteItr.setBorder(null);
		btnDeleteItr.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
					@Override
		            protected String doInBackground() throws Exception {

		                if(table.getSelectionModel().isSelectionEmpty()) {
		                	int resultCode = JOptionPane.showConfirmDialog(ItrListManteinancePanel.this, "Elija un nombre de ITR que desee modificar.", "¡Atención!", JOptionPane.OK_CANCEL_OPTION);
		                	if(resultCode == JOptionPane.CANCEL_OPTION) {
		                		return null;
		                	}
		                }
						
						while (table.getSelectedRow() < 0 || table.getSelectedColumn() < 0) {
		                    Thread.sleep(100); // Wait para no sobreecargar cpu
		                }
						
						int selectedRow = table.getSelectedRow();
						int selectedColumn = table.getSelectedColumn();

						if(!table.getColumnName(selectedColumn).equals("Nombre")) {
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
		            			JOptionPane.showMessageDialog(ItrListManteinancePanel.this, "Ups! La columna seleccionada no es nombre de ITR. Intente de nuevo,");
		            			return;
		            		}
		            		int optionCode = JOptionPane.showConfirmDialog(ItrListManteinancePanel.this, "¿Está seguro que desea dar de baja al ITR: " + cellValue + "?", "¡Atención!", JOptionPane.YES_NO_OPTION);
		            		if(optionCode == JOptionPane.NO_OPTION) {
		            			return;
		            		}
		            		
		            		int exitCode = itrBean.logicalDeleteBy(cellValue);
		            		
		            		if(exitCode == 0) {
		            			fireUpdateTableContentFromDB();
		            			JOptionPane.showMessageDialog(ItrListManteinancePanel.this, "¡Yep! El ITR ha sido dado de baja exitosamente", "Operación completada", JOptionPane.PLAIN_MESSAGE);
		            			return;
		            		}
		            		JOptionPane.showMessageDialog(ItrListManteinancePanel.this, "¡Oh no :(! Ocurrió un error mientras intentabamos dar de baja al ITR: " + cellValue + "\n Por favor, intente de nuevo más tarde");
		            		
		            	} catch (Exception e) {
		            		e.printStackTrace();
		            	}
					}
				};
				worker.execute();
			}
		});
		add(btnDeleteItr, "cell 2 0");
		
		JButton btnAddItr = new JButton("");
		btnAddItr.setToolTipText("Dar de alta a un ITR");
		btnAddItr.setIcon(new ImageIcon(UsersListPanel.class.getResource("/com/java/resources/images/add-file.png")));
		btnAddItr.setFont(new Font("sansserif", 1, 12));
		btnAddItr.setForeground(new Color(30, 122, 236));
		btnAddItr.setContentAreaFilled(false);
		btnAddItr.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAddItr.setBorder(null);
		btnAddItr.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String newItr = JOptionPane.showInputDialog(ItrListManteinancePanel.this, "Ingrese el nombre del nuevo ITR:");

				Itr itrExists = itrBean.selectItrBy(newItr);
				
				if(itrExists != null) {
					int choiceCode = JOptionPane.showConfirmDialog(ItrListManteinancePanel.this, "¡Heeey! El ITR " + newItr + " ya se encuentra ingresado; intente con otro nombre o:\n\t\t¿Desea activarlo?");
					if(choiceCode == JOptionPane.YES_OPTION) {
						int exitCode = itrBean.activeItrBy(newItr);
						if(exitCode == 0) {
		        			fireUpdateTableContentFromDB();
		        			JOptionPane.showMessageDialog(ItrListManteinancePanel.this, "¡Yep! El ITR ha sido dado de alta exitosamente", "Operación completada", JOptionPane.PLAIN_MESSAGE);
		        			return;
		        		} else {
		        			JOptionPane.showMessageDialog(ItrListManteinancePanel.this, "¡Oh no :(! Ocurrió un error mientras intentabamos dar de alta al ITR: " + newItr + "\n Por favor, intente de nuevo más tarde");
		        			return;
		        		}
					}
					return;
				}
				
				Itr itr = new Itr(newItr);
				
				int exitCode = itrBean.create(itr);
				
				if(exitCode == 0) {
        			JOptionPane.showMessageDialog(ItrListManteinancePanel.this, "¡Yep! El ITR ha sido dado de alta exitosamente", "Operación completada", JOptionPane.PLAIN_MESSAGE);
        			fireUpdateTableContentFromDB();
        			return;
        		}
        		JOptionPane.showMessageDialog(ItrListManteinancePanel.this, "¡Oh no :(! Ocurrió un error mientras intentabamos dar de alta al ITR: " + newItr + "\n Por favor, intente de nuevo más tarde");
			}
		});
		add(btnAddItr, "cell 2 0");
	}

	public void fireUpdateTableContentFromDB() {
		List<Itr> itrs = itrBean.selectAllActives();
		String[] itrColNames = Arrays.stream(itrBean.getColsNames())
				.filter(value -> value.contains("nombre"))
				.toArray(String[]::new);
		itrTableModel = new EntityTableModel<>(itrColNames, itrs);
		table.setModel(itrTableModel);
	} 
}
