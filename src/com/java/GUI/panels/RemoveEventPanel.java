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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class RemoveEventPanel extends JPanel {

	private JTable tableEvents;

	public RemoveEventPanel() {

		JLabel lblTitle = new JLabel("ELIMINAR EVENTO");
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
						sheetEvent.getContentPane().add(new JLabel("EN ESTE ESPACIO SE MOSTRARA LA FICHA DEL EVENTO"));
						sheetEvent.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						sheetEvent.pack();
						sheetEvent.setVisible(true);
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
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
				groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTitle, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 500,
										Short.MAX_VALUE)
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
										.addComponent(lblAdvice).addGap(39).addComponent(btnListEvents)))
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE).addGap(26)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblAdvice)
								.addComponent(btnListEvents, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
						.addGap(18)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 370, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		setLayout(groupLayout);

	}
}
