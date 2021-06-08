package com.unla.grupoDos.services;

import java.time.LocalDate;
import java.util.List;

import com.unla.grupoDos.entities.Permiso;
import com.unla.grupoDos.models.PermisoModel;
import com.unla.grupoDos.models.PermisoDiarioModel;
import com.unla.grupoDos.models.PermisoPeriodoModel;

public interface IPermisoService {

	public List<Permiso> getAll();
	public PermisoDiarioModel insertOrUpdate(PermisoDiarioModel permisoModel);
	public PermisoPeriodoModel insertOrUpdate(PermisoPeriodoModel permisoModel);
	public boolean remove(int id);
	PermisoDiarioModel permisoDiarioFindById(int id);
	PermisoPeriodoModel permisoPeriodoFindById(int id);
	public List<Permiso> getAllBetweenDates(LocalDate startDate, LocalDate endDate);
	public List<Permiso> getAllByLugares(List<Integer> lugaresId);
	public List<Permiso> getAllByPersona(long dni);
	public Permiso findByIdPermiso(int id);
	
}
