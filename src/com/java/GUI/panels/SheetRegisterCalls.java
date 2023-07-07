package com.java.GUI.panels;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.entities.Estudiante;
import com.entities.Evento;
import com.java.GUI.utils.EntityTableModel;
import com.java.controller.BeansFactory;
import com.java.enums.Beans;
import com.services.EstudianteBeanRemote;
import javax.swing.JButton;

public class SheetRegisterCalls extends JPanel {
	
	private JTable estudiantesTable;
	private JTable convocadosTable;
	private SheetEventPanel sheetEventPanel;
	private List<Estudiante> estudiantes;
	private List<Estudiante> convocados;

	
	public SheetRegisterCalls(Evento evento) {
		
		EstudianteBeanRemote estudiantesBean = BeansFactory.getBean(Beans.Estudiante, EstudianteBeanRemote.class);

		setLayout(new MigLayout("", "[125,center][158.00,center][128.00,center][130,center][125,center][125,center][125,center]", "[50.00][50.00][50.00,grow][50.00][50.00][50.00][50.00][50.00][50.00][50.00][50.00][50.00]"));
		
		JLabel lblTitle = new JLabel("CONVOCATORIA A EVENTO");
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
		
		convocados = estudiantesBean.selectAll();

		convocadosTable = new JTable();
		TableModel listModel2 = new EntityTableModel<>(estudiantesColNames, convocados);
		convocadosTable.setModel(listModel2);
		scrollPaneConvocados.setViewportView(convocadosTable);
		
		JButton btnNewButton = new JButton("Seleccionar ");
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 11));
		add(btnNewButton, "cell 3 6");
		
	
	}

}
