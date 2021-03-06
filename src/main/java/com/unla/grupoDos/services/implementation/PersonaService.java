package com.unla.grupoDos.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.unla.grupoDos.converters.PersonaConverter;
import com.unla.grupoDos.entities.Persona;
import com.unla.grupoDos.models.PersonaModel;
import com.unla.grupoDos.repositories.IPersonaRepository;
import com.unla.grupoDos.services.IPersonaService;

@Service("personaService")
public class PersonaService implements IPersonaService{
	@Autowired
	@Qualifier("personaRepository")
	private IPersonaRepository personaRepository;
	
	@Autowired
	@Qualifier("personaConverter")
	private PersonaConverter personaConverter;

	@Override
	public List<Persona> getAll() {
		return personaRepository.findAll();
	}

	@Override
	public Persona insertOrUpdate(Persona persona) {
		return personaRepository.save(persona);
	}

	@Override
	public PersonaModel findById(int idPersona) {
		Persona persona = personaRepository.findByIdPersona(idPersona);
		PersonaModel personaModel = null;
		if(persona != null) 
			personaModel = personaConverter.entidadAModelo(persona);
		return personaModel;
	}

	@Override
	public PersonaModel findByNombre(String nombre) {
		Persona persona = personaRepository.findByNombre(nombre);
		PersonaModel personaModel = null;
		if(persona != null) 
			personaModel = personaConverter.entidadAModelo(persona);
		return personaModel;
	}

	@Override
	public PersonaModel findByApellido(String apellido) {
		Persona persona = personaRepository.findByApellido(apellido);
		PersonaModel personaModel = null;
		if(persona != null) 
			personaModel = personaConverter.entidadAModelo(persona);
		return personaModel;
	}

	@Override
	public Persona findByDni(long dni) {
		return personaRepository.findByDni(dni);
	}

	@Override
	public boolean remove(int idPersona) {
		try {
			personaRepository.deleteById(idPersona);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
}
