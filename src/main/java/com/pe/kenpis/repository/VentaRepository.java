package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface VentaRepository extends JpaRepository<VentaEntity, Integer> {

  @Query(value = "SELECT " + "SUM(v.ven_total) AS precioVentaTotal, " + "SUM(vd.ven_det_cantidad * p.pro_precio_costo) AS precioCostoTotal, " + "SUM(v.ven_total) - SUM(vd.ven_det_cantidad * p.pro_precio_costo) AS ganancia, " + "COUNT(DISTINCT v.ven_id) AS totalVentas, " + "SUM(CASE WHEN v.ven_tipo_pago = 'Yape' THEN v.ven_total ELSE 0 END) AS totalYape, " + "SUM(CASE WHEN v.ven_tipo_pago = 'Plin' THEN v.ven_total ELSE 0 END) AS totalPlin, " + "SUM(CASE WHEN v.ven_tipo_pago = 'Efectivo' THEN v.ven_total ELSE 0 END) AS totalEfectivo, " + "p.pro_id AS productoId, " + "p.pro_descripcion AS productoNombre, " + "SUM(vd.ven_det_cantidad) AS cantidadVendida, " + "ROUND(SUM(vd.ven_det_cantidad) * 100.0 / SUM(SUM(vd.ven_det_cantidad)) OVER (), 2) AS popularidad " + "FROM T_VENTA v " + "JOIN T_VENTA_DETALLE vd ON v.ven_id = vd.venta_id " + "JOIN T_PRODUCTO p ON vd.producto_id = p.pro_id " + "WHERE CONVERT(DATE, v.ven_fecha) BETWEEN :fechaInicio AND :fechaFin " + "GROUP BY p.pro_id, p.pro_descripcion " + "ORDER BY cantidadVendida DESC", nativeQuery = true)
  List<Map<String, Object>> obtenerReporteVentas(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

  @Query(value = "SELECT " + "(SELECT COALESCE(SUM(v.ven_total), 0) " + " FROM T_VENTA v " + " WHERE CAST(v.ven_fecha AS DATE) BETWEEN :fechaInicio AND :fechaFin " + " AND v.empresa_id = :empresaId) AS precioVentaTotal, " +

      "(SELECT COALESCE(SUM(vd.ven_det_cantidad * p.pro_precio_costo), 0) " + " FROM T_VENTA v " + " JOIN T_VENTA_DETALLE vd ON v.ven_id = vd.venta_id " + " JOIN T_PRODUCTO p ON vd.producto_id = p.pro_id " + " WHERE CAST(v.ven_fecha AS DATE) BETWEEN :fechaInicio AND :fechaFin " + " AND v.empresa_id = :empresaId) AS precioCostoTotal, " +

      "(COALESCE((SELECT SUM(v.ven_total) " + "           FROM T_VENTA v " + "           WHERE CAST(v.ven_fecha AS DATE) BETWEEN :fechaInicio AND :fechaFin " + "           AND v.empresa_id = :empresaId), 0) " + " - COALESCE((SELECT SUM(vd.ven_det_cantidad * p.pro_precio_costo) " + "             FROM T_VENTA v " + "             JOIN T_VENTA_DETALLE vd ON v.ven_id = vd.venta_id " + "             JOIN T_PRODUCTO p ON vd.producto_id = p.pro_id " + "             WHERE CAST(v.ven_fecha AS DATE) BETWEEN :fechaInicio AND :fechaFin " + "             AND v.empresa_id = :empresaId), 0)) AS ganancia, " +

      "(SELECT COALESCE(COUNT(DISTINCT v.ven_id), 0) " + " FROM T_VENTA v " + " WHERE CAST(v.ven_fecha AS DATE) BETWEEN :fechaInicio AND :fechaFin " + " AND v.empresa_id = :empresaId) AS totalVentas, " +

      "(SELECT COALESCE(SUM(CASE WHEN v.ven_tipo_pago = 'Yape' THEN v.ven_total ELSE 0 END), 0) " + " FROM T_VENTA v " + " WHERE CAST(v.ven_fecha AS DATE) BETWEEN :fechaInicio AND :fechaFin " + " AND v.empresa_id = :empresaId) AS totalYape, " +

      "(SELECT COALESCE(SUM(CASE WHEN v.ven_tipo_pago = 'Plin' THEN v.ven_total ELSE 0 END), 0) " + " FROM T_VENTA v " + " WHERE CAST(v.ven_fecha AS DATE) BETWEEN :fechaInicio AND :fechaFin " + " AND v.empresa_id = :empresaId) AS totalPlin, " +

      "(SELECT COALESCE(SUM(CASE WHEN v.ven_tipo_pago = 'Efectivo' THEN v.ven_total ELSE 0 END), 0) " + " FROM T_VENTA v " + " WHERE CAST(v.ven_fecha AS DATE) BETWEEN :fechaInicio AND :fechaFin " + " AND v.empresa_id = :empresaId) AS totalEfectivo, " +

      "(SELECT COALESCE(SUM(CASE WHEN v.ven_tipo_pago = 'Tarjeta' THEN v.ven_total ELSE 0 END), 0) " + " FROM T_VENTA v " + " WHERE CAST(v.ven_fecha AS DATE) BETWEEN :fechaInicio AND :fechaFin " + " AND v.empresa_id = :empresaId) AS totalTarjeta, " +

      "p.pro_id AS productoId, " + "p.pro_descripcion AS productoNombre, " + "SUM(vd.ven_det_cantidad) AS cantidadVendida, " + "ROUND(SUM(vd.ven_det_cantidad) * 100.0 / NULLIF(SUM(SUM(vd.ven_det_cantidad)) OVER (), 0), 2) AS popularidad " + "FROM T_VENTA v " + "JOIN T_VENTA_DETALLE vd ON v.ven_id = vd.venta_id " + "JOIN T_PRODUCTO p ON vd.producto_id = p.pro_id " + "WHERE CAST(v.ven_fecha AS DATE) BETWEEN :fechaInicio AND :fechaFin " + "AND v.empresa_id = :empresaId " + "GROUP BY p.pro_id, p.pro_descripcion " + "ORDER BY cantidadVendida DESC", nativeQuery = true)
  List<Map<String, Object>> obtenerReporteVentasXFecha(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin, @Param("empresaId") Integer empresaId);
}
