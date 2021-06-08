package com.unla.grupoDos.services.implementation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.unla.grupoDos.entities.Lugar;
import com.unla.grupoDos.entities.Permiso;
import com.unla.grupoDos.entities.PermisoDiario;
import com.unla.grupoDos.entities.PermisoPeriodo;
import com.unla.grupoDos.entities.Persona;
import com.unla.grupoDos.entities.Rodado;
import com.unla.grupoDos.models.LugarModel;
import com.unla.grupoDos.models.PermisoDiarioModel;
import com.unla.grupoDos.models.PermisoModel;
import com.unla.grupoDos.models.PermisoPeriodoModel;
import com.unla.grupoDos.repositories.IPermisoRepository;
import com.unla.grupoDos.services.ILugarService;
import com.unla.grupoDos.services.IPermisoService;
import com.unla.grupoDos.services.IPersonaService;
import com.unla.grupoDos.services.IRodadoService;
import com.unla.grupoDos.converters.LugarConverter;
import com.unla.grupoDos.converters.PermisoConverter;
import com.unla.grupoDos.converters.PersonaConverter;
import com.unla.grupoDos.converters.RodadoConverter;


@Service("permisoService")
public class PermisoService implements IPermisoService{
	@Autowired
	@Qualifier("permisoRepository")
	private IPermisoRepository permisoRepository;
	@Autowired
	@Qualifier("permisoConverter")
	private PermisoConverter permisoConverter;
	
	@Autowired
	@Qualifier("rodadoService")
	private IRodadoService rodadoService;
	
	@Autowired
	@Qualifier("lugarService")
	private ILugarService lugarService;

	@Autowired
	@Qualifier("personaService")
	private IPersonaService personaService;

	@Autowired
	@Qualifier("rodadoConverter")
	private RodadoConverter rodadoConverter;
	@Autowired
	@Qualifier("personaConverter")
	private PersonaConverter personaConverter;
	@Autowired
	@Qualifier("lugarConverter")
	private LugarConverter lugarConverter;
	
	@Override
	public List<Permiso> getAll() {
		return permisoRepository.findAll();
	}

	@Override
	public Permiso findByIdPermiso(int id) {
		return permisoRepository.findByIdPermiso(id);
	}

	@Override
	public PermisoModel insertOrUpdate(PermisoModel permisoModel) {
		Permiso permiso = null;
		if(permisoModel instanceof PermisoDiarioModel) {
			permisoModel = (PermisoDiarioModel) this.obtenerDatos(permisoModel);
			permiso = permisoRepository.save(permisoConverter.modeloAEntidad(permisoModel));
		}
		if(permisoModel instanceof PermisoPeriodoModel) {
			permisoModel = (PermisoPeriodoModel) this.obtenerDatos(permisoModel);
			permiso = permisoRepository.save(permisoConverter.modeloAEntidad(permisoModel));
		}
		return permisoConverter.entidadAModelo(permiso);
	}
	

	private PermisoModel obtenerDatos(PermisoModel permisoModel) {
		// Guardar persona si no existe, si existe asignarle su ID
		Persona persona = personaService.findByDni(permisoModel.getPedido().getDni());
		if(persona == null) 
			persona = personaService.insertOrUpdate(personaConverter.modeloAEntidad(permisoModel.getPedido()));
		permisoModel.setPedido(personaConverter.entidadAModelo(persona)); // Esto se hace por si se ingreso un dni con nombre distinto. Se va a remplazar por los datos correcto que vienen de la bd.
		
		// Guardar rodado si no existe, si existe asignarle su ID
		
		if(permisoModel instanceof PermisoPeriodoModel) {
			Rodado rodado = rodadoService.findByDominio(((PermisoPeriodoModel) permisoModel).getRodado().getDominio());
			if(rodado == null)
				rodado = rodadoService.insertOrUpdate(rodadoConverter.modeloAEntidad(((PermisoPeriodoModel) permisoModel).getRodado()));
			((PermisoPeriodoModel) permisoModel).setRodado(rodadoConverter.entidadAModelo(rodado));
		}
		
		Lugar lugarAux = new Lugar();
		for(LugarModel lugar: permisoModel.getDesdeHasta()) {
			lugarAux = lugarService.findByCodPostal(lugar.getCodPostal());
			if(lugarAux == null)
				lugarAux = lugarService.insertOrUpdate(lugarConverter.modeloAEntidad(lugar));			
			lugar.setIdLugar(lugarAux.getIdLugar());
			lugar.setCodPostal(lugarAux.getCodPostal());
			lugar.setLugar(lugarAux.getLugar());
		}
		
		return permisoModel;
	}
	
	@Override
	public boolean remove(int id) {
		try {
			permisoRepository.deleteById(id);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}

	@Override
	public List<Permiso> getAllBetweenDates(LocalDate startDate, LocalDate endDate) {
		List<Permiso> lista = permisoRepository.getAllBetweenDates(startDate, endDate);
		return lista;
	}
	
	@Override
	public List<Permiso> getAllByLugares(List<Integer> lugaresId) {
		List<Permiso> lista = permisoRepository.findByLugares(lugaresId);
		return lista;
	}

	@Override
	public List<Permiso> getAllByPersona(long dni) {
		List<Permiso> lista = permisoRepository.getAllByPersona(dni);
		return lista;
	}
}
