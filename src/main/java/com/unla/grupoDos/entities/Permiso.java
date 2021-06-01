package com.unla.grupoDos.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "permiso")
@Inheritance(
    strategy = InheritanceType.JOINED
)
public abstract class Permiso {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int idPermiso;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_pedido", nullable=false)
	protected Persona pedido;
	
	@Column(name = "fecha")
	protected LocalDate fecha;
	

	@Column(name="createdat")
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(name="updatedat")
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@ManyToMany
	@JoinTable(
		name = "permisoxlugar",
		joinColumns = {@JoinColumn(name = "id_permiso", nullable = false)},
		inverseJoinColumns = {@JoinColumn(name = "id_lugar", nullable = false)}
	)
	protected Set<Lugar> desdeHasta;
	
	public Permiso() {}
	
	public Permiso(int idPermiso, Persona pedido, LocalDate fecha, Set<Lugar>desdeHasta) {
		super();
		this.idPermiso = idPermiso;
		this.pedido = pedido;
		this.fecha = fecha;
		this.setDesdeHasta(desdeHasta);
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public abstract boolean esValido(LocalDate desde, LocalDate hasta);

	public abstract LocalDate getFechaVencimiento();
	@Override
	public String toString() {
		return "Permiso [idPermiso=" + idPermiso + ", pedido=" + pedido + ", fecha=" + fecha + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", desdeHasta=" + desdeHasta + "]";
	}
	
}
