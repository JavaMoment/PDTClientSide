package com.java.GUI.panels;

import java.awt.Color;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ModifyCallsEventPanel extends JPanel{
	
	private JTable tableEvents;
	private SheetStudentsPanel sheetStudentsPanel;

	public ModifyCallsEventPanel() {

		JLabel lblTitle = new JLabel(" Modificación de Convocatorias a Eventos");
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
						JFrame sheetStudents = new JFrame();
						sheetStudentsPanel = new SheetStudentsPanel();
						sheetStudents.getContentPane().add(sheetStudentsPanel);
						sheetStudents.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						sheetStudents.pack();
						sheetStudents.setVisible(true);
					}
				}
			}
		});

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 370, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);

		setLayout(groupLayout);

	}

}
