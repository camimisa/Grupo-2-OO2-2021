package com.unla.grupoDos.entities;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name="permiso_diario")
public class PermisoDiario extends Permiso{

	@Column(name="motivo", nullable=false)
	private String motivo;
	public PermisoDiario() {}
	public PermisoDiario(int idPermiso, Persona pedido, LocalDate fecha, String motivo, Set<Lugar>desdeHasta) {
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
	public LocalDate getFechaVencimiento() {
		return fecha;
	}
	
	@Override
	public boolean esValido(LocalDate desde, LocalDate hasta) { 
		LocalDate fechaEnLaQueSacoElPermiso = this.fecha;
		return (fechaEnLaQueSacoElPermiso.isEqual(desde) 
			|| fechaEnLaQueSacoElPermiso.isEqual(hasta) 
			|| (hasta.isBefore(fechaEnLaQueSacoElPermiso) 
				&& 
				desde.isAfter(fechaEnLaQueSacoElPermiso)
			)
		);
	}
	
}
