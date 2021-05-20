package com.unla.grupoDos.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unla.grupoDos.entities.Usuario;


@Repository("usuarioRepository")
public interface IUsuarioRepository extends JpaRepository<Usuario, Serializable> {

	public abstract Usuario findByDocumento(long doc);
	public abstract Usuario findByIdUsuario(int idUsuario);
	public abstract Usuario findByNombreUsuario(String nombreUsuario);
	
	@Query(value = "SELECT * FROM usuario u INNER JOIN perfil p ON u.id_perfil = p.id_perfil "
			+ "where p.id_perfil=:idPerfil",nativeQuery=true)
	public abstract List<Usuario> findByIdPerfil(@Param("idPerfil") int idPerfil);
	
}	
