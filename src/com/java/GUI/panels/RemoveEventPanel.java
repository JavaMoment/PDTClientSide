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
import com.enums.TipoEvento;


import net.miginfocom.swing.MigLayout;

public class RemoveEventPanel extends ContentPanel {

	private JTable eventoTable;
	private SheetEventPanel sheetEventPanel;
	private List<Evento> eventos;


	public RemoveEventPanel(){
		
		EventoBeanRemote eventoBean = BeansFactory.getBean(Beans.Evento, EventoBeanRemote.class);

		
		setLayout(new MigLayout("", "[130,grow,center][130,center][130,center][130,grow,center][130,grow,center][130,grow,center][130,center]", "[60][60][60,grow][60][60][60][60][60][60][28.00]"));
		
		JLabel lblTitle = new JLabel("BORRAR EVENTO");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 25));
		add(lblTitle, "cell 0 0 7 1");
		
		JComboBox comboBoxTypeEvent = new JComboBox();
		add(comboBoxTypeEvent, "cell 0 1,growx");
		
		JComboBox comboBoxModality = new JComboBox();
		add(comboBoxModality, "cell 3 1,growx");
		
		JComboBox comboBoxITR = new JComboBox();
		add(comboBoxITR, "cell 4 1,growx");
		
		JComboBox comboBoxEstado = new JComboBox();
		add(comboBoxEstado, "cell 5 1,growx");
		
		
		JScrollPane scrollPane = new JScrollPane();

		JButton btnListEvents = new JButton("Filtrar");
		btnListEvents.setFont(new Font("Arial", Font.PLAIN, 13));


		JLabel lblAdvice = new JLabel("Estamos trabajando con la implementacion de filtors para mejorar su experincia");
		lblAdvice.setForeground(Color.DARK_GRAY);

		tableEvents = new JTable(model);
		scrollPane.setViewportView(tableEvents);

		tableEvents.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	            @Override
	            public void valueChanged(ListSelectionEvent event) {
	                if (!event.getValueIsAdjusting()) {
	                    // Obtener la fila seleccionada
	                    int selectedRow = tableEvents.getSelectedRow();
	                    
	                    // Verificar si hay una fila seleccionada
	                    if (selectedRow != -1) {
	                        // Obtener los datos de la fila seleccionada
	                        Object[] rowData = new Object[columnNames.length];
	                        for (int i = 0; i < columnNames.length; i++) {
	                            rowData[i] = tableEvents.getValueAt(selectedRow, i);
	                        }

	                      
	                    }
	                }
	            }
	        });
		
		JComboBox cBoxTipoEvento = new JComboBox();
		cBoxTipoEvento.addItem(tipoEvento.DEFENSA_DE_PROYECTO);
		cBoxTipoEvento.addItem(tipoEvento.JORNADA_PRESENCIAL);
		cBoxTipoEvento.addItem(tipoEvento.EXAMEN);
		cBoxTipoEvento.addItem(tipoEvento.PRUEBA_FINAL);
		
		
		JComboBox cBoxModalidad = new JComboBox();
		cBoxModalidad.addItem(modalidad.PRESENCIAL);
		cBoxModalidad.addItem(modalidad.SEMIPRESENCIAL);
		cBoxModalidad.addItem(modalidad.VIRTUAL);

		JComboBox cBoxITR = new JComboBox();
		
		eventos = eventoBean.selectAll();
		
		String[] eventosColNames = Arrays.stream(eventoBean.getColsNames())
                .filter(value ->  !value.equals("fechaHoraFinal"))
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