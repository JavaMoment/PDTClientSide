package com.java.GUI.panels;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.entities.Evento;
import com.services.EventoBeanRemote;
import com.services.EventoBean;
import com.entities.Itr;
import com.entities.Tutor;
import com.entities.Usuario;
import com.services.ItrBeanRemote;
import com.services.TutorBeanRemote;
import com.enums.Modalidad;
import com.enums.Status;
import com.enums.TipoEvento;
import com.java.GUI.utils.EntityTableModel;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuBar;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JScrollBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class CreateEventPanel extends JPanel {
    private JTextField titleField;
    private JDateChooser startDateChooser;
    private JDateChooser endDateChooser;
    private JTextField locationField;
    private JButton submitButton;
    private JButton cancelButton;
    private JComboBox comboBoxTipoEvento;
    private JComboBox comboBoxModalidad;
    private JComboBox comboBoxStatus;
    private JComboBox comboBoxItr;
    private List<Tutor> tutor;
    private JTable tableOrigen;
    private JTable tableDestino;
    private DefaultTableModel tableModel;
    private ArrayList <String> tutoresElegidos = new ArrayList <String>();
	

    


    public CreateEventPanel(EventoBeanRemote eventoBeanRemote, ItrBeanRemote itrBean, TutorBeanRemote tutorBean) {
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
                	
                	//where in y popular con arraylist
                    registerEvent(eventoBeanRemote, tutorBean);
                    System.out.println(eventoBeanRemote.getColsNames().toString());
                
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

        JLabel statusLabel_1 = new JLabel("Status:");

        comboBoxModalidad = new JComboBox();
        comboBoxModalidad.setModel(new DefaultComboBoxModel(Modalidad.values()));

        comboBoxTipoEvento = new JComboBox();
        comboBoxTipoEvento.setModel(new DefaultComboBoxModel(TipoEvento.values()));

        comboBoxStatus = new JComboBox();
        comboBoxStatus.setModel(new DefaultComboBoxModel(Status.values()));

        comboBoxItr = new JComboBox(itrBean.selectAll().toArray());
        
        //codigo para pasar de una tabla de tutores a otra

        tableOrigen = new JTable();
       
     
       
   
		List<Tutor> tutores = tutorBean.selectAll();
		String[] tutoresColNames = Arrays.stream(tutorBean.getColsNames())
				.filter(value -> value.equals("usuario"))
				.toArray(String[]::new);
		
		TableModel tutoresTableModel = new EntityTableModel<>(tutoresColNames, tutores);

		tableOrigen.setModel(tutoresTableModel);
		tableOrigen.setColumnSelectionAllowed(true);
		tableOrigen.setCellSelectionEnabled(true);
		
		JScrollPane scrollPane = new JScrollPane ();
		scrollPane.add(tableOrigen);
		scrollPane.setViewportView(tableOrigen);

		//PASAR A TABLEDESTINO
		
		
        tableDestino = new JTable();
        JButton btnAgregar = new JButton("Agregar tutor");
     
     
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	DefaultTableModel DTM = new DefaultTableModel (tutoresColNames, 0); //0 es el header
            	
            	//selecciono los datos de la celda
            	int selectedRow = tableOrigen.getSelectedRow();
				int selectedColumn = tableOrigen.getSelectedColumn();
                String cellValue = tableOrigen.getValueAt(selectedRow, selectedColumn).toString();
                
                //para agregar al arraylist
                
                tutoresElegidos.add(cellValue);
               
                
                //agregar a la tabla
            	DTM.addRow(tutoresElegidos.toArray());
            	
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
        				.addComponent(comboBoxStatus, 0, 511, Short.MAX_VALUE)
        				.addComponent(tutorLabel, GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
        			.addContainerGap(202, Short.MAX_VALUE))
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGap(103)
        			.addComponent(submitButton, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
        			.addGap(62)
        			.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(322, Short.MAX_VALUE))
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
        			.addComponent(comboBoxStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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
        			.addGap(30)
        			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(submitButton, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
        				.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
        			.addGap(52))
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


    private void registerEvent(EventoBeanRemote eventoBeanRemote, TutorBeanRemote tutorBean) {
        String titulo = titleField.getText();
        Date fechaHoraInicio = startDateChooser.getDate();
        Date fechaHoraFinal = endDateChooser.getDate();
        Itr itr = (Itr) comboBoxItr.getSelectedItem();
        String localizacion = locationField.getText();
        TipoEvento tipoEvento = (TipoEvento) comboBoxTipoEvento.getSelectedItem();
        Modalidad modalidad = (Modalidad) comboBoxModalidad.getSelectedItem();
        Status status = (Status) comboBoxStatus.getSelectedItem();
     
        

        Evento newEvento = new Evento(titulo, tipoEvento, fechaHoraInicio, fechaHoraFinal, modalidad, itr, localizacion, status, tutor);

        int exitCode = eventoBeanRemote.create(newEvento);
        if (exitCode == 0) {
            JOptionPane.showMessageDialog(CreateEventPanel.this, "El evento ha sido correctamente creado.");
        } else {
            JOptionPane.showMessageDialog(CreateEventPanel.this, "Ha ocurrido un error mientras se intentaba crear el evento.\nPor favor, intente de nuevo.");
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