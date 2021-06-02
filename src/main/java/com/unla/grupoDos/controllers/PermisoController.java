package com.unla.grupoDos.controllers;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.unla.grupoDos.converters.PermisoConverter;
import com.unla.grupoDos.converters.PersonaConverter;
import com.unla.grupoDos.converters.RodadoConverter;
import com.unla.grupoDos.entities.Lugar;
import com.unla.grupoDos.entities.Permiso;
import com.unla.grupoDos.entities.PermisoDiario;
import com.unla.grupoDos.entities.PermisoPeriodo;
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
	
	@GetMapping("/{id}")
	public ModelAndView verPermiso(@PathVariable("id") int id) {
		ModelAndView mAV = new ModelAndView();
		Permiso permiso = permisoService.findByIdPermiso(id);
		mAV.addObject("permiso", permiso);
		if(permiso == null) mAV.setViewName(ViewRouteHelper.PERMISO_NO_ENCONTRADO);
		else
			mAV.setViewName(ViewRouteHelper.VER_PERMISO);
		return mAV;
	}
	
	// --------------- PERMISO DIARIO -------------------------
	
	@GetMapping("/diario")
	public String preguntaPermisoDiario(Model model) {
		return ViewRouteHelper.PREGUNTA_DIARIO;
	}

	@GetMapping("/diario/buscarDatos/")
	public String formularioPermisoDiario(Model model, @RequestParam(name="documento", required = false, defaultValue = "0") long documento) {
		PermisoModel permiso = new PermisoDiarioModel();
		if(documento != 0) {
			permiso.setPedido(personaService.findByDni(Long.valueOf(documento)));
		}
		model.addAttribute("permiso", permiso);
		return ViewRouteHelper.NUEVO_PERMISO;
	}
	
	@GetMapping("/diario/nuevo")
	public String nuevoPermisoDiario(Model model) {
		model.addAttribute("permiso", new PermisoDiarioModel());
		model.addAttribute("pedido", new Persona());
		return ViewRouteHelper.NUEVO_PERMISO;
	}
	
	@PostMapping("/diario/crear")
	public RedirectView crearPermisoDiario(@ModelAttribute("permiso") PermisoDiarioModel permiso,
			@RequestParam(name="desdeLugar", required = true) String desdeLugar,
			@RequestParam(name="desdeCodPostal", required = true) String desdeCodPostal,
			@RequestParam(name="hastaLugar", required = true) String hastaLugar,
			@RequestParam(name="hastaCodPostal", required = true) String hastaCodPostal,
			RedirectAttributes atts) {
		
		permiso.setDesdeHasta(new HashSet<Lugar>());
		Lugar lugarDesde = new Lugar(desdeLugar, desdeCodPostal);
		Lugar lugarHasta = new Lugar(hastaLugar, hastaCodPostal);
		String url = "";
		if(!lugarDesde.equals(lugarHasta)) {
			permiso.getDesdeHasta().add(lugarDesde);
			permiso.getDesdeHasta().add(lugarHasta);
			permiso = permisoService.insertOrUpdate(permiso);
			url = "../"+permiso.getIdPermiso();
			atts.addFlashAttribute("guardado", true);
		}else {
			url = "../diario";
			atts.addFlashAttribute("errorLugares", true);
		}
		
		return new RedirectView(url);
	}
	
	// --------------- PERMISO PERIODO -------------------------
	
	@GetMapping("/periodo")
	public String preguntaPermisoPeriodo(Model model) {
		return ViewRouteHelper.PREGUNTA_PERIODO;
	}

	@GetMapping("/periodo/buscarDatos/")
	public String formularioPermisoPeriodo(Model model,@RequestParam(name="dominio", required = false, defaultValue = "") String dominio, 
			@RequestParam(name="documento", required = false, defaultValue = "0") long documento) {
		PermisoModel permiso = new PermisoPeriodoModel();
		if(!dominio.isEmpty()) {
			((PermisoPeriodoModel) permiso).setRodado(rodadoService.findByDominio(dominio));
		}
		if(documento != 0) {
			permiso.setPedido(personaService.findByDni(Long.valueOf(documento)));
		}
		model.addAttribute("permiso", permiso);
		return ViewRouteHelper.NUEVO_PERMISO;
	}
	
	@GetMapping("/periodo/nuevo")
	public String nuevoPermisoPeriodo(Model model) {
		model.addAttribute("permiso", new PermisoPeriodoModel());
		model.addAttribute("rodado", new Rodado());
		model.addAttribute("pedido", new Persona());
		return ViewRouteHelper.NUEVO_PERMISO;
	}
	
	@PostMapping("/periodo/crear")
	public RedirectView crearPermisoPeriodo(@ModelAttribute("permiso") PermisoPeriodoModel permiso,
			@RequestParam(name="desdeLugar", required = true) String desdeLugar,
			@RequestParam(name="desdeCodPostal", required = true) String desdeCodPostal,
			@RequestParam(name="hastaLugar", required = true) String hastaLugar,
			@RequestParam(name="hastaCodPostal", required = true) String hastaCodPostal,
			RedirectAttributes atts) {
		
		permiso.setDesdeHasta(new HashSet<Lugar>());
		Lugar lugarDesde = new Lugar(desdeLugar, desdeCodPostal);
		Lugar lugarHasta = new Lugar(hastaLugar, hastaCodPostal);
		String url = "";
		if(!lugarDesde.equals(lugarHasta)) {
			permiso.getDesdeHasta().add(lugarDesde);
			permiso.getDesdeHasta().add(lugarHasta);
			permiso = permisoService.insertOrUpdate(permiso);
			url = "../"+permiso.getIdPermiso();
			atts.addFlashAttribute("guardado", true);
		}else {
			url = "../periodo";
			atts.addFlashAttribute("errorLugares", true);
		}
		
		return new RedirectView(url);
	}
	
	
	// --------------- PERMISO POR PERSONA -------------------------
	
	@GetMapping("/buscarPorPersona")
	public String preguntaPermisoPorPersona(Model model) {
		return ViewRouteHelper.PREGUNTA_PERMISO_POR_PERSONA;
	}
	
	@GetMapping("/listarPermisoPorPersona")
	public ModelAndView listarPermisoPorPersona(@RequestParam(name="dni", required = true) long dni) {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.LISTADO_PERMISOS_POR_PERSONA); 
		List<Permiso>permisosActivos = permisoService.getAllByPersona(dni);
		mAV.addObject("permisosActivos",permisosActivos);
		String titulo = "Permisos encontrados, dni: " + dni;
		mAV.addObject("titulo", titulo );
		return mAV;
	}
	
	
}
