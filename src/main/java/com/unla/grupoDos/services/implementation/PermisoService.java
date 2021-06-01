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
import com.unla.grupoDos.models.PermisoDiarioModel;
import com.unla.grupoDos.models.PermisoPeriodoModel;
import com.unla.grupoDos.repositories.IPermisoRepository;
import com.unla.grupoDos.repositories.IPersonaRepository;
import com.unla.grupoDos.repositories.IRodadoRepository;
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
	
	@Override
	public List<Permiso> getAll() {
		return permisoRepository.findAll();
	}

	@Override
	public PermisoDiarioModel permisoDiarioFindById(int id) {
		return permisoConverter.entidadAModelo(permisoRepository.permisoDiarioFindById(id));
	}


	@Override
	public Permiso findByIdPermiso(int id) {
		return permisoRepository.findByIdPermiso(id);
	}
	@Override
	public PermisoPeriodoModel permisoPeriodoFindById(int id) {
		return permisoConverter.entidadAModelo(permisoRepository.permisoPeriodoFindById(id));
	}

	@Override
	public PermisoDiarioModel insertOrUpdate(PermisoDiarioModel permisoModel) {

		Persona persona = personaService.findByDni(permisoModel.getPedido().getDni());
		if(persona == null)
			persona = personaService.insertOrUpdate(permisoModel.getPedido());
		
		permisoModel.setPedido(persona); // Esto se hace por si se ingreso un dni con nombre distinto. Se va a remplazar por los datos correcto que vienen de la bd.

		Lugar lugarAux = null;
		for(Lugar lugar: permisoModel.getDesdeHasta()) {	
			lugarAux = new Lugar();
			lugarAux = lugarService.findByCodPostal(lugar.getCodPostal());
			if(lugarAux == null) 
				lugarAux = lugarService.insertOrUpdate(lugar);
			lugar.setIdLugar(lugarAux.getIdLugar());
			lugar.setCodPostal(lugarAux.getCodPostal());
			lugar.setLugar(lugarAux.getLugar());
		}
		PermisoDiario permiso = (PermisoDiario) permisoRepository.save(permisoConverter.modeloAEntidad(permisoModel));
		return permisoConverter.entidadAModelo(permiso);
	}
	
	@Override
	public PermisoPeriodoModel insertOrUpdate(PermisoPeriodoModel permisoModel) {
		// Guardar persona si no existe, si existe asignarle su ID
		Persona persona = personaService.findByDni(permisoModel.getPedido().getDni());
		if(persona == null) 
			persona = personaService.insertOrUpdate(permisoModel.getPedido());
		permisoModel.setPedido(persona); // Esto se hace por si se ingreso un dni con nombre distinto. Se va a remplazar por los datos correcto que vienen de la bd.
		
		// Guardar rodado si no existe, si existe asignarle su ID
		Rodado rodado = rodadoService.findByDominio(permisoModel.getRodado().getDominio());
		if(rodado == null)
			rodado = rodadoService.insertOrUpdate(permisoModel.getRodado());
		permisoModel.setRodado(rodado);

		Lugar lugarAux = new Lugar();
		for(Lugar lugar: permisoModel.getDesdeHasta()) {
			lugarAux = lugarService.findByCodPostal(lugar.getCodPostal());
			if(lugarAux == null)
				lugarAux = lugarService.insertOrUpdate(lugar);			
			lugar.setIdLugar(lugarAux.getIdLugar());
			lugar.setCodPostal(lugarAux.getCodPostal());
			lugar.setLugar(lugarAux.getLugar());
		}

		PermisoPeriodo permiso = (PermisoPeriodo) permisoRepository.save(permisoConverter.modeloAEntidad(permisoModel));
		return permisoConverter.entidadAModelo(permiso);
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
