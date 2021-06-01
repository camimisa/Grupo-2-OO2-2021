package com.unla.grupoDos.entities;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="lugar")
public class Lugar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idLugar;
	
	@Column(name="lugar", nullable=false, length=35)
	private String lugar;
	
	@Column(name="codPostal", unique=true, nullable=false, length=10)
	private String codPostal;
	
	
	public Lugar() {}

	public Lugar(int idLugar, String lugar, String codPostal) {
		super();
		this.idLugar = idLugar;
		this.lugar = lugar;
		this.codPostal = codPostal;
	
	}
	
	public Lugar(String lugar, String codPostal) {
		super();
		this.setLugar(lugar);
		this.setCodPostal(codPostal);
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
		if(codPostal.matches("[0-9]{4}"))
			this.codPostal = codPostal;
		else
			this.codPostal = "0000";
	}

	@Override
	public String toString() {
		return this.codPostal + " - " + this.lugar;
	}
	
	
}
