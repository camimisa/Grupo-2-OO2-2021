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

@Service("PersonaService")
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
	public PersonaModel insertOrUpdate(PersonaModel personaModel) {
		Persona persona = personaRepository.save(personaConverter.modeloAEntidad(personaModel));
		return personaConverter.entidadAModelo(persona);
	}

	@Override
	public PersonaModel findById(int idPersona) {
		return personaConverter.entidadAModelo(personaRepository.findByIdPersona(idPersona));
	}

	@Override
	public PersonaModel findByNombre(String nombre) {
		return personaConverter.entidadAModelo(personaRepository.findByNombre(nombre));
	}

	@Override
	public PersonaModel findByApellido(String apellido) {
		return personaConverter.entidadAModelo(personaRepository.findByApellido(apellido));
	}

	@Override
	public PersonaModel findByDni(long dni) {
		return personaConverter.entidadAModelo(personaRepository.findByDni(dni));
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
