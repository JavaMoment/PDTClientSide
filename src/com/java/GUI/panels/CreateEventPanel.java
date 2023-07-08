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
import com.services.EstadoBeanRemote;
import com.services.EventoBeanRemote;
import com.services.ItrBeanRemote;
import com.services.ModalidadBeanRemote;
import com.services.TutorBeanRemote;
import com.toedter.calendar.JDateChooser;

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

	public CreateEventPanel(EventoBeanRemote eventoBeanRemote, ItrBeanRemote itrBean, TutorBeanRemote tutorBean,
			ModalidadBeanRemote modalidadBean, EstadoBeanRemote estadoBean) {
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

					registerEvent(eventoBeanRemote, tutorBean, estadoBean, modalidadBean);
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

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(182)
					.addComponent(title, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(210, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(statusLabel_1, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(27, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(comboBoxItr, Alignment.LEADING, 0, 477, Short.MAX_VALUE)
						.addComponent(comboBoxTipoEvento, Alignment.LEADING, 0, 477, Short.MAX_VALUE)
						.addComponent(comboBoxModalidad, Alignment.LEADING, 0, 477, Short.MAX_VALUE)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(locationField, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
							.addComponent(locationLabel, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
							.addComponent(itrLabel, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
							.addComponent(modalityLabel, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
							.addComponent(endDateChooser, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
							.addComponent(endTimeLabel, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
							.addComponent(startDateChooser, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
							.addComponent(startTimeLabel, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
							.addComponent(typeLabel, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
							.addComponent(titleField, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
							.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)))
					.addGap(236))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnAgregar)
							.addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
							.addComponent(tableDestino, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE))
						.addComponent(comboBoxEstado, 0, 511, Short.MAX_VALUE)
						.addComponent(tutorLabel, GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
					.addContainerGap(202, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(115)
					.addComponent(submitButton, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
					.addGap(57)
					.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(113, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(22)
					.addComponent(title)
					.addGap(10)
					.addComponent(titleLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(titleField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(typeLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBoxTipoEvento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(startTimeLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(startDateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(endTimeLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(endDateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(modalityLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBoxModalidad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(itrLabel)
					.addGap(4)
					.addComponent(comboBoxItr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(locationLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(locationField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(statusLabel_1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(comboBoxEstado, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(14)
					.addComponent(tutorLabel)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
								.addComponent(tableDestino, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(20)
							.addComponent(btnAgregar)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(submitButton, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addGap(24))
		);
		setLayout(groupLayout);
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
