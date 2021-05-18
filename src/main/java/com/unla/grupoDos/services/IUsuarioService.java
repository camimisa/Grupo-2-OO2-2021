package com.unla.grupoDos.services;

import java.util.List;

import com.unla.grupoDos.entities.Usuario;
import com.unla.grupoDos.models.UsuarioModel;


public interface IUsuarioService {

	public List<Usuario> getAll();
	public UsuarioModel insertOrUpdate(UsuarioModel usuarioModel);
	public boolean remove(int id);
	UsuarioModel findById(int id);
	UsuarioModel findByNombreUsuario(String nombreUsuario);

}
