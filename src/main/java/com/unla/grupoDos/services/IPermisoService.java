package com.unla.grupoDos.services;

import java.util.List;

import com.unla.grupoDos.entities.Permiso;
import com.unla.grupoDos.models.PermisoModel;
import com.unla.grupoDos.models.PermisoDiarioModel;
import com.unla.grupoDos.models.PermisoPeriodoModel;

public interface IPermisoService {

	public List<Permiso> getAll();
	public PermisoDiarioModel insertOrUpdate(PermisoDiarioModel ermisoModel);
	public PermisoPeriodoModel insertOrUpdate(PermisoPeriodoModel ermisoModel);
	public boolean remove(int id);
	PermisoDiarioModel permisoDiarioFindById(int id);
	PermisoPeriodoModel permisoPeriodoFindById(int id);
	
}
