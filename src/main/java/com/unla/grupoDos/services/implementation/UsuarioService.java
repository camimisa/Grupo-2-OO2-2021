package com.unla.grupoDos.services.implementation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.unla.grupoDos.entities.Perfil;
import com.unla.grupoDos.entities.Usuario;
import com.unla.grupoDos.models.UsuarioModel;
import com.unla.grupoDos.repositories.IUsuarioRepository;
import com.unla.grupoDos.services.IUsuarioService;
import com.unla.grupoDos.converters.UsuarioConverter;


@Service("usuarioService")
public class UsuarioService implements IUsuarioService, UserDetailsService{
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

	@Override
	public List<Usuario> findByIdPerfil(int idPerfil) {
		List<Usuario> lista = usuarioRepository.findByIdPerfil(idPerfil);
		return lista;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByNombreUsuario(username);
		User u = buildUser(usuario, buildGrantedAuthorities(usuario.getPerfil()));
		System.out.println("EN LOAD USER " +u.getUsername());
		return u;
	}
	
	private User buildUser(Usuario usuario, List<GrantedAuthority> grantedAuthorities) {
		return new User(usuario.getNombreUsuario(), usuario.getClave(), usuario.isEnabled(),
						true, true, true, //accountNonExpired, credentialsNonExpired, accountNonLocked,
						grantedAuthorities);
	}
	
	private List<GrantedAuthority> buildGrantedAuthorities(Perfil perfil) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority(perfil.getNombre()));

		return new ArrayList<GrantedAuthority>(grantedAuthorities);
	}
}
