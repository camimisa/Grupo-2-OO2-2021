package com.unla.grupoDos.models;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.unla.grupoDos.entities.Lugar;
import com.unla.grupoDos.entities.Persona;

public abstract class PermisoModel {
	protected int idPermiso;
	protected PersonaModel pedido;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected LocalDate fecha;
	protected Set<LugarModel> desdeHasta;
	
	public PermisoModel() {}
	
	public PermisoModel(int idPermiso, PersonaModel pedido, LocalDate fecha, Set<LugarModel>desdeHasta) {
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
	public PersonaModel getPedido() {
		return pedido;
	}
	public void setPedido(PersonaModel pedido) {
		this.pedido = pedido;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public Set<LugarModel> getDesdeHasta() {
		return desdeHasta;
	}
	public void setDesdeHasta(Set<LugarModel> desdeHasta) {
		if(desdeHasta.size() == 2 || desdeHasta.isEmpty())
			this.desdeHasta = desdeHasta;
		else
			this.desdeHasta = null;
	}
	
	
	
}
