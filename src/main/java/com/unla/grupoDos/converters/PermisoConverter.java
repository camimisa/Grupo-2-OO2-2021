package com.unla.grupoDos.converters;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.unla.grupoDos.entities.Lugar;
import com.unla.grupoDos.entities.Permiso;
import com.unla.grupoDos.entities.PermisoDiario;
import com.unla.grupoDos.entities.PermisoPeriodo;
import com.unla.grupoDos.entities.Rodado;
import com.unla.grupoDos.models.LugarModel;
import com.unla.grupoDos.models.PermisoDiarioModel;
import com.unla.grupoDos.models.PermisoModel;
import com.unla.grupoDos.models.PermisoPeriodoModel;
import com.unla.grupoDos.models.RodadoModel;

@Component("permisoConverter")
public class PermisoConverter {

	@Autowired
	@Qualifier("rodadoConverter")
	private RodadoConverter rodadoConverter;
	@Autowired
	@Qualifier("personaConverter")
	private PersonaConverter personaConverter;
	@Autowired
	@Qualifier("lugarConverter")
	private LugarConverter lugarConverter;
	
	public PermisoModel entidadAModelo(Permiso permiso) {
		
		PermisoModel permisoModel = null;
		Set<LugarModel> listaLugares = new HashSet<LugarModel>();
		for(Lugar lugar : permiso.getDesdeHasta())
			listaLugares.add(lugarConverter.entidadAModelo(lugar));
		
		if(permiso instanceof PermisoDiario) {
			permisoModel = new PermisoDiarioModel(permiso.getIdPermiso(), 
					personaConverter.entidadAModelo(permiso.getPedido()),
					permiso.getFecha(), ((PermisoDiario) permiso).getMotivo(), listaLugares);
		}
		
		if(permiso instanceof PermisoPeriodo) {
			RodadoModel rodado = rodadoConverter.entidadAModelo(((PermisoPeriodo) permiso).getRodado());
			permisoModel = new PermisoPeriodoModel(permiso.getIdPermiso(), 
					personaConverter.entidadAModelo(permiso.getPedido()),
					permiso.getFecha(),((PermisoPeriodo) permiso).getCantDias(), ((PermisoPeriodo) permiso).isVacaciones(),
					rodado, listaLugares);
		}
		
		return permisoModel;
	}
	public Permiso modeloAEntidad(PermisoModel permisoModel) {
		
		Permiso permiso = null;

		Set<Lugar> listaLugares = new HashSet<Lugar>();
		for(LugarModel lugar : permisoModel.getDesdeHasta())
			listaLugares.add(lugarConverter.modeloAEntidad(lugar));
		
		if(permisoModel instanceof PermisoDiarioModel) {
			permiso = new PermisoDiario(permisoModel.getIdPermiso(), 
					personaConverter.modeloAEntidad(permisoModel.getPedido()),
					permisoModel.getFecha(), ((PermisoDiarioModel) permisoModel).getMotivo(), listaLugares);
		}
		
		if(permisoModel instanceof PermisoPeriodoModel) {
			Rodado rodado = rodadoConverter.modeloAEntidad(((PermisoPeriodoModel) permisoModel).getRodado());
			permiso = new PermisoPeriodo(permisoModel.getIdPermiso(), 
					personaConverter.modeloAEntidad(permisoModel.getPedido()),
					permisoModel.getFecha(),((PermisoPeriodoModel) permisoModel).getCantDias(), ((PermisoPeriodoModel) permisoModel).isVacaciones(),
					rodado, listaLugares);
		}
		
		return permiso;
	}

}
