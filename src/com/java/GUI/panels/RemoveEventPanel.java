package com.java.GUI.panels;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.java.GUI.utils.EntityTableModel;
import com.java.controller.BeansFactory;
import com.java.enums.Beans;
import com.services.EventoBeanRemote;
import com.entities.Evento;

import net.miginfocom.swing.MigLayout;

public class RemoveEventPanel extends ContentPanel {

	private JTable eventoTable;
	private SheetEventPanel sheetEventPanel;
	private List<Evento> eventos;


	public RemoveEventPanel(){
		
		EventoBeanRemote eventoBean = BeansFactory.getBean(Beans.Evento, EventoBeanRemote.class);

		
		setLayout(new MigLayout("", "[130,grow,center][130,center][130,center][130,grow,center][130,grow,center][130,grow,center][130,center]", "[60][60][60,grow][60][60][60][60][60][60][28.00]"));
		
		JLabel lblTitle = new JLabel("BAJA EVENTO");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 25));
		add(lblTitle, "cell 0 0 7 1");
		
		JComboBox comboBoxTypeEvent = new JComboBox();
		//add(comboBoxTypeEvent, "cell 0 1,growx");
		
		JComboBox comboBoxModality = new JComboBox();
		//add(comboBoxModality, "cell 3 1,growx");
		
		JComboBox comboBoxITR = new JComboBox();
		//add(comboBoxITR, "cell 4 1,growx");
		
		JComboBox comboBoxEstado = new JComboBox();
		//add(comboBoxEstado, "cell 5 1,growx");
		
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 2 7 7,grow");
		
		eventos = eventoBean.selectAll();
		
		String[] eventosColNames = Arrays.stream(eventoBean.getColsNames())
                .filter(value ->  !value.equals("fechaHoraFinal") & !value.equals("tutorEventos"))
                .toArray(String[]::new);
		
						
		eventoTable = new JTable();
		TableModel listModel = new EntityTableModel<>(eventosColNames, eventos);
		eventoTable.setModel(listModel);
		scrollPane.setViewportView(eventoTable);	
		
		
		
		JButton btnNewButton = new JButton("Eliminar evento");
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int[] filasSeleccionadas = eventoTable.getSelectedRows();
		        for (int fila : filasSeleccionadas) {
		            // Acceder a los datos de cada fila seleccionada
		            String datoId =  eventoTable.getValueAt(fila, 8).toString();
		            System.out.println(datoId);
		            int idEvento = Integer.parseInt(datoId);
		            Evento enventoActivo = new Evento();
		            for(Evento evento : eventos) {
		            	if(evento.getIdEvento() == idEvento) {
		            		enventoActivo = evento;
		            	}
		            }
		            int confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el evento?", "Confirmación", JOptionPane.YES_NO_OPTION);
		            if (confirmacion == JOptionPane.YES_OPTION) {
		            	enventoActivo.setActivo(0);
		            	eventoBean.update(enventoActivo);
		            	eventos = eventoBean.selectAll();
		        		TableModel listModel = new EntityTableModel<>(eventosColNames, eventos);
		        		eventoTable.setModel(listModel);
		            	
		            }
		        }
		    }
		});
		add(btnNewButton, "cell 0 9 7 1,grow");
		
		
	
			
		}
}