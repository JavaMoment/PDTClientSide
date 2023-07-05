package com.java.GUI.panels;

import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.*;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.entities.Evento;
import com.entities.Itr;
import com.entities.Usuario;
import com.enums.Estado;
import com.enums.Modalidad;
import com.enums.Status;
import com.enums.TipoEvento;
import com.java.GUI.utils.EntityTableModel;
import com.services.EventoBeanRemote;
import com.services.ItrBeanRemote;
import com.services.TutorBeanRemote;
import com.services.UsuarioBeanRemote;


import java.awt.Color;
import javax.swing.LayoutStyle.ComponentPlacement;


public class ListEventPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tableEvents;
	private SheetEventPanel sheetEventPanel;
	private EntityTableModel jtableModel;
	private TipoEvento tipoEvento;
	private Modalidad modalidad;
	private Estado estado;
	private JComboBox comboBoxTipoEvento;
	private JComboBox comboBoxModalidad;
	private JComboBox comboBoxStatus;
	private JComboBox comboBoxItr;
	private JTextField textFieldSearch;
	private JTextField textField;

	public ListEventPanel(EventoBeanRemote eventoBean, ItrBeanRemote itrBean, TutorBeanRemote tutorBean){
		
		JLabel lblTitle = new JLabel("LISTA DE EVENTOS");
		lblTitle.setFont(new Font("Arial", Font.PLAIN, 30));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
   
		textFieldSearch = new JTextField();
		add(textFieldSearch, "flowx,cell 1 0,alignx left,growy");
		textFieldSearch.setColumns(10);
	   
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 1 1 1 13,grow");
		
		List<Evento> eventos = eventoBean.selectAll();
		String[] eventosColNames = Arrays.stream(eventoBean.getColsNames())
				.filter(value -> !value.equals("idEvento") && !value.equals("analistas"))
				.toArray(String[]::new);
		
		
		
		tableEvents = new JTable();
		TableModel eventosTableModel = new EntityTableModel<>(eventosColNames, eventos);
		tableEvents.setModel(eventosTableModel);
		tableEvents.setColumnSelectionAllowed(true);
		tableEvents.setCellSelectionEnabled(true);
		scrollPane.setViewportView(tableEvents);
		
		
		
		JButton btnListEvents = new JButton("Filtrar");
		btnListEvents.setFont(new Font("Arial", Font.PLAIN, 13));		

		
		 comboBoxModalidad = new JComboBox();
	     comboBoxModalidad.setModel(new DefaultComboBoxModel(Modalidad.values()));

	     comboBoxTipoEvento = new JComboBox();
	     comboBoxTipoEvento.setModel(new DefaultComboBoxModel(TipoEvento.values()));

	     comboBoxStatus = new JComboBox();
	     comboBoxStatus.setModel(new DefaultComboBoxModel(Status.values()));

	        comboBoxItr = new JComboBox(itrBean.selectAll().toArray());
		
	       
	        
	        JButton btnSearch = new JButton("Buscar");
	        btnSearch.setToolTipText("Buscar por nombre");
			btnSearch.addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					String searchText = textFieldSearch.getText();
					if(searchText.isBlank() || searchText.isEmpty()) {
						return;
					}
					RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
						public boolean include(Entry entry) {
							String name1 = (String) entry.getValue(10);
							String name2 = entry.getValue(11) != null ? (String) entry.getValue(11) : "";
							return name1.toUpperCase().contains(searchText.toUpperCase()) || name2.contains(searchText);
						}
					};
					TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(eventosTableModel);
					sorter.setRowFilter(filter);
					tableEvents.setRowSorter(sorter);
				}
			});
		
		textField = new JTextField();
		textField.setColumns(10);
     

		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 862, Short.MAX_VALUE)
						.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 862, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(comboBoxTipoEvento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(61)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
							.addComponent(comboBoxModalidad, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxItr, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxStatus, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnListEvents)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnListEvents, GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
						.addComponent(comboBoxStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBoxItr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBoxModalidad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBoxTipoEvento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSearch)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 370, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
			
				
		setLayout(groupLayout);
	
			
	}
}
