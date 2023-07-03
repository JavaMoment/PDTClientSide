package com.java.GUI.panels;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.*;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.java.controller.BeansFactory;
import com.java.enums.Beans;
import com.services.EventoBeanRemote;
import com.services.ItrBeanRemote;

import java.awt.Insets;



public class EventsPanel extends JPanel{
	
		private CreateEventPanel createEvent;
		private ListEventPanel listEvent = new ListEventPanel();
		private ModifyEventPanel modifyEvent = new ModifyEventPanel();
		private RemoveEventPanel removeEvent = new RemoveEventPanel();
		private ModifyCallsEventPanel modifyCallsEvent = new ModifyCallsEventPanel();
		private RegisterCallsEventPanel registerCallsEvent = new RegisterCallsEventPanel();
		private RegisterAssistsEventPanel registerAsissistsEvent = new RegisterAssistsEventPanel();
		private EventModeAuxiliaryListPanel eventModeAuxiliaryListPanel = new EventModeAuxiliaryListPanel();
		private AuxiliaryListofEventStatesPanel auxiliaryListofEventStatesPanel = new AuxiliaryListofEventStatesPanel();

		private EventoBeanRemote eventoBeanRemote;
		private ItrBeanRemote itrBeanRemote;

		

		 public EventsPanel() {
			 
			eventoBeanRemote = BeansFactory.getBean(Beans.Evento, EventoBeanRemote.class);
			itrBeanRemote = BeansFactory.getBean(Beans.Itr, ItrBeanRemote.class);

			CreateEventPanel createEvent = new CreateEventPanel (eventoBeanRemote, itrBeanRemote);

			setBackground(new Color(255, 255, 255));

			JButton btnVolver = new JButton("Volver");
			btnVolver.setPreferredSize(new Dimension(30, 30));
			btnVolver.setBackground(new Color(255, 0, 0));

			
			JLabel lblTitle = new JLabel("GESTION DE EVENTOS");
			lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitle.setFont(new Font("Arial", Font.BOLD, 21));
			
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
			tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
			tabbedPane.addTab("Alta evento", createEvent);
			tabbedPane.addTab("Listar eventos", listEvent);
			tabbedPane.addTab("Modificar evento", modifyEvent);
			tabbedPane.addTab("Borrar evento", removeEvent);
			tabbedPane.addTab("Modificar convocatoria a evento", modifyCallsEvent);
			tabbedPane.addTab("Registrar convocatoria a evento", registerCallsEvent);
			tabbedPane.addTab("Registrar asistencias a evento", registerAsissistsEvent);
			tabbedPane.addTab("Lista auxiliar modalidades de evento", eventModeAuxiliaryListPanel);
			tabbedPane.addTab("Lista auxiliar estados de evento", auxiliaryListofEventStatesPanel);


					                    
			
			GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(10)
								.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 570, Short.MAX_VALUE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(btnVolver, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)))
						.addContainerGap())
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(btnVolver, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(11)
								.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 413, Short.MAX_VALUE)
						.addContainerGap())
			);

			setLayout(groupLayout);
			
		
			}
}

