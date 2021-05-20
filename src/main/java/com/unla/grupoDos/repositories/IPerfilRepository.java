package com.unla.grupoDos.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.grupoDos.entities.Perfil;

@Repository("perfilRepository")
public interface IPerfilRepository extends JpaRepository<Perfil, Serializable> {

	public abstract Perfil findByIdPerfil(int idPerfil);
	public abstract Perfil findByNombre(String nombre);
}	