package com.pe.kenpis.repository;

import com.pe.kenpis.model.api.empresa.sucursal.SucursalDTOResponse;
import com.pe.kenpis.model.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Integer> {

  // Combo listar empresas
  @Query(value = "SELECT e.emp_id AS empId, e.emp_nombre_comercial AS empNombreComercial FROM T_EMPRESA e WHERE e.emp_is_active = 1 and e.emp_padre_id = 0", nativeQuery = true)
  List<Map<String, Object>> findAllByEmpIsActive();

  @Transactional
  @Modifying
  @Query(value = "UPDATE T_EMPRESA SET emp_is_active = :estado WHERE emp_id = :empresaId OR emp_padre_id = :empresaId", nativeQuery = true)
  void updateEstadoEmpresaYSucursales(@Param("empresaId") Integer empresaId, @Param("estado") boolean estado);

  // Listar empresas
  @Query(value = "SELECT e.emp_id AS empId, e.emp_responsable AS empResponsable, e.emp_imagen_logo AS empImagenLogo, e.emp_nombre_comercial AS empNombreComercial, e.emp_fecha_contrato_inicio AS empFechaContratoInicio, e.emp_fecha_contrato_fin AS empFechaContratoFin, e.emp_telefono AS empTelefono, e.emp_is_active AS empIsActive FROM T_EMPRESA e WHERE e.emp_padre_id = 0", nativeQuery = true)
  List<Map<String, Object>> findAllActiveEmpresaById();


  // Listar las sucursales por empresa
  @Query(value = "SELECT e.emp_id AS empId, e.emp_nombre_comercial AS empNombreComercial, e.emp_telefono AS empTelefono, e.emp_is_active AS empIsActive FROM T_EMPRESA e WHERE e.emp_padre_id = :empId", nativeQuery = true)
  List<Map<String, Object>> findSucursalesByEmpresaPadreId(@Param("empId") Integer empId);

  //Listar solo sucursales activas
  @Query(value = "SELECT e.emp_id AS empId, e.emp_nombre_comercial AS empNombreComercial, e.emp_telefono AS empTelefono, e.emp_is_active AS empIsActive FROM T_EMPRESA e WHERE e.emp_padre_id = :empId AND e.emp_is_active = 1", nativeQuery = true)
  List<Map<String, Object>> findSucursalesByEmpresa(@Param("empId") Integer empId);

}