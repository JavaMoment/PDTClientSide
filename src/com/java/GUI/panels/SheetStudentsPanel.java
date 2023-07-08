package com.java.GUI.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;
import com.toedter.calendar.JDateChooser;

public class SheetStudentsPanel extends ContentPanel {

    private JTextField titleField;
    private JTextField typeField;
    private JDateChooser startDateChooser;
    private JDateChooser endDateChooser;
    private JTextField modalityField;
    private JTextField itrField;
    private JTextField locationField;
    private JTable estudiantesTable;
    private JButton marcarAsistenciaButton;
    private JTable table;

    public SheetStudentsPanel() {
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

        JList<String> tutorList = new JList<>();

        JLabel title = new JLabel("Detalles Evento");
        title.setFont(new Font("Arial", Font.PLAIN, 21));

        table = new JTable();

        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGap(161)
                    .addComponent(title, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(159, Short.MAX_VALUE))
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap()
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
                    .addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                    .addComponent(table, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(marcarAsistenciaButton)
                    .addGap(24))
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
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                            .addGap(67)
                            .addComponent(table, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addComponent(tutorList, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(marcarAsistenciaButton)
                    .addContainerGap(24, Short.MAX_VALUE))
        );
        setLayout(groupLayout);

        DefaultTableModel tableModel = new DefaultTableModel(
            new Object[][]{
                {"Estudiante 1", false},
                {"Estudiante 2", false},
                {"Estudiante 3", false},
                {"Estudiante 4", false}
            },
            new String[]{"Nombre", "Asistencia"}
        );

        estudiantesTable = new JTable(tableModel);
        estudiantesTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JCheckBox()));

        marcarAsistenciaButton = new JButton("Marcar asistencia");

        add(new JScrollPane(estudiantesTable), BorderLayout.CENTER);
        add(marcarAsistenciaButton, BorderLayout.SOUTH);

        marcarAsistenciaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                marcarAsistencia();
            }
        });
    }

    private void marcarAsistencia() {
        DefaultTableModel tableModel = (DefaultTableModel) estudiantesTable.getModel();

        for (int row = 0; row < tableModel.getRowCount(); row++) {
            boolean asistencia = (boolean) tableModel.getValueAt(row, 1);

        }

        JOptionPane.showMessageDialog(this, "Asistencia marcada correctamente", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
    }
}

