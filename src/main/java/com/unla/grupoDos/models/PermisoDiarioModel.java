package com.unla.grupoDos.models;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.unla.grupoDos.entities.Lugar;
import com.unla.grupoDos.entities.Persona;

public class PermisoDiarioModel extends PermisoModel{
	@NotNull
	private String motivo;

	public PermisoDiarioModel() {}
	public PermisoDiarioModel(int idPermiso, PersonaModel pedido, LocalDate fecha, String motivo, Set<LugarModel>desdeHasta) {
		super(idPermiso, pedido, fecha, desdeHasta);
		this.motivo = motivo;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	@Override
	public String toString() {
		return "PermisoDiarioModel [motivo=" + motivo + ", idPermiso=" + idPermiso + ", pedido=" + pedido + ", fecha="
				+ fecha + ", desdeHasta=" + desdeHasta + "]";
	}
	
	
	
}
