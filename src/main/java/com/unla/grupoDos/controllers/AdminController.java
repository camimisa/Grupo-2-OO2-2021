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

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public String indexAdmin(Model model, @RequestParam(name="nombreUsuario",required=false) String nombreUsuario) {
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 nombreUsuario = auth.getName();
		model.addAttribute("nombreUsuario", nombreUsuario);
		return ViewRouteHelper.INICIO_ADMIN;
	}	
	
	@GetMapping(value={"/usuarios","/usuarios/"})
	public ModelAndView vistaUsuariosAdmin() {
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String nombreUsuario = auth.getName();
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.VISTA_USUARIOS_INDEX);
		mAV.addObject("listaUsuarios", usuarioService.getAll());
		mAV.addObject("usuario", new UsuarioModel());
		mAV.addObject("usuarioActual",usuarioService.findByNombreUsuario(nombreUsuario));
		
		return mAV;
	}
	
	@GetMapping("/usuario/nuevo")
	public String usuario(Model model) {
		model.addAttribute("usuario", new UsuarioModel());
		model.addAttribute("listaPerfiles", perfilService.traerPerfilesHabilitados());
		return ViewRouteHelper.VISTA_USUARIO_NUEVO;
	}
	
	@PostMapping("/usuario/crear")
	public RedirectView create(@ModelAttribute("usuario") UsuarioModel usuarioModel, RedirectAttributes atts) {

		PerfilModel m = perfilService.findById(usuarioModel.getIdPerfil());
		Perfil p = perfilConverter.modeloAEntidad(m);
		usuarioModel.setPerfil(p);
		
		if(corroborarUsuario(usuarioModel)) {
			usuarioService.insertOrUpdate(usuarioModel);
			atts.addFlashAttribute("guardado", true);
		}
		else
			atts.addFlashAttribute("errorUsuario", true);
	
		return new RedirectView(ViewRouteHelper.ADMINISTRADOR_ROOT_USUARIOS);
	}
	
	@GetMapping("/usuario/{id}")
	public ModelAndView get(@PathVariable("id") int id) {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.VISTA_USUARIO_ACTUALIZAR);
		mAV.addObject("usuario", usuarioService.findById(id));
		mAV.addObject("listaPerfiles", perfilService.traerPerfilesHabilitados());
		return mAV;
	}
	
	@PostMapping("/usuario/actualizar")
	public RedirectView actualizarUsuario(@ModelAttribute("usuario") UsuarioModel usuario,RedirectAttributes atts) {
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UsuarioModel usuarioActual = usuarioService.findByNombreUsuario(auth.getName());
		
		if(!usuarioActual.equals(usuario)) {
			PerfilModel m = perfilService.findById(usuario.getIdPerfil());
			Perfil p = perfilConverter.modeloAEntidad(m);
			usuario.setPerfil(p);
			
			if(corroborarUsuario(usuario)) {
				usuarioService.insertOrUpdate(usuario);
				atts.addFlashAttribute("guardado", true);
			}
			else
				atts.addFlashAttribute("errorUsuario", true);			
		}
		
		return new RedirectView(ViewRouteHelper.ADMINISTRADOR_ROOT_USUARIOS);
	}
	
	@GetMapping("/usuario/eliminar/{id}")
	public RedirectView eliminarUsuario(@PathVariable("id") int idUsuario, RedirectAttributes atts) {
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UsuarioModel usuarioActual = usuarioService.findByNombreUsuario(auth.getName());
		if(!(usuarioActual.getIdUsuario() == idUsuario)) {
			usuarioService.remove(idUsuario);
			atts.addFlashAttribute("eliminado", true);
		}
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
	
	@GetMapping(value = {"/perfiles", "/perfiles/"})
	public ModelAndView perfilIndex() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.VISTA_PERFIL_INDEX);
		mAV.addObject("listaPerfiles", perfilService.getAll());
		mAV.addObject("perfil", new PerfilModel());
		return mAV;
	}
	
	@GetMapping("/perfil/actualizar/{id}")
	public ModelAndView perfilActualizar(
			@PathVariable("id") int id, 
			@RequestParam(required = false, name = "error") String error
	) {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.VISTA_PERFIL_ACTUALIZAR);
		mAV.addObject("perfil", perfilService.findById(id));
		mAV.addObject("error", error);
		return mAV;
	}
	
	@GetMapping("/perfil/nuevo")
	public String nuevo(
			Model model,
			@RequestParam(required = false, name = "error") String error
	) {
		model.addAttribute("perfil", new PerfilModel());
		model.addAttribute("error", error);
		return ViewRouteHelper.VISTA_PERFIL_NUEVO;
	}
	
	@PostMapping("/perfil/actualizar/{id}")
	public RedirectView perfilActualizar(
			@PathVariable("id") int id,
			@ModelAttribute("perfil") PerfilModel perfil
	) {
		return perfilUpsert(perfil, ViewRouteHelper.ADMINISTRADOR_ROOT_PERFILES, ViewRouteHelper.VISTA_PERFIL_ACTUALIZAR);
	}
	
	@PostMapping("/perfil/nuevo")
	public RedirectView perfilNuevo(@ModelAttribute("perfil") PerfilModel perfil) {
		return perfilUpsert(perfil, ViewRouteHelper.ADMINISTRADOR_ROOT_PERFILES, ViewRouteHelper.VISTA_PERFIL_NUEVO);
	}
	
	private RedirectView perfilUpsert(PerfilModel perfil, String rutaExitosa, String rutaErronea) {
		RedirectView rView = new RedirectView();
		rView.setContextRelative(true);
		if (corroborarPerfil(perfil)) {
			try {
				perfilService.insertOrUpdate(perfil);				
			} catch(Exception e) {
				System.out.println(e);
			}
			rView.setUrl(rutaExitosa);
		} else {
			String queryParams = "?error=Ya existe un perfil con el nombre \"" + perfil.getNombre() + "\"";
			Boolean incluirId = perfil.getIdPerfil() != 0;
			rView.setUrl(rutaErronea + (incluirId ? "/" + perfil.getIdPerfil() : "") + queryParams);
		}
		return rView;
	}
	
	@GetMapping("/perfil/deshabilitar/{id}")
	public RedirectView perfilDeshabilitar(@PathVariable("id") int id) {
		RedirectView rView = new RedirectView(ViewRouteHelper.ADMINISTRADOR_ROOT_PERFILES, true);
		PerfilModel perfil = perfilService.findById(id);
		if (usuarioService.findByIdPerfil(id).size() > 0) {
			rView.addStaticAttribute("error", "El perfil que intentó deshablitar está asociado a al menos un usuario.");
			System.out.println("Tiene usuarios asociados");
		}else { 
			perfil.setHabilitado(false);
			perfilService.insertOrUpdate(perfil);
		}
		return rView;
	}
	
	@GetMapping("/perfil/habilitar/{id}")
	public RedirectView perfilHabilitar(@PathVariable("id") int id) {
		RedirectView rView = new RedirectView(ViewRouteHelper.ADMINISTRADOR_ROOT_PERFILES, true);
		PerfilModel perfil = perfilService.findById(id);
		perfil.setHabilitado(true);
		perfilService.insertOrUpdate(perfil);
		return rView;
	}
	
	private boolean corroborarPerfil(PerfilModel perfil) {
		boolean perfilValido = true;
		Iterator<Perfil> perfilesIt = perfilService.getAll().iterator();
		PerfilConverter perfilConverter = new PerfilConverter();
		Perfil perfilEnt = perfilConverter.modeloAEntidad(perfil);
		while (perfilesIt.hasNext() && perfilValido) {
			Perfil perfilIt = perfilesIt.next();
			if (perfilIt.equals(perfilEnt) 
				&& perfilIt.getIdPerfil() != perfilEnt.getIdPerfil()
			) {
				perfilValido = false;
			}
		}
		return perfilValido;
	}
	
	
}
