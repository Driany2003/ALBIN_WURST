package com.pe.kenpis.repository;

import com.pe.kenpis.model.api.empresa.caja.EmpresaCajaResponse;
import com.pe.kenpis.model.entity.EmpresaCajaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EmpresaCajaRepository extends JpaRepository<EmpresaCajaEntity, Integer> {

  // Obtener todas las cajas de una empresa, ordenadas por fecha de apertura o cierre
  @Query(value = "SELECT * FROM T_EMPRESA_CAJA  WHERE emp_padre_id = :empresaId ORDER BY caja_fecha_apertura DESC", nativeQuery = true)
  List<EmpresaCajaEntity>findCajasByEmpresaId(@Param("empresaId") Integer empresaId);

}