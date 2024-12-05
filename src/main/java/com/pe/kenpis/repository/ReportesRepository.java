package com.pe.kenpis.repository;

import com.pe.kenpis.model.api.reporte.ReporteResponse;
import com.pe.kenpis.model.entity.ReporteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ReportesRepository extends JpaRepository<ReporteEntity, Integer> {

  @Query(value = "SELECT r.caja_id AS cajaId,  r.rep_fecha_creacion AS repFechaCreacion, s.emp_nombre_comercial AS sucursalNombre, r.rep_is_active AS repIsActive " +
      "FROM T_REPORTE r " +
      "JOIN T_EMPRESA s ON r.sucursal_id = s.emp_id " +
      "WHERE r.emp_id = :empId",
      nativeQuery = true)
  List<Map<String, Object>> findReportesWithSucursalNameByEmpId(@Param("empId") Integer empId);

  Optional<ReporteEntity> findByCajaId(Integer cajaId);

}
