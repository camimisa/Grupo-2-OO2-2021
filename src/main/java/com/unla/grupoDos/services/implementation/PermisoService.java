package com.unla.grupoDos.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.unla.grupoDos.entities.Permiso;
import com.unla.grupoDos.entities.PermisoDiario;
import com.unla.grupoDos.entities.PermisoPeriodo;
import com.unla.grupoDos.models.PermisoDiarioModel;
import com.unla.grupoDos.models.PermisoPeriodoModel;
import com.unla.grupoDos.repositories.IPermisoRepository;
import com.unla.grupoDos.services.IPermisoService;
import com.unla.grupoDos.converters.PermisoConverter;


@Service("permisoService")
public class PermisoService implements IPermisoService{
	@Autowired
	@Qualifier("permisoRepository")
	private IPermisoRepository permisoRepository;
	
	@Autowired
	@Qualifier("permisoConverter")
	private PermisoConverter permisoConverter;	
	
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
	public PermisoPeriodoModel insertOrUpdate(PermisoPeriodoModel PermisoModel) {
		PermisoPeriodo Permiso = (PermisoPeriodo) permisoRepository.save(permisoConverter.modeloAEntidad(PermisoModel));
		return permisoConverter.entidadAModelo(Permiso);
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
}
