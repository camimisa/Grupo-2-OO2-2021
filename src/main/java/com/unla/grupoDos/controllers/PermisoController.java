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
		return ViewRouteHelper.NUEVO_PERMISO_DIARIO;
	}
	
	// --------------- PERMISO PERIODO -------------------------
	
	@GetMapping("/periodo")
	public String preguntaPermisoPeriodo(Model model) {
		//int idPermiso, Persona pedido, LocalDate fecha, int cantDias, boolean vacaciones, Rodado rodado
		//int idPersona, String nombre, String apellido, long dni
		//public Rodado(int idRodado, String dominio, String vehiculo)

		Persona persona = new Persona(4,"Micaela","Gomez",41239871L);
		Rodado rodado = new Rodado(5,"FK123ML","Toyota 29L");
		PermisoPeriodoModel permisoModel = new PermisoPeriodoModel(6,persona,LocalDate.now(),10,false,rodado);
		Set<Lugar> listaDesdeHasta = new HashSet<Lugar>();
		listaDesdeHasta.add(new Lugar(7,"Monte Grande","1842"));
		listaDesdeHasta.add(new Lugar(8,"Luis Guillon","1836"));
		permisoModel.setDesdeHasta(listaDesdeHasta);
		System.out.println(permisoModel.toString());
		permisoService.insertOrUpdate(permisoModel);
		return ViewRouteHelper.PREGUNTA_PERIODO;
	}

	@GetMapping("/periodo/buscarDatos")
	public String formularioPermisoPeriodo(Model model, @RequestParam(name="documento", required = false) long documento,
			@RequestParam(name="dominio", required = false) String dominio) {
		PermisoModel permiso = new PermisoPeriodoModel();
		if(!dominio.isEmpty()) {
			((PermisoPeriodoModel) permiso).setRodado(rodadoConverter.modeloAEntidad(rodadoService.findByDominio(dominio)));
		}
		if(documento != 0) {
			permiso.setPedido(personaConverter.modeloAEntidad(personaService.findByDni(Long.valueOf(documento))));
		}
		model.addAttribute("permiso", permiso);
		System.out.println(model.toString());
		return ViewRouteHelper.NUEVO_PERMISO_PERIODO;
	}
	
	@GetMapping("/periodo/nuevo")
	public String nuevoPermisoPeriodo(Model model) {
		Set<Lugar> listaDesdeHasta = new HashSet<Lugar>();
		listaDesdeHasta.add(new Lugar());
		listaDesdeHasta.add(new Lugar());
		PermisoPeriodoModel permiso = new PermisoPeriodoModel();
		permiso.setDesdeHasta(listaDesdeHasta);
		model.addAttribute("permiso", permiso);
		model.addAttribute("rodado", new Rodado());
		model.addAttribute("pedido", new Persona());
		//model.addAttribute("desdeHasta", listaDesdeHasta);
		
		return ViewRouteHelper.NUEVO_PERMISO_PERIODO;
	}
	
	@PostMapping("/periodo/crear")
	public RedirectView create(@ModelAttribute("permiso") PermisoPeriodoModel permiso,
			@ModelAttribute("rodado") Rodado rodado,
			@ModelAttribute("pedido") Persona pedido) {

		//permisoService.insertOrUpdate(permiso);
		System.out.println(permiso.toString());
		System.out.println(rodado.toString());
		System.out.println(pedido.toString());

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
		System.out.println(permisosActivos);
		mAV.addObject("permisosActivos",permisosActivos);
		
		return mAV;
	}
}
