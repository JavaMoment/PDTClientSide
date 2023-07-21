package com.java.GUI.panels;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

import net.miginfocom.swing.MigLayout;

public class SheetModifyCallsPanel extends JPanel {
	
	private JTable estudiantesTable;
	private SheetEventPanel sheetEventPanel;
	private List<EstudianteEvento> estudianteEvento;
	private List<Estudiante> estudiantesDelEvento;

	
	public SheetModifyCallsPanel(Evento evento) {
		
		EstudianteBeanRemote estudiantesBean = BeansFactory.getBean(Beans.Estudiante, EstudianteBeanRemote.class);
		EstudianteEventoBeanRemote estudianteventoBean = BeansFactory.getBean(Beans.EstudianteEvento, EstudianteEventoBeanRemote.class);

		setLayout(new MigLayout("", "[125,center][158.00,center][128.00,center][130,center][125,center][125,center][125,center]", "[50.00][50.00][50.00,grow][50.00][50.00][50.00][50.00][50.00][50.00][50.00][50.00]"));
		
		JLabel lblTitle = new JLabel("CONVOCATORIA A EVENTO AL " + evento.getTitulo());
		lblTitle.setFont(new Font("Arial", Font.BOLD, 25));
		add(lblTitle, "cell 0 0 7 1");
		
		JScrollPane scrollPaneEstudiantes = new JScrollPane();
		add(scrollPaneEstudiantes, "cell 0 1 7 10,grow");
		
		estudianteEvento = estudianteventoBean.selectAll();

	
		String[] estudiantesColNames = Arrays.stream(estudianteventoBean.getColsNames())
						.toArray(String[]::new);
		
		
		for(EstudianteEvento e : estudianteEvento) {
			System.out.println(e.getIdEstudiante());
		}
		
		System.out.println(evento.getIdEvento());
		
		estudiantesTable = new JTable();
		TableModel listModel = new EntityTableModel<>(estudiantesColNames, estudianteEvento);
		estudiantesTable.setModel(listModel);
		scrollPaneEstudiantes.setViewportView(estudiantesTable);
	}

}
