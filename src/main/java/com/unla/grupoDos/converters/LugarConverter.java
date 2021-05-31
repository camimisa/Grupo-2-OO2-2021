package com.unla.grupoDos.converters;

import org.springframework.stereotype.Component;

import com.unla.grupoDos.entities.Lugar;
import com.unla.grupoDos.models.LugarModel;

@Component("lugarConverter")
public class LugarConverter {

	public Lugar modeloAEntidad(LugarModel lugarModel) {
		Lugar lugar = null;
		if(lugarModel != null)
			lugar = new Lugar(lugarModel.getIdLugar(),lugarModel.getLugar(),lugarModel.getCodPostal());
		return lugar;
	}
	
	public LugarModel entidadAModelo(Lugar lugar) {
		LugarModel lugarModel = null;
		if(lugar != null)
			lugarModel = new LugarModel(lugar.getIdLugar(), lugar.getLugar(), lugar.getCodPostal());
		return lugarModel ;
	}
	
}