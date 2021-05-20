package com.unla.grupoDos.util;

import java.awt.Color;
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

@Component(ViewRouteHelper.PERFIL_INDEX_DESCARGAR)
public class ListarPerfilesPdf extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		@SuppressWarnings("unchecked")
		List<Perfil> listadoPerfiles = (List<Perfil>) model.get("listaPerfiles");
		
		PdfPTable tablaPerfiles = new PdfPTable(2);
		tablaPerfiles.setSpacingBefore(20);
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(Color.WHITE);
		
		
		
		document.add(new Paragraph("Lista de perfiles"));
		
		cell.setPhrase(new Phrase("Nombre",font));
		tablaPerfiles.addCell(cell);
		cell.setPhrase(new Phrase("Descripcion",font));
		tablaPerfiles.addCell(cell);
		
		Font fontParaFilas = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontParaFilas.setColor(Color.BLACK);
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		
		listadoPerfiles.forEach(perfil -> {
			cell.setPhrase(new Phrase(perfil.getNombre(),fontParaFilas));
			tablaPerfiles.addCell(cell);
			cell.setPhrase(new Phrase(perfil.getDescripcion(),fontParaFilas));
			tablaPerfiles.addCell(cell);
			
		});
		
		document.addTitle("perfiles");
		document.add(tablaPerfiles);
		document.addCreationDate();
	}

}
