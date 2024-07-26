package com.pe.kenpis.repository;

import com.pe.kenpis.model.api.venta.detalle.VentaDetailDTO;
import com.pe.kenpis.model.api.venta.estado.del_dia.VentaEstadoDelDiaResponse;
import com.pe.kenpis.model.entity.VentaEstadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VentaEstadoRepository extends JpaRepository<VentaEstadoEntity, Integer> {

  @Query(value = "EXEC SP_COUNT_PEDIDOS_X_ESTADO", nativeQuery = true)
  Map<String, Object> getCountPedidosXEstado();

  @Query(value = "SELECT  p.pro_tipo AS proTipo, " + "       d.ven_det_cantidad AS venDetCantidad, " +  " cli.cli_nombre AS clienteNombre " +"FROM T_VENTA v " + "INNER JOIN T_VENTA_DETALLE d ON v.ven_id = d.venta_id " + "INNER JOIN T_PRODUCTO p ON d.producto_id = p.pro_id " + "INNER JOIN T_VENTA_ESTADO ve ON v.ven_id = ve.venta_id " + "INNER JOIN T_CLIENTE cli ON v.cliente_id = cli.cli_id " + "WHERE UPPER(ve.ven_estado) = UPPER(:venEstado) " + "AND CAST(v.ven_fecha AS DATE) = CAST(GETDATE() AS DATE)", nativeQuery = true)
  List<Map<String, Object>> findVentaEstadoEntityByVenEstado(@Param("venEstado") String venEstado);

}