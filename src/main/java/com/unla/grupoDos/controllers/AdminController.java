package com.unla.grupoDos.controllers;

import com.unla.grupoDos.converters.PerfilConverter;
import com.unla.grupoDos.converters.UsuarioConverter;
import com.unla.grupoDos.entities.Perfil;
import com.unla.grupoDos.entities.Usuario;
import com.unla.grupoDos.helpers.*;
import com.unla.grupoDos.models.PerfilModel;
import com.unla.grupoDos.models.UsuarioModel;
import com.unla.grupoDos.services.IPerfilService;
import com.unla.grupoDos.services.IUsuarioService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	@Qualifier("usuarioService")
	private IUsuarioService usuarioService;
	
	@Autowired
	@Qualifier("perfilService")
	private IPerfilService perfilService;
	@Autowired
	@Qualifier("perfilConverter")
	private PerfilConverter perfilConverter;
	
	
	@GetMapping("/")
	public String indexAdmin() {
		return ViewRouteHelper.INICIO_ADMIN;
	}	
	
	@GetMapping("/usuarios")
	public ModelAndView vistaUsuariosAdmin() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.VISTA_USUARIOS_ADMINISTRADOR);
		mAV.addObject("listaUsuarios", usuarioService.getAll());
		mAV.addObject("usuario", new UsuarioModel());
		
		return mAV;
	}
	
	@GetMapping("/nuevoUsuario")
	public String usuario(Model model) {
		model.addAttribute("usuario", new UsuarioModel());
		model.addAttribute("listaPerfiles", perfilService.getAll());
		return ViewRouteHelper.NUEVO_USUARIO_ADMINISTRADOR;
	}
	
	@PostMapping("/crear")
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
	
	@GetMapping("/{id}")
	public ModelAndView get(@PathVariable("id") int id) {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.ACTUALIZAR_USUARIO_ADMINISTRADOR);
		mAV.addObject("usuario", usuarioService.findById(id));
		mAV.addObject("listaPerfiles", perfilService.getAll());
		return mAV;
	}
	
	@PostMapping("/actualizarUsuario")
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
	
	@GetMapping("/eliminarUsuario/{id}")
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
				valido = false;
		}

		return valido;
	}
	
}
