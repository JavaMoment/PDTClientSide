package com.java.GUI.panels;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.toedter.calendar.JDateChooser;

public class SheetModifyPanel extends JPanel {
	private JTextField titleField;
	private JTextField typeField;
	private JDateChooser startDateChooser;
	private JDateChooser endDateChooser;
	private JTextField modalityField;
	private JTextField itrField;
	private JTextField locationField;
	private JButton submitButton;
	private JButton cancelButton;

	public SheetModifyPanel() {

		JLabel titleLabel = new JLabel("Título del evento:");
		titleLabel.setFont(new Font("Arial", Font.PLAIN, 10));

		titleField = new JTextField();
		titleField.setColumns(10);

		JLabel typeLabel = new JLabel("Tipo de evento:");

		typeField = new JTextField();
		typeField.setColumns(10);

		JLabel startTimeLabel = new JLabel("Fecha y hora de inicio:");

		startDateChooser = new JDateChooser();

		JLabel endTimeLabel = new JLabel("Fecha y hora de finalización:");

		endDateChooser = new JDateChooser();

		JLabel modalityLabel = new JLabel("Modalidad:");

		modalityField = new JTextField();
		modalityField.setColumns(10);

		JLabel itrLabel = new JLabel("ITR:");

		itrField = new JTextField();
		itrField.setColumns(10);

		JLabel locationLabel = new JLabel("Localización:");

		locationField = new JTextField();
		locationField.setColumns(10);

		JLabel tutorLabel = new JLabel("Tutor(es) encargado(s):");

		JList tutorList = new JList();

		JLabel title = new JLabel("Modificacion Evento");
		title.setFont(new Font("Arial", Font.PLAIN, 21));

		submitButton = new JButton("Aceptar");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validateData() && validateDates()) {
					registerEvent();
					showConfirmationMessage("El evento se registró correctamente.");
				}
			}
		});

		cancelButton = new JButton("Cancelar");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelEventRegistration();
				showConfirmationMessage("El registro del evento se canceló.");
			}
		});

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(tutorList, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
						.addComponent(tutorLabel, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
						.addComponent(locationField, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
						.addComponent(locationLabel, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
						.addComponent(itrField, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
						.addComponent(itrLabel, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
						.addComponent(modalityField, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
						.addComponent(modalityLabel, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
						.addComponent(endDateChooser, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
						.addComponent(endTimeLabel, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
						.addComponent(startDateChooser, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
						.addComponent(startTimeLabel, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
						.addComponent(typeField, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
						.addComponent(typeLabel, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
						.addComponent(titleField, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
						.addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE))
					.addGap(236))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(98)
					.addComponent(submitButton, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
					.addGap(68)
					.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(321, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(151)
					.addComponent(title, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(341, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(22)
					.addComponent(title)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(titleLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(titleField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(typeLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(typeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
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
					.addComponent(modalityField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(itrLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(itrField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(locationLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(locationField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tutorLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tutorList, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(submitButton, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addGap(72))
		);
		setLayout(groupLayout);
		typeLabel = new JLabel("Tipo de evento:");
	}

	private boolean validateData() {
		String errorMessage = "";

		if (titleField.getText().isEmpty()) {
			errorMessage += "- Título del evento\n";
		}

		if (typeField.getText().isEmpty()) {
			errorMessage += "- Tipo de evento\n";
		}

		if (startDateChooser.getDate() == null) {
			errorMessage += "- Fecha y hora de inicio\n";
		}

		if (endDateChooser.getDate() == null) {
			errorMessage += "- Fecha y hora de finalización\n";
		}

		if (modalityField.getText().isEmpty()) {
			errorMessage += "- Modalidad\n";
		}

		if (itrField.getText().isEmpty()) {
			errorMessage += "- ITR\n";
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

	private void registerEvent() {
		String title = titleField.getText();
		String type = typeField.getText();
		Date startDate = startDateChooser.getDate();
		Date endDate = endDateChooser.getDate();
		String modality = modalityField.getText();
		String itr = itrField.getText();
		String location = locationField.getText();

	}

	private void cancelEventRegistration() {
		// Limpia todos los campos del formulario
		titleField.setText("");
		typeField.setText("");
		startDateChooser.setDate(null);
		endDateChooser.setDate(null);
		modalityField.setText("");
		itrField.setText("");
		locationField.setText("");
		// tutorList.clearSelection(); LO COMENTO PQ DA ERROR ANDA A SABER PQ

		// Muestra un mensaje de cancelación
		showConfirmationMessage("El registro del evento se canceló.");
	}

	private void showConfirmationMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Confirmación", JOptionPane.INFORMATION_MESSAGE);
	}

	private void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
	}


}
