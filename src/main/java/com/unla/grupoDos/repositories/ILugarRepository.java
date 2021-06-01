package com.unla.grupoDos.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unla.grupoDos.entities.Lugar;

@Repository("lugarRepository")
public interface ILugarRepository extends JpaRepository<Lugar, Serializable> {

	public abstract Lugar findByIdLugar(int idLugar);
	public abstract Lugar findByCodPostal(String codPostal);
	@Query(value = "FROM Lugar l WHERE id IN (:ids)")
	public abstract List<Lugar> findAllByIds(List<Integer> ids);
}	