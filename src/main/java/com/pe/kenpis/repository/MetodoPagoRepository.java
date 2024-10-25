package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.MetodoPagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPagoEntity, Integer> {

  @Query(value = "SELECT * FROM T_METODO_PAGO m WHERE m.emp_id = :empId", nativeQuery = true)
  List<MetodoPagoEntity> findMetodoPagoByEmpId(@Param("empId") Integer empId);

  @Query( value = "SELECT * FROM T_METODO_PAGO  WHERE met_pago_tipo = :metPagoTipo AND emp_id = :empId", nativeQuery = true)
  Optional<MetodoPagoEntity> findByTipoAndEmpId(String metPagoTipo, Integer empId);
}
