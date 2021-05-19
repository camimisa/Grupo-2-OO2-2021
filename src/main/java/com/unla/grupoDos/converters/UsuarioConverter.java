package com.unla.grupoDos.converters;

import org.springframework.stereotype.Component;

import com.unla.grupoDos.entities.Usuario;
import com.unla.grupoDos.models.UsuarioModel;

@Component("usuarioConverter")
public class UsuarioConverter {
	
	public Usuario modeloAEntidad(UsuarioModel usuarioModel) {
		return new Usuario(usuarioModel.getIdUsuario(),usuarioModel.getTipoDoc(),usuarioModel.getDocumento(),
				usuarioModel.getNombre(),usuarioModel.getApellido(),usuarioModel.getEmail(),usuarioModel.getNombreUsuario(),
				usuarioModel.getClave(),usuarioModel.getPerfil());
	}
	
	public UsuarioModel entidadAModelo(Usuario usuario) {
		return new UsuarioModel(usuario.getIdUsuario(), usuario.getTipoDoc(),usuario.getDocumento(), usuario.getNombre(),
				usuario.getApellido(), usuario.getEmail(), usuario.getNombreUsuario(), usuario.getClave(),usuario.getPerfil());
	}
}
