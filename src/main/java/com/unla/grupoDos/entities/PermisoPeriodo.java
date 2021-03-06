package com.unla.grupoDos.entities;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity(name = "permisoPeriodo")
public class PermisoPeriodo extends Permiso{

	@Column(name = "cant_dias", nullable = false)
	private int cantDias;
	
	@Column(name = "vacaciones", nullable = false)
	private boolean vacaciones;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_rodado", nullable=false)
	private Rodado rodado;

	public PermisoPeriodo() {}

	public PermisoPeriodo(int idPermiso, Persona pedido, LocalDate fecha, int cantDias, boolean vacaciones,
			Rodado rodado,  Set<Lugar>desdeHasta) {
		super(idPermiso, pedido, fecha, desdeHasta);
		this.cantDias = cantDias;
		this.vacaciones = vacaciones;
		this.rodado = rodado;
		this.desdeHasta = desdeHasta;
	}

	public int getCantDias() {
		return cantDias;
	}

	public void setCantDias(int cantDias) {
		this.cantDias = cantDias;
	}

	public boolean isVacaciones() {
		return vacaciones;
	}

	public void setVacaciones(boolean vacaciones) {
		this.vacaciones = vacaciones;
	}

	public Rodado getRodado() {
		return rodado;
	}

	public void setRodado(Rodado rodado) {
		this.rodado = rodado;
	}

	@Override
	public boolean esValido(LocalDate desde, LocalDate hasta) { 
		LocalDate fechaHastaEnLaQueElPermisoEsValido = this.fecha.plusDays(cantDias);
		return (fechaHastaEnLaQueElPermisoEsValido.isEqual(desde) 
			|| fechaHastaEnLaQueElPermisoEsValido.isEqual(hasta) 
			|| (hasta.isBefore(fechaHastaEnLaQueElPermisoEsValido) 
				&& 
				desde.isAfter(fechaHastaEnLaQueElPermisoEsValido)
			)
		);
	}
}
