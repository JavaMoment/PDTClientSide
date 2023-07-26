
package com.java.GUI.panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import com.java.GUI.Main;

public class EventsPanel extends JTabbedPane {

	private CreateEventPanel createEvent;
	private ContentPanel listEvent;
	private ModifyEventPanel modifyEvent;
	private RemoveEventPanel removeEvent;
	private ModifyCallsEventPanel modifyCallsEvent;
	private RegisterCallsEventPanel registerCallsEvent;
	private EventModeAuxiliaryListPanel eventModeAuxiliaryListPanel;
	private AuxiliaryListofEventStatesPanel auxiliaryListofEventStatesPanel;
	private CreateEventPanel createEvent_1;
	private JButton logout;
	private RegisterAssistsEventPanel registerAsissistsEvent;

	public EventsPanel() {

		super(JTabbedPane.TOP);

		logout = new JButton();
		logout.setIcon(new ImageIcon(HomePanel.class.getResource("/com/java/resources/images/icons8-go-back-16.png")));
		logout.setBackground(new Color(125, 229, 251));
		logout.setForeground(new Color(40, 40, 40));
		logout.setContentAreaFilled(false);
		logout.setBorder(null);
		logout.setCursor(new Cursor(Cursor.HAND_CURSOR));
		logout.setToolTipText("Click aquí para volver hacia el menú de Home");
		logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Main main = (Main) SwingUtilities.getWindowAncestor(EventsPanel.this);
				main.initHome();
				main.revalidate();
				revalidate();

			}
		});

		addTab("", null, new JPanel(), null);

		setTabComponentAt(getTabCount() - 1, logout);

		listEvent = new ListEventPanel();
		addTab("Listar eventos", listEvent);

		createEvent_1 = new CreateEventPanel();
		addTab("Alta evento", createEvent_1);

		modifyEvent = new ModifyEventPanel();
		addTab("Modificar evento", modifyEvent);

		removeEvent = new RemoveEventPanel();
		addTab("Baja de evento", removeEvent);

		modifyCallsEvent = new ModifyCallsEventPanel();
		addTab("Modificar convocatoria a evento", modifyCallsEvent);

		registerCallsEvent = new RegisterCallsEventPanel();
		addTab("Registrar convocatoria a evento", registerCallsEvent);
		
		registerAsissistsEvent = new RegisterAssistsEventPanel();
    addTab("Registrar asistencias a evento", registerAsissistsEvent);

		eventModeAuxiliaryListPanel = new EventModeAuxiliaryListPanel();
		addTab("Lista auxiliar modalidades de evento", eventModeAuxiliaryListPanel);

		auxiliaryListofEventStatesPanel = new AuxiliaryListofEventStatesPanel();
		addTab("Lista auxiliar estados de evento", auxiliaryListofEventStatesPanel);

		setSelectedIndex(1);

	}

	public ContentPanel getListEvent() {
		return listEvent;
	}

	public void setListEvent(ContentPanel listEvent) {
		this.listEvent = listEvent;
		this.listEvent.revalidate();
		this.listEvent.repaint();
		remove(1);
		insertTab("Listado de Eventos", null, listEvent, "Acceder al listado", 1);
		revalidate();
		repaint();
		
	}
}
