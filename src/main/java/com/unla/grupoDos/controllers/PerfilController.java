package com.unla.grupoDos.controllers;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.grupoDos.entities.Perfil;
import com.unla.grupoDos.helpers.ViewRouteHelper;
import com.unla.grupoDos.models.PerfilModel;
import com.unla.grupoDos.services.IPerfilService;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

	@Autowired
	@Qualifier("perfilService")
	private IPerfilService perfilService;
	

	@GetMapping(value = {"/",""})
	public ModelAndView listar() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.PERFIL_INDEX);
		mAV.addObject("listaPerfiles", perfilService.getAll());
		mAV.addObject("perfil", new PerfilModel());
		return mAV;
	}
	
	@GetMapping("/actualizar/{id}")
	public ModelAndView actualizar(
			@PathVariable("id") int id, 
			@RequestParam(required = false, name = "invalidReason") String invalidReason
	) {
		System.out.println("get actualizar");
		System.out.println(id);
		System.out.println(invalidReason);
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.ACTUALIZAR_PERFIL);
		mAV.addObject("perfil", perfilService.findById(id));
		mAV.addObject("invalidReason", invalidReason);
		return mAV;
	}
	
	@PostMapping("/actualizar/{id}")
	public RedirectView actualizar(
			@PathVariable("id") int id,
			@ModelAttribute("perfil") PerfilModel perfil) {
		System.out.println("post actualizar");
		System.out.println(perfil);
		RedirectView rView = new RedirectView(ViewRouteHelper.ACTUALIZAR_PERFIL);
		if (!nombreExiste(perfil)) {
			perfilService.insertOrUpdate(perfil);
		} else {
			String invalidReason = "El nombre seleccionado \"" + perfil.getNombre() + "\" ya existe.";
			rView.setUrl(rView.getUrl() + "?invalidReason=" + invalidReason);
		}
		System.out.println("url: " + rView.getUrl());
		return rView;
	}
	
	@GetMapping("/nuevo")
	public ModelAndView nuevo() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.NUEVO_PERFIL);
		return mAV;
	}
	
	@GetMapping("/eliminar/{id}")
	public RedirectView eliminar(@PathVariable("id") int id) {
		try {
			perfilService.remove(id);			
		} catch(Exception e) {
			
		}
		return new RedirectView(ViewRouteHelper.PERFIL_INDEX);
	}
	
	private boolean nombreExiste(PerfilModel perfil) {
		boolean valido = true;
		Iterator<Perfil> perfilesIt = perfilService.getAll().iterator();
		while (perfilesIt.hasNext() && !valido) {
			Perfil perfilIt = perfilesIt.next();
			if (perfilIt.equals(perfil)) {
				valido = false;
			}
		}
		return valido;
	}
	
}
