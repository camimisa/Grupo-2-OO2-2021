package com.unla.grupoDos.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.unla.grupoDos.helpers.ViewRouteHelper;
import com.unla.grupoDos.models.PerfilModel;
import com.unla.grupoDos.models.UsuarioModel;
import com.unla.grupoDos.services.IPerfilService;
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
	
	@GetMapping("/descargar/perfiles")
	public ModelAndView indexAdminDescargar() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.VISTA_USUARIOS_AUDITOR_DESCARGAR_USUARIOS);
		mAV.addObject("listaUsuarios", usuarioService.getAll());
		mAV.addObject("usuario", new UsuarioModel());
		return mAV;
	}
}
