package com.java.GUI.panels;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.entities.Modalidad;
import com.java.controller.BeansFactory;
import com.java.enums.Beans;
import com.services.ModalidadBeanRemote;

import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

public class SheetModifyModalities extends ContentPanel {
	private JTextField txtFieldName;
	public SheetModifyModalities(Modalidad modalidad) {
		
		ModalidadBeanRemote modalidadBean = BeansFactory.getBean(Beans.Modalidad, ModalidadBeanRemote.class);

		JLabel lblTitle = new JLabel("Modificaciones en: " + modalidad.getNombre());
		lblTitle.setFont(new Font("Arial", Font.PLAIN, 24));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtFieldName = new JTextField();
		txtFieldName.setColumns(10);
		
		JButton btnChangeName = new JButton("Cambiar nombre");
		btnChangeName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String name = txtFieldName.getText().trim();
					if(name.isEmpty()) {
	                    JOptionPane.showMessageDialog(null, "Hubo un error al realizar el alta; por favor verificar de no dejar espacios en blanco al principio o final", "Error", JOptionPane.ERROR_MESSAGE);
					} else {
						modalidad.setNombre(name);
						modalidadBean.update(modalidad);
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		JButton btnDelete = new JButton("Desactivar Modalidad");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int borrado = 0;
				modalidad.setActivo(borrado);
				modalidadBean.update(modalidad);
				
			}
		});
		
		JButton btnActivar = new JButton("Activar Modalidad");
		btnActivar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int activo = 1;
				modalidad.setActivo(activo);
				modalidadBean.update(modalidad);
				
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnDelete, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtFieldName, GroupLayout.PREFERRED_SIZE, 204, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnChangeName, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
						.addComponent(btnActivar, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtFieldName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnChangeName))
					.addGap(18)
					.addComponent(btnDelete)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnActivar)
					.addContainerGap(18, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}
}
