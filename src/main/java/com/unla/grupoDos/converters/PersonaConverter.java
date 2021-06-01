package com.unla.grupoDos.converters;

import org.springframework.stereotype.Component;

import com.unla.grupoDos.entities.Persona;
import com.unla.grupoDos.models.PersonaModel;

@Component("personaConverter")
public class PersonaConverter {
	public Persona modeloAEntidad(PersonaModel personaModel) {
		Persona persona = null;
		if(personaModel != null)
			persona = new Persona(personaModel.getIdPersona(), personaModel.getNombre(), personaModel.getApellido(), personaModel.getDni());
		return persona;
	}
	
	public PersonaModel entidadAModelo(Persona persona) {
		PersonaModel personaModel = null;
		if(persona != null)
			personaModel = new PersonaModel(persona.getIdPersona(), persona.getNombre(), persona.getApellido(), persona.getDni());
		return personaModel;
	}
}
