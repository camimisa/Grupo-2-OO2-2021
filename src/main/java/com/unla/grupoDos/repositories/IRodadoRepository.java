package com.unla.grupoDos.repositories;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.grupoDos.entities.Rodado;

@Repository("rodadoRepository")
public interface IRodadoRepository extends JpaRepository<Rodado, Serializable>{

	public abstract Rodado findByDominio(String dominio);
	public abstract Rodado findByIdRodado(int idUsuario);

}
