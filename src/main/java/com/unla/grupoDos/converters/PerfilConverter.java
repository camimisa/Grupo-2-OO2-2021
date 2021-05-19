package com.unla.grupoDos.converters;

import com.unla.grupoDos.entities.Perfil;
import com.unla.grupoDos.models.PerfilModel;

import org.springframework.stereotype.Component;

@Component("perfilConverter")
public class PerfilConverter {

	public Perfil modeloAEntidad(PerfilModel perfilModel) {
		return new Perfil(perfilModel.getIdPerfil(),perfilModel.getNombre(),perfilModel.getDescripcion());
	}
	
	public PerfilModel entidadAModelo(Perfil perfil) {
		return new PerfilModel(perfil.getIdPerfil(), perfil.getNombre(), perfil.getDescripcion());
	}

}
