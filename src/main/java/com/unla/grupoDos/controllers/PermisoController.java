package com.unla.grupoDos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.grupoDos.converters.PermisoConverter;
import com.unla.grupoDos.converters.PersonaConverter;
import com.unla.grupoDos.converters.RodadoConverter;
import com.unla.grupoDos.entities.Permiso;
import com.unla.grupoDos.entities.PermisoDiario;
import com.unla.grupoDos.models.PermisoDiarioModel;
import com.unla.grupoDos.models.PermisoModel;
import com.unla.grupoDos.models.PermisoPeriodoModel;
import com.unla.grupoDos.services.IPermisoService;
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
	
	@Autowired
	@Qualifier("permisoService")
	private IPermisoService permisoService;
	@Autowired
	@Qualifier("permisoConverter")
	private PermisoConverter permisoConverter;
	
	
	@GetMapping("/")
	public String index(Model model) {
		//PermisoPeriodoModel permiso = permisoService.permisoPeriodoFindById(2);
		//PermisoDiarioModel permiso2 = permisoService.permisoDiarioFindById(3);
		//System.out.println(permiso.toString() + "\n" + permiso2.toString());
		return "permiso/permisoDiario/pregunta";
	}
	
	// --------------- PERMISO DIARIO -------------------------
	
	@GetMapping("/diario/pregunta")
	public String preguntaPermisoDiario(Model model) {
		return "permiso/permisoDiario/pregunta";
	}

	@GetMapping("/diario/buscarDatos")
	public RedirectView formularioPermisoDiario(Model model, @RequestParam(name="documento", required = false) long documento) {
		PermisoModel permiso = new PermisoPeriodoModel();
		if(documento != 0) {
			permiso.setPedido(personaConverter.modeloAEntidad(personaService.findByDni(Long.valueOf(documento))));
		}
		model.addAttribute("permiso", permiso);
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
		PermisoModel permiso = new PermisoPeriodoModel();
		if(!dominio.isEmpty()) {
			((PermisoPeriodoModel) permiso).setRodado(rodadoConverter.modeloAEntidad(rodadoService.findByDominio(dominio)));
		}
		if(documento != 0) {
			permiso.setPedido(personaConverter.modeloAEntidad(personaService.findByDni(Long.valueOf(documento))));
		}
		model.addAttribute("permiso", permiso);
		return new RedirectView("permiso/nuevo");
	}
	
	@GetMapping("/periodo/nuevo")
	public String nuevoPermisoPeriodo(Model model) {
		return "permiso/nuevo";
	}
}
