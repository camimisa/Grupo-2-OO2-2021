package com.unla.grupoDos.entities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

	public int getIdLugar() {
		return idLugar;
	}

	protected void setIdLugar(int idLugar) {
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

	@Override
	public String toString() {
		return "Lugar [idLugar=" + idLugar + ", lugar=" + lugar + ", codPostal=" + codPostal + "]";
	}
	
	
}
