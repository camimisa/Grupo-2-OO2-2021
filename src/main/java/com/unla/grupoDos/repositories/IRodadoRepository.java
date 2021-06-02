package com.unla.grupoDos.repositories;
import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unla.grupoDos.entities.Perfil;
import com.unla.grupoDos.entities.Rodado;
import com.unla.grupoDos.entities.Usuario;

@Repository("rodadoRepository")
public interface IRodadoRepository extends JpaRepository<Rodado, Serializable>{

	public abstract Rodado findByDominio(String dominio);
	public abstract Rodado findByIdRodado(int idUsuario);

}
