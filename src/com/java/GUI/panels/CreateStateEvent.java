package com.java.GUI.panels;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;

import com.entities.Estado;
import com.entities.Modalidad;
import com.java.controller.BeansFactory;
import com.java.enums.Beans;
import com.services.EstadoBeanRemote;
import com.services.ModalidadBeanRemote;

public class CreateStateEvent extends ContentPanel {
	
	private JTextField textField;
	private Estado estado; 
	public CreateStateEvent() {
		
		EstadoBeanRemote estadoBean = BeansFactory.getBean(Beans.Estado, EstadoBeanRemote.class);

		
		JLabel lblTitle = new JLabel("ALTA NUEVO ESTADO");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 17));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Arial", Font.PLAIN, 14));
		textField.setColumns(10);
		
		
		
		JButton btnNewButton = new JButton("Crear estado");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textField.getText();
				estado = new Estado();
				estado.setNombre(name);
				estado.setActivo(1);
				estadoBean.create(estado);
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 13));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(textField, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnNewButton)
					.addContainerGap(85, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}
}
