package com.unla.grupoDos.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.unla.grupoDos.entities.Lugar;
import com.unla.grupoDos.entities.Persona;

public abstract class PermisoModel {
	protected int idPermiso;
	protected Persona pedido;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected LocalDate fecha;
	protected Set<Lugar> desdeHasta;
	
	public PermisoModel() {}
	
	public PermisoModel(int idPermiso, Persona pedido, LocalDate fecha, Set<Lugar>desdeHasta) {
		super();
		this.idPermiso = idPermiso;
		this.pedido = pedido;
		this.fecha = fecha;
		this.desdeHasta = desdeHasta;
	}
	
	public int getIdPermiso() {
		return idPermiso;
	}
	public void setIdPermiso(int idPermiso) {
		this.idPermiso = idPermiso;
	}
	public Persona getPedido() {
		return pedido;
	}
	public void setPedido(Persona pedido) {
		this.pedido = pedido;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public Set<Lugar> getDesdeHasta() {
		return desdeHasta;
	}
	public void setDesdeHasta(Set<Lugar> desdeHasta) {
		if(desdeHasta.size() == 2)
			this.desdeHasta = desdeHasta;
		else
			this.desdeHasta = null;
	}
	
	
	
}
