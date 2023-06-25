package com.java.GUI.panels;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.entities.Modalidad;

import com.java.GUI.utils.EntityTableModel;
import com.java.controller.BeansFactory;
import com.java.enums.Beans;
import com.services.ModalidadBeanRemote;

public class EventModeAuxiliaryListPanel extends JPanel{
	private JTable tableModalities;
	private EntityTableModel jtableModel;

	public EventModeAuxiliaryListPanel() {
		
		ModalidadBeanRemote modalidadBean = BeansFactory.getBean(Beans.Modalidad, ModalidadBeanRemote.class);

		
		JLabel lblTitle = new JLabel("LISTA AUXILIAR DE MODALIDADES DE EVENTOS");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		String[] columnNames = modalidadBean.getColsNames();
		
		List<Modalidad> rowData = modalidadBean.selectAll();
		
		jtableModel = new EntityTableModel(columnNames, rowData);
		
		
		tableModalities = new JTable(jtableModel);
		
		JScrollPane scrollPane = new JScrollPane();
	    
		
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 468, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(116, Short.MAX_VALUE))
		);
		
		scrollPane.setViewportView(tableModalities);
		setLayout(groupLayout);
		
	}
}
