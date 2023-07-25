package com.java.GUI.panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import com.entities.Usuario;
import com.java.GUI.Main;
import com.java.GUI.panels.tutores.TeacherAnalystReportsPanel;
import com.java.GUI.utils.EntityTableModel;
import com.java.controller.BeansFactory;
import com.java.enums.Beans;
import com.services.EstudianteBeanRemote;
import com.services.UsuarioBeanRemote;

public class StudentSelectionPanel extends ContentPanel {


	private UsuarioBeanRemote usuarioBean = BeansFactory.getBean(Beans.Usuario, UsuarioBeanRemote.class);
	private EntityTableModel usersTableModel;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnCancel;
	private JPanel southWrapperPanel;
	private JButton btnSelect;

	public StudentSelectionPanel() {
		ContentHomePanel studentPanelContent = new ContentStudentSelectionPanel();
		
        setLayout(new BorderLayout());
        add(studentPanelContent, BorderLayout.NORTH);

        scrollPane = new JScrollPane();
        
        List<Usuario> users = usuarioBean.selectAllStudents();
        String[] usersColNames = Arrays.stream(usuarioBean.getColsNames())
        		.filter(value -> !value.equals("estudiantes") && !value.equals("tutores") && !value.equals("analistas")
        				&& !value.equals("contrasenia") && !value.equals("activo"))
        		.toArray(String[]::new);
        String[] transientCols = {"generacion"};
        usersTableModel = new EntityTableModel<>(usersColNames, users, transientCols);
        
        table = new JTable();
        table.setModel(usersTableModel);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setOpaque(false);
		scrollPane.setViewportView(table);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		
		add(scrollPane, BorderLayout.CENTER);
        
		btnSelect = new JButton("Seleccionar");
		btnSelect.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnSelect.setBackground(new Color(125, 229, 251));
        btnSelect.setForeground(new Color(40, 40, 40));
        btnSelect.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
					@Override
		            protected String doInBackground() throws Exception {

		                if(table.getSelectionModel().isSelectionEmpty()) {
		                	int resultCode = JOptionPane.showConfirmDialog(StudentSelectionPanel.this, "Elija un nombre de usuario que desee crear reportes.", "¡Atención!", JOptionPane.OK_CANCEL_OPTION);
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
		            			JOptionPane.showMessageDialog(StudentSelectionPanel.this, "Ups! La columna seleccionada no es nombre de usuario o el usuario ya se encuentra dado de alta :D.\nIntente de nuevo");
		            			return;
		            		}
		            		int optionCode = JOptionPane.showConfirmDialog(StudentSelectionPanel.this, "¿Está seguro que desea crear reportes para el usuario: " + cellValue + "?", "¡Atención!", JOptionPane.YES_NO_OPTION);
		            		if(optionCode == JOptionPane.NO_OPTION) {
		            			return;
		            		}
		            		Usuario user = usuarioBean.selectUserBy(cellValue);
		            		if(user != null) {
		            			Main main = (Main) SwingUtilities.getAncestorOfClass(JFrame.class, StudentSelectionPanel.this);
		            			ReportsPanel r = new TeacherAnalystReportsPanel(user);
		        		        main.setContentPane(r);
		        				main.revalidate();
		            			return;
		            		}
		            		JOptionPane.showMessageDialog(StudentSelectionPanel.this, "¡Oh no :(! Ocurrió un error mientras intentabamos buscar los datos para: " + cellValue + "\n Por favor, intente de nuevo más tarde");
		            	} catch (Exception e) {
		            		e.printStackTrace();
		            	}
					}
				};
				worker.execute();
			}
		});
		
		btnCancel = new JButton("  Cancelar  ");
		btnCancel.setBackground(new Color(244, 113, 116));
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		Main jtp = (Main) SwingUtilities.getAncestorOfClass(JFrame.class, StudentSelectionPanel.this);
        		jtp.initHome();
        		jtp.revalidate();
        	}
        });

        
        southWrapperPanel = new JPanel();
        southWrapperPanel.setLayout(new FlowLayout());
        southWrapperPanel.setBackground(new Color(135, 206, 235, 250));
        southWrapperPanel.add(btnSelect);
        southWrapperPanel.add(btnCancel);
        
        add(southWrapperPanel, BorderLayout.SOUTH);
        
	}
	
}
