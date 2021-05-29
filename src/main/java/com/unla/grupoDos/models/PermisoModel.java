package com.unla.grupoDos.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.unla.grupoDos.entities.Lugar;
import com.unla.grupoDos.entities.Persona;

public abstract class PermisoModel {
	protected int idPermiso;
	protected Persona pedido;
	protected LocalDate fecha;
	protected Set<Lugar> desdeHasta;
	
	public PermisoModel() {}
	
	public PermisoModel(int idPermiso, Persona pedido, LocalDate fecha) {
		super();
		this.idPermiso = idPermiso;
		this.pedido = pedido;
		this.fecha = fecha;
		this.desdeHasta = new HashSet<Lugar>();
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
		this.desdeHasta = desdeHasta;
	}
	
	
	
}
