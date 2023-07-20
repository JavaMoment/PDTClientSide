package com.java.GUI.panels;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import com.java.GUI.utils.EntityTableModel;
import com.java.controller.BeansFactory;
import com.java.enums.Beans;
import com.services.EventoBeanRemote;
import com.entities.Evento;
import com.entities.Modalidad;

public class RegisterCallsEventPanel extends ContentPanel {
	
	private JTable eventoTable;
	private SheetEventPanel sheetEventPanel;
	private List<Evento> eventos;
	
	public RegisterCallsEventPanel() {
		
		EventoBeanRemote eventoBean = BeansFactory.getBean(Beans.Evento, EventoBeanRemote.class);

		
		setLayout(new MigLayout("", "[125,grow,center][125,center][125,center][125,center][125,center][125,center][125,center]", "[50.00][50.00][50.00,grow][50.00][50.00][50.00][50.00][50.00][50.00][50.00][50.00][50.00]"));
		
		JLabel lblTitle = new JLabel("CONVOCATORIA A EVENTOS");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 25));
		add(lblTitle, "cell 0 0 7 1");
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 2 7 9,grow");
		
		eventos = eventoBean.selectAllByActive(1);
		
		String[] eventosColNames = Arrays.stream(eventoBean.getColsNames())
                .filter(value ->  !value.equals("fechaHoraFinal") & !value.equals("tutorEventos"))
                .toArray(String[]::new);
		
		for(Evento e : eventos) {
			System.out.println(e.getTitulo());
		}
	
		eventoTable = new JTable();
		TableModel listModel = new EntityTableModel<>(eventosColNames, eventos);
		eventoTable.setModel(listModel);
		scrollPane.setViewportView(eventoTable);
		
	//Permitir seleccioanar una sola fila del JTable para asi actualziar los datos con los cambios en la tabla
		
		eventoTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    // Obtener la fila seleccionada
                    int selectedRow = eventoTable.getSelectedRow();
                    
                    // Verificar si hay una fila seleccionada
                    Object[] rowData = new Object[eventosColNames.length];
                    if (selectedRow != -1) {
                        // Obtener los datos de la fila seleccionada
                        for (int i = 0; i < eventosColNames.length; i++) {
                            rowData[i] = eventoTable.getValueAt(selectedRow, i);
                        }
                        
                        Evento evento = eventos.get(selectedRow);
                        
                                                
                        
                        // Abrir el nuevo JFrame con los datos de la fila seleccionada
                        JFrame sheetEvent = new JFrame();
                        SheetRegisterCalls  eventoCalls = new SheetRegisterCalls(evento);
                        sheetEvent.getContentPane().add(eventoCalls);
                        sheetEvent.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        
                        sheetEvent.pack();
                        sheetEvent.setVisible(true);
                    }
                }
            }
		});
		
		
		JLabel lblDescription = new JLabel("*Para registrar una convocatoria a evento debe presionar sobre el evento de la lista");
		add(lblDescription, "cell 0 11 7 1");
		
	}
}
