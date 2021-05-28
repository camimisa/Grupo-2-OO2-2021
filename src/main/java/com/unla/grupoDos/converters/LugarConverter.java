package com.unla.grupoDos.converters;

import org.springframework.stereotype.Component;

import com.unla.grupoDos.entities.Lugar;
import com.unla.grupoDos.models.LugarModel;

@Component("lugarConverter")
public class LugarConverter {

	public Lugar modeloAEntidad(LugarModel lugarModel) {
		return new Lugar(lugarModel.getIdLugar(),lugarModel.getLugar(),lugarModel.getCodPostal());
	}
	
	public LugarModel entidadAModelo(Lugar lugar) {
		return new LugarModel(lugar.getIdLugar(), lugar.getLugar(), lugar.getCodPostal());
	}
	
}