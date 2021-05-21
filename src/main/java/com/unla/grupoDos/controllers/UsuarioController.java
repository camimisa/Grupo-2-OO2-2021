package com.unla.grupoDos.controllers;


import com.unla.grupoDos.converters.PerfilConverter;
import com.unla.grupoDos.helpers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		
		@GetMapping("/loginsuccess")
		public String loginCheck() {
			return ViewRouteHelper.INDEX;
		}
}
