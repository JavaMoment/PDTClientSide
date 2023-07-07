package com.java.GUI.panels;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.entities.Evento;
import com.entities.Itr;
import com.entities.Tutor;
import com.enums.Estado;
import com.enums.Modalidad;
import com.enums.Status;
import com.enums.TipoEvento;
import com.java.GUI.utils.EntityTableModel;
import com.services.EventoBeanRemote;
import com.services.ItrBeanRemote;
import com.services.TutorBeanRemote;

public class ListEventPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tableEvents;
	private SheetEventPanel sheetEventPanel;
	private EntityTableModel jtableModel;
	private TipoEvento tipoEvento;
	private Modalidad modalidad;
	private Estado estado;
	private JComboBox comboBoxTipoEvento;
	private JComboBox comboBoxModalidad;
	private JComboBox comboBoxStatus;
	private JComboBox comboBoxItr;
	private JTextField textFieldSearch;
	private JTextField textField;
	private TutorBeanRemote tutorBean;

	public ListEventPanel(EventoBeanRemote eventoBean, ItrBeanRemote itrBean, TutorBeanRemote tutorBean) {

		JLabel lblTitle = new JLabel("LISTA DE EVENTOS");
		lblTitle.setFont(new Font("Arial", Font.PLAIN, 30));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

		textFieldSearch = new JTextField();
		add(textFieldSearch, "flowx,cell 1 0,alignx left,growy");
		textFieldSearch.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 1 1 1 13,grow");

		List<Evento> eventos = eventoBean.selectAll();
		String[] eventosColNames = Arrays.stream(eventoBean.getColsNames())
				.filter(value -> !value.equals("idEvento") && !value.equals("analistas")).toArray(String[]::new);

		tableEvents = new JTable();
		EntityTableModel<Evento> eventosTableModel = new EntityTableModel<>(eventosColNames, eventos);

		int contador = 0;
		// Recorremos los eventos que hay en la BD
		for (Evento evento : eventos) {
			long idTutor = 0;

			for (var eventoTutor : evento.getTutorEventos()) {
				if (eventoTutor.getId().getIdEvento() == evento.getIdEvento()) {
					idTutor = eventoTutor.getId().getIdTutor();
					break;
				}
			}

			if (idTutor != 0) {
				Tutor tutor = eventoBean.tutorDelEvento(idTutor);
				String nombreTutor = tutor.getUsuario().getNombre1();
				eventosTableModel.setValueAt(nombreTutor, contador, 8);
				contador++;
			} else {
				eventosTableModel.setValueAt("-", contador, 8);
				contador++;
			}

		}

		tableEvents.setModel(eventosTableModel);
		tableEvents.setColumnSelectionAllowed(true);
		tableEvents.setCellSelectionEnabled(true);
		scrollPane.setViewportView(tableEvents);

		JButton btnListEvents = new JButton("Filtrar");
		btnListEvents.setFont(new Font("Arial", Font.PLAIN, 13));

		comboBoxModalidad = new JComboBox();
		comboBoxModalidad.setModel(new DefaultComboBoxModel(Modalidad.values()));

		comboBoxTipoEvento = new JComboBox();
		comboBoxTipoEvento.setModel(new DefaultComboBoxModel(TipoEvento.values()));

		comboBoxStatus = new JComboBox();
		comboBoxStatus.setModel(new DefaultComboBoxModel(Status.values()));

		comboBoxItr = new JComboBox(itrBean.selectAll().toArray());

		JButton btnSearch = new JButton("Buscar");
		btnSearch.setToolTipText("Buscar por nombre");
		btnSearch.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				String searchText = textFieldSearch.getText();
				if (searchText.isBlank() || searchText.isEmpty()) {
					return;
				}
				RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
					public boolean include(Entry entry) {
						String name1 = (String) entry.getValue(10);
						String name2 = entry.getValue(11) != null ? (String) entry.getValue(11) : "";
						return name1.toUpperCase().contains(searchText.toUpperCase()) || name2.contains(searchText);
					}
				};
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(eventosTableModel);
				sorter.setRowFilter(filter);
				tableEvents.setRowSorter(sorter);
			}
		});

		textField = new JTextField();
		textField.setColumns(10);

		// para que me aparezca la ficha del evento

		tableEvents.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()) {
					// Obtener la fila seleccionada
					int selectedRow = tableEvents.getSelectedRow();

					// Verificar si hay una fila seleccionada
					if (selectedRow != -1) {
						// Obtener los datos de la fila seleccionada
						Object[] rowData = new Object[eventosColNames.length];
						for (int i = 0; i < eventosColNames.length; i++) {
							rowData[i] = tableEvents.getValueAt(selectedRow, i);
						}

						// Obtener los datos específicos del evento seleccionado
						String titulo = (String) rowData[7];
						Date fechaHoraInicio = (Date) rowData[1];
						Date fechaHoraFinal = (Date) rowData[0];
						Itr itr = (Itr) rowData[2];
						String localizacion = (String) rowData[3];
						TipoEvento tipoEvento = (TipoEvento) rowData[6];
						Modalidad modalidad = (Modalidad) rowData[4];
						Status status = (Status) rowData[5];

						String tutorSeleccionado = obtenerNombreTutor(); // Obtener la lista de tutores
																							// seleccionados

						SheetEventPanel sheetEventPanel = new SheetEventPanel(titulo, fechaHoraInicio, fechaHoraFinal,
								itr, localizacion, tipoEvento, modalidad, status);
						sheetEventPanel.setTutorSeleccionado(tutorSeleccionado);

						sheetEventPanel.setTitulo(titulo);
						sheetEventPanel.setFechaHoraInicio(fechaHoraInicio);
						sheetEventPanel.setFechaHoraFinal(fechaHoraFinal);
						sheetEventPanel.setItr(itr);
						sheetEventPanel.setLocalizacion(localizacion);
						sheetEventPanel.setTipoEvento(tipoEvento);
						sheetEventPanel.setModalidad(modalidad);
						sheetEventPanel.setStatus(status);

						// Abrir el nuevo JFrame con los datos de la fila seleccionada
						JFrame sheetList = new JFrame();
						sheetList.getContentPane().add(sheetEventPanel);
						sheetList.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						sheetList.pack();
						sheetList.setVisible(true);
					}
				}
			}

			private String obtenerNombreTutor() {
				

				int selectedRows = tableEvents.getSelectedRow();
				
				String nombreTutor = (String) tableEvents.getValueAt (selectedRows, 8);
				
				System.out.println(nombreTutor);
				
				return nombreTutor;
			}

		});

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(
						groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup().addContainerGap()
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
												.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 862,
														Short.MAX_VALUE)
												.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 862, Short.MAX_VALUE)
												.addGroup(groupLayout.createSequentialGroup()
														.addComponent(comboBoxTipoEvento, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addGap(61)
														.addComponent(textField, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addGap(18)
														.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 99,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED, 60,
																Short.MAX_VALUE)
														.addComponent(comboBoxModalidad, GroupLayout.PREFERRED_SIZE, 92,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(comboBoxItr, GroupLayout.PREFERRED_SIZE, 86,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(comboBoxStatus, GroupLayout.PREFERRED_SIZE, 76,
																GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(btnListEvents)))
										.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE).addGap(26)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnListEvents, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
								.addComponent(comboBoxStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBoxItr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBoxModalidad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBoxTipoEvento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSearch).addComponent(textField, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 370, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		setLayout(groupLayout);

	}

}
