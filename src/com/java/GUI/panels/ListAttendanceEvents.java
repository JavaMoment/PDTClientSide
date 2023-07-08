package com.java.GUI.panels;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


public class ListAttendanceEvents extends ContentPanel {
	 
	public ListAttendanceEvents() {
		
		JLabel lblTitle = new JLabel("LISTAR ASISTENCIAS POR ESTUDIANTES A LOS EVENTOS");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnBack = new JButton("Volver");
		
		JButton btnPDF = new JButton("GENERAR PDF");
		btnPDF.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               PDFCreator();
            }
        });

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addComponent(btnBack, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnPDF, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 525, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnBack, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)))
					.addGap(391)
					.addComponent(btnPDF)
					.addContainerGap(38, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}
	
	public void PDFCreator() {
		// Ruta del directorio de descargas
        String downloadsPath = System.getProperty("user.home") + File.separator + "Downloads";
        String outputPath = downloadsPath + File.separator + "pruebas.pdf";
        
            try {
                Document document = new Document(PageSize.A4);
                PdfWriter.getInstance(document, new FileOutputStream(outputPath));
                document.open();
             
                document.addTitle("Escolaridad del estudiante");
                 
                document.add(crateParagraph("UTEC"));
        

                document.add(new Paragraph("Fecha de creación"));
                document.close();
                System.out.println("PDF generado exitosamente!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
       
	}
	
	public Paragraph crateParagraph(String s) {
		 Paragraph paragraph = new Paragraph(s);
		 paragraph.setAlignment(ABORT);
		 return paragraph;
	}
}
