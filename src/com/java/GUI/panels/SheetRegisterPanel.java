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

public class SheetRegisterPanel extends JPanel {
	
	private JTable estudiantesTable;
	private SheetEventPanel sheetEventPanel;
	private List<EstudianteEvento> estudianteEvento;
	private List<EstudianteEvento> estudiantesDelEvento;
	private List<Estudiante> estudiantes;
	private Asistencia asistencia;

	
	public SheetRegisterPanel(Evento evento) {
		
		EstudianteBeanRemote estudiantesBean = BeansFactory.getBean(Beans.Estudiante, EstudianteBeanRemote.class);
		EstudianteEventoBeanRemote estudianteventoBean = BeansFactory.getBean(Beans.EstudianteEvento, EstudianteEventoBeanRemote.class);

		setLayout(new MigLayout("", "[125,center][158.00,center][128.00,center][130,center][125,center][125,center][125,center]", "[50.00][50.00][50.00,grow][50.00][50.00][50.00][50.00][50.00][50.00][50.00][50.00]"));
		
		JLabel lblTitle = new JLabel("REGISTRAR ASISTENCIA AL EVENTO " + evento.getTitulo());
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
		
		
		JButton btnModify= new JButton("Modificar");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	                    // Obtener la fila seleccionada
	                    int selectedRow = estudiantesTable.getSelectedRow();
	                    
	                    // Verificar si hay una fila seleccionada
	                    Object[] rowData = new Object[estudiantesColNames.length];
	                    if (selectedRow != -1) {
	                        // Obtener los datos de la fila seleccionada
	                        for (int i = 0; i < estudiantesColNames.length; i++) {
	                            rowData[i] = estudiantesTable.getValueAt(selectedRow, i);
	                        }
	                        
	                        EstudianteEvento estudiante = estudiantesDelEvento.get(selectedRow);
	                              
	                        String[] opciones = {asistencia.PENDIENTE.toString(),asistencia.ASISTENCIA.toString(), asistencia.AUSENCIA.toString(), 
	                        		asistencia.AUSENCIA_JUSTIFICADA.toString(),asistencia.MEDIA_ASISTENCIA_MATUTINA.toString(),
	                        		asistencia.MEDIA_ASISTENCIA_VESPERTINA.toString()};

	                        // Mostrar el JOptionPane con el JComboBox
	                        String seleccion = (String) JOptionPane.showInputDialog(null, "Selecciona una opción:",
	                                "Selector de opciones", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
	                        
	                        BigDecimal min = new BigDecimal(1); // Valor mínimo permitido
	                        BigDecimal max = new BigDecimal(5); // Valor máximo permitido

	                        BigDecimal calificacion = obtenerNumeroEntreMinMax(min, max);                        
	                        
	                        estudiante.setAsistencia(seleccion);
	                        estudiante.setCalificacion(calificacion);
	                        estudianteventoBean.update(estudiante); 
	                        
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
	                		
	                		TableModel listModel2 = new EntityTableModel<>(estudiantesColNames, estudiantesDelEvento, transientColNames);
	                		estudiantesTable.setModel(listModel2);
	                		scrollPaneEstudiantes.setViewportView(estudiantesTable);
	                        
	                    }
	                
	            }
		});
		add(btnModify, "cell 2 10 3 1");

		
		
	}

	public static BigDecimal obtenerNumeroEntreMinMax(BigDecimal min, BigDecimal max) {
		  BigDecimal valor;
	        do {
	            String input = JOptionPane.showInputDialog(null,
	                    "Ingresa un número entre " + min + " y " + max + ":");
	            try {
	                valor = new BigDecimal(input);
	                if (valor.compareTo(min) < 0 || valor.compareTo(max) >  5) {
	                    JOptionPane.showMessageDialog(null, "El número debe estar entre " + min + " y " + max + ".",
	                            "Valor inválido", JOptionPane.ERROR_MESSAGE);
	                }
	            } catch (NumberFormatException e) {
	                valor = min.subtract(BigDecimal.ONE); // Valor inválido para que continúe el bucle
	                JOptionPane.showMessageDialog(null, "Por favor, ingresa un número válido.",
	                        "Valor inválido", JOptionPane.ERROR_MESSAGE);
	            }
	        } while (valor.compareTo(min) < 0 || valor.compareTo(max) > 0);

	        return valor;
	    }
	
}
