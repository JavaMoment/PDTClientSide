package com.java.GUI.panels;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
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

import javax.swing.JButton;
import javax.swing.JFrame;

public class SheetRegisterCalls extends ContentPanel {
	
	private JTable estudiantesTable;
	private JTable convocadosTable;
	private SheetEventPanel sheetEventPanel;
	private List<Estudiante> estudiantes;
	private List<Estudiante> convocados;
	private Asistencia asistencia;

	
	public SheetRegisterCalls(Evento evento) {
		
		EstudianteBeanRemote estudiantesBean = BeansFactory.getBean(Beans.Estudiante, EstudianteBeanRemote.class);
		EstudianteEventoBeanRemote estudianteventoBean = BeansFactory.getBean(Beans.EstudianteEvento, EstudianteEventoBeanRemote.class);

		setLayout(new MigLayout("", "[125,center][158.00,center][128.00,center][130,center][125,center][125,center][125,center]", "[50.00][50.00][50.00,grow][50.00][50.00][50.00][50.00][50.00][50.00][50.00][50.00][50.00]"));
		
		JLabel lblTitle = new JLabel("CONVOCATORIA A EVENTO AL " + evento.getTitulo());
		lblTitle.setFont(new Font("Arial", Font.BOLD, 25));
		add(lblTitle, "cell 0 0 7 1");
		
		JScrollPane scrollPaneEstudiantes = new JScrollPane();
		add(scrollPaneEstudiantes, "cell 0 2 3 9,grow");
		
		estudiantes = estudiantesBean.selectAll();
		
		String[] estudiantesColNames = Arrays.stream(estudiantesBean.getColsNames())
						.filter(e -> !e.equals("usuario"))
						.toArray(String[]::new);
		
		String[] transientColNames = {"NombreUsuario"};
				
		System.out.println(evento.getIdEvento());
		
		estudiantesTable = new JTable();
		TableModel listModel = new EntityTableModel<>(estudiantesColNames, estudiantes, transientColNames);
		estudiantesTable.setModel(listModel);
		scrollPaneEstudiantes.setViewportView(estudiantesTable);
		
		
		JScrollPane scrollPaneConvocados = new JScrollPane();
		add(scrollPaneConvocados, "cell 4 2 3 9,grow");
		
		
		
		convocadosTable = new JTable();
		convocados = new ArrayList<>();
		scrollPaneConvocados.setViewportView(convocadosTable);

		JButton btnSelect= new JButton("Seleccionar ");
		btnSelect.setFont(new Font("Arial", Font.PLAIN, 11));
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					int rowIndex = estudiantesTable.getSelectedRow();
					Estudiante estudianteSeleccionado = estudiantes.get(rowIndex);
					if(!convocados.contains(estudianteSeleccionado)) {
						convocados.add(estudianteSeleccionado);
					}
					TableModel listModel2 = new EntityTableModel<>(estudiantesColNames, convocados, transientColNames);
					convocadosTable.setModel(listModel2);
				
                	}
                });
		add(btnSelect, "cell 3 6");
		
		JButton btnAgregarEstudiantes = new JButton("Agregar estudiantes a evento");
		btnAgregarEstudiantes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long idEvento = evento.getIdEvento();
				
				try {
					int option = JOptionPane.showOptionDialog(
		                        null,
		                        "¿Desea crear la convocatoria a evento?",
		                        "Confirmación",
		                        JOptionPane.YES_NO_OPTION,
		                        JOptionPane.QUESTION_MESSAGE,
		                        null,
		                        new Object[]{"Aceptar", "Cancelar"},
		                        "Aceptar"
		            );
					
					if(option == JOptionPane.YES_OPTION) {
						for(Estudiante es : convocados) {
							long idEstudiante = es.getIdEstudiante();
							EstudianteEventoPK relationEV = new EstudianteEventoPK();
							relationEV.setIdEstudiante(idEstudiante);
							relationEV.setIdEvento(idEvento);
							EstudianteEvento relation = new EstudianteEvento();
							relation.setId(relationEV);
							//relation.setAsistencia(Asistencia.PENDIENTE.toString());
							relation.setCalificacion(0);
							estudianteventoBean.create(relation);
						}
						
						convocados = new ArrayList<>();
						TableModel listModel2 = new EntityTableModel<>(estudiantesColNames, convocados, transientColNames);
						convocadosTable.setModel(listModel2);
					}
					
                }catch(Exception exception) {
                	exception.printStackTrace();
                }
			 }
            });
		add(btnAgregarEstudiantes, "cell 4 11 3 1");
		
		
	
	}

}
