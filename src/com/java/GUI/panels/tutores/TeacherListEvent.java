package com.java.GUI.panels.tutores;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.entities.Estado;
import com.entities.Evento;
import com.entities.Itr;
import com.entities.Modalidad;
import com.entities.Tutor;
import com.entities.Usuario;
import com.enums.TipoEvento;
import com.java.GUI.panels.ContentPanel;
import com.java.GUI.panels.SheetEventPanel;
import com.java.GUI.utils.DefaultComboBox;
import com.java.GUI.utils.EntityTableModel;
import com.java.controller.BeansFactory;
import com.java.enums.Beans;
import com.services.EstadoBeanRemote;
import com.services.EventoBeanRemote;
import com.services.ItrBeanRemote;
import com.services.ModalidadBeanRemote;
import com.services.TutorBeanRemote;
import com.toedter.calendar.JCalendar;

import net.miginfocom.swing.MigLayout;

public class TeacherListEvent extends ContentPanel {

	// Identificador único de versión para el control de serialización (ignorar para
	// fines de comentario).
	private static final long serialVersionUID = 1L;

	// Componentes GUI para la visualización de la lista de eventos y filtrado.
	private JTable tableEvents;
	private SheetEventPanel sheetEventPanel;
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

	// Componentes para el filtrado de fechas.
	private JComboBox<String> comboBoxFiltroFecha;
	private JCalendar jCalendarInicio;
	private JCalendar jCalendarInicio_1;
	private JCalendar jCalendarFin;
	private JCalendar jCalendarFin_1;
	private JButton btnAplicarFiltroFecha;

	// Atributo para almacenar el usuario actual que está utilizando la interfaz
    private Usuario currentUser;
	

	public TeacherListEvent(Usuario user) {
	


		// Inicialización de los servicios remotos para acceder a los datos de las
		// entidades relacionadas con los eventos.
		eventoBean = BeansFactory.getBean(Beans.Evento, EventoBeanRemote.class);
		itrBean = BeansFactory.getBean(Beans.Itr, ItrBeanRemote.class);
		tutorBean = BeansFactory.getBean(Beans.Tutor, TutorBeanRemote.class);
		modalidadBean = BeansFactory.getBean(Beans.Modalidad, ModalidadBeanRemote.class);
		estadoBean = BeansFactory.getBean(Beans.Estado, EstadoBeanRemote.class);
		
		Tutor tutor = tutorBean.selectUserBy(user.getNombreUsuario());

		// Etiqueta para mostrar el título de la lista de eventos con formato y
		// alineación específicos.
		JLabel lblTitle = new JLabel("LISTA DE EVENTOS");
		lblTitle.setFont(new Font("Arial", Font.PLAIN, 30));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

		// Panel de desplazamiento que contiene la tabla de eventos.
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 1 1 1 13,grow");

		// Crear los componentes para el filtrado de fechas.
		comboBoxFiltroFecha = new JComboBox<>();
		comboBoxFiltroFecha.addItem("Exacta");
		comboBoxFiltroFecha.addItem("Desde");
		comboBoxFiltroFecha.addItem("Hasta");
		comboBoxFiltroFecha.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarFiltroFecha();
			}
		});

		jCalendarInicio = new JCalendar();
		jCalendarFin = new JCalendar();

		btnAplicarFiltroFecha = new JButton("Aplicar Filtro");
		btnAplicarFiltroFecha.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("entra en el boton");
				aplicarFiltroFecha();
			}
		});

		
		// Obtiene la lista de eventos desde el servicio remoto.
		List<Evento> eventos = eventoBean.selectEventosByTutor(tutor.getIdTutor());

//		// Filtrar los eventos para mostrar solo los asignados al tutor actual (currentUser).
//		if (currentUser.getTipoUsuario() == "Tutor"){
//		    eventos = eventos.stream()
//		        .filter(evento -> evento.getTutorEventos() != null && evento.getTutorEventos().contains(currentUser.getNombre1()))
//		        .collect(Collectors.toList());
//		}


	
		// Filtra las columnas de la tabla para excluir algunas como "idEvento" y
		// "analistas".
		String[] eventosColNames = Arrays.stream(eventoBean.getColsNames()).filter(
				value -> !value.equals("idEvento") && !value.equals("tutorEventos") && !value.equals("analistas"))
				.toArray(String[]::new);

		// Creación de la tabla de eventos con los datos obtenidos y los nombres de las
		// columnas filtradas.
		tableEvents = new JTable();
		tableEvents.getTableHeader().setReorderingAllowed(false);
		EntityTableModel<Evento> eventosTableModel = new EntityTableModel<>(eventosColNames, eventos);

		// Configura el modelo de datos en la tabla de eventos.
		tableEvents.setModel(eventosTableModel);
		tableEvents.setColumnSelectionAllowed(true);
		tableEvents.setCellSelectionEnabled(true);
		scrollPane.setViewportView(tableEvents);

		// Establece un escuchador de eventos para la tabla de eventos.
		tableEvents.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()) {
					// Obtiene la fila seleccionada en la tabla.
					int selectedRow = tableEvents.getSelectedRow();

					// Verifica si hay una fila seleccionada.
					if (selectedRow != -1) {
						// Obtiene los datos de la fila seleccionada.
						Object[] rowData = new Object[eventosColNames.length];
						for (int i = 0; i < eventosColNames.length; i++) {
							rowData[i] = tableEvents.getValueAt(selectedRow, i);
						}

						// Obtiene los datos específicos del evento seleccionado.
						String titulo = (String) rowData[8];
						Date fechaHoraInicio = (Date) rowData[3];
						Date fechaHoraFinal = (Date) rowData[2];
						Itr itr = (Itr) rowData[4];
						String localizacion = (String) rowData[5];
						TipoEvento tipoEvento = (TipoEvento) rowData[7];
						Modalidad modalidad = (Modalidad) rowData[6];
						Estado estado = (Estado) rowData[1];

						// Crea un nuevo panel con los datos del evento seleccionado.
						SheetEventPanel sheetEventPanel = new SheetEventPanel(titulo, fechaHoraInicio, fechaHoraFinal,
								itr, localizacion, tipoEvento, modalidad, estado);

						sheetEventPanel.setTitulo(titulo);
						sheetEventPanel.setFechaHoraInicio(fechaHoraInicio);
						sheetEventPanel.setFechaHoraFinal(fechaHoraFinal);
						sheetEventPanel.setItr(itr);
						sheetEventPanel.setLocalizacion(localizacion);
						sheetEventPanel.setTipoEvento(tipoEvento);
						sheetEventPanel.setModalidad(modalidad);
						sheetEventPanel.setEstado(estado);

						// Abre una nueva ventana con los datos del evento seleccionado.
						JFrame sheetList = new JFrame();
						sheetList.getContentPane().add(sheetEventPanel);
						sheetList.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						sheetList.pack();
						sheetList.setVisible(true);
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
					TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(eventosTableModel);
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
					TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(eventosTableModel);
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
						Itr itr = (Itr) entry.getValue(4);
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
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(eventosTableModel);

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
					TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(eventosTableModel);
					sorter.setRowFilter(null);
					tableEvents.setRowSorter(sorter);
					return;
				}
				if (comboBoxModalidad.getSelectedItem() == null
						& comboBoxes.stream().anyMatch(box -> box.getSelectedItem() != null)) {
					filters.remove("Modalidad");
					RowFilter<Object, Object> filter = RowFilter.andFilter(filters.values());
					TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(eventosTableModel);
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
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(eventosTableModel);
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
					TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(eventosTableModel);
					sorter.setRowFilter(null);
					tableEvents.setRowSorter(sorter);
					return;
				}
				if (comboBoxEstado.getSelectedItem() == null
						& comboBoxes.stream().anyMatch(box -> box.getSelectedItem() != null)) {
					filters.remove("Modalidad");
					RowFilter<Object, Object> filter = RowFilter.andFilter(filters.values());
					TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(eventosTableModel);
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
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(eventosTableModel);
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
					TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(eventosTableModel);
					sorter.setRowFilter(null);
					tableEvents.setRowSorter(sorter);
					return;
				}
				if (comboBoxTipoEvento.getSelectedItem() == null
						& comboBoxes.stream().anyMatch(box -> box.getSelectedItem() != null)) {
					filters.remove("TipoEvento");
					RowFilter<Object, Object> filter = RowFilter.andFilter(filters.values());
					TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(eventosTableModel);
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
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(eventosTableModel);
				sorter.setRowFilter(filter);
				tableEvents.setRowSorter(sorter);
			}
		});

		// Calendarios para seleccionar fechas de inicio y fin.
		jCalendarInicio_1 = new JCalendar();
		jCalendarFin_1 = new JCalendar();

		// Etiquetas para mostrar "Fecha de Inicio" y "Fecha de Fin" en la interfaz.
		JLabel lblFechaInicio = new JLabel("Fecha de Inicio:");
		JLabel lblFechaFin = new JLabel("Fecha de Fin:");

		// Botón para aplicar el filtro de fechas.
		JButton btnAplicarFiltroFecha = new JButton("Aplicar Filtro");
		btnAplicarFiltroFecha.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("entra en el boton");
				aplicarFiltroFecha();
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
		add(lblTitle, "cell 0 0 3 1,grow");
		add(jCalendarInicio_1, "cell 4 4 1 7,alignx left,aligny bottom");
		add(lblFechaInicio, "cell 4 2,alignx center,aligny top");
		add(comboBoxFiltroFecha, "cell 4 1,growx,aligny bottom");
		add(jCalendarFin_1, "cell 6 4 1 7,alignx left,aligny bottom");
		add(lblFechaFin, "cell 6 2,alignx left,aligny top");
		add(btnAplicarFiltroFecha, "cell 6 1,growx,aligny top");

	}




	private void actualizarFiltroFecha() {
		String opcionSeleccionada = (String) comboBoxFiltroFecha.getSelectedItem();

		if (opcionSeleccionada.equals("Exacta")) {
			// no hay q realizar cambios en los JCalendar,
			// ya que la fecha seleccionada por el usuario se utilizará directamente
			// en el método aplicarFiltroFecha().
		} else if (opcionSeleccionada.equals("Desde")) {
			// Configurar JCalendarInicio para permitir seleccionar cualquier fecha.
			jCalendarInicio_1.setSelectableDateRange(null, null);

			// Establecer la fecha actual en JCalendarFin.
			jCalendarFin_1.setDate(new Date());

			// Establecer el rango de fechas seleccionables en JCalendarFin.
			// Ahora se puede seleccionar cualquier fecha posterior o igual a la fecha
			// actual.
			jCalendarFin_1.setSelectableDateRange(new Date(), null);
		} else if (opcionSeleccionada.equals("Hasta")) {
			// Establecer la fecha actual en JCalendarInicio.
			jCalendarInicio_1.setDate(new Date());

			// Establecer el rango de fechas seleccionables en JCalendarInicio.
			// Ahora se puede seleccionar cualquier fecha anterior o igual a la fecha
			// actual.
			jCalendarInicio_1.setSelectableDateRange(null, new Date());

			// Configurar JCalendarFin para permitir seleccionar cualquier fecha.
			jCalendarFin_1.setSelectableDateRange(null, null);
		}
	}

	// Método para aplicar el filtro de fechas
	private void aplicarFiltroFecha() {
		Date fechaInicio = jCalendarInicio_1.getDate();
		Date fechaFin = jCalendarFin_1.getDate();
		System.out.println(fechaFin);
		System.out.println(fechaInicio);

		String opcionSeleccionada = (String) comboBoxFiltroFecha.getSelectedItem();

		// Obtener el modelo de la tabla de eventos
		EntityTableModel<Evento> eventosTableModel = (EntityTableModel<Evento>) tableEvents.getModel();

		// Crear un nuevo TableRowSorter utilizando el modelo de tabla
		// "eventosTableModel".
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(eventosTableModel);

		// Crear el filtro para las fechas seleccionadas.
		RowFilter<Object, Object> fechaFilter = new RowFilter<Object, Object>() {
			public boolean include(Entry entry) {
				int fechaInicioColIdx = tableEvents.getColumn("FechaHoraInicio").getModelIndex();
				int fechaFinColIdx = tableEvents.getColumn("FechaHoraFinal").getModelIndex();
				Date fechaInicioEvento = (Date) entry.getValue(fechaInicioColIdx);
				Date fechaFinEvento = (Date) entry.getValue(fechaFinColIdx);

				// Lógica para aplicar el filtro según la opción seleccionada.
				if (opcionSeleccionada.equals("Exacta")) {
					return fechaInicioEvento.equals(fechaInicio) && fechaFinEvento.equals(fechaFin);
				} else if (opcionSeleccionada.equals("Desde")) {
					return fechaInicioEvento.compareTo(fechaInicio) >= 0;
				} else if (opcionSeleccionada.equals("Hasta")) {
					return fechaFinEvento.compareTo(fechaFin) <= 0;
				}

				return true; // Si no se selecciona ninguna opción, mostrar todos los eventos.
			}
		};

		// Establecer el filtro en el TableRowSorter.
		sorter.setRowFilter(fechaFilter);

		// Asignar el TableRowSorter a la tabla "tableEvents" para que se aplique el
		// filtro de fechas.
		tableEvents.setRowSorter(sorter);
	}

	
	

}
