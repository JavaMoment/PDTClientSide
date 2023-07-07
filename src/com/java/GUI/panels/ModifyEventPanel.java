package com.java.GUI.panels;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.entities.Evento;
import com.entities.Itr;
import com.entities.Tutor;
import com.enums.Modalidad;
import com.enums.Status;
import com.enums.TipoEvento;
import com.java.GUI.utils.EntityTableModel;
import com.services.EventoBeanRemote;
import com.services.ItrBeanRemote;
import com.services.TutorBeanRemote;
import com.toedter.calendar.JDateChooser;

public class ModifyEventPanel extends JPanel {

	private JTable tableEvents;
	private SheetModifyPanel sheetModifyPanel;
	private JTextField titleField;
	private JDateChooser startDateChooser;
	private JDateChooser endDateChooser;
	private JButton submitButton;
	private JButton cancelButton;
	private JComboBox comboBoxTipoEvento;
	private JComboBox comboBoxModalidad;
	private JComboBox comboBoxStatus;
	private JComboBox comboBoxItr;
	private JTextField textLocalizacion;
	private JTextField textTitulo;
	private Object[] itrArray;
	private String tituloViejo = null;

	public ModifyEventPanel(EventoBeanRemote eventoBean, ItrBeanRemote itrBean, TutorBeanRemote tutorBean) {
		itrArray = itrBean.selectAll().toArray();

		JLabel lblTitle = new JLabel("MODIFICACION DE EVENTOS");
		setLayout(null);

		JLabel lblModificacion = new JLabel("Modificación Evento");
		lblModificacion.setBounds(36, 7, 849, 37);
		lblModificacion.setHorizontalAlignment(SwingConstants.CENTER);
		lblModificacion.setFont(new Font("Tahoma", Font.PLAIN, 30));
		add(lblModificacion);

		JLabel lblEvento = new JLabel("Evento Seleccionado");
		lblEvento.setBounds(1011, 34, 277, 37);
		lblEvento.setFont(new Font("Tahoma", Font.PLAIN, 30));
		add(lblEvento);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(36, 48, 849, 500);
		add(scrollPane);

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

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int selectedRow = tableEvents.getSelectedRow();

				System.out.println(selectedRow);
				if (selectedRow != -1) {

					String fechaHoraFinal = (String) tableEvents.getValueAt(selectedRow, 0).toString();
					String fechaHoraInicio = (String) tableEvents.getValueAt(selectedRow, 1).toString();

					String itr = (String) tableEvents.getValueAt(selectedRow, 2).toString();
					String localizacion = (String) tableEvents.getValueAt(selectedRow, 3).toString();

					String modalidad = (String) tableEvents.getValueAt(selectedRow, 4).toString();
					String status = (String) tableEvents.getValueAt(selectedRow, 5).toString();
					String tipoEvento = (String) tableEvents.getValueAt(selectedRow, 6).toString();

					String titulo = (String) tableEvents.getValueAt(selectedRow, 7).toString();
					tituloViejo = titulo;
					// Define el formato de fecha esperado
					SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

					Date fechaFinal = null;
					try {
						// Analiza la cadena en un objeto Date
						fechaFinal = formatoFecha.parse(fechaHoraFinal);

						// Establece la fecha en el JDateChooser
						endDateChooser.setDate(fechaFinal);
					} catch (ParseException e2) {
						// Manejo de la excepción en caso de que el formato no sea válido
						e2.printStackTrace();
					}

					Date fechaInicial = null;
					try {
						// Analiza la cadena en un objeto Date
						fechaInicial = formatoFecha.parse(fechaHoraInicio);

						// Establece la fecha en el JDateChooser
						startDateChooser.setDate(fechaInicial);
					} catch (ParseException e2) {
						// Manejo de la excepción en caso de que el formato no sea válido
						e2.printStackTrace();
					}

					textTitulo.setText(titulo);
					textLocalizacion.setText(localizacion);

					for (Modalidad enumValue : Modalidad.values()) {

						if (enumValue.toString().equals(modalidad)) {
							comboBoxModalidad.setSelectedItem(enumValue);
							break;
						}

					}

					for (TipoEvento enumValue : TipoEvento.values()) {

						if (enumValue.toString().equals(tipoEvento)) {
							comboBoxTipoEvento.setSelectedItem(enumValue);
							break;
						}

					}

					for (Status enumValue : Status.values()) {

						if (enumValue.toString().equals(status)) {
							comboBoxStatus.setSelectedItem(enumValue);
							break;
						}

					}

					for (Object itrCom : itrArray) {
						if (String.valueOf(itrCom).equals(itr)) {
							comboBoxItr.setSelectedItem(itrCom);
							break;
						}
					}
				}
			}
		});

		scrollPane.setViewportView(tableEvents);

		JLabel titleLabel = new JLabel("Título del evento:");
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 10));

		titleField = new JTextField();
		titleField.setColumns(10);

		JLabel typeLabel = new JLabel("Tipo de evento:");
		typeLabel.setBounds(940, 441, 72, 13);
		add(typeLabel);

		JLabel startTimeLabel = new JLabel("Fecha y hora de inicio:");
		startTimeLabel.setBounds(940, 168, 104, 13);
		add(startTimeLabel);

		JLabel itrLabel = new JLabel("ITR:");
		itrLabel.setBounds(940, 223, 72, 13);
		add(itrLabel);

		JLabel locationLabel = new JLabel("Localización:");
		locationLabel.setBounds(940, 279, 72, 13);
		add(locationLabel);

		JLabel statusLabel_1 = new JLabel("Status:");
		statusLabel_1.setBounds(940, 388, 72, 13);
		add(statusLabel_1);

		JLabel lblEndTime = new JLabel("Fecha y hora de finalización:");
		lblEndTime.setBounds(940, 117, 161, 13);
		add(lblEndTime);

		JLabel lblModalidad = new JLabel("Modalidad");
		lblModalidad.setBounds(940, 335, 72, 13);
		add(lblModalidad);

		JLabel lblTitulo = new JLabel("Título ");
		lblTitulo.setBounds(940, 497, 95, 13);
		add(lblTitulo);

		comboBoxModalidad = new JComboBox();
		comboBoxModalidad.setModel(new DefaultComboBoxModel(Modalidad.values()));
		comboBoxModalidad.setBounds(1194, 330, 161, 21);
		add(comboBoxModalidad);

		comboBoxTipoEvento = new JComboBox();
		comboBoxTipoEvento.setModel(new DefaultComboBoxModel(TipoEvento.values()));
		comboBoxTipoEvento.setBounds(1194, 445, 161, 26);
		add(comboBoxTipoEvento);

		comboBoxStatus = new JComboBox();
		comboBoxStatus.setModel(new DefaultComboBoxModel(Status.values()));
		comboBoxStatus.setBounds(1194, 385, 161, 26);
		add(comboBoxStatus);

		comboBoxItr = new JComboBox(itrArray);
		comboBoxItr.setBounds(1194, 215, 161, 21);
		add(comboBoxItr);

		startDateChooser = new JDateChooser();
		startDateChooser.setBounds(1194, 160, 161, 21);
		add(startDateChooser);

		endDateChooser = new JDateChooser();
		endDateChooser.setBounds(1194, 105, 161, 21);
		add(endDateChooser);

		textLocalizacion = new JTextField();
		textLocalizacion.setBounds(1192, 270, 163, 26);
		add(textLocalizacion);
		textLocalizacion.setColumns(10);

		textTitulo = new JTextField();
		textTitulo.setColumns(10);
		textTitulo.setBounds(1192, 505, 163, 21);
		add(textTitulo);

		JButton btnEditar = new JButton("Editar");
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (validateData() && validateDates()) {
					String titulo = textTitulo.getText();
					Date fechaHoraInicio = startDateChooser.getDate();
					Date fechaHoraFinal = endDateChooser.getDate();
					Itr itr = (Itr) comboBoxItr.getSelectedItem();
					String localizacion = textLocalizacion.getText();
					TipoEvento tipoEvento = (TipoEvento) comboBoxTipoEvento.getSelectedItem();
					Modalidad modalidad = (Modalidad) comboBoxModalidad.getSelectedItem();
					Status status = (Status) comboBoxStatus.getSelectedItem();

					Evento newEvento = new Evento(titulo, tipoEvento, fechaHoraInicio, fechaHoraFinal, modalidad, itr,
							localizacion, status);
					newEvento.setIdEvento(eventoBean.buscarId(tituloViejo));

					try {
						int exitCode = eventoBean.update(newEvento);

						if (exitCode == 0) {
							tituloViejo = null;
							cargarLista(eventoBean);
							limpiarCampos();
							JOptionPane.showMessageDialog(null, "El evento ha sido correctamente creado.");

						} else {
							JOptionPane.showMessageDialog(null,
									"Ha ocurrido un error mientras se intentaba crear el evento.\nPor favor, intente de nuevo.");
						}

					} catch (Exception e2) {

						System.out.println(e2);
					}
				}

			}
		});
		btnEditar.setBounds(1072, 531, 85, 21);
		add(btnEditar);

	}

	public void cargarLista(EventoBeanRemote eventoBean) {
		List<Evento> eventos = eventoBean.selectAll();
		String[] eventosColNames = Arrays.stream(eventoBean.getColsNames())
				.filter(value -> !value.equals("idEvento") && !value.equals("analistas")).toArray(String[]::new);

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
	}

	private boolean validateData() {
		String errorMessage = "";

		if (textTitulo.getText().isEmpty()) {
			errorMessage += "- Título del evento\n";
		}

		if (startDateChooser.getDate() == null) {
			errorMessage += "- Fecha y hora de inicio\n";
		}

		if (endDateChooser.getDate() == null) {
			errorMessage += "- Fecha y hora de finalización\n";
		}

		if (!errorMessage.isEmpty()) {
			errorMessage = "Los siguientes campos son obligatorios y no pueden quedar vacíos:\n" + errorMessage;
			showErrorMessage(errorMessage);
			return false;
		}

		return true;
	}
	
	private void limpiarCampos() {
		textTitulo.setText(null);
		textLocalizacion.setText(null);
		startDateChooser.setDate(null);
		endDateChooser.setDate(null);
		comboBoxItr.setSelectedIndex(0);
		comboBoxModalidad.setSelectedIndex(0);
		comboBoxStatus.setSelectedIndex(0);
		comboBoxTipoEvento.setSelectedIndex(0);
	}
	
	private boolean validateDates() {
		Date startDate = startDateChooser.getDate();
		Date endDate = endDateChooser.getDate();

		if (startDate.after(endDate)) {
			showErrorMessage("La fecha de inicio debe ser anterior a la fecha de finalización.");
			return false;
		}
		
		return true;
	}
	
	private void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
