package com.pe.kenpis.repository;

import com.pe.kenpis.model.api.empresa.caja.EmpresaCajaResponse;
import com.pe.kenpis.model.entity.EmpresaCajaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface EmpresaCajaRepository extends JpaRepository<EmpresaCajaEntity, Integer> {

  // Obtener todas las cajas de una empresa, ordenadas por fecha de apertura o cierre
  @Query(value = "SELECT * FROM T_EMPRESA_CAJA  WHERE emp_padre_id = :empresaId ORDER BY caja_fecha_apertura DESC", nativeQuery = true)
  List<EmpresaCajaEntity> findCajasByEmpresaId(@Param("empresaId") Integer empresaId);

  @Query(value = "SELECT * FROM T_EMPRESA_CAJA  WHERE emp_padre_id = :empresaId  AND caja_estado = 1", nativeQuery = true)
  List<EmpresaCajaEntity> findByEmpresaIdAndCajaEstado(@Param("empresaId") Integer empresaId);

  @Query(value = "SELECT COALESCE(SUM(v.ven_total), 0) AS total_por_caja " + "FROM T_VENTA v " + "JOIN T_EMPRESA_CAJA ec ON v.empresa_id = ec.emp_padre_id AND v.caja_id = ec.caja_id " + "WHERE ec.emp_padre_id = :empresaId AND v.caja_id = :cajaId " + "GROUP BY ec.emp_padre_id, v.caja_id " + "ORDER BY ec.emp_padre_id, v.caja_id", nativeQuery = true)
  float obtenerTotalPorEmpresaYCaja(@Param("empresaId") Integer empresaId, @Param("cajaId") Integer cajaId);

}