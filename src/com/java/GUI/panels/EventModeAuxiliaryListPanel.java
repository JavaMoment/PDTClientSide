package com.java.GUI.panels;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import java.awt.Font;

import java.util.Arrays;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JFrame;


import com.entities.Modalidad;
import com.java.GUI.panels.SheetModifyModalities;

import com.java.GUI.utils.EntityTableModel;
import com.java.controller.BeansFactory;
import com.java.enums.Beans;
import com.services.ModalidadBeanRemote;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class EventModeAuxiliaryListPanel extends ContentPanel {

	private List<Modalidad> modalidades;
	private JTable modalidadList;
	private EntityTableModel modelo;

	public EventModeAuxiliaryListPanel() {
			
		ModalidadBeanRemote modalidadBean = BeansFactory.getBean(Beans.Modalidad, ModalidadBeanRemote.class);

		setLayout(new MigLayout("", "[165.00,grow][235.00,grow][165.00,grow,center][134.00,grow]", "[grow][12.00,grow][][fill][][][]"));
		
		JLabel lblNewLabel = new JLabel("LISTA AUXILIAR MODALIDADES DE EVENTO");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		add(lblNewLabel, "cell 1 0 2 1");
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 1 4 4,grow");
		
		modalidades = modalidadBean.selectAll();
		
		String[] modalidadColNames = Arrays.stream(modalidadBean.getColsNames())
                .filter(value -> !value.equals("eventos") && !value.equals("idModalidad"))
                .toArray(String[]::new);
				
		modalidadList = new JTable();
		TableModel modelo = new EntityTableModel<>(modalidadColNames, modalidades);
		modalidadList.setModel(modelo);
		scrollPane.setViewportView(modalidadList);
		
		JButton btnNewButton = new JButton("AGREAGAR NUEVA MODALIDAD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame sheetModality = new JFrame();
                CreateModalityPanel  modalityCreate = new CreateModalityPanel();
                sheetModality.getContentPane().add(modalityCreate);
                sheetModality.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                sheetModality.addWindowListener(new WindowAdapter(){
                	public void windowClosing(WindowEvent e) {
                		modalidades = modalidadBean.selectAll();
                		TableModel modelo = new EntityTableModel<>(modalidadColNames, modalidades);
                		modalidadList.setModel(modelo);
                	}
                });
                sheetModality.pack();
                sheetModality.setVisible(true);
			}
		});
		add(btnNewButton, "cell 0 5 2097051 1,grow");
		
		
		
		//Permitir seleccioanar una sola fila del JTable para asi actualziar los datos con los cambios en la tabla
		
		modalidadList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    // Obtener la fila seleccionada
                    int selectedRow = modalidadList.getSelectedRow();
                    
                    // Verificar si hay una fila seleccionada
                    Object[] rowData = new Object[modalidadColNames.length];
                    if (selectedRow != -1) {
                        // Obtener los datos de la fila seleccionada
                        for (int i = 0; i < modalidadColNames.length; i++) {
                            rowData[i] = modalidadList.getValueAt(selectedRow, i);
                        }
                        Modalidad modalidad = modalidades.get(selectedRow);
                                                
                        
                        // Abrir el nuevo JFrame con los datos de la fila seleccionada
                        JFrame sheetEvent = new JFrame();
                        SheetModifyModalities  modalitesModify = new SheetModifyModalities(modalidad);
                        sheetEvent.getContentPane().add(modalitesModify);
                        sheetEvent.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        sheetEvent.addWindowListener(new WindowAdapter(){
                        	public void windowClosing(WindowEvent e) {
                        		modalidadBean.selectAll();
                        		TableModel modelo = new EntityTableModel<>(modalidadColNames, modalidades);
                        		modalidadList.setModel(modelo);
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
