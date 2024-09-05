package com.pe.kenpis.repository;

import com.pe.kenpis.model.api.empresa.EmpresaDTO;
import com.pe.kenpis.model.api.empresa.EmpresaResponse;
import com.pe.kenpis.model.entity.EmpresaEntity;
import com.pe.kenpis.model.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Integer> {

  @Query(value = "SELECT e.emp_id as empId, e.emp_razon_social as empRazonSocial FROM T_EMPRESA e WHERE e.emp_is_active = 1", nativeQuery = true)
  List<Map<String, Object>> findAllByEmpIsActive();

}
