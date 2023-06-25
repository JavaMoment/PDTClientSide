package com.java.GUI.panels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.*;
import javax.swing.LayoutStyle.ComponentPlacement;



public class EventsPanel extends JPanel{
	

		private CreateEventPanel createEvent; 
		private ListEventPanel listEvent;
		private ModifyEventPanel modifyEvent;
		private RemoveEventPanel removeEvent;
		private ModifyCallsEventPanel modifyCallsEvent;
		private RegisterCallsEventPanel registerCallsEvent;
		private RegisterAssistsEventPanel registerAsissistsEvent;
		private EventModeAuxiliaryListPanel eventModeAuxiliaryListPanel;
		private AuxiliaryListofEventStatesPanel auxiliaryListofEventStatesPanel;



		 public EventsPanel() {
			 
				 CreateEventPanel createEvent = new CreateEventPanel (null, null);

			setBackground(new Color(255, 255, 255));

			JButton btnVolver = new JButton("Volver");
			btnVolver.setPreferredSize(new Dimension(30, 30));
			btnVolver.setBackground(new Color(255, 0, 0));

			
			JLabel lblTitle = new JLabel("GESTION DE EVENTOS");
			lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitle.setFont(new Font("Arial", Font.BOLD, 21));
			
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
			tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
			
			createEvent = new CreateEventPanel();
			tabbedPane.addTab("Alta evento", createEvent);
			
			listEvent = new ListEventPanel();
			tabbedPane.addTab("Listar eventos", listEvent);
			
			modifyEvent = new ModifyEventPanel();
			//tabbedPane.addTab("Modificar evento", modifyEvent);
			
			removeEvent = new RemoveEventPanel();
			tabbedPane.addTab("Borrar evento", removeEvent);
			
			modifyCallsEvent = new ModifyCallsEventPanel();
			tabbedPane.addTab("Modificar convocatoria a evento", modifyCallsEvent);
			
			registerCallsEvent = new RegisterCallsEventPanel();
			tabbedPane.addTab("Registrar convocatoria a evento", registerCallsEvent);
			
			registerAsissistsEvent = new RegisterAssistsEventPanel();
			tabbedPane.addTab("Registrar asistencias a evento", registerAsissistsEvent);
			
			eventModeAuxiliaryListPanel = new EventModeAuxiliaryListPanel();
			tabbedPane.addTab("Lista auxiliar modalidades de evento", eventModeAuxiliaryListPanel);
			
			auxiliaryListofEventStatesPanel = new AuxiliaryListofEventStatesPanel();
			tabbedPane.addTab("Lista auxiliar estados de evento", auxiliaryListofEventStatesPanel);


					                    
			
			GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
					.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(btnVolver, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 841, Short.MAX_VALUE))
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(10)
								.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)))
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
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 511, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(18, Short.MAX_VALUE))
			);

			setLayout(groupLayout);
			
		
			}
}

