package com.java.GUI.panels;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import com.java.GUI.utils.EntityTableModel;
import com.java.controller.BeansFactory;
import com.java.enums.Beans;
import com.services.EstadoBeanRemote;
import com.services.ModalidadBeanRemote;

import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;

import com.entities.Estado;
import com.entities.Modalidad;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class AuxiliaryListofEventStatesPanel extends ContentPanel {
	
	private List<Estado> estados;
	private JTable estadosList;
	private EntityTableModel listModel;

	public AuxiliaryListofEventStatesPanel() {	
		
		EstadoBeanRemote estadoBean = BeansFactory.getBean(Beans.Estado, EstadoBeanRemote.class);

		setLayout(new MigLayout("", "[185.00][235.00][165.00,center][134.00]", "[][][][27.00][][][338.00][]"));
		
		JLabel lblNewLabel = new JLabel("LISTA AUXILIAR MODALIDADES DE EVENTO");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblNewLabel, "cell 1 0 2 3");
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 3 4 4,grow");
		
		estados = estadoBean.selectAll();
		
		String[] estadosColNames = Arrays.stream(estadoBean.getColsNames())
                .filter(value -> !value.equals("idEstado") && !value.equals("eventos"))
                .toArray(String[]::new);
		
				
		estadosList = new JTable();
		TableModel listModel = new EntityTableModel<>(estadosColNames, estados);
		estadosList.setModel(listModel);
		scrollPane.setViewportView(estadosList);
		
		JButton btnNewButton = new JButton("AGREAGAR NUEVO ESTADO");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame sheetStates = new JFrame();
                CreateStateEvent  stateCreate = new CreateStateEvent();
                sheetStates.getContentPane().add(stateCreate);
                sheetStates.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                sheetStates.addWindowListener(new WindowAdapter(){
                	public void windowClosing(WindowEvent e) {
                		estados = estadoBean.selectAll();
                		TableModel listModel = new EntityTableModel<>(estadosColNames, estados);
                		estadosList.setModel(listModel);
                	}
                });
                sheetStates.pack();
                sheetStates.setVisible(true);
			}
		});
		add(btnNewButton, "cell 0 7 4 1, grow, span");
		
		
		
		//Permitir seleccioanar una sola fila del JTable para asi actualziar los datos con los cambios en la tabla
				
		
		estadosList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    // Obtener la fila seleccionada
                    int selectedRow = estadosList.getSelectedRow();
                    
                    // Verificar si hay una fila seleccionada
                    Object[] rowData = new Object[estadosColNames.length];
                    if (selectedRow != -1) {
                        // Obtener los datos de la fila seleccionada
                        for (int i = 0; i < estadosColNames.length; i++) {
                            rowData[i] = estadosList.getValueAt(selectedRow, i);
                        }
                        Estado estado = estados.get(selectedRow);
                                                
                        
                        // Abrir el nuevo JFrame con los datos de la fila seleccionada
                        JFrame sheetEvent = new JFrame();
                        SheetModifyStatesEvent  statesModify = new SheetModifyStatesEvent(estado);
                        sheetEvent.getContentPane().add(statesModify);
                        sheetEvent.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        sheetEvent.addWindowListener(new WindowAdapter(){
                        	public void windowClosing(WindowEvent e) {
                        		estados = estadoBean.selectAll();
                        		TableModel modelo = new EntityTableModel<>(estadosColNames, estados);
                        		estadosList.setModel(modelo);
                        	}
                        });
                        sheetEvent.pack();
                        sheetEvent.setVisible(true);
                    }
                }
            }
		});

	
		
	}
	
}
