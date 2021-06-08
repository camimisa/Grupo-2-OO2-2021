package com.unla.grupoDos.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class LugarModel {
	private int idLugar;
	
	@NotNull
	@Pattern(regexp="^[A-Za-z\\s]+$", message="Debe ingresar un lugar correcto. [Solo caracteres]")
	private String lugar;

	@NotNull
	@Pattern(regexp="^[0-9]{4}$", message="Debe ingresar un codigo postal correcto. [4 numeros]")
	private String codPostal;
	
	public LugarModel() {}

	public LugarModel(int idLugar, String lugar, String codPostal) {
		super();
		this.idLugar = idLugar;
		this.lugar = lugar;
		this.codPostal = codPostal;
	}
	
	public LugarModel(String lugar, String codPostal) {
		super();
		this.lugar = lugar;
		this.codPostal = codPostal;
	}

	public int getIdLugar() {
		return idLugar;
	}

	public void setIdLugar(int idLugar) {
		this.idLugar = idLugar;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}
}
