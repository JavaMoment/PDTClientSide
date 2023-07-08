package com.java.GUI.panels;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MantenimientoPanel extends ContentPanel {
    private JTable itrTable;
    private JButton modifyButton;
    private JButton deleteButton;
    private JButton newButton;

    public MantenimientoPanel() {
        // Crear componentes GUI, como botones y tabla
        modifyButton = new JButton("Modificar");
        deleteButton = 	new JButton("Dar de baja ");
        newButton = new JButton("Ingresar nuevo ITR");

        // Configurar listeners de los botones
        modifyButton.addActionListener(new ModifyButtonListener());
        deleteButton.addActionListener(new DeleteButtonListener());
        newButton.addActionListener(new NewButtonListener());

        // Agregar los componentes al panel
        add(modifyButton);
        add(deleteButton);
        add(newButton);

        // Configurar la tabla de ITRs
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Estado");
        itrTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(itrTable);
        add(scrollPane);

      
    }

    private class ModifyButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Obtener el registro seleccionado en la tabla
            int selectedRow = itrTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(MantenimientoPanel.this, "Por favor seleccione un registro para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener el ID del registro seleccionado
            String id = itrTable.getValueAt(selectedRow, 0).toString();

            
        }
    }

    private class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Obtener el registro seleccionado en la tabla
            int selectedRow = itrTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(MantenimientoPanel.this, "Por favor seleccione un registro para dar de baja lógica.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener el ID del registro seleccionado
            String id = itrTable.getValueAt(selectedRow, 0).toString();

            // Mostrar un cuadro de diálogo de confirmación antes de dar de baja lógica el registro
            int choice = JOptionPane.showConfirmDialog(MantenimientoPanel.this, "¿Está seguro que desea dar de baja lógica el registro seleccionado?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
            }
        }
    }

    private class NewButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           
        }
    }
}
