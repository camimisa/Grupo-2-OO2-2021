package com.unla.grupoDos.models;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Min;

public class PermisoPeriodoModel extends PermisoModel{
	@Min(value = 1, message = "Para sacar este permiso tiene que seleccionar un valor igual o mayor a 1 dia.")
	private int cantDias;
	private boolean vacaciones;

	@Valid
	private RodadoModel rodado;
	
	public PermisoPeriodoModel() {}
	
	public PermisoPeriodoModel(int idPermiso, PersonaModel pedido, LocalDate fecha, int cantDias, boolean vacaciones, RodadoModel rodado, Set<LugarModel>desdeHasta) {
		super(idPermiso, pedido, fecha, desdeHasta);
		this.cantDias = cantDias;
		this.vacaciones = vacaciones;
		this.rodado = rodado;
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

	public RodadoModel getRodado() {
		return rodado;
	}

	public void setRodado(RodadoModel rodado) {
		this.rodado = rodado;
	}

	@Override
	public LocalDate getFechaVencimiento() {
		LocalDate fechaVencimiento = this.fecha.plusDays(cantDias);
		return fechaVencimiento;
	}
	@Override
	public String toString() {
		return "PermisoPeriodoModel [cantDias=" + cantDias + ", vacaciones=" + vacaciones + ", rodado=" + rodado
				+ ", idPermiso=" + idPermiso + ", pedido=" + pedido + ", fecha=" + fecha + ", desdeHasta=" + desdeHasta
				+ "]";
	}
	
	
	

}
