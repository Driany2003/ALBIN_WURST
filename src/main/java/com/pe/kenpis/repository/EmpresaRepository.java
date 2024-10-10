package com.pe.kenpis.repository;

import com.pe.kenpis.model.api.empresa.sucursal.SucursalDTOResponse;
import com.pe.kenpis.model.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Integer> {

  // Combo listar empresas
  @Query(value = "SELECT e.emp_id AS empId, e.emp_nombre_comercial AS empNombreComercial FROM T_EMPRESA e WHERE e.emp_is_active = 1", nativeQuery = true)
  List<Map<String, Object>> findAllByEmpIsActive();

  // Listar las sucursales por empresa
  @Query(value = "SELECT e.emp_id AS empId,e.emp_responsable AS empResponsable,e.emp_imagen_logo AS empImagenLogo, e.emp_nombre_comercial AS empNombreComercial, e.emp_fecha_contrato_inicio AS empFechaContratoInicio, e.emp_fecha_contrato_fin AS empFechaContratoFin, e.emp_telefono AS empTelefono, e.emp_is_active AS empIsActive FROM T_EMPRESA e WHERE e.emp_padre_id = :empId", nativeQuery = true)
  List<Map<String, Object>> findSucursalesByEmpresaIdList(@Param("empId") Integer empId);

  // Listar empresas
  @Query(value = "SELECT e.emp_id AS empId,e.emp_imagen_logo AS empImagenLogo, e.emp_nombre_comercial AS empNombreComercial, e.emp_fecha_contrato_inicio AS empFechaContratoInicio, e.emp_fecha_contrato_fin AS empFechaContratoFin, e.emp_telefono AS empTelefono, e.emp_is_active AS empIsActive FROM T_EMPRESA e WHERE e.emp_padre_id = 0", nativeQuery = true)
  List<Map<String, Object>> findAllActiveEmpresaById();

  // Combo
  @Query(value = "SELECT e.emp_id AS empId, e.emp_nombre_comercial AS empNombreComercial FROM T_EMPRESA e WHERE e.emp_padre_id = :empId AND e.emp_is_active = 1", nativeQuery = true)
  List<Map<String, Object>> findSucursalesByEmpresaId(@Param("empId") Integer empId);

  // BUSCAR POR ID "empId" LAS SUCURSALES
  @Query(value = "SELECT e.emp_nombre_comercial AS empNombreComercial , e.emp_telefono AS empTelefono, e.emp_responsable AS empResponsable, e.emp_id AS empId FROM T_EMPRESA e WHERE e.emp_id = :empId AND e.emp_padre_id != 0 AND e.emp_is_active = 1", nativeQuery = true)
  Map<String,Object> findBySucursales(@Param("empId") Integer empId);
}
