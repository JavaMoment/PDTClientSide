package com.java.GUI.panels;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.entities.Estado;
import com.entities.Evento;
import com.entities.Itr;
import com.entities.Modalidad;
import com.entities.Tutor;
import com.enums.TipoEvento;
import com.java.GUI.utils.EntityTableModel;
import com.java.controller.BeansFactory;
import com.java.enums.Beans;
import com.services.EstadoBeanRemote;
import com.services.EventoBeanRemote;
import com.services.ItrBeanRemote;
import com.services.ModalidadBeanRemote;
import com.services.TutorBeanRemote;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;

public class CreateEventPanel extends JPanel {

	private JTextField titleField;
	private JDateChooser startDateChooser;
	private JDateChooser endDateChooser;
	private JTextField locationField;
	private JButton submitButton;
	private JButton cancelButton;
	private JComboBox comboBoxTipoEvento;
	private JComboBox comboBoxModalidad;
	private JComboBox comboBoxEstado;
	private JComboBox comboBoxItr;
	private List<Tutor> tutor;
	private JTable tableOrigen;
	private JTable tableDestino;
	private DefaultTableModel tableModel;
	private ArrayList<String> tutoresElegidos = new ArrayList<String>();
	private List<Tutor> tutoresSeleccionados;
	private EventoBeanRemote eventoBean;
	private ItrBeanRemote itrBean;
	private TutorBeanRemote tutorBean;
	private ModalidadBeanRemote modalidadBean;
	private EstadoBeanRemote estadoBean;

	public CreateEventPanel() {
		
		eventoBean = BeansFactory.getBean(Beans.Evento, EventoBeanRemote.class);
		itrBean = BeansFactory.getBean(Beans.Itr, ItrBeanRemote.class);
		tutorBean = BeansFactory.getBean(Beans.Tutor, TutorBeanRemote.class);
		modalidadBean = BeansFactory.getBean(Beans.Modalidad, ModalidadBeanRemote.class);
		estadoBean = BeansFactory.getBean(Beans.Estado, EstadoBeanRemote.class);

		
		JLabel titleLabel = new JLabel("Título del evento:");
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 10));

		titleField = new JTextField();
		titleField.setColumns(10);

		JLabel typeLabel = new JLabel("Tipo de evento:");

		JLabel startTimeLabel = new JLabel("Fecha y hora de inicio:");

		startDateChooser = new JDateChooser();

		JLabel endTimeLabel = new JLabel("Fecha y hora de finalización:");

		endDateChooser = new JDateChooser();

		JLabel modalityLabel = new JLabel("Modalidad:");

		JLabel itrLabel = new JLabel("ITR:");

		JLabel locationLabel = new JLabel("Localización:");

		locationField = new JTextField();

		JLabel tutorLabel = new JLabel("Nombre de Usuario de Tutores");

		JLabel title = new JLabel("Crear Evento");
		title.setFont(new Font("Arial", Font.PLAIN, 21));

		submitButton = new JButton("Aceptar");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validateData() && validateDates()) {

					registerEvent(eventoBean, tutorBean, estadoBean, modalidadBean);
					showConfirmationMessage("El evento ha sido creado correctamente :)");
					titleField.setText("");
					startDateChooser.setDate(null);
					endDateChooser.setDate(null);
					locationField.setText("");
				}
			}
		});

		cancelButton = new JButton("Cancelar");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelEventRegistration();
			}
		});

		JLabel statusLabel_1 = new JLabel("Status:");

		comboBoxTipoEvento = new JComboBox();
		comboBoxTipoEvento.setModel(new DefaultComboBoxModel(TipoEvento.values()));

		comboBoxItr = new JComboBox(itrBean.selectAll().toArray());

		comboBoxModalidad = new JComboBox(modalidadBean.selectAll().toArray());

		comboBoxEstado = new JComboBox(estadoBean.selectAllByActive(1).toArray());

		// codigo para pasar de una tabla de tutores a otra

		tableOrigen = new JTable();

		List<Tutor> tutores = tutorBean.selectAll();
		String[] tutoresColNames = Arrays.stream(tutorBean.getColsNames()).filter(value -> value.equals("usuario"))
				.toArray(String[]::new);

		TableModel tutoresTableModel = new EntityTableModel<>(tutoresColNames, tutores);

		tableOrigen.setModel(tutoresTableModel);
		tableOrigen.setColumnSelectionAllowed(true);
		tableOrigen.setCellSelectionEnabled(true);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.add(tableOrigen);
		scrollPane.setViewportView(tableOrigen);

		// PASAR A TABLEDESTINO

		tableDestino = new JTable();
		JButton btnAgregar = new JButton("Agregar tutor");

		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				DefaultTableModel DTM = new DefaultTableModel(tutoresColNames, 0); // 0 es el header

				// selecciono los datos de la celda
				int selectedRow = tableOrigen.getSelectedRow();
				int selectedColumn = tableOrigen.getSelectedColumn();
				String cellValue = tableOrigen.getValueAt(selectedRow, selectedColumn).toString();

				// para agregar al arraylist

				tutoresElegidos.add(cellValue);

				// agregar a la tabla
				DTM.addRow(tutoresElegidos.toArray());

				// Obtener los nombres de usuario seleccionados en el ArrayList
				List<String> nombresUsuarios = new ArrayList<>(tutoresElegidos);

				// Realizar la consulta utilizando la cláusula WHERE IN
				List<Tutor> tutoresSeleccionados = tutorBean.selectByUsernames(nombresUsuarios);

				tableDestino.setModel(DTM);

			}
		});
		setLayout(new MigLayout("", "[191px][18px][14px][57px][24px][16px][191px]", "[25px][13px][19px][13px][19px][13px][19px][13px][19px][13px][21px][13px][21px][13px][19px][13px][21px][13px][63px][29px]"));
		add(title, "cell 0 0 5 1,alignx right,aligny top");
		add(statusLabel_1, "cell 0 15 7 1,growx,aligny top");
		add(comboBoxItr, "cell 0 12 7 1,growx,aligny top");
		add(comboBoxTipoEvento, "cell 0 4 7 1,growx,aligny top");
		add(comboBoxModalidad, "cell 0 10 7 1,growx,aligny top");
		add(locationField, "cell 0 14 7 1,growx,aligny top");
		add(locationLabel, "cell 0 13 7 1,growx,aligny top");
		add(itrLabel, "cell 0 11 7 1,growx,aligny top");
		add(modalityLabel, "cell 0 9 7 1,growx,aligny top");
		add(endDateChooser, "cell 0 8 7 1,growx,aligny top");
		add(endTimeLabel, "cell 0 7 7 1,growx,aligny top");
		add(startDateChooser, "cell 0 6 7 1,growx,aligny top");
		add(startTimeLabel, "cell 0 5 7 1,growx,aligny top");
		add(typeLabel, "cell 0 3 7 1,growx,aligny top");
		add(titleField, "cell 0 2 7 1,growx,aligny top");
		add(titleLabel, "cell 0 1 7 1,growx,aligny top");
		add(scrollPane, "cell 0 18,grow");
		add(btnAgregar, "cell 2 18 3 1,alignx left,aligny top");
		add(tableDestino, "cell 6 18,grow");
		add(comboBoxEstado, "cell 0 16 7 1,growx,aligny top");
		add(tutorLabel, "cell 0 17 7 1,growx,aligny top");
		add(submitButton, "cell 0 19 3 1,alignx right,growy");
		add(cancelButton, "cell 4 19 3 1,alignx left,growy");
		typeLabel = new JLabel("Tipo de evento:");
	}

	private boolean validateData() {
		String errorMessage = "";

		if (titleField.getText().isEmpty()) {
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

	private boolean validateDates() {
		Date startDate = startDateChooser.getDate();
		Date endDate = endDateChooser.getDate();

		if (startDate.after(endDate)) {
			showErrorMessage("La fecha de inicio debe ser anterior a la fecha de finalización.");
			return false;
		}

		return true;
	}

	private void registerEvent(EventoBeanRemote eventoBeanRemote, TutorBeanRemote tutorBean, EstadoBeanRemote estadoBean, ModalidadBeanRemote modalidadBean) {
		String titulo = titleField.getText();
		Date fechaHoraInicio = startDateChooser.getDate();
		Date fechaHoraFinal = endDateChooser.getDate();
		Itr itr = (Itr) comboBoxItr.getSelectedItem();
		String localizacion = locationField.getText();
		TipoEvento tipoEvento = (TipoEvento) comboBoxTipoEvento.getSelectedItem();
		Modalidad modalidad = (Modalidad) comboBoxModalidad.getSelectedItem();
		Estado estado = (Estado) comboBoxEstado.getSelectedItem(); 

		tutoresSeleccionados = tutorBean.selectByUsernames(tutoresElegidos);

		Evento newEvento = new Evento(titulo, tipoEvento, fechaHoraInicio, fechaHoraFinal, modalidad, itr, localizacion,
				estado, 1);

		try {
			var eventoCreado = eventoBeanRemote.createEvento(newEvento);
			System.out.println(eventoCreado.getIdEvento());
			if (eventoCreado != null) {
				for (var tutor : tutoresSeleccionados) {
					boolean respuesta = tutorBean.asignarEventoTutor(eventoCreado, tutor);
					System.out.println(respuesta);
				}
			}

		} catch (Exception e) {

			System.out.println(e);
		}
	}

	private void cancelEventRegistration() {
		// Limpia todos los campos del formulario
		titleField.setText("");
		startDateChooser.setDate(null);
		endDateChooser.setDate(null);
		locationField.setText("");

		// Muestra un mensaje de cancelación
		System.out.println("entra al cancelar");
		showConfirmationMessage("El registro del evento se canceló.");
	}

	private void showConfirmationMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Confirmación", JOptionPane.INFORMATION_MESSAGE);
	}

	private void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
