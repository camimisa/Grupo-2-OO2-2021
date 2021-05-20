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
import com.unla.grupoDos.entities.Usuario;
import com.unla.grupoDos.helpers.ViewRouteHelper;

@Component(ViewRouteHelper.VISTA_USUARIOS_AUDITOR_DESCARGAR_USUARIOS)
public class ListarUsuariosPdf extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		@SuppressWarnings("unchecked")
		List<Usuario> listadoUsuarios = (List<Usuario>) model.get("listaUsuarios");
		
		PdfPTable tablaUsuarios = new PdfPTable(7);
		tablaUsuarios.setSpacingBefore(20);
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(Color.WHITE);
		
		
		
		document.add(new Paragraph("Lista de usuarios"));
		
		cell.setPhrase(new Phrase("Apellido",font));
		tablaUsuarios.addCell(cell);
		cell.setPhrase(new Phrase("Nombre",font));
		tablaUsuarios.addCell(cell);
		cell.setPhrase(new Phrase("Nombre de usuario",font));
		tablaUsuarios.addCell(cell);
		cell.setPhrase(new Phrase("Email",font));
		tablaUsuarios.addCell(cell);
		cell.setPhrase(new Phrase("Tipo documento",font));
		tablaUsuarios.addCell(cell);
		cell.setPhrase(new Phrase("DNI",font));
		tablaUsuarios.addCell(cell);
		cell.setPhrase(new Phrase("Tipo de usuario",font));
		tablaUsuarios.addCell(cell);
		
		Font fontParaFilas = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontParaFilas.setColor(Color.BLACK);
		cell.setBackgroundColor(Color.LIGHT_GRAY);
		
		listadoUsuarios.forEach(usuario -> {
			String dni = String.valueOf(usuario.getDocumento());
			cell.setPhrase(new Phrase(usuario.getApellido(),fontParaFilas));
			tablaUsuarios.addCell(cell);
			cell.setPhrase(new Phrase(usuario.getNombre(),fontParaFilas));
			tablaUsuarios.addCell(cell);
			cell.setPhrase(new Phrase(usuario.getNombreUsuario(),fontParaFilas));
			tablaUsuarios.addCell(cell);
			cell.setPhrase(new Phrase(usuario.getEmail(),fontParaFilas));
			tablaUsuarios.addCell(cell);
			cell.setPhrase(new Phrase(usuario.getTipoDoc(),fontParaFilas));
			tablaUsuarios.addCell(cell);
			cell.setPhrase(new Phrase(dni,fontParaFilas));
			tablaUsuarios.addCell(cell);
			cell.setPhrase(new Phrase("perfil",fontParaFilas));
			tablaUsuarios.addCell(cell);
		});
		
		document.addTitle("usuarios");
		document.add(tablaUsuarios);
		document.addCreationDate();
	}

}
