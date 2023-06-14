package com.java.GUI.panels;


import java.awt.Color;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.enums.Estado;
import com.enums.Modalidad;
import com.enums.TipoEvento;


public class RemoveEventPanel extends JPanel{

	private JTable tableEvents;
	private SheetEventPanel sheetEventPanel;
	private TipoEvento tipoEvento;
	private Modalidad modalidad;
	private Estado estado;

	public RemoveEventPanel(){
		
		JLabel lblTitle = new JLabel("BORRAR EVENTO");
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
		
		JComboBox cBoxTipoEvento = new JComboBox();
		cBoxTipoEvento.addItem(tipoEvento.DEFENSA_DE_PROYECTO);
		cBoxTipoEvento.addItem(tipoEvento.JORNADA_PRESENCIAL);
		cBoxTipoEvento.addItem(tipoEvento.EXAMEN);
		cBoxTipoEvento.addItem(tipoEvento.PRUEBA_FINAL);
		
		
		JComboBox cBoxModalidad = new JComboBox();
		cBoxModalidad.addItem(modalidad.PRESENCIAL);
		cBoxModalidad.addItem(modalidad.SEMIPRESENCIAL);
		cBoxModalidad.addItem(modalidad.VIRTUAL);

		JComboBox cBoxITR = new JComboBox();
		
		JComboBox cBoxEstado = new JComboBox();
		cBoxEstado.addItem(estado.FUTURO);
		cBoxEstado.addItem(estado.CORRIENTE);
		cBoxEstado.addItem(estado.FINALIZADO);

		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
						.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(cBoxTipoEvento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
							.addComponent(cBoxModalidad, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cBoxITR, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cBoxEstado, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
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
						.addComponent(btnListEvents, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
						.addComponent(cBoxEstado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cBoxITR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cBoxModalidad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cBoxTipoEvento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 370, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
			
				
		setLayout(groupLayout);
	
			
	}
}
