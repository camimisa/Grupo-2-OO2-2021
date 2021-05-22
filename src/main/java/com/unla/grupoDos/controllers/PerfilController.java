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
	

	
	@GetMapping(value = {"/descargar/perfiles"})
	public ModelAndView descargarLista() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.PERFIL_INDEX_DESCARGAR);
		mAV.addObject("listaPerfiles", perfilService.getAll());
		mAV.addObject("perfil", new PerfilModel());
		return mAV;
	}
	
}
