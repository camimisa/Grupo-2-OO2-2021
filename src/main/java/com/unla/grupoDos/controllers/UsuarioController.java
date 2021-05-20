package com.unla.grupoDos.controllers;

import java.util.ArrayList;

import com.unla.grupoDos.converters.PerfilConverter;
import com.unla.grupoDos.converters.UsuarioConverter;
import com.unla.grupoDos.entities.Perfil;
import com.unla.grupoDos.entities.Usuario;
import com.unla.grupoDos.helpers.*;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


import com.unla.grupoDos.models.*;
import com.unla.grupoDos.services.IPerfilService;
import com.unla.grupoDos.services.IUsuarioService;
@Controller
@RequestMapping("/")
public class UsuarioController {

	@Autowired
	@Qualifier("usuarioService")
	private IUsuarioService usuarioService;
	
	@Autowired
	@Qualifier("perfilService")
	private IPerfilService perfilService;
	@Autowired
	@Qualifier("perfilConverter")
	private PerfilConverter perfilConverter;
	
	// ------------------------------ PERMISOS ADMIN ---------------------------
	@GetMapping("/admin")
	public String indexAdmin() {
		return ViewRouteHelper.INICIO_ADMIN;
	}	
	
	@GetMapping("/admin/usuarios")
	public ModelAndView vistaUsuariosAdmin() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.VISTA_USUARIOS_ADMINISTRADOR);
		mAV.addObject("listaUsuarios", usuarioService.getAll());
		mAV.addObject("usuario", new UsuarioModel());
		
		return mAV;
	}
	
	@GetMapping("/admin/nuevoUsuario")
	public String usuario(Model model) {
		model.addAttribute("usuario", new UsuarioModel());
		model.addAttribute("listaPerfiles", perfilService.getAll());
		return ViewRouteHelper.NUEVO_USUARIO_ADMINISTRADOR;
	}
	
	@PostMapping("/admin/crear")
	public RedirectView create(@ModelAttribute("usuario") UsuarioModel usuarioModel, RedirectAttributes atts) {
		PerfilModel m = perfilService.findById(usuarioModel.getIdPerfil());
		Perfil p = perfilConverter.modeloAEntidad(m);
		usuarioModel.setPerfil(p);
		
		if(corroborarUsuario(usuarioModel))
			usuarioService.insertOrUpdate(usuarioModel);
		else
			atts.addFlashAttribute("errorUsuario", true);
		return new RedirectView(ViewRouteHelper.ADMINISTRADOR_ROOT_USUARIOS);
	}
	
	@GetMapping("/admin/{id}")
	public ModelAndView get(@PathVariable("id") int id) {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.ACTUALIZAR_USUARIO_ADMINISTRADOR);
		mAV.addObject("usuario", usuarioService.findById(id));
		mAV.addObject("listaPerfiles", perfilService.getAll());
		return mAV;
	}
	
	@PostMapping("/admin/actualizarUsuario")
	public RedirectView actualizarUsuario(@ModelAttribute("usuario") UsuarioModel usuario,RedirectAttributes atts) {
		PerfilModel m = perfilService.findById(usuario.getIdPerfil());
		Perfil p = perfilConverter.modeloAEntidad(m);
		usuario.setPerfil(p);
		
		if(corroborarUsuario(usuario))
			usuarioService.insertOrUpdate(usuario);
		else
			atts.addFlashAttribute("errorUsuario", true);
		return new RedirectView(ViewRouteHelper.ADMINISTRADOR_ROOT_USUARIOS);
	}
	
	@GetMapping("/admin/eliminarUsuario/{id}")
	public RedirectView eliminarUsuario(@PathVariable("id") int idUsuario, RedirectAttributes atts) {
		usuarioService.remove(idUsuario);
		atts.addFlashAttribute("eliminado", true);
		return new RedirectView(ViewRouteHelper.ADMINISTRADOR_ROOT_USUARIOS);
	}
	
	private boolean corroborarUsuario(UsuarioModel usuarioModel) {
		boolean valido = true;
		List<Usuario> listaUsuarios = usuarioService.getAll();
		UsuarioConverter usuarioConverter = new UsuarioConverter();
		Usuario usuario = usuarioConverter.modeloAEntidad(usuarioModel);
		
		for(Usuario u: listaUsuarios) {
			if(
				((u.getDocumento() == usuario.getDocumento()) 
				|| (u.getNombreUsuario().equalsIgnoreCase(usuario.getNombreUsuario()))) 
					&& !(u.getIdUsuario() == usuario.getIdUsuario())
				) 
			{
				valido = false;
			}
		}

		return valido;
	}
	
	// ------------------------------ PERMISOS AUDITOR ---------------------------
	@GetMapping("/auditor")
	public String indexAuditor() {
		return ViewRouteHelper.INICIO_AUDITOR;
	}
	@GetMapping("/auditor/usuarios")
	public ModelAndView vistaUsuariosAuditor() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.VISTA_USUARIOS_AUDITOR);
		mAV.addObject("listaUsuarios", usuarioService.getAll());
		mAV.addObject("usuario", new UsuarioModel());
		return mAV;
	}
	
	@GetMapping("/auditor/perfiles")
	public ModelAndView vistaPerfilesAuditor() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.VISTA_PERFILES_AUDITOR);
		mAV.addObject("listaPerfiles", perfilService.getAll());
		mAV.addObject("perfil", new PerfilModel());
		return mAV;
	}
	
	@GetMapping("/auditor/descargar/perfiles")
	public ModelAndView indexAdminDescargar() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.VISTA_USUARIOS_AUDITOR_DESCARGAR_USUARIOS);
		mAV.addObject("listaUsuarios", usuarioService.getAll());
		mAV.addObject("usuario", new UsuarioModel());
		return mAV;
	}
	
	// ---------------------LOG IN------------------------------
		@GetMapping("/login")
		public String login(Model model,
							@RequestParam(name="error",required=false) String error,
							@RequestParam(name="logout", required=false) String logout) {
			model.addAttribute("error", error);
			model.addAttribute("logout", logout);
			return ViewRouteHelper.USUARIO_LOGIN;
		}
		
		@GetMapping("/logout")
		public String logout(Model model) {
			return ViewRouteHelper.USUARIO_LOGOUT;
		}
		
		@PostMapping("/prueba")
		public RedirectView process() {
			System.out.println("ACA ANDA");
			return new RedirectView(ViewRouteHelper.USUARIO_LOGIN);
		}
		
		@GetMapping("/loginsuccess")
		public String loginCheck() {
			return "redirect:/index";
		}
}
