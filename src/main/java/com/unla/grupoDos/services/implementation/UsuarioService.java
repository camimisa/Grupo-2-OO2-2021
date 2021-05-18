package com.unla.grupoDos.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.unla.grupoDos.entities.Usuario;
import com.unla.grupoDos.models.UsuarioModel;
import com.unla.grupoDos.repositories.IUsuarioRepository;
import com.unla.grupoDos.services.IUsuarioService;
import com.unla.grupoDos.converters.UsuarioConverter;


@Service("usuarioService")
public class UsuarioService implements IUsuarioService{
	@Autowired
	@Qualifier("usuarioRepository")
	private IUsuarioRepository usuarioRepository;
	
	@Autowired
	@Qualifier("usuarioConverter")
	private UsuarioConverter usuarioConverter;	
	
	@Override
	public List<Usuario> getAll() {
		return usuarioRepository.findAll();
	}

	@Override
	public UsuarioModel findById(int id) {
		return usuarioConverter.entidadAModelo(usuarioRepository.findByIdUsuario(id));
	}

	@Override
	public UsuarioModel findByNombreUsuario(String nombreUsuario) {
		return usuarioConverter.entidadAModelo(usuarioRepository.findByNombreUsuario(nombreUsuario));
	}
	@Override
	public UsuarioModel insertOrUpdate(UsuarioModel UsuarioModel) {
		Usuario Usuario = usuarioRepository.save(usuarioConverter.modeloAEntidad(UsuarioModel));
		return usuarioConverter.entidadAModelo(Usuario);
	}

	@Override
	public boolean remove(int id) {
		try {
			usuarioRepository.deleteById(id);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
}
