package com.unla.grupoDos.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.grupoDos.entities.Persona;

@Repository("personaRepository")
public interface IPersonaRepository extends JpaRepository<Persona, Serializable>{
	public abstract Persona findByIdPersona(int idPerfil);
	public abstract Persona findByNombre(String nombre);
	public abstract Persona findByApellido(String apellido);
	public abstract Persona findByDni(long dni);
}
