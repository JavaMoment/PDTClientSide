package com.java.GUI.panels;

import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.entities.Estado;
import com.entities.Evento;
import com.entities.Itr;
import com.entities.Modalidad;
import com.enums.TipoEvento;
import com.java.GUI.utils.DefaultComboBox;
import com.java.GUI.utils.EntityTableModel;
import com.java.controller.BeansFactory;
import com.java.enums.Beans;
import com.services.EstadoBeanRemote;
import com.services.EventoBeanRemote;
import com.services.ItrBeanRemote;
import com.services.ModalidadBeanRemote;
import com.services.TutorBeanRemote;

import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;

public class RegisterCallsEventPanel extends ContentPanel {

	private JTable eventoTable;
	private SheetEventPanel sheetEventPanel;
	private List<Evento> eventos;
	// Componentes GUI para la visualización de la lista de eventos y filtrado.
	private JTable tableEvents;
	private EntityTableModel jtableModel;
	private TipoEvento tipoEvento;
	private JComboBox comboBoxTipoEvento;
	private JComboBox comboBoxModalidad;
	private JComboBox comboBoxEstado;
	private JComboBox comboBoxItr;

	// Servicios remotos para obtener datos de las entidades relacionadas con los
	// eventos.
	private EventoBeanRemote eventoBean;
	private ItrBeanRemote itrBean;
	private TutorBeanRemote tutorBean;
	private ModalidadBeanRemote modalidadBean;
	private EstadoBeanRemote estadoBean;

	// Etiquetas para mostrar información en la GUI.
	private JLabel lblItr;
	private JLabel lblModalidad;

	// HashMap para almacenar los filtros aplicados en la tabla.
	private HashMap<String, RowFilter<Object, Object>> filters = new HashMap<>();

	public RegisterCallsEventPanel() {

		// Inicialización de los servicios remotos para acceder a los datos de las
		// entidades relacionadas con los eventos.
		eventoBean = BeansFactory.getBean(Beans.Evento, EventoBeanRemote.class);
		itrBean = BeansFactory.getBean(Beans.Itr, ItrBeanRemote.class);
		tutorBean = BeansFactory.getBean(Beans.Tutor, TutorBeanRemote.class);
		modalidadBean = BeansFactory.getBean(Beans.Modalidad, ModalidadBeanRemote.class);
		estadoBean = BeansFactory.getBean(Beans.Estado, EstadoBeanRemote.class);

		setLayout(new MigLayout("", "[118.00][78.00][133.00][125,center][125][62.00,center][119.00]", "[50.00][50.00][50.00,grow][23.00][50.00][50.00][50.00][50.00][50.00][50.00][50.00][50.00]"));

		JLabel lblTitle = new JLabel("CONVOCATORIA A EVENTOS");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 25));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 2 7 9,grow");

		eventos = eventoBean.selectAllByActive(1);

		String[] eventosColNames = Arrays.stream(eventoBean.getColsNames())
				.filter(value -> !value.equals("fechaHoraFinal") & !value.equals("tutorEventos"))
				.toArray(String[]::new);
    
		tableEvents = new JTable();
		TableModel listModel = new EntityTableModel<>(eventosColNames, eventos);
		tableEvents.setModel(listModel);
		scrollPane.setViewportView(tableEvents);

		// Permitir seleccioanar una sola fila del JTable para asi actualziar los datos
		// con los cambios en la tabla

		tableEvents.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()) {
					// Obtener la fila seleccionada
					int selectedRow = tableEvents.getSelectedRow();

					// Verificar si hay una fila seleccionada
					Object[] rowData = new Object[eventosColNames.length];
					if (selectedRow != -1) {
						// Obtener los datos de la fila seleccionada
						for (int i = 0; i < eventosColNames.length; i++) {
							rowData[i] = tableEvents.getValueAt(selectedRow, i);
						}

						Evento evento = eventos.get(selectedRow);

						// Abrir el nuevo JFrame con los datos de la fila seleccionada
						JFrame sheetEvent = new JFrame();
						SheetRegisterCalls eventoCalls = new SheetRegisterCalls(evento);
						sheetEvent.getContentPane().add(eventoCalls);
						sheetEvent.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

						sheetEvent.pack();
						sheetEvent.setVisible(true);
					}
				}
			}
		});

		// default combobox para que pueda seleccionar opcion nula

				List<Modalidad> modalidades = modalidadBean.selectAllByActive(1);
				JComboBox comboBoxModalidad = new JComboBox(new DefaultComboBox(modalidades.toArray()));

				List<Estado> estados = estadoBean.selectAllByActive(1);
				JComboBox comboBoxEstado = new JComboBox(new DefaultComboBox(estados.toArray()));

				JComboBox comboBoxTipoEvento = new JComboBox(new DefaultComboBoxModel(TipoEvento.values()));

				// Etiqueta para mostrar "ITR" en la interfaz.
				lblItr = new JLabel("ITR");

				// Obtiene la lista de Itr desde el servicio remoto.
				List<Itr> itrs = itrBean.selectAll();

				JLabel lblItr = new JLabel("ITR");
				add(lblItr, "flowy,cell 0 4");

				JComboBox comboBoxItr = new JComboBox(new DefaultComboBox(itrs.toArray()));
				lblItr.setLabelFor(comboBoxItr);
				add(comboBoxItr, "cell 0 4,growx");

				// Lista de cuadros combinados utilizados para facilitar la gestión de eventos
				// de filtrado.
				List<JComboBox> comboBoxes = List.of(comboBoxItr, comboBoxModalidad, comboBoxTipoEvento, comboBoxEstado);

				// Escuchador de eventos para el cuadro combinado de Itr.
				comboBoxItr.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (comboBoxes.stream().allMatch(box -> box.getSelectedItem() == null)) {
							// Si no se seleccionan elementos en ningún cuadro combinado, se eliminan todos
							// los filtros.
							filters.clear();
							TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(listModel);
							sorter.setRowFilter(null);
							tableEvents.setRowSorter(sorter);
							return;
						}
						if (comboBoxItr.getSelectedItem() == null
								& comboBoxes.stream().anyMatch(box -> box.getSelectedItem() != null)) {
							// Si no se selecciona Itr, pero otros cuadros combinados tienen elementos
							// seleccionados, se elimina el filtro de Itr.
							filters.remove("Itr");
							RowFilter<Object, Object> filter = RowFilter.andFilter(filters.values());
							TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(listModel);
							sorter.setRowFilter(filter);
							tableEvents.setRowSorter(sorter);
							return;
						}
						// Si se selecciona un Itr, se obtiene el objeto Itr seleccionado del JComboBox
						// comboBoxItr.
						Itr selectedItr = (Itr) comboBoxItr.getSelectedItem();

						// System.out.println(selectedItr);

						// Se crea un nuevo filtro para las filas basado en el Itr seleccionado.
						RowFilter<Object, Object> itrFilter = new RowFilter<Object, Object>() {
							public boolean include(Entry entry) {

								// Obtiene el valor de la columna "Itr" de la fila actual en el modelo de la
								// tabla.
								Itr itr = (Itr) entry.getValue(3);
								// Obtiene el nombre del Itr de la fila actual.
								String itrName = itr.getNombre();

								// Retorna true si el nombre del Itr de la fila coincide con el nombre del Itr
								// seleccionado (ignorando mayúsculas y minúsculas).
								return itrName.toUpperCase().equals(selectedItr.getNombre().toUpperCase());
							}
						};

						// Se agrega el filtro "itrFilter" al mapa "filters" con la clave "Itr".
						filters.put("Itr", itrFilter);

						// Se crea un filtro compuesto (andFilter) que combina todos los filtros en el
						// mapa "filters".
						RowFilter<Object, Object> filter = RowFilter.andFilter(filters.values());

						// Se crea un nuevo TableRowSorter utilizando el modelo de tabla
						// "eventosTableModel".
						TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(listModel);

						// Se establece el filtro en el TableRowSorter para aplicar los filtros a la
						// tabla.
						sorter.setRowFilter(filter);

						// Se asigna el TableRowSorter a la tabla "tableEvents" para que los filtros se
						// apliquen a la tabla.
						tableEvents.setRowSorter(sorter);
					}
				});

				// Escuchador de eventos para el cuadro combinado de modalidad (similar al
				// escuchador de Itr).
				comboBoxModalidad.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (comboBoxes.stream().allMatch(box -> box.getSelectedItem() == null)) {
							filters.clear();
							TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(listModel);
							sorter.setRowFilter(null);
							tableEvents.setRowSorter(sorter);
							return;
						}
						if (comboBoxModalidad.getSelectedItem() == null
								& comboBoxes.stream().anyMatch(box -> box.getSelectedItem() != null)) {
							filters.remove("Modalidad");
							RowFilter<Object, Object> filter = RowFilter.andFilter(filters.values());
							TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(listModel);
							sorter.setRowFilter(filter);
							tableEvents.setRowSorter(sorter);
							return;
						}
						Modalidad selectedModalidad = (Modalidad) comboBoxModalidad.getSelectedItem();
						RowFilter<Object, Object> modalidadFilter = new RowFilter<Object, Object>() {
							public boolean include(Entry entry) {
								int modalidadColIdx = tableEvents.getColumn("Modalidad").getModelIndex();
								Modalidad modalidad = (Modalidad) entry.getValue(modalidadColIdx);
								// Modalidad modalidad = (Modalidad) entry.getValue(6);
								String modalidadName = modalidad.getNombre();
								return modalidadName.toUpperCase().equals(selectedModalidad.getNombre().toUpperCase());
							}
						};
						filters.put("Modalidad", modalidadFilter);
						RowFilter<Object, Object> filter = RowFilter.andFilter(filters.values());
						TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(listModel);
						sorter.setRowFilter(filter);
						tableEvents.setRowSorter(sorter);

					}
				});

				// Etiqueta para mostrar "Modalidad" en la interfaz.
				lblModalidad = new JLabel("Modalidad");

				// filtro por estado

				comboBoxEstado.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (comboBoxes.stream().allMatch(box -> box.getSelectedItem() == null)) {
							filters.clear();
							TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(listModel);
							sorter.setRowFilter(null);
							tableEvents.setRowSorter(sorter);
							return;
						}
						if (comboBoxEstado.getSelectedItem() == null
								& comboBoxes.stream().anyMatch(box -> box.getSelectedItem() != null)) {
							filters.remove("Modalidad");
							RowFilter<Object, Object> filter = RowFilter.andFilter(filters.values());
							TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(listModel);
							sorter.setRowFilter(filter);
							tableEvents.setRowSorter(sorter);
							return;
						}
						Estado selectedEstado = (Estado) comboBoxEstado.getSelectedItem();
						RowFilter<Object, Object> estadoFilter = new RowFilter<Object, Object>() {
							public boolean include(Entry entry) {
								int estadoColIdx = tableEvents.getColumn("Estado").getModelIndex();
								Estado estado = (Estado) entry.getValue(estadoColIdx);
								String estadoName = estado.getNombre();
								return estadoName.toUpperCase().equals(selectedEstado.getNombre().toUpperCase());
							}
						};
						filters.put("Estado", estadoFilter);
						RowFilter<Object, Object> filter = RowFilter.andFilter(filters.values());
						TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(listModel);
						sorter.setRowFilter(filter);
						tableEvents.setRowSorter(sorter);
					}
				});

				// filtro por tipoEvento

				comboBoxTipoEvento.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if (comboBoxes.stream().allMatch(box -> box.getSelectedItem() == null)) {
							filters.clear();
							TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(listModel);
							sorter.setRowFilter(null);
							tableEvents.setRowSorter(sorter);
							return;
						}
						if (comboBoxTipoEvento.getSelectedItem() == null
								& comboBoxes.stream().anyMatch(box -> box.getSelectedItem() != null)) {
							filters.remove("TipoEvento");
							RowFilter<Object, Object> filter = RowFilter.andFilter(filters.values());
							TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(listModel);
							sorter.setRowFilter(filter);
							tableEvents.setRowSorter(sorter); // chequear
							return;
						}
						TipoEvento selectedTipoEvento = (TipoEvento) comboBoxTipoEvento.getSelectedItem();
						RowFilter<Object, Object> tipoEventoFilter = new RowFilter<Object, Object>() {
							public boolean include(Entry entry) {
								int tipoEventoColIdx = tableEvents.getColumn("TipoEvento").getModelIndex();
								TipoEvento tipoEvento = (TipoEvento) entry.getValue(tipoEventoColIdx);
								String tipoEventoName = tipoEvento.name();
								return tipoEventoName.toUpperCase().equals(selectedTipoEvento.name().toUpperCase());
							}
						};
						filters.put("TipoEvento", tipoEventoFilter);
						RowFilter<Object, Object> filter = RowFilter.andFilter(filters.values());
						TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(listModel);
						sorter.setRowFilter(filter);
						tableEvents.setRowSorter(sorter);
					}
				});

			
				

				JLabel lblEstado = new JLabel("Estado");

				JLabel lblTipoEvento = new JLabel("Tipo Evento");
				setLayout(new MigLayout("", "[182px][12px][219px][12px][222px][12px][222px]",
						"[50px][26px][16px][1px][21px][18px][25px][22px][25px][22px][39px][208px]"));
				add(scrollPane, "cell 0 11 7 1,grow");
				add(lblItr, "cell 2 8,alignx left,aligny center");
				add(comboBoxItr, "cell 0 8,growx,aligny top");
				add(comboBoxTipoEvento, "cell 0 2 1 3,alignx left,aligny bottom");
				add(comboBoxModalidad, "cell 0 6,growx,aligny top");
				add(comboBoxEstado, "cell 0 10,growx,aligny top");
				add(lblEstado, "cell 2 10,alignx left,aligny top");
				add(lblModalidad, "cell 2 6,alignx left,aligny center");
				add(lblTipoEvento, "cell 2 4,alignx left,aligny top");
				add(lblTitle, "cell 0 0 7 1,grow");
				

			}

	}

