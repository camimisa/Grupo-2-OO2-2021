package com.unla.grupoDos.util;

import java.awt.Color;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.unla.grupoDos.entities.Perfil;
import com.unla.grupoDos.helpers.ViewRouteHelper;

@Component(ViewRouteHelper.VISTA_AUDITOR_DESCARGAR_PERFILES)
public class ListarPerfilesPdf extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		@SuppressWarnings("unchecked")
		List<Perfil> listadoPerfiles = (List<Perfil>) model.get("listaPerfiles");
		
		response.setHeader("Content-Disposition","attachment; filename=Lista-de-perfiles"+LocalDate.now().toString()+".pdf");
		PdfPTable tablaPerfiles = new PdfPTable(2);
		tablaPerfiles.setSpacingBefore(20);
		
		PdfPCell cell = new PdfPCell();
		Color colorParaCeldasHeader = new Color(252, 144, 121);
		Color primerColorParaCeldas = new Color(253,239,236);
		Color segundoColorParaCeldas = new Color(250,225,220);
		cell.setBackgroundColor(colorParaCeldasHeader);
		cell.setPadding(5);
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE);
		font.setColor(Color.BLACK);
		
		Paragraph titulo = new Paragraph("Listado de perfiles", font);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(titulo);

		
		dibujarHeader(tablaPerfiles,cell,font);
		dibujarTabla(listadoPerfiles,tablaPerfiles,cell,primerColorParaCeldas,segundoColorParaCeldas,font);
	
		document.add(tablaPerfiles);
	}
	
	private void dibujarTabla(List<Perfil> listadoPerfiles,PdfPTable tablaPerfiles,PdfPCell cell,Color primerColor, Color segundoColor, Font font) {
		
		for(int i = 0; i < listadoPerfiles.size();i++) {
			if(i % 2 == 0)cell.setBackgroundColor(primerColor);
			
			cell.setPhrase(new Phrase(listadoPerfiles.get(i).getNombre(),font));
			tablaPerfiles.addCell(cell);
			cell.setPhrase(new Phrase(listadoPerfiles.get(i).getDescripcion(),font));
			tablaPerfiles.addCell(cell);
			cell.setBackgroundColor(segundoColor);
		}
	}
	
	private void dibujarHeader(PdfPTable tablaPerfiles, PdfPCell cell, Font font) {
		
		cell.setPhrase(new Phrase("Nombre",font));
		tablaPerfiles.addCell(cell);
		cell.setPhrase(new Phrase("Descripcion",font));
		tablaPerfiles.addCell(cell);
	}

}
