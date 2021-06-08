package com.unla.grupoDos.converters;

import org.springframework.stereotype.Component;

import com.unla.grupoDos.entities.Rodado;
import com.unla.grupoDos.models.RodadoModel;

@Component("rodadoConverter")
public class RodadoConverter {
	
	public Rodado modeloAEntidad(RodadoModel rodadoModel) throws Exception {
		Rodado rodado = null;
		if(rodadoModel != null)
			rodado = new Rodado(rodadoModel.getIdRodado(),rodadoModel.getDominio(),rodadoModel.getVehiculo());
		return rodado;
	}
	
	public RodadoModel entidadAModelo(Rodado rodado) {
		RodadoModel rodadoModel = null;
		if(rodado != null)
			rodadoModel = new RodadoModel(rodado.getIdRodado(), rodado.getDominio(), rodado.getVehiculo());
		return rodadoModel;
	}
}
