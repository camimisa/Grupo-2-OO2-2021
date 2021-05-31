package com.unla.grupoDos.repositories;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unla.grupoDos.entities.Permiso;
import com.unla.grupoDos.entities.PermisoPeriodo;
import com.unla.grupoDos.entities.PermisoDiario;

@Repository("permisoRepository")
public interface IPermisoRepository extends JpaRepository<Permiso, Serializable> {

	@Query(
		value = "SELECT * FROM permiso_periodo pp INNER JOIN Rodado r ON pp.id_rodado = r.id_rodado "
			+ "where pp.id_permiso = (:idPermiso)",
		nativeQuery=true
	)
	public abstract PermisoDiario permisoDiarioFindById(int idPermiso);
	
	
	@Query(
		value = "SELECT * FROM permiso_diario pd where pd.id_permiso = (:idPermiso)",
		nativeQuery=true
	)
	public abstract PermisoPeriodo permisoPeriodoFindById(int idPermiso);
	
	public abstract Permiso findByIdPermiso(int idPermiso);
	@Query(
			value = "from Permiso p inner join fetch p.pedido pe "
					+ "where p.fecha between (:startDate) and (:endDate)")
	public abstract List<Permiso> getAllBetweenDates(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);

	// from Permiso p inner join fetch p.pedido pe inner join fetch p.rodado r where p.idPermiso ="
	@Query(
			value = "from Permiso p inner join fetch p.pedido pe "
					+ "where pe.dni =:dni")
	public abstract List<Permiso> getAllByPersona(@Param("dni")long dni);

}
