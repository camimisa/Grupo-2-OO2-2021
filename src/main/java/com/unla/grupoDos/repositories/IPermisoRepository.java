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
				+ "where pd.id_permiso = :idPermiso",
		nativeQuery=true
	)
	public abstract PermisoDiario permisoDiarioFindById(int idPermiso);
	
	
	@Query(
		value = "SELECT * FROM permiso p "
				+ "INNER JOIN permiso_periodo pp ON p.id_permiso = pp.id_permiso"
				+ "INNER JOIN rodado r ON r.id_rodado = pp.id_rodado"
				+ "WHERE pd.id_permiso = :idPermiso",
		nativeQuery=true
	)
	public abstract PermisoPeriodo permisoPeriodoFindById(int idPermiso);

	@Query(
			value = "FROM Permiso p "
					+ "INNER JOIN FETCH p.desdeHasta pxl "
					+ "WHERE pxl.idLugar IN (:idLugares)"
	)
	public abstract List<Permiso> findByLugares(@Param("idLugares") List<Integer> idLugares);
	
	public abstract Permiso findByIdPermiso(int idPermiso);
	@Query(
			value = "from Permiso p inner join fetch p.pedido pe "
					+ "where p.fecha between (:startDate) and (:endDate)")
	public abstract List<Permiso> getAllBetweenDates(@Param("startDate")LocalDate startDate, @Param("endDate")LocalDate endDate);

	@Query(
			value = "from Permiso p inner join fetch p.pedido pe "
					+ "where pe.dni =:dni")
	public abstract List<Permiso> getAllByPersona(@Param("dni")long dni);

}
