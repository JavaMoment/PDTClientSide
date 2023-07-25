package com.java.GUI.panels;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import com.entities.Estudiante;
import com.entities.EstudianteEvento;
import com.entities.EstudianteEventoPK;
import com.entities.Evento;
import com.enums.Asistencia;
import com.java.GUI.utils.EntityTableModel;
import com.java.controller.BeansFactory;
import com.java.enums.Beans;
import com.services.EstudianteBeanRemote;
import com.services.EstudianteEventoBeanRemote;

import net.miginfocom.swing.MigLayout;

public class SheetModifyCallsPanel extends JPanel {
	
	private JTable estudiantesTable;
	private SheetEventPanel sheetEventPanel;
	private List<EstudianteEvento> estudianteEvento;
	private List<EstudianteEvento> estudiantesDelEvento;
	private List<Estudiante> estudiantes;
	private Asistencia asistencia;

	
	public SheetModifyCallsPanel(Evento evento) {
		
		EstudianteBeanRemote estudiantesBean = BeansFactory.getBean(Beans.Estudiante, EstudianteBeanRemote.class);
		EstudianteEventoBeanRemote estudianteventoBean = BeansFactory.getBean(Beans.EstudianteEvento, EstudianteEventoBeanRemote.class);

		setLayout(new MigLayout("", "[125,center][158.00,center][128.00,center][130,center][125,center][125,center][125,center]", "[50.00][50.00][50.00,grow][50.00][50.00][50.00][50.00][50.00][50.00][50.00][50.00]"));
		
		JLabel lblTitle = new JLabel("MODIFICACION DE CONVOCATORIA AL EVENTO " + evento.getTitulo());
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		add(lblTitle, "cell 0 0 7 1");
		
		JScrollPane scrollPaneEstudiantes = new JScrollPane();
		add(scrollPaneEstudiantes, "cell 0 1 7 9,grow");
		
		estudianteEvento = estudianteventoBean.selectAll();
		estudiantes = estudiantesBean.selectAll();
		estudiantesDelEvento = new ArrayList<EstudianteEvento>();
		
		 for (EstudianteEvento e : estudianteEvento) {
			for(Estudiante estudiante : estudiantes) {
				String username = estudiante.getNombreUsuario();
					if(e.getIdEstudiante() == estudiante.getIdEstudiante()){
						e.setNombreUsuario(username);
					}
			}
			
	        if (e.getIdEvento() == evento.getIdEvento()) {
	            	estudiantesDelEvento.add(e);
	        }
	     }
	
		 for(EstudianteEvento e : estudiantesDelEvento) {
			 System.out.println(e.getNombreUsuario());
		 }
		 
		 
		String[] estudiantesColNames = Arrays.stream(estudianteventoBean.getColsNames())
						.filter(e -> !e.equals("id"))
						.toArray(String[]::new);
		
		String[] transientColNames = {"nombreUsuario"};
		
		estudiantesTable = new JTable();
		TableModel listModel = new EntityTableModel<>(estudiantesColNames, estudiantesDelEvento, transientColNames);
		estudiantesTable.setModel(listModel);
		scrollPaneEstudiantes.setViewportView(estudiantesTable);
		
		JButton btnDelete = new JButton("Eliminar");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                int selectedRow = estudiantesTable.getSelectedRow();
                EstudianteEvento estudiante = estudiantesDelEvento.get(selectedRow);
                int confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea eliminar la convocatoria?", "Confirmación", JOptionPane.YES_NO_OPTION);
	            if (confirmacion == JOptionPane.YES_OPTION) {
	            	estudianteventoBean.delete(estudiante);
	            	estudianteEvento = estudianteventoBean.selectAll();
            		estudiantes = estudiantesBean.selectAll();
            		estudiantesDelEvento = new ArrayList<EstudianteEvento>();
            		
            		 for (EstudianteEvento e1 : estudianteEvento) {
            			for(Estudiante estudiante2 : estudiantes) {
            				String username = estudiante2.getNombreUsuario();
            					if(e1.getIdEstudiante() == estudiante2.getIdEstudiante()){
            						e1.setNombreUsuario(username);
            					}
            			}
            			
            	        if (e1.getIdEvento() == evento.getIdEvento()) {
            	            	estudiantesDelEvento.add(e1);
            	        }
            	     }
            		 
            		String[] estudiantesColNames = Arrays.stream(estudianteventoBean.getColsNames())
            						.filter(e1 -> !e1.equals("id"))
            						.toArray(String[]::new);
            		
            		String[] transientColNames = {"nombreUsuario"};
            		
            		TableModel listModel3 = new EntityTableModel<>(estudiantesColNames, estudiantesDelEvento, transientColNames);
            		estudiantesTable.setModel(listModel3);
            		scrollPaneEstudiantes.setViewportView(estudiantesTable);
	            	
	            	
	            }

		    }
			    
		});
		add(btnDelete, "cell 2 10 3 1");
			
	}
	
}
