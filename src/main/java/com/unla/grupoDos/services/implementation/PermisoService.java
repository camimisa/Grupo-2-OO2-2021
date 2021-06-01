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
	@Qualifier("rodadoConverter")
	private RodadoConverter rodadoConverter;
	
	@Autowired
	@Qualifier("lugarService")
	private ILugarService lugarService;
	@Autowired
	@Qualifier("lugarConverter")
	private LugarConverter lugarConverter;
	
	@Autowired
	@Qualifier("personaService")
	private IPersonaService personaService;
	@Autowired
	@Qualifier("personaConverter")
	private PersonaConverter personaConverter;
	
	
	@Override
	public List<Permiso> getAll() {
		return permisoRepository.findAll();
	}

	@Override
	public PermisoDiarioModel permisoDiarioFindById(int id) {
		return permisoConverter.entidadAModelo(permisoRepository.permisoDiarioFindById(id));
	}
	
	@Override
	public PermisoPeriodoModel permisoPeriodoFindById(int id) {
		return permisoConverter.entidadAModelo(permisoRepository.permisoPeriodoFindById(id));
	}

	@Override
	public PermisoDiarioModel insertOrUpdate(PermisoDiarioModel PermisoModel) {
		PermisoDiario Permiso = (PermisoDiario) permisoRepository.save(permisoConverter.modeloAEntidad(PermisoModel));
		return permisoConverter.entidadAModelo(Permiso);
	}
	
	@Override
	public PermisoPeriodoModel insertOrUpdate(PermisoPeriodoModel permisoModel) {
		personaService.insertOrUpdate(personaConverter.entidadAModelo(permisoModel.getPedido()));
		rodadoService.insertOrUpdate(rodadoConverter.entidadAModelo(permisoModel.getRodado()));
		for(Lugar lugar: permisoModel.getDesdeHasta())
			lugarService.insertOrUpdate(lugarConverter.entidadAModelo(lugar));
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
		System.out.println(dni);
		System.out.println(lista);
		return lista;
	}
}
