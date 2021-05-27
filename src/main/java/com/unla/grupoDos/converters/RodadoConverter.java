package com.unla.grupoDos.converters;

import org.springframework.stereotype.Component;

import com.unla.grupoDos.entities.Rodado;
import com.unla.grupoDos.models.RodadoModel;

@Component("rodadoConverter")
public class RodadoConverter {
	
	public Rodado modeloAEntidad(RodadoModel rodadoModel) {
		return new Rodado(rodadoModel.getIdRodado(),rodadoModel.getDominio(),rodadoModel.getVehiculo());
	}
	
	public RodadoModel entidadAModelo(Rodado rodado) {
		return new RodadoModel(rodado.getIdRodado(), rodado.getDominio(), rodado.getVehiculo());
	}
}
