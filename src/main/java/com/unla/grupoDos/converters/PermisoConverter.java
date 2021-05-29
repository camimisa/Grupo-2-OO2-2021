package com.unla.grupoDos.converters;

import org.springframework.stereotype.Component;

import com.unla.grupoDos.entities.PermisoDiario;
import com.unla.grupoDos.entities.PermisoPeriodo;
import com.unla.grupoDos.models.PermisoDiarioModel;
import com.unla.grupoDos.models.PermisoPeriodoModel;

@Component("permisoConverter")
public class PermisoConverter {

	public PermisoDiario modeloAEntidad(PermisoDiarioModel permisoModel) {
		return new PermisoDiario(permisoModel.getIdPermiso(),permisoModel.getPedido(), permisoModel.getFecha(), permisoModel.getMotivo());
	}
	
	public PermisoDiarioModel entidadAModelo(PermisoDiario permiso) {
		return new PermisoDiarioModel(permiso.getIdPermiso(), permiso.getPedido(), permiso.getFecha(), permiso.getMotivo());
	}
	
	public PermisoPeriodo modeloAEntidad(PermisoPeriodoModel permisoModel) {
		return new PermisoPeriodo(permisoModel.getIdPermiso(),permisoModel.getPedido(), permisoModel.getFecha(), permisoModel.getCantDias(), 
				permisoModel.isVacaciones(), permisoModel.getRodado());
	}
	
	public PermisoPeriodoModel entidadAModelo(PermisoPeriodo permiso) {
		return new PermisoPeriodoModel(permiso.getIdPermiso(), permiso.getPedido(), permiso.getFecha(), permiso.getCantDias(), 
				permiso.isVacaciones(), permiso.getRodado());
	}
}
