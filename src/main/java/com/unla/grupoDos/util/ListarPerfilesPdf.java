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

		
		cell.setPhrase(new Phrase("Nombre",font));
		tablaPerfiles.addCell(cell);
		cell.setPhrase(new Phrase("Descripcion",font));
		tablaPerfiles.addCell(cell);
		
		Font fontParaFilas = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontParaFilas.setColor(Color.BLACK);
		
		for(int i = 0; i < listadoPerfiles.size();i++) {
			if(i % 2 == 0)cell.setBackgroundColor(primerColorParaCeldas);
			
			cell.setPhrase(new Phrase(listadoPerfiles.get(i).getNombre(),fontParaFilas));
			tablaPerfiles.addCell(cell);
			cell.setPhrase(new Phrase(listadoPerfiles.get(i).getDescripcion(),fontParaFilas));
			tablaPerfiles.addCell(cell);
			cell.setBackgroundColor(segundoColorParaCeldas);
		}
		
		document.addTitle("perfiles");
		document.add(tablaPerfiles);
		document.addCreationDate();
	}

}
