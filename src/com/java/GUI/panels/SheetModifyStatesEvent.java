package com.java.GUI.panels;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.entities.Estado;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

public class SheetModifyStatesEvent extends JPanel {
	private JTextField textField;
	
	public SheetModifyStatesEvent(Estado estado) {
		
		JLabel lblNewLabel = new JLabel("Modificaciones en:");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JButton btnChangeName = new JButton("Cambiar nombre");
		
		JButton btnActivar = new JButton("Activar estado ");
		btnActivar.setFont(new Font("Arial", Font.PLAIN, 14));
		
		JButton btnDesactivar = new JButton("Desactivar estado ");
		btnDesactivar.setFont(new Font("Arial", Font.PLAIN, 14));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnActivar, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnChangeName, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addComponent(btnDesactivar, GroupLayout.PREFERRED_SIZE, 430, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnChangeName))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnActivar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDesactivar, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(57, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}
}
