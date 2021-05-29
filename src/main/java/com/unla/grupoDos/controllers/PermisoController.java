package com.unla.grupoDos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.grupoDos.converters.PersonaConverter;
import com.unla.grupoDos.converters.RodadoConverter;
import com.unla.grupoDos.models.PersonaModel;
import com.unla.grupoDos.models.RodadoModel;
import com.unla.grupoDos.services.IPersonaService;
import com.unla.grupoDos.services.IRodadoService;

@Controller
@RequestMapping("/permiso")
public class PermisoController {

	@Autowired
	@Qualifier("rodadoService")
	private IRodadoService rodadoService;
	@Autowired
	@Qualifier("rodadoConverter")
	private RodadoConverter rodadoConverter;
	
	@Autowired
	@Qualifier("personaService")
	private IPersonaService personaService;
	@Autowired
	@Qualifier("personaConverter")
	private PersonaConverter personaConverter;
	
	// --------------- PERMISO DIARIO -------------------------
	
	@GetMapping("/diario/pregunta")
	public String preguntaPermisoDiario(Model model) {
		return "permiso/permisoDiario/pregunta";
	}

	@GetMapping("/diario/buscarDatos")
	public RedirectView formularioPermisoDiario(Model model, @RequestParam(name="documento", required = false) long documento) {
		//PermisoPeriodoModel permiso = new PermisoPeriodoModel();
		PersonaModel persona = new PersonaModel();
		if(documento != 0) {
			persona = personaService.findByDni(Long.valueOf(documento));
			// permiso.setPedido(personaService.findByDni(Long.valueOf(documento)));
		}
		model.addAttribute("persona", persona);
		//model.addAttribute("pedido", pedido);
		return new RedirectView("permiso/diario/nuevo");
	}
	
	@GetMapping("/diario/nuevo")
	public String nuevoPermisoDiario(Model model) {
		return "permiso/nuevo";
	}
	
	// --------------- PERMISO PERIODO -------------------------
	
	@GetMapping("/periodo/pregunta")
	public String preguntaPermisoPeriodo(Model model) {
		return "permiso/primeraPagina";
	}

	@GetMapping("/periodo/buscarDatos")
	public RedirectView formularioPermisoPeriodo(Model model, @RequestParam(name="documento", required = false) long documento,
			@RequestParam(name="dominio", required = false) String dominio) {
		//PermisoPeriodoModel permiso = new PermisoPeriodoModel();
		PersonaModel persona = new PersonaModel();
		RodadoModel rodado = new RodadoModel();
		if(!dominio.isEmpty()) {
			rodado = rodadoService.findByDominio(dominio);
			// permiso.setRodado(rodadoService.findByDominio(dominio));
		}
		if(documento != 0) {
			persona = personaService.findByDni(Long.valueOf(documento));
			// permiso.setPedido(personaService.findByDni(Long.valueOf(documento)));
		}
		model.addAttribute("rodado", rodado);
		model.addAttribute("persona", persona);
		//model.addAttribute("pedido", pedido);
		return new RedirectView("permiso/nuevo");
	}
	
	@GetMapping("/periodo/nuevo")
	public String nuevoPermisoPeriodo(Model model) {
		return "permiso/nuevo";
	}
}
