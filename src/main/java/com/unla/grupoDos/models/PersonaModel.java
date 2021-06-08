package com.unla.grupoDos.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PersonaModel {
	private int idPersona;
	@NotNull
	@Pattern(regexp="^[A-Za-z\\s]+$", message="Debe ingresar un nombre valido. [Solo caracteres]")
	private String nombre;
	@NotNull
	@Pattern(regexp="^[A-Za-z\\s]+$", message="Debe ingresar un apellido valido. [Solo caracteres]")
	private String apellido;
	@NotNull
	@Min(value=1111111, message="Debe ingresar un dni valido. [Solo numeros, de 7 a 8 numeros]")	
	@Max(value=99999999, message="Debe ingresar un dni valido. [Solo numeros, de 7 a 8 numeros]")
	private long dni;
	
	public PersonaModel() {}

	public PersonaModel(int idPersona, String nombre, String apellido, long dni) {
		super();
		this.idPersona = idPersona;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
	}

	public int getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
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

	public long getDni() {
		return dni;
	}

	public void setDni(long dni) {
		this.dni = dni;
	}

	@Override
	public String toString() {
		return "PersonaModel [idPersona=" + idPersona + ", nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
		result = prime * result + (int) (dni ^ (dni >>> 32));
		result = prime * result + idPersona;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonaModel other = (PersonaModel) obj;
		if (apellido == null) {
			if (other.apellido != null)
				return false;
		} else if (!apellido.equals(other.apellido))
			return false;
		if (dni != other.dni)
			return false;
		if (idPersona != other.idPersona)
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
	
	
}
