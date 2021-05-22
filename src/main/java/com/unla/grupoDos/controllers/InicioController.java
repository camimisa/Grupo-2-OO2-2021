package com.unla.grupoDos.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.unla.grupoDos.helpers.ViewRouteHelper;
@Controller
@RequestMapping("/")
public class InicioController {
	
	@GetMapping("/")
	public String index(Model model, @RequestParam(name="nombreUsuario",required=false) String perfil) {
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		perfil = (auth.getAuthorities().toString()).toUpperCase();
		// getAuthorities devuelve una coleccion, entonces devuelve [AUDITOR]
		// utilizo el substring para eliminar los []
		perfil = perfil.substring(1, perfil.length()-1);
		model.addAttribute("perfil", perfil);
		System.out.println("\n\n\nPERFIL: " + perfil + "\n\n\n");
		return ViewRouteHelper.INDEX;
	}	
	
}
