package com.unla.grupoDos.models;

public class PerfilModel {
	
	private int idPerfil;
	private String nombre;
	private String descripcion;
	private boolean habilitado;
	
	public PerfilModel() {}

	public PerfilModel(String nombre, String descripcion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	public PerfilModel(int idPerfil, String nombre, String descripcion, boolean habilitado) {
		super();
		this.nombre = nombre;
		this.idPerfil = idPerfil;
		this.descripcion = descripcion;
		this.habilitado = habilitado;
	}

	public int getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "PerfilModel [idPerfil=" + idPerfil + ", nombre=" + nombre + ", descripcion=" + descripcion + "]";
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}
	
	

}
