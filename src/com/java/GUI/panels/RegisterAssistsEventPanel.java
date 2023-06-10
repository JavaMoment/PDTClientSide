package com.java.GUI.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RegisterAssistsEventPanel extends JPanel {
    private JComboBox<String> attendanceComboBox;
    private JTextField scoreTextField;
    private JButton confirmButton;
    private JTable studentsTable;
    private DefaultTableModel tableModel;

    public RegisterAssistsEventPanel() {
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        // Crear el modelo de tabla con las columnas deseadas
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Apellido");
        tableModel.addColumn("Asistencia");

        // Crear la tabla con el modelo de tabla
        studentsTable = new JTable(tableModel);

        // Ejemplo de datos de estudiantes
        List<String[]> studentsData = new ArrayList<>();
        studentsData.add(new String[]{"John", "Doe", "Asistencia"});
        studentsData.add(new String[]{"Jane", "Smith", "Ausencia"});
        studentsData.add(new String[]{"Alice", "Johnson", "Asistencia"});

        // Agregar los datos de los estudiantes a la tabla
        for (String[] student : studentsData) {
            tableModel.addRow(student);
        }

        attendanceComboBox = new JComboBox<>();
        scoreTextField = new JTextField();
        confirmButton = new JButton("Confirmar");
    }

    private void setupLayout() {
        setLayout(new GridLayout(3, 2));
        add(new JLabel("Estado de Asistencia:"));
        add(attendanceComboBox);
        add(new JLabel("Puntaje o Nota:"));
        add(scoreTextField);
        add(confirmButton);
    }
}
