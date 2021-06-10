package com.unla.grupoDos.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.unla.grupoDos.entities.Perfil;
import com.unla.grupoDos.models.PerfilModel;
import com.unla.grupoDos.repositories.IPerfilRepository;
import com.unla.grupoDos.services.IPerfilService;
import com.unla.grupoDos.converters.PerfilConverter;


@Service("perfilService")
public class PerfilService implements IPerfilService{
	@Autowired
	@Qualifier("perfilRepository")
	private IPerfilRepository perfilRepository;
	
	@Autowired
	@Qualifier("perfilConverter")
	private PerfilConverter perfilConverter;	
	
	@Override
	public List<Perfil> getAll() {
		return perfilRepository.findAll();
	}

	@Override
	public PerfilModel findById(int id) {
		return perfilConverter.entidadAModelo(perfilRepository.findByIdPerfil(id));
	}

	@Override
	public PerfilModel findByNombrePerfil(String nombrePerfil) {
		return perfilConverter.entidadAModelo(perfilRepository.findByNombre(nombrePerfil));
	}
	@Override
	public PerfilModel insertOrUpdate(PerfilModel PerfilModel) {
		Perfil Perfil = perfilRepository.save(perfilConverter.modeloAEntidad(PerfilModel));
		return perfilConverter.entidadAModelo(Perfil);
	}

	@Override
	public boolean remove(int id) {
		try {
			perfilRepository.deleteById(id);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}

	@Override
	public List<Perfil> traerPerfilesHabilitados() {
		return perfilRepository.traerPerfilesHabilitados();
	}
}
