package com.unla.grupoDos.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name="permiso_diario")
public class PermisoDiario extends Permiso{

	@Column(name="motivo")
	private String motivo;
	public PermisoDiario() {}
	public PermisoDiario(int idPermiso, Persona pedido, LocalDate fecha, String motivo) {
		super(idPermiso, pedido, fecha);
		this.motivo = motivo;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	
}
