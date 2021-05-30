package com.unla.grupoDos.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.grupoDos.entities.Permiso;
import com.unla.grupoDos.entities.PermisoDiario;
import com.unla.grupoDos.helpers.ViewRouteHelper;
import com.unla.grupoDos.models.PerfilModel;
import com.unla.grupoDos.models.PermisoModel;
import com.unla.grupoDos.models.PermisoPeriodoModel;
import com.unla.grupoDos.models.UsuarioModel;
import com.unla.grupoDos.services.IPerfilService;
import com.unla.grupoDos.services.IPermisoService;
import com.unla.grupoDos.services.IUsuarioService;

@Controller
@RequestMapping("/auditor")
public class AuditorController {
	
	@Autowired
	@Qualifier("usuarioService")
	private IUsuarioService usuarioService;
	
	@Autowired
	@Qualifier("perfilService")
	private IPerfilService perfilService;
	
	@Autowired
	@Qualifier("permisoService")
	private IPermisoService permisoService;

	// ------------------------------ PERMISOS AUDITOR ---------------------------
	@GetMapping("/")
	public String indexAuditor(Model model, @RequestParam(name="nombreUsuario",required=false) String nombreUsuario) {
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		nombreUsuario = auth.getName();
		model.addAttribute("nombreUsuario", nombreUsuario);
		return ViewRouteHelper.INICIO_AUDITOR;
	}
	@GetMapping("/usuarios")
	public ModelAndView vistaUsuariosAuditor() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.VISTA_USUARIOS_AUDITOR);
		mAV.addObject("listaUsuarios", usuarioService.getAll());
		mAV.addObject("usuario", new UsuarioModel());
		return mAV;
	}
	
	@GetMapping("/perfiles")
	public ModelAndView vistaPerfilesAuditor() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.VISTA_PERFILES_AUDITOR);
		mAV.addObject("listaPerfiles", perfilService.getAll());
		mAV.addObject("perfil", new PerfilModel());
		return mAV;
	}
	
	@GetMapping("/usuarios/descargar")
	public ModelAndView indexAdminDescargar() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.VISTA_AUDITOR_DESCARGAR_USUARIOS);
		mAV.addObject("listaUsuarios", usuarioService.getAll());
		mAV.addObject("usuario", new UsuarioModel());
		return mAV;
	}
	
	@GetMapping("/perfiles/descargar")
	public ModelAndView descargarLista() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.VISTA_AUDITOR_DESCARGAR_PERFILES);
		mAV.addObject("listaPerfiles", perfilService.getAll());
		mAV.addObject("perfil", new PerfilModel());
		return mAV;
	}
	
	// ------------------------------ ACCIONES CON PERMISOS ---------------------------
	@GetMapping("/permiso/buscarEntreFechas")
	public String preguntaPermisoDiario(Model model) {
		return ViewRouteHelper.PREGUNTA_ENTRE_FECHAS;
	}

	@GetMapping("/permiso/buscarPermisosFecha")
	public ModelAndView formularioPermisoDiario( @RequestParam(name="desdeFecha", required = true) String desdeFecha,
			@RequestParam(name="hastaFecha", required = true) String hastaFecha) {
		
		LocalDate hasta = LocalDate.parse(hastaFecha);
		LocalDate desde = LocalDate.parse(desdeFecha);
		List<Permiso>permisosActivos = permisoService.getAllBetweenDates(desde, hasta);
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.LISTADO_PERMISOS);
		
		mAV.addObject("permisosActivos", permisosActivos);
		String titulo = "Permisos activos entre " + desde + " hasta " + hasta;
		mAV.addObject("titulo", titulo );
		return mAV;
	}
	
	
}
