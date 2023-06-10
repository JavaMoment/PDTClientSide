package com.java.GUI.panels;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.*;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Color;


public class ListEventPanel extends JPanel {
	
	private JTable tableEvents;
	private SheetEventPanel sheetEventPanel;

	public ListEventPanel(){
		
		JLabel lblTitle = new JLabel("LISTA DE EVENTOS");
		lblTitle.setFont(new Font("Arial", Font.PLAIN, 30));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		

	    String[] columnNames = new String[] {"Titulo", "Fecha de inicio", "Fecha de fin", "Modalidad", "ITR", "Estado"};
        String[][] rowData = new String[][]{
        	{"IT BUILDER", "01/05/2023", "01/05/2023", "Presencial", "-", "Finalizado"},
        	{"CHARLA VME 1", "01/05/2023", "01/05/2023", "Presencial", "-", "Finalizado"},
        	{"CHARLA VME 5", "01/05/2023", "01/05/2023", "Presencial", "-", "Finalizado"}
        };
        
        @SuppressWarnings("serial")
		DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hacer que todas las celdas sean no editables
                return false;
            }
        };

		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnListEvents = new JButton("Filtrar");
		btnListEvents.setFont(new Font("Arial", Font.PLAIN, 13));
		
		JLabel lblAdvice = new JLabel("Estamos trabajando con la implementacion de filtors para mejorar su experincia");
		lblAdvice.setForeground(Color.DARK_GRAY);

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
	                        JFrame sheetEvent = new JFrame();
	                        sheetEventPanel = new SheetEventPanel();
	                        sheetEvent.getContentPane().add(sheetEventPanel);
	                        sheetEvent.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	                        sheetEvent.pack();
	                        sheetEvent.setVisible(true);
	                    }
	                }
	            }
	        });

		
	
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTitle, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(lblAdvice)
							.addGap(39)
							.addComponent(btnListEvents)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAdvice)
						.addComponent(btnListEvents, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 370, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
			
				
		setLayout(groupLayout);
	
		
		
	}
}