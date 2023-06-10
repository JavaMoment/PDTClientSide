package com.java.GUI.panels;

import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.*;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableModel;

import java.awt.Color;

public class ModifyEventPanel extends JPanel {
	
	private JTable tableEvents;
	private SheetModifyPanel sheetModifyPanel;

	public ModifyEventPanel(){
		
		JLabel lblTitle = new JLabel("MODIFICACION DE EVENTOS");
		lblTitle.setFont(new Font("Arial", Font.PLAIN, 30));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		

	    String[] columnNames = new String[] {"Titulo", "Fecha de inicio", "Fecha de fin", "Modalidad", "ITR", "Estado"};
        String[][] rowData = new String[][]{
        	{"IT BUILDER", "01/05/2023", "01/05/2023", "Presencial", "-", "Finalizado"},
        	{"CHARLA VME 1", "01/05/2023", "01/05/2023", "Presencial", "-", "Finalizado"},
        	{"CHARLA VME 5", "01/05/2023", "01/05/2023", "Presencial", "-", "Finalizado"}
        };
        
        DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hacer que todas las celdas sean no editables
                return false;
            }
        };

		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnModifyEvents = new JButton("Modificacion");
		btnModifyEvents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnModifyEvents.setFont(new Font("Arial", Font.PLAIN, 13));

		tableEvents = new JTable(model);
		scrollPane.setViewportView(tableEvents);
		

		

		tableEvents.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	            @Override
	            public void valueChanged(ListSelectionEvent event) {
	                if (!event.getValueIsAdjusting()) {
	                    // Obtener la fila seleccionada
	                    int selectedRow = tableEvents.getSelectedRow();
	                    
	                    // Verificar si hay una fila seleccionada
	                    if (selectedRow != -1) {
	                        // Obtener los datos de la fila seleccionada
	                        Object[] rowData = new Object[columnNames.length];
	                        for (int i = 0; i < columnNames.length; i++) {
	                            rowData[i] = tableEvents.getValueAt(selectedRow, i);
	                        }

	                        // Abrir el nuevo JFrame con los datos de la fila seleccionada
	                        JFrame sheetModify = new JFrame();
	                        sheetModifyPanel = new SheetModifyPanel();
	                        sheetModify.getContentPane().add(sheetModifyPanel);
	                        sheetModify.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	                        sheetModify.pack();
	                        sheetModify.setVisible(true);
	                    }
	                }
	            }
	        });
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
								.addContainerGap())
							.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE)
								.addGap(29)))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(btnModifyEvents)
							.addGap(20))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(16)
					.addComponent(btnModifyEvents, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 370, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
			
				
		setLayout(groupLayout);
	
		
		
	}

}
