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

import com.entities.Estado;
import com.entities.Evento;
import com.entities.Itr;
import com.entities.Modalidad;
import com.entities.Tutor;
import com.enums.TipoEvento;
import com.java.GUI.utils.EntityTableModel;
import com.services.EstadoBeanRemote;
import com.services.EventoBeanRemote;
import com.services.ItrBeanRemote;
import com.services.ModalidadBeanRemote;
import com.services.TutorBeanRemote;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;

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
	private JComboBox comboBoxEstado;
	private JComboBox comboBoxItr;
	private JTextField textLocalizacion;
	private JTextField textTitulo;
	private Object[] itrArray;
	private Object[] modalidadArray;
	private Object[] estadoArray;

	private String tituloViejo = null;

	public ModifyEventPanel(EventoBeanRemote eventoBean, ItrBeanRemote itrBean, TutorBeanRemote tutorBean,
			ModalidadBeanRemote modalidadBean, EstadoBeanRemote estadoBean) {
		itrArray = itrBean.selectAll().toArray();
		estadoArray = estadoBean.selectAllByActive(1).toArray();
	//	estadoArray = estadoBean.selectAll().toArray();
		modalidadArray = modalidadBean.selectAllByActive(1).toArray();
	//	modalidadArray = modalidadBean.selectAll().toArray();

		JLabel lblTitle = new JLabel("MODIFICACION DE EVENTOS");
		setLayout(new MigLayout("", "[849px][104px][28px][85px][35px][163px]", "[37px][4px][23px][34px][25px][30px][21px][34px][21px][34px][26px][34px][21px][34px][26px][30px][30px][26px][29px][5px][21px]"));

		JLabel lblModificacion = new JLabel("Modificación Evento");
		lblModificacion.setHorizontalAlignment(SwingConstants.CENTER);
		lblModificacion.setFont(new Font("Tahoma", Font.PLAIN, 30));
		add(lblModificacion, "cell 0 0,growx,aligny top");

		JLabel lblEvento = new JLabel("Evento Seleccionado");
		lblEvento.setFont(new Font("Tahoma", Font.PLAIN, 30));
		add(lblEvento, "cell 1 0 5 3,alignx center,aligny bottom");

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 2 1 19,grow");

		List<Evento> eventos = eventoBean.selectAll();
		String[] eventosColNames = Arrays.stream(eventoBean.getColsNames())
				.filter(value -> !value.equals("idEvento") && !value.equals("analistas")).toArray(String[]::new);

		tableEvents = new JTable();
		tableEvents.getTableHeader().setReorderingAllowed(false);
		
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
				eventosTableModel.setValueAt(nombreTutor, contador, 9);
				contador++;
			} else {
				eventosTableModel.setValueAt("-", contador, 9);
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

					String fechaHoraFinal = (String) tableEvents.getValueAt(selectedRow, 2).toString();
					String fechaHoraInicio = (String) tableEvents.getValueAt(selectedRow, 3).toString();

					String itr = (String) tableEvents.getValueAt(selectedRow, 4).toString();
					String localizacion = (String) tableEvents.getValueAt(selectedRow, 5).toString();

					String modalidad = (String) tableEvents.getValueAt(selectedRow, 6).toString();
					String estado = (String) tableEvents.getValueAt(selectedRow, 2).toString();
					String tipoEvento = (String) tableEvents.getValueAt(selectedRow, 7).toString();

					String titulo = (String) tableEvents.getValueAt(selectedRow, 8).toString();
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

					for (TipoEvento enumValue : TipoEvento.values()) {

						if (enumValue.toString().equals(tipoEvento)) {
							comboBoxTipoEvento.setSelectedItem(enumValue);
							break;
						}

					}

					for (Object estadoCom : estadoArray) {
						if (String.valueOf(estadoCom).equals(estado)) {
							comboBoxEstado.setSelectedItem(estadoCom);
							break;
						}
					}

					for (Object modalidadCom : modalidadArray) {
						if (String.valueOf(modalidadCom).equals(modalidad)) {
							comboBoxModalidad.setSelectedItem(modalidadCom);
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
		add(typeLabel, "cell 1 16,alignx left,aligny top");

		JLabel startTimeLabel = new JLabel("Fecha y hora de inicio:");
		add(startTimeLabel, "cell 1 6,alignx left,aligny bottom");

		JLabel itrLabel = new JLabel("ITR:");
		add(itrLabel, "cell 1 8,growx,aligny bottom");

		JLabel locationLabel = new JLabel("Localización:");
		add(locationLabel, "cell 1 10,alignx left,aligny center");

		JLabel statusLabel_1 = new JLabel("Status:");
		add(statusLabel_1, "cell 1 14,growx,aligny top");

		JLabel lblEndTime = new JLabel("Fecha y hora de finalización:");
		add(lblEndTime, "cell 1 4 3 1,alignx left,aligny bottom");

		JLabel lblModalidad = new JLabel("Modalidad");
		add(lblModalidad, "cell 1 12,alignx left,aligny center");

		JLabel lblTitulo = new JLabel("Título ");
		add(lblTitulo, "cell 1 18,growx,aligny top");

		comboBoxModalidad = new JComboBox(modalidadArray);
		add(comboBoxModalidad, "cell 5 12,growx,aligny top");

		comboBoxTipoEvento = new JComboBox();
		comboBoxTipoEvento.setModel(new DefaultComboBoxModel(TipoEvento.values()));
		add(comboBoxTipoEvento, "cell 5 16,grow");

		comboBoxEstado = new JComboBox(estadoArray);
		add(comboBoxEstado, "cell 5 14,grow");

		comboBoxItr = new JComboBox(itrArray);
		add(comboBoxItr, "cell 5 8,growx,aligny top");

		startDateChooser = new JDateChooser();
		add(startDateChooser, "cell 5 6,grow");

		endDateChooser = new JDateChooser();
		add(endDateChooser, "cell 5 4,growx,aligny top");

		textLocalizacion = new JTextField();
		add(textLocalizacion, "cell 5 10,grow");
		textLocalizacion.setColumns(10);

		textTitulo = new JTextField();
		textTitulo.setColumns(10);
		add(textTitulo, "cell 5 18,growx,aligny bottom");

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
					Estado estado = (Estado) comboBoxEstado.getSelectedItem();

					Evento newEvento = new Evento(titulo, tipoEvento, fechaHoraInicio, fechaHoraFinal, modalidad, itr,
							localizacion, estado, 1);
					newEvento.setIdEvento(eventoBean.buscarId(tituloViejo));

					try {
						int exitCode = eventoBean.update(newEvento);

						if (exitCode == 0) {
							tituloViejo = null;
							cargarLista(eventoBean);
							limpiarCampos();
							JOptionPane.showMessageDialog(null, "El evento ha sido correctamente modificado.");

						} else {
							JOptionPane.showMessageDialog(null,
									"Ha ocurrido un error mientras se intentaba modificar el evento.\nPor favor, intente de nuevo.");
						}

					} catch (Exception e2) {

						System.out.println(e2);
					}
				}

			}
		});
		add(btnEditar, "cell 3 20,growx,aligny top");

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
				eventosTableModel.setValueAt(nombreTutor, contador, 9);
				contador++;
			} else {
				eventosTableModel.setValueAt("-", contador, 9);
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
		comboBoxEstado.setSelectedIndex(0);
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
