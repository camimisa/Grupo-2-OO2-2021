package com.unla.grupoDos.services;

import java.util.List;

import com.unla.grupoDos.entities.Persona;
import com.unla.grupoDos.models.PersonaModel;

public interface IPersonaService {
	public List<Persona> getAll();
	public Persona insertOrUpdate(Persona persona);
	public PersonaModel findById(int idPersona);
	public PersonaModel findByNombre(String nombre);
	public PersonaModel findByApellido(String apellido);
	public Persona findByDni(long dni);
	public boolean remove(int idPersona);
}
