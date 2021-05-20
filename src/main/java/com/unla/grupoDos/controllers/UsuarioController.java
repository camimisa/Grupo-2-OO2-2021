package com.unla.grupoDos.controllers;

import java.util.ArrayList;

import com.unla.grupoDos.converters.UsuarioConverter;
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
import org.springframework.web.servlet.view.RedirectView;


import com.unla.grupoDos.models.*;
import com.unla.grupoDos.services.IUsuarioService;
@Controller
@RequestMapping("/")
public class UsuarioController {

	@Autowired
	@Qualifier("usuarioService")
	private IUsuarioService usuarioService;
	
	// ------------------------------ PERMISOS ADMIN ---------------------------
	
	@GetMapping("/admin")
	public ModelAndView indexAdmin() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.VISTA_USUARIOS_ADMINISTRADOR);
		mAV.addObject("listaUsuarios", usuarioService.getAll());
		mAV.addObject("usuario", new UsuarioModel());
		return mAV;
	}
	
	
	@GetMapping("/admin/nuevoUsuario")
	public String usuario(Model model) {
		model.addAttribute("usuario", new UsuarioModel());
		return ViewRouteHelper.NUEVO_USUARIO_ADMINISTRADOR;
	}
	
	@PostMapping("/admin/crear")
	public RedirectView create(@ModelAttribute("usuario") UsuarioModel usuarioModel) {
		if(corroborarUsuario(usuarioModel))
			usuarioService.insertOrUpdate(usuarioModel);
		else
			System.out.println("Mismo dni o user. Implementar aviso"); //TODO
		return new RedirectView(ViewRouteHelper.ADMINISTRADOR_ROOT);
	}
	
	@GetMapping("/admin/{id}")
	public ModelAndView get(@PathVariable("id") int id) {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.ACTUALIZAR_USUARIO_ADMINISTRADOR);
		mAV.addObject("usuario", usuarioService.findById(id));
		return mAV;
	}
	
	@PostMapping("/admin/actualizar")
	public RedirectView actualizarUsuario(@ModelAttribute("usuario") UsuarioModel usuario) {
		if(corroborarUsuario(usuario))
			usuarioService.insertOrUpdate(usuario);
		else
			System.out.println("Mismo dni o user. Implementar aviso"); //TODO
		return new RedirectView(ViewRouteHelper.ADMINISTRADOR_ROOT);
	}
	
	@GetMapping("/admin/eliminarUsuario/{id}")
	public RedirectView eliminarUsuario(@PathVariable("id") int idUsuario) {
		usuarioService.remove(idUsuario);
		return new RedirectView(ViewRouteHelper.ADMINISTRADOR_ROOT);
	}
	
	private boolean corroborarUsuario(UsuarioModel usuarioModel) {
		boolean valido = true;
		List<Usuario> listaUsuarios = usuarioService.getAll();
		UsuarioConverter usuarioConverter = new UsuarioConverter();
		Usuario usuario = usuarioConverter.modeloAEntidad(usuarioModel);
		
		for(Usuario u: listaUsuarios) {
			if((u.getDocumento() == usuario.getDocumento()) || 
					(u.getNombreUsuario().equalsIgnoreCase(usuario.getNombreUsuario())))
				valido = false;
		}
		return valido;
	}
	
	
	// ------------------------------ PERMISOS AUDITOR ---------------------------
	
	@GetMapping("/auditor")
	public ModelAndView indexAuditor() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.VISTA_USUARIOS_AUDITOR);
		mAV.addObject("listaUsuarios", usuarioService.getAll());
		mAV.addObject("usuario", new UsuarioModel());
		return mAV;
	}
	
	@GetMapping("/auditor/descargar/perfiles")
	public ModelAndView indexAdminDescargar() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.VISTA_USUARIOS_AUDITOR_DESCARGAR_USUARIOS);
		mAV.addObject("listaUsuarios", usuarioService.getAll());
		mAV.addObject("usuario", new UsuarioModel());
		return mAV;
	}
}
