package com.unla.grupoDos.controllers;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.grupoDos.converters.PerfilConverter;
import com.unla.grupoDos.converters.UsuarioConverter;
import com.unla.grupoDos.entities.Perfil;
import com.unla.grupoDos.entities.Usuario;
import com.unla.grupoDos.helpers.ViewRouteHelper;
import com.unla.grupoDos.models.PerfilModel;
import com.unla.grupoDos.repositories.IUsuarioRepository;
import com.unla.grupoDos.services.IPerfilService;
import com.unla.grupoDos.services.implementation.UsuarioService;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

	@Autowired
	@Qualifier("perfilService")
	private IPerfilService perfilService;
	@Autowired
	@Qualifier("usuarioService")
	UsuarioService usuarioService;
	

	@GetMapping(value = {"/listado","","/"})
	public ModelAndView listar() {
		System.out.println("INDEX");
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.PERFIL_INDEX);
		mAV.addObject("listaPerfiles", perfilService.getAll());
		mAV.addObject("perfil", new PerfilModel());
		return mAV;
	}
	
	@GetMapping(value = {"/descargar/perfiles"})
	public ModelAndView descargarLista() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.PERFIL_INDEX_DESCARGAR);
		mAV.addObject("listaPerfiles", perfilService.getAll());
		mAV.addObject("perfil", new PerfilModel());
		return mAV;
	}
	
	@GetMapping("/actualizar/{id}")
	public ModelAndView actualizar(
			@PathVariable("id") int id, 
			@RequestParam(required = false, name = "error") String error
	) {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.ACTUALIZAR_PERFIL);
		mAV.addObject("perfil", perfilService.findById(id));
		mAV.addObject("error", error);
		return mAV;
	}
	
	@GetMapping("/nuevo")
	public String nuevo(
			Model model,
			@RequestParam(required = false, name = "error") String error
	) {
		System.out.println("go to nuevo perfil");
		model.addAttribute("perfil", new PerfilModel());
		model.addAttribute("error", error);
		return ViewRouteHelper.NUEVO_PERFIL;
	}
	
	@PostMapping("/actualizar/{id}")
	public RedirectView actualizar(
			@PathVariable("id") int id,
			@ModelAttribute("perfil") PerfilModel perfil
	) {
		return upsert(perfil, ViewRouteHelper.PERFIL_INDEX, ViewRouteHelper.ACTUALIZAR_PERFIL);
	}
	
	@PostMapping("/nuevo")
	public RedirectView nuevo(@ModelAttribute("perfil") PerfilModel perfil) {
		return upsert(perfil, ViewRouteHelper.PERFIL_INDEX, ViewRouteHelper.NUEVO_PERFIL);
	}
	
	private RedirectView upsert(PerfilModel perfil, String rutaExitosa, String rutaErronea) {
		RedirectView rView = new RedirectView();
		rView.setContextRelative(true);
		if (corroborarPerfil(perfil)) {
			try {
				perfilService.insertOrUpdate(perfil);				
			} catch(Exception e) {
				System.out.println(e);
				System.out.println(e.getLocalizedMessage());
				System.out.println(e.getMessage());
			}
			rView.setUrl(rutaExitosa);
		} else {
			String queryParams = "?error=Ya existe un perfil con el nombre \"" + perfil.getNombre() + "\"";
			Boolean includeId = perfil.getIdPerfil() != 0;
			rView.setUrl(rutaErronea + (includeId ? "/" + perfil.getIdPerfil() : "") + queryParams);
		}
		System.out.println(rView.getUrl());
		return rView;
	}
	
	@GetMapping("/eliminar/{id}")
	public RedirectView eliminar(@PathVariable("id") int id) {
		RedirectView rView = new RedirectView(ViewRouteHelper.PERFIL_INDEX, true);
		if (usuarioService.findByIdPerfil(id).size() > 0) {
			rView.setUrl(rView.getUrl() + " ?error=El perfil que intentó eliminar está asociado a almenos un usuario.");
		}else { 
			perfilService.remove(id);
		}
		
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
