package com.unla.grupoDos.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.unla.grupoDos.converters.PermisoConverter;
import com.unla.grupoDos.entities.Lugar;
import com.unla.grupoDos.entities.Permiso;
import com.unla.grupoDos.helpers.ViewRouteHelper;
import com.unla.grupoDos.models.PerfilModel;
import com.unla.grupoDos.models.UsuarioModel;
import com.unla.grupoDos.services.ILugarService;
import com.unla.grupoDos.services.IPerfilService;
import com.unla.grupoDos.services.IPermisoService;
import com.unla.grupoDos.services.IUsuarioService;

@Controller
@RequestMapping("/auditor")
public class AuditorController {
	
	@Autowired
	@Qualifier("usuarioService")
	private IUsuarioService usuarioService;
	
	@Autowired
	@Qualifier("perfilService")
	private IPerfilService perfilService;
	
	@Autowired
	@Qualifier("permisoService")
	private IPermisoService permisoService;
	
	@Autowired
	@Qualifier("lugarService")
	private ILugarService lugarService;
	
	@Autowired
	@Qualifier("permisoConverter")
	private PermisoConverter permisoConverter;

	// ------------------------------ PERMISOS AUDITOR ---------------------------
	@GetMapping("/")
	public String indexAuditor(Model model, @RequestParam(name="nombreUsuario",required=false) String nombreUsuario) {
		org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		nombreUsuario = auth.getName();
		model.addAttribute("nombreUsuario", nombreUsuario);
		return ViewRouteHelper.INICIO_AUDITOR;
	}
	@GetMapping("/usuarios")
	public ModelAndView vistaUsuariosAuditor() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.VISTA_USUARIOS_AUDITOR);
		mAV.addObject("listaUsuarios", usuarioService.getAll());
		mAV.addObject("usuario", new UsuarioModel());
		return mAV;
	}
	
	@GetMapping("/perfiles")
	public ModelAndView vistaPerfilesAuditor() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.VISTA_PERFILES_AUDITOR);
		mAV.addObject("listaPerfiles", perfilService.getAll());
		mAV.addObject("perfil", new PerfilModel());
		return mAV;
	}
	
	@GetMapping("/usuarios/descargar")
	public ModelAndView indexAdminDescargar() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.VISTA_AUDITOR_DESCARGAR_USUARIOS);
		mAV.addObject("listaUsuarios", usuarioService.getAll());
		mAV.addObject("usuario", new UsuarioModel());
		return mAV;
	}
	
	@GetMapping("/perfiles/descargar")
	public ModelAndView descargarLista() {
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.VISTA_AUDITOR_DESCARGAR_PERFILES);
		mAV.addObject("listaPerfiles", perfilService.getAll());
		mAV.addObject("perfil", new PerfilModel());
		return mAV;
	}
	
	// ------------------------------ ACCIONES CON PERMISOS ---------------------------
	@GetMapping("/permiso/buscarEntreFechas")
	public String preguntaPermisoDiario(Model model) {
		model.addAttribute("lugares", lugarService.getAll());
		return ViewRouteHelper.PREGUNTA_ENTRE_FECHAS;
	}

	@GetMapping("/permiso/buscarPermisosFecha")
	public ModelAndView formularioPermisoDiario( 
			@RequestParam(name="desdeFecha") String desdeFecha,
			@RequestParam(name="hastaFecha") String hastaFecha,
			@RequestParam(name="desdeLugar", defaultValue = "-55") int desdeLugarId,
			@RequestParam(name="hastaLugar", defaultValue = "-55") int hastaLugarId
	) {
		LocalDate hasta; 
		LocalDate desde;			
		String titulo = "";
		List<Permiso> permisosActivos;
		List<Lugar> lugares;
		ModelAndView mAV = new ModelAndView(ViewRouteHelper.LISTADO_PERMISOS);
		try {
			
			List<Integer> lugaresId = new ArrayList<Integer>();
			if (desdeLugarId != -55 || hastaLugarId != -55) {
				if (desdeLugarId != -55) {
					lugaresId.add(desdeLugarId);			
				} 
				if (hastaLugarId != -55) {
					lugaresId.add(hastaLugarId);			
				}
				permisosActivos = permisoService.getAllByLugares(lugaresId);
				//Si ambos son distintos de -55, significa que tiene que haber s?? o s?? 2 elementos en el set desdeHasta.
				if (desdeLugarId != -55 && hastaLugarId != -55 ) {
					permisosActivos.removeIf(p -> p.getDesdeHasta().size() < 2);			
				}
			} else {
				permisosActivos = permisoService.getAll();	
			}
			
			hasta = LocalDate.parse(hastaFecha);
			desde = LocalDate.parse(desdeFecha);

			lugares = lugarService.findByIds(lugaresId);
			permisosActivos.removeIf(p -> !p.esValido(hasta, desde));
			titulo = "Permisos activos entre fechas: " + desde + " hasta " + hasta + 
					" y desde " + ((desdeLugarId != -55) ? lugares.get(0).getLugar() : "cualquier lugar") +
					" hasta " + ((hastaLugarId != -55) ? lugares.get(1).getLugar() : "cualquier lugar");
			mAV.addObject("permisosActivos", permisoConverter.listaEntidadAModelo(permisosActivos));
			mAV.addObject("lugares", lugares);
		} catch(Exception e) {
			titulo = "Datos err??neos.";
		}
		mAV.addObject("titulo", titulo );
		return mAV;
	}
}
