package com.unla.grupoDos.util;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.unla.grupoDos.entities.Usuario;
import com.unla.grupoDos.helpers.ViewRouteHelper;
import com.unla.grupoDos.models.UsuarioModel;

@Component(ViewRouteHelper.VISTA_USUARIOS_ADMINISTRADOR)
public class ListarClientesPdf extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		@SuppressWarnings("unchecked")
		List<Usuario> listadoUsuarios = (List<Usuario>) model.get("listaUsuarios");
		
		PdfPTable tablaUsuarios = new PdfPTable(6);
		
		tablaUsuarios.addCell("Apellido");
		tablaUsuarios.addCell("Nombre");
		tablaUsuarios.addCell("Nombre de usuario");
		tablaUsuarios.addCell("email");
		tablaUsuarios.addCell("tipo documento");
		tablaUsuarios.addCell("dni");
		
		listadoUsuarios.forEach(usuario -> {
			tablaUsuarios.addCell(usuario.getApellido());
			tablaUsuarios.addCell(usuario.getNombre());
			tablaUsuarios.addCell(usuario.getNombreUsuario());
			tablaUsuarios.addCell(usuario.getEmail());
			tablaUsuarios.addCell(usuario.getTipoDoc());
			tablaUsuarios.addCell("11222333");
		});
		
		document.add(tablaUsuarios);
	}

}
