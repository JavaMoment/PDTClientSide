package com.java.GUI.panels;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.enums.TipoEvento;
import com.enums.Modalidad;
import com.enums.Estado;



import javax.swing.LayoutStyle.ComponentPlacement;

public class RemoveEventPanel extends JPanel {

	private JTable tableEvents;
	private SheetEventPanel sheetEventPanel;
	private TipoEvento tipoEvento;
	private Modalidad modalidad;
	private Estado estado;

	public RemoveEventPanel(){
		
		JLabel lblTitle = new JLabel("BORRAR EVENTO");
		lblTitle.setFont(new Font("Arial", Font.PLAIN, 30));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

		String[] columnNames = new String[] { "Titulo", "Fecha de inicio", "Fecha de fin", "Modalidad", "ITR",
				"Estado" };
		String[][] rowData = new String[][] {
				{ "IT BUILDER", "01/05/2023", "01/05/2023", "Presencial", "-", "Finalizado" },
				{ "CHARLA VME 1", "01/05/2023", "01/05/2023", "Presencial", "-", "Finalizado" },
				{ "CHARLA VME 5", "01/05/2023", "01/05/2023", "Presencial", "-", "Finalizado" } };

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

        JButton btnDeleteEvents = new JButton("Eliminar");
        btnDeleteEvents.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableEvents.getSelectedRow();
                if (selectedRow != -1) {
                    int confirm = JOptionPane.showConfirmDialog(null,
                            "¿Está seguro de que desea eliminar el evento seleccionado?", "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        DefaultTableModel model = (DefaultTableModel) tableEvents.getModel();
                        model.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(null, "El evento ha sido eliminado del sistema.",
                                "Evento eliminado", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un evento para eliminar.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnDeleteEvents.setFont(new Font("Arial", Font.PLAIN, 13));

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

                    // Verificar si hay una fila seleccionada
                    if (selectedRow != -1) {
                        // Obtener los datos de la fila seleccionada
                        Object[] rowData1 = new Object[columnNames.length];
                        for (int i = 0; i < columnNames.length; i++) {
                            rowData1[i] = tableEvents.getValueAt(selectedRow, i);
                        }

                        // Abrir el nuevo JFrame con los datos de la fila seleccionada
                        JFrame sheetEvent = new JFrame();
                        sheetEvent.getContentPane().add(new JLabel("EN ESTE ESPACIO SE MOSTRARA LA FICHA DEL EVENTO"));
                        sheetEvent.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        sheetEvent.pack();
                        sheetEvent.setVisible(true);
                    }
                }
            }
	       }
	     });

        JButton btnRemoveEvent = new JButton("Eliminar");
        btnRemoveEvent.setFont(new Font("Arial", Font.PLAIN, 13));

        btnRemoveEvent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableEvents.getSelectedRow();
                if (selectedRow != -1) {
                    int confirm = JOptionPane.showConfirmDialog(null,
                            "¿Está seguro de que desea eliminar el evento seleccionado?", "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        DefaultTableModel model = (DefaultTableModel) tableEvents.getModel();
                        model.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(null, "El evento ha sido eliminado del sistema.",
                                "Evento eliminado", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un evento para eliminar.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addContainerGap(480, Short.MAX_VALUE)
        			.addComponent(btnDeleteEvents)
        			.addContainerGap())
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGap(68)
        			.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
        			.addGap(35))
        		.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
        			.addContainerGap())
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(btnDeleteEvents, GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 370, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );

        setLayout(groupLayout);


	     // Abrir el nuevo JFrame con los datos de la fila seleccionada
	     JFrame sheetEvent = new JFrame();
	     sheetEventPanel = new SheetEventPanel();
	     sheetEvent.getContentPane().add(sheetEventPanel);
	     sheetEvent.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	     sheetEvent.pack();
	     sheetEvent.setVisible(true);
	                   
	            
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

		
		GroupLayout groupLayout1 = new GroupLayout(this);

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
		
			
				
		setLayout(groupLayout1);
	
			
		}
}