package com.unla.grupoDos.converters;

import com.unla.grupoDos.entities.Persona;
import com.unla.grupoDos.models.PersonaModel;

public class PersonaConverter {
	public Persona modeloAEntidad(PersonaModel personaModel) {
		return new Persona(personaModel.getIdPersona(), personaModel.getNombre(), 
				personaModel.getApellido(), personaModel.getDni());
	}
	
	public PersonaModel entidadAModelo(Persona persona) {
			return new PersonaModel(persona.getIdPersona(), persona.getNombre(),
					persona.getApellido(), persona.getDni());
	}
}
