package com.java.GUI.utils;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;

import org.jfree.chart.JFreeChart;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFBuilder {

	private static Document document;
    private static List<JFreeChart> charts;
    private static List<JTable> tables;
    private String tableSectionName;
    private String chartSectionName;
	private String docTitle;
	private String author;
	
	// Fonts
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    
    private PDFBuilder() {
    	if(this.document == null) {
    		this.document = new Document(PageSize.A4, 36, 36, 90, 36);
    	}
    	if(this.charts == null) {
    		this.charts = new ArrayList<>();
    	}
    	if(this.tables == null) {
    		this.tables = new ArrayList<>();
    	}
    }

    public static PDFBuilder create() {
        return new PDFBuilder();
    }

    public PDFBuilder withChart(JFreeChart chart) {
        this.charts.add(chart);
        return this;
    }

    public PDFBuilder withTable(JTable table) {
        this.tables.add(table);
        return this;
    }
    
    public PDFBuilder setTableSectionName(String sectionName) {
    	this.tableSectionName = sectionName;
    	return this;
    }
    
    public PDFBuilder setChartSectionName(String sectionName) {
    	this.chartSectionName = sectionName;
    	return this;
    }

    public int generatePDF(String filePath) {
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            writer.setPageEvent(new HeaderPageEvent());
            document.open();

            Paragraph preface = null;
            if(this.docTitle != null) {
            	preface = new Paragraph();
        		preface.add(new Paragraph());
            	document.add(Chunk.NEWLINE);
            	preface.add(new Paragraph(docTitle, catFont));
            	document.add(Chunk.NEWLINE);
            }
            
            if(this.author != null) {
            	preface.add(new Paragraph("Reporte generado para: " + author + ", " + new Date(), smallBold));
            	preface.add(Chunk.NEWLINE);
            }
            
            if(preface != null) {
            	document.add(preface);
            	document.add(Chunk.NEXTPAGE);
            }
            
            addTablesToPDF();
            document.add(Chunk.NEXTPAGE);
            addChartsToPDF();

            document.close();
            return 0;
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void addChartsToPDF() throws DocumentException, IOException {
    	if(chartSectionName != null) {
    		document.add(new Paragraph());
    		document.add(Chunk.NEWLINE);
    		document.add(new Paragraph(chartSectionName, catFont));
    		document.add(Chunk.NEWLINE);
    	}
        for (JFreeChart chart : charts) {
        	document.add(Chunk.NEWLINE);
        	
            BufferedImage chartImage = chart.createBufferedImage(600, 400);

            Image image = Image.getInstance(chartImage, null);
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);

            document.add(new Paragraph());
            document.add(Chunk.NEWLINE);
        }
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEXTPAGE);
    }

    private void addTablesToPDF() throws DocumentException {
    	
    	if(tableSectionName != null) {
    		document.add(new Paragraph());
    		document.add(Chunk.NEWLINE);
    		document.add(new Paragraph(tableSectionName, catFont));
    		document.add(Chunk.NEWLINE);
    	}
        for (JTable table : tables) {
            int rowCount = table.getRowCount();
            int columnCount = table.getColumnCount();
            
            PdfPTable pdfTbl = new PdfPTable(columnCount);
            pdfTbl.setTotalWidth(800F);
            pdfTbl.setHorizontalAlignment(1);
            pdfTbl.setHeaderRows(1);
            pdfTbl.setWidthPercentage(100F);
            
            for (int i = 0; i < columnCount; i++) {
                String header = table.getColumnName(i);
                PdfPCell headerCell = new PdfPCell(new Paragraph(header));
                headerCell.setBackgroundColor(new BaseColor(17, 222, 61));
                pdfTbl.addCell(headerCell);
            }
            
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    String rowData = table.getValueAt(i, j).toString();
                    pdfTbl.addCell(rowData);
                }
                
            }
            document.add(pdfTbl);
            document.add(Chunk.NEWLINE);
        }
    }
    
    private class HeaderPageEvent extends PdfPageEventHelper {

        private Image headerImage;
        private PdfTemplate t;

        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {
            t = writer.getDirectContent().createTemplate(30, 16);
            try {
            	headerImage = Image.getInstance(t);
            } catch (DocumentException de) {
                throw new ExceptionConverter(de);
            }
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            addHeader(writer);
            addFooter(writer);
        }
        
        private void addHeader(PdfWriter writer){
            PdfPTable header = new PdfPTable(2);
            try {
                // set defaults
                header.setWidths(new int[]{2, 24});
                header.setTotalWidth(527);
                header.setLockedWidth(true);
                header.getDefaultCell().setFixedHeight(40);
                header.getDefaultCell().setBorder(Rectangle.BOTTOM);
                header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

                // add image
                Image logo = Image.getInstance("src/com/java/resources/images/utec-Isotipo.png");
                header.addCell(logo);

                // add text
                PdfPCell text = new PdfPCell();
                text.setPaddingBottom(15);
                text.setPaddingLeft(10);
                text.setBorder(Rectangle.BOTTOM);
                text.setBorderColor(BaseColor.LIGHT_GRAY);
                text.addElement(Chunk.NEWLINE);
                text.addElement(Chunk.NEWLINE);
                header.addCell(text);

                // write content
                header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
                
            } catch(DocumentException de) {
                throw new ExceptionConverter(de);
            } catch (MalformedURLException e) {
                throw new ExceptionConverter(e);
            } catch (IOException e) {
                throw new ExceptionConverter(e);
            }
        }
        
        private void addFooter(PdfWriter writer){
            PdfPTable footer = new PdfPTable(3);
            try {
                // set defaults
                footer.setWidths(new int[]{24, 2, 1});
                footer.setTotalWidth(527);
                footer.setLockedWidth(true);
                footer.getDefaultCell().setFixedHeight(40);
                footer.getDefaultCell().setBorder(Rectangle.TOP);
                footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

                // add copyright
                footer.addCell(new Phrase("\u00A9 Universidad Tecnológica", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));

                // add current page count
                footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                footer.addCell(new Phrase(String.format("Página %d", writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)));

                // add placeholder for total page count
                PdfPCell totalPageCount = new PdfPCell();
                totalPageCount.setBorder(Rectangle.TOP);
                totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
                footer.addCell(totalPageCount);

                // write page
                PdfContentByte canvas = writer.getDirectContent();
                canvas.beginMarkedContentSequence(PdfName.ALLPAGES);
                footer.writeSelectedRows(0, -1, 34, 50, canvas);
                canvas.endMarkedContentSequence();
            } catch(DocumentException de) {
                throw new ExceptionConverter(de);
            }
        }
        
        @Override
        public void onCloseDocument(PdfWriter writer, Document document) {
            int totalLength = String.valueOf(writer.getPageNumber()).length();
            int totalWidth = totalLength * 5;
            ColumnText.showTextAligned(t, Element.ALIGN_RIGHT,
                    new Phrase(String.valueOf(writer.getPageNumber()-1), new Font(Font.FontFamily.HELVETICA, 8)),
                    totalWidth, 6, 0);
        }

    }
    
    public PDFBuilder setPdfTitle(String title) {
    	this.docTitle = title;
    	return this;
    }
    
    public PDFBuilder setPdfAuthor(String author) {
    	this.author = author;
    	return this;
    }
	
}
