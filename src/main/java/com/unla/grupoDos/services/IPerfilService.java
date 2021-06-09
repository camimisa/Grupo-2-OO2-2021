package com.unla.grupoDos.services;

import java.util.List;

import com.unla.grupoDos.entities.Perfil;
import com.unla.grupoDos.models.PerfilModel;

public interface IPerfilService {

	public List<Perfil> getAll();
	public PerfilModel insertOrUpdate(PerfilModel PerfilModel);
	public boolean remove(int id);
	PerfilModel findById(int id);
	PerfilModel findByNombrePerfil(String nombrePerfil);
	public abstract List<Perfil> traerPerfilesHabilitados();
}
