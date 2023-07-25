package com.java.GUI.panels;

import java.awt.Font;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.entities.Estado;
import com.entities.Itr;
import com.entities.Modalidad;
import com.enums.TipoEvento;

import net.miginfocom.swing.MigLayout;

public class SheetEventPanel extends ContentPanel {
	private JTextField txtTitulo;
	private JTextField txtFechaHoraInicio;
	private JTextField txtFechaHoraFinal;
	private JTextField txtItr;
	private JTextField txtLocalizacion;
	private JTextField txtTipoEvento;
	private JTextField txtModalidad;
	private JTextField txtStatus;
//	private JTextField txtTutorSeleccionado;
	private JTextArea txtTutorSeleccionado;


	public SheetEventPanel(String titulo, Date fechaHoraInicio, Date fechaHoraFinal, Itr itr, String localizacion,
			TipoEvento tipoEvento, Modalidad modalidad, Estado estado) {
		setLayout(new MigLayout("", "[][][][grow]", "[][][][][][][][][]"));

		JLabel lblTitulo = new JLabel("Ficha Evento");
		lblTitulo.setFont(new Font("Verdana", Font.PLAIN, 31));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTitulo, "cell 1 0 3 1");

		JLabel lblTituloEvento = new JLabel("Título:");
		add(lblTituloEvento, "cell 1 1");

		txtTitulo = new JTextField();
		txtTitulo.setEditable(false);
		add(txtTitulo, "cell 2 1");
		txtTitulo.setColumns(20);
		JLabel lblFechaHoraInicio = new JLabel("Fecha y Hora de Inicio:");
		add(lblFechaHoraInicio, "cell 1 2");

		txtFechaHoraInicio = new JTextField();
		txtFechaHoraInicio.setEditable(false);
		add(txtFechaHoraInicio, "cell 2 2");
		txtFechaHoraInicio.setColumns(20);

		JLabel lblFechaHoraFinal = new JLabel("Fecha y Hora de Finalización:");
		add(lblFechaHoraFinal, "cell 1 3");

		txtFechaHoraFinal = new JTextField();
		txtFechaHoraFinal.setEditable(false);
		add(txtFechaHoraFinal, "cell 2 3");
		txtFechaHoraFinal.setColumns(20);

		JLabel lblItr = new JLabel("ITR:");
		add(lblItr, "cell 1 4");

		txtItr = new JTextField();
		txtItr.setEditable(false);
		add(txtItr, "cell 2 4");
		txtItr.setColumns(20);

		JLabel lblLocalizacion = new JLabel("Localización:");
		add(lblLocalizacion, "cell 1 5");

		txtLocalizacion = new JTextField();
		txtLocalizacion.setEditable(false);
		add(txtLocalizacion, "cell 2 5");
		txtLocalizacion.setColumns(20);

		JLabel lblTipoEvento = new JLabel("Tipo de Evento:");
		add(lblTipoEvento, "cell 1 6");

		txtTipoEvento = new JTextField();
		txtTipoEvento.setEditable(false);
		add(txtTipoEvento, "cell 2 6");
		txtTipoEvento.setColumns(20);

		JLabel lblModalidad = new JLabel("Modalidad:");
		add(lblModalidad, "cell 1 7");

		txtModalidad = new JTextField();
		txtModalidad.setEditable(false);
		add(txtModalidad, "cell 2 7");
		txtModalidad.setColumns(20);

		JLabel lblStatus = new JLabel("Status:");
		add(lblStatus, "cell 1 8");

		txtStatus = new JTextField();
		txtStatus.setEditable(false);
		add(txtStatus, "cell 2 8");
		txtStatus.setColumns(20);

		JLabel lblTutorSeleccionado = new JLabel("Tutores Seleccionados:");
		add(lblTutorSeleccionado, "cell 1 9");

//		txtTutorSeleccionado = new JTextField();
//		txtTutorSeleccionado.setEditable(false);
		txtTutorSeleccionado = new JTextArea(5, 20); // 5 rows, 20 columns (adjust as needed)
		txtTutorSeleccionado.setEditable(false); // To prevent user editing

		add(txtTutorSeleccionado, "cell 2 9");
		txtTutorSeleccionado.setColumns(20);

		// Establecer los datos del evento en los componentes correspondientes
		txtTitulo.setText(titulo);
		txtFechaHoraInicio.setText(fechaHoraInicio.toString());
		txtFechaHoraFinal.setText(fechaHoraFinal.toString());
		txtItr.setText(itr.toString());
		txtLocalizacion.setText(localizacion);
		txtTipoEvento.setText(tipoEvento.toString());
		txtModalidad.setText(modalidad.toString());
		txtStatus.setText(estado.toString());

	}

	public void setTitulo(String titulo) {
		txtTitulo.setText(titulo);
	}

	public void setFechaHoraInicio(Date fechaHoraInicio) {
		txtFechaHoraInicio.setText(fechaHoraInicio.toString());
	}

	public void setFechaHoraFinal(Date fechaHoraFinal) {
		txtFechaHoraFinal.setText(fechaHoraFinal.toString());
	}

	public void setItr(Itr itr) {
		txtItr.setText(itr.toString());
	}

	public void setLocalizacion(String localizacion) {
		txtLocalizacion.setText(localizacion);
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		txtTipoEvento.setText(tipoEvento.toString());
	}

	public void setModalidad(Modalidad modalidad) {
		txtModalidad.setText(modalidad.toString());
	}

	public void setEstado(Estado estado) {
		txtStatus.setText(estado.toString());
	}

	public void setTutoresSeleccionados(List<String> tutoresSeleccionados) {
	    String nombresTutores = String.join("\n", tutoresSeleccionados);
	    txtTutorSeleccionado.setText(nombresTutores);
	}


}
