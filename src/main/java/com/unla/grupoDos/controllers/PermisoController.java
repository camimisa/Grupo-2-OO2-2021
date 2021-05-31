package com.unla.grupoDos.controllers;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.grupoDos.converters.PermisoConverter;
import com.unla.grupoDos.converters.PersonaConverter;
import com.unla.grupoDos.converters.RodadoConverter;
import com.unla.grupoDos.entities.Lugar;
import com.unla.grupoDos.entities.Permiso;
import com.unla.grupoDos.entities.Persona;
import com.unla.grupoDos.entities.Rodado;
import com.unla.grupoDos.helpers.ViewRouteHelper;
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
	public String index() {
		return ViewRouteHelper.PREGUNTA_PERMISO;
	}
	
	// --------------- PERMISO DIARIO -------------------------
	
	@GetMapping("/diario")
	public String preguntaPermisoDiario(Model model) {
		return ViewRouteHelper.PREGUNTA_DIARIO;
	}

	@GetMapping("/diario/buscarDatos/")
	public String formularioPermisoDiario(Model model, @RequestParam(name="documento", required = false) long documento) {
		PermisoModel permiso = new PermisoDiarioModel();
		if(documento != 0) {
			permiso.setPedido(personaService.findByDni(Long.valueOf(documento)));
		}
		model.addAttribute("permiso", permiso);
		return ViewRouteHelper.NUEVO_PERMISO_DIARIO;
	}
	
	@GetMapping("/diario/nuevo")
	public String nuevoPermisoDiario(Model model) {
		model.addAttribute("permiso", new PermisoDiarioModel());
		model.addAttribute("pedido", new Persona());
		return ViewRouteHelper.NUEVO_PERMISO_DIARIO;
	}
	
	@PostMapping("/diario/crear")
	public RedirectView crearPermisoDiario(@ModelAttribute("permiso") PermisoDiarioModel permiso,
			@RequestParam(name="desdeLugar", required = true) String desdeLugar,
			@RequestParam(name="desdeCodPostal", required = true) String desdeCodPostal,
			@RequestParam(name="hastaLugar", required = true) String hastaLugar,
			@RequestParam(name="hastaCodPostal", required = true) String hastaCodPostal) {
		
		permiso.setDesdeHasta(new HashSet<Lugar>());
		permiso.getDesdeHasta().add(new Lugar(desdeLugar, desdeCodPostal));
		permiso.getDesdeHasta().add(new Lugar(hastaLugar, hastaCodPostal));
		
		permisoService.insertOrUpdate(permiso);
		return new RedirectView("/");
	}
	
	// --------------- PERMISO PERIODO -------------------------
	
	@GetMapping("/periodo")
	public String preguntaPermisoPeriodo(Model model) {
		return ViewRouteHelper.PREGUNTA_PERIODO;
	}

	@GetMapping("/periodo/buscarDatos/")
	public String formularioPermisoPeriodo(Model model,@RequestParam(name="dominio", required = false, defaultValue = "") String dominio, 
			@RequestParam(name="documento", required = false) long documento) {
		PermisoModel permiso = new PermisoPeriodoModel();
		if(!dominio.isEmpty()) {
			((PermisoPeriodoModel) permiso).setRodado(rodadoService.findByDominio(dominio));
		}
		if(documento != 0) {
			permiso.setPedido(personaService.findByDni(Long.valueOf(documento)));
		}
		model.addAttribute("permiso", permiso);
		return ViewRouteHelper.NUEVO_PERMISO_PERIODO;
	}
	
	@GetMapping("/periodo/nuevo")
	public String nuevoPermisoPeriodo(Model model) {
		model.addAttribute("permiso", new PermisoPeriodoModel());
		model.addAttribute("rodado", new Rodado());
		model.addAttribute("pedido", new Persona());
		return ViewRouteHelper.NUEVO_PERMISO_PERIODO;
	}
	
	@PostMapping("/periodo/crear")
	public RedirectView crearPermisoPeriodo(@ModelAttribute("permiso") PermisoPeriodoModel permiso,
			@RequestParam(name="desdeLugar", required = true) String desdeLugar,
			@RequestParam(name="desdeCodPostal", required = true) String desdeCodPostal,
			@RequestParam(name="hastaLugar", required = true) String hastaLugar,
			@RequestParam(name="hastaCodPostal", required = true) String hastaCodPostal) {

		permiso.setDesdeHasta(new HashSet<Lugar>());
		permiso.getDesdeHasta().add(new Lugar(desdeLugar, desdeCodPostal));
		permiso.getDesdeHasta().add(new Lugar(hastaLugar, hastaCodPostal));

		permisoService.insertOrUpdate(permiso);
		return new RedirectView("/");
	}
	
	// --------------- PERMISO POR PERSONA -------------------------
	
	@GetMapping("/buscarPorPersona")
	public String preguntaPermisoPorPersona(Model model) {
		return "permiso/traer/buscarPermiso";
	}
	
	@GetMapping("/listarPermisoPorPersona")
	public ModelAndView listarPermisoPorPersona(@RequestParam(name="dni", required = true) long dni) {
		ModelAndView mAV = new ModelAndView("permiso/traer/listaPermisosPorPersona"); 
		List<Permiso>permisosActivos = permisoService.getAllByPersona(dni);
		mAV.addObject("permisosActivos",permisosActivos);
		
		return mAV;
	}
}
