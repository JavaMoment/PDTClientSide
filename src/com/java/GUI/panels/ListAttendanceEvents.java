package com.java.GUI.panels;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.HistogramDataset;

import com.entities.Estudiante;
import com.entities.EstudianteEvento;
import com.entities.Evento;
import com.java.GUI.entities.CombinedEntities;
import com.java.GUI.utils.EntitiesTableModel;
import com.java.GUI.utils.PDFBuilder;
import com.java.controller.BeansFactory;
import com.java.enums.Beans;
import com.services.EstudianteEventoBeanRemote;
import com.services.EventoBeanRemote;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class ListAttendanceEvents extends ContentPanel {
	
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnDownloadPdf;
	private ChartPanel attendanceChart;
	private static EventoBeanRemote eventoBean = BeansFactory.getBean(Beans.Evento, EventoBeanRemote.class);
	private static EstudianteEventoBeanRemote estudianteEventoBean = BeansFactory.getBean(Beans.EstudianteEvento, EstudianteEventoBeanRemote.class);
	private EntitiesTableModel<Evento, EstudianteEvento> eventoTableModel;
	protected final List<String> colsFilter = Arrays.asList("activo", "idEvento", "id"); 
	private JLabel lblTitle;
	private HistogramDataset notesDataset;
	 
	public ListAttendanceEvents(Estudiante student) {
		setLayout(new MigLayout("", "[275.00,grow][450.00,growprio 70,grow]", "[][grow][][][]"));
		
		lblTitle = new JLabel("Eventos asistidos por el usuario:");
		lblTitle.setFont(new Font("Verdana", Font.BOLD, 18));
		//scrollPane.setColumnHeaderView(lblTitle);
		add(lblTitle, "cell 0 0,alignx center");

		scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 1 1 3,grow");
		
		// Rows data creation
		List<Evento> eventosFromUser = eventoBean.getEventosBy(student.getIdEstudiante());
		List<EstudianteEvento> estudianteEventos = estudianteEventoBean.getEstudianteEventoFrom(student.getIdEstudiante());
		List<CombinedEntities> eventosAndEstudianteEvento = IntStream
		  .range(0, Math.min(eventosFromUser.size(), estudianteEventos.size()))
		  .mapToObj(i -> new CombinedEntities(eventosFromUser.get(i), estudianteEventos.get(i)))
		  .toList();
		
		// Columns headers creation
		String[] eventosColNames = eventoBean.getColsNames();
		String[] estudianteEventoColNames = estudianteEventoBean.getColsNames();
		String[] colsNames = Stream
				.concat(Arrays.stream(eventosColNames),
						Arrays.stream(estudianteEventoColNames))
				.filter(value -> !colsFilter.contains(value))
				.toArray(String[]::new);
		
		table = new JTable();
		eventoTableModel = new EntitiesTableModel<Evento, EstudianteEvento>(colsNames, eventosAndEstudianteEvento);
		table.setModel(eventoTableModel);
		scrollPane.setViewportView(table);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setOpaque(false);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		
		
		double pctAttendance = (double) estudianteEventos.stream()
                .filter(ee -> ee.getAsistencia().startsWith("S"))
                .count() / estudianteEventos.size() * 100;
		double pctNoAttendance = pctAttendance * estudianteEventos.size() / 100;
		
		DefaultPieDataset<String> attendanceDataset = new DefaultPieDataset<String>();
		attendanceDataset.setValue("Asistidos", pctAttendance);
		attendanceDataset.setValue("NO asistidos", pctNoAttendance);
		
		JFreeChart attendancePieChart = ChartFactory.createPieChart("Porcentajes de asistencia a eventos", attendanceDataset, true, false, false);
		attendanceChart = new ChartPanel(attendancePieChart);
		add(attendanceChart, "cell 1 1,alignx right");
		
		if(table.getRowCount() != 0) {
			notesDataset = new HistogramDataset();
			double[] califications = estudianteEventos.stream()
					.mapToDouble(event -> event.getCalificacion().doubleValue())
					.toArray();
			notesDataset.addSeries("Calificaciones", califications, 10);
		}
		

        JFreeChart notesHistogram = ChartFactory.createHistogram(
                "Histograma de calificaciones por eventos",
                "Calificacion",
                "Frecuencia",
                notesDataset
        );
        
		attendanceChart = new ChartPanel(notesHistogram);
		add(attendanceChart, "cell 1 3,alignx right");
		
		btnDownloadPdf = new JButton("Descargar PDF");
		btnDownloadPdf.setBackground(new Color(220, 58, 50));
        btnDownloadPdf.setForeground(new Color(40, 40, 40));
        btnDownloadPdf.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		if(table.getRowCount() == 0) {
        			 JOptionPane.showMessageDialog(ListAttendanceEvents.this, "¡Hey! No existen datos suficientes para generar un reporte", "Alerta alerta", JOptionPane.WARNING_MESSAGE);
        			 return;
        		}
        		JFileChooser j = new JFileChooser();
        		j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        		
        		int x = j.showSaveDialog(ListAttendanceEvents.this);
        		if (x != JFileChooser.APPROVE_OPTION) {
        			return;
        		}
        		
        		String path = j.getSelectedFile().getPath();
        		String os = System.getProperty("os.name");
        		if(!path.endsWith(".pdf")) {
        			if(os.startsWith("Windows")) {
        				path = path + "\\" + "UTEC_EscolaridadEventos.pdf";
        			} else {
        				path = path + "/" + "UTEC_EscolaridadEventos.pdf";
        			}
        		}
        		
        		int pdfExitCode = PDFBuilder.create()
        				.withChart(attendancePieChart)
        				.withChart(notesHistogram)
        				.setChartSectionName("Estadisticas de asistencias a eventos y notas relacionadas")
        				.withTable(table)
        				.setTableSectionName("Listado de Eventos asistidos")
        				.setPdfTitle("Informe de " + lblTitle.getText().substring(0, lblTitle.getText().length()-1))
        				.setPdfAuthor(student.getUsuario().getNombre1() + " " + student.getUsuario().getApellido1())
        				.generatePDF(path);
        		
        		if(pdfExitCode == 0) {
        			JOptionPane.showMessageDialog(ListAttendanceEvents.this, "¡Excelentes noticias! El PDF ha sido éxitosamente generado en la ubicación: " + path);
        		} else {
        			JOptionPane.showMessageDialog(ListAttendanceEvents.this, "¡Rayos! El PDF no ha podido ser generado");
        		}
        	}
        });
		add(btnDownloadPdf, "cell 0 4");
	}
}
