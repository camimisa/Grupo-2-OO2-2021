package com.unla.grupoDos.models;

import javax.validation.constraints.Pattern;

public class RodadoModel {
	private int idRodado;
	@Pattern(regexp="^([a-zA-Z]{3}[0-9]{3})|([a-zA-Z]{2}[0-9]{3}[a-zA-Z]{2})$", message="Debe ingresar una patente valida. [AAA123 // AA123AA]")
	private String dominio;
	private String vehiculo;
	
	public RodadoModel() {}

	public RodadoModel(int idRodado, String dominio, String vehiculo) {
		super();
		this.idRodado = idRodado;
		this.dominio = dominio;
		this.vehiculo = vehiculo;
	}

	public int getIdRodado() {
		return idRodado;
	}

	protected void setIdRodado(int idRodado) {
		this.idRodado = idRodado;
	}

	public String getDominio() {
		return dominio;
	}

	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	public String getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(String vehiculo) {
		this.vehiculo = vehiculo;
	}

	@Override
	public String toString() {
		return "RodadoModel [idRodado=" + idRodado + ", dominio=" + dominio + ", vehiculo=" + vehiculo + "]";
	}
	
	
}
