package com.unla.grupoDos.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="perfil")
public class Perfil {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idPerfil;
	
	@Column(name="nombre", unique=true, nullable=false, length=50)
	private String nombre;
	@Column(name="descripcion", unique=false, nullable=true, length=255)
	private String descripcion;
	
	@Column(name="habilitado", nullable=false)
	private boolean habilitado;

	@Column(name="createdat")
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(name="updatedat")
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	public Perfil() {}

	public Perfil(int idPerfil, String nombre, String descripcion, boolean habilitado) {
		super();
		this.nombre = nombre;
		this.idPerfil = idPerfil;
		this.descripcion = descripcion;
		this.habilitado = habilitado;
	}


	public int getIdPerfil() {
		return idPerfil;
	}

	protected void setIdPerfil(int idPerfil) {
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
	public boolean equals(Object obj) {
		return obj instanceof Perfil && ((Perfil) obj).getNombre().equalsIgnoreCase(this.nombre);
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}
	
}
