package com.unla.grupoDos.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.grupoDos.entities.Usuario;


@Repository("usuarioRepository")
public interface IUsuarioRepository extends JpaRepository<Usuario, Serializable> {

	//public abstract Usuario findByUserName(String nombeUsuario);
	public abstract Usuario findByDocumento(long doc);
	//public abstract List<Usuario> findByInstitutionAndYearOrderByYearDesc(String institution, int year);
	public abstract Usuario findByIdUsuario(int idUsuario);
	public abstract Usuario findByNombreUsuario(String nombreUsuario);
}	
