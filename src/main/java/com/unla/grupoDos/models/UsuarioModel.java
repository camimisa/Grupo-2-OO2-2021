package com.unla.grupoDos.models;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UsuarioModel {

	private int idUsuario;
	private String tipoDoc;
	private long documento;
	private String nombre;
	private String apellido;
	
	@Pattern(regexp="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message="Ingrese un email correcto")
	private String email;
	private String nombreUsuario;
	private String clave;
	
	public UsuarioModel() {}
	
	public UsuarioModel(long documento, String nombre, String apellido, String tipoDoc, String email, String nombreUsuario,
			String clave) {
		super();
		this.documento = documento;
		this.nombre = nombre;
		this.apellido = apellido;
		this.tipoDoc = tipoDoc;
		this.email = email;
		this.nombreUsuario = nombreUsuario;
		this.clave = clave;
	}


	
	public UsuarioModel(int idUsuario, String tipoDoc, long documento, String nombre, String apellido,String email,
			String nombreUsuario, String clave) {
		super();
		this.idUsuario = idUsuario;
		this.tipoDoc = tipoDoc;
		this.documento = documento;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.nombreUsuario = nombreUsuario;
		this.clave = clave;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	protected void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public long getDocumento() {
		return documento;
	}

	public void setDocumento(long documento) {
		this.documento = documento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	@Override
	public String toString() {
		return "UsuarioModel [idUsuario=" + idUsuario + ", tipoDoc=" + tipoDoc + ", documento=" + documento
				+ ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", nombreUsuario="
				+ nombreUsuario + ", clave=" + clave + "]";
	}
	
	
}
