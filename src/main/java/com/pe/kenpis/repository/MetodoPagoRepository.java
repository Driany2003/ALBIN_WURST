package com.pe.kenpis.repository;

import com.pe.kenpis.model.api.metodoPago.MetodoPagoResponse;
import com.pe.kenpis.model.entity.MetodoPagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MetodoPagoRepository extends JpaRepository<MetodoPagoEntity,Integer> {
  @Query(value = "SELECT m.met_pago_tipo AS metPagoTipo, m.met_pago_logo AS metPagoLogo, m.met_pago_qr AS metPagoQr, "
      + "m.met_pago_cuenta_nombre AS metPagoCuentaNombre, m.met_pago_cuenta_numero AS metPagoCuentaNumero, "
      + "m.met_pago_detalle AS metPagoDetalle "
      + "FROM T_METODO_PAGO m WHERE m.emp_id = :empId", nativeQuery = true)
  List<MetodoPagoEntity> findMetodoPagoByEmpId(@Param("empId") Integer empId);


}
