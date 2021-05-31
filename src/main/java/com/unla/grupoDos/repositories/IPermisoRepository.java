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
		value = "SELECT * FROM permido p "
				+ "INNER JOIN permiso_diario pd ON pd.id_pedido= p.id_pedido"
				+ "where pd.id_permiso = (:idPermiso)",
		nativeQuery=true
	)
	public abstract PermisoDiario permisoDiarioFindById(int idPermiso);
	
	
	@Query(
		value = "SELECT * FROM permiso p "
				+ "INNER JOIN permiso_periodo pp ON p.id_permiso = pp.id_permiso"
				+ "INNER JOIN rodado r ON r.id_rodado = pp.id_rodado"
				+ "WHERE pd.id_permiso = (:idPermiso)",
		nativeQuery=true
	)
	public abstract PermisoPeriodo permisoPeriodoFindById(int idPermiso);
	/*@Query(
			value = "SELECT *, 0 AS clazz_ FROM permisoxlugar pxl"
					+ " INNER JOIN permiso p ON p.id_permiso = pxl.id_permiso"
					+ " INNER JOIN lugar l ON l.id_lugar = pxl.id_lugar"
					+ " WHERE l.id_lugar IN (:idLugares) AND p.fecha between :startDate AND :endDate",
			nativeQuery = true
	)*/
	@Query(
			value = "FROM Permiso p "
					+ "INNER JOIN FETCH p.desdeHasta pxl "
					+ "WHERE pxl.idLugar IN (:idLugares) AND p.fecha between :startDate AND :endDate"
	)
	public abstract List<Permiso> getAllEntreFechasYLugaresEspecificos(
			@Param("startDate") LocalDate startDate, 
			@Param("endDate") LocalDate endDate,
			@Param("idLugares") List<Integer> idLugares
	);
	
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
