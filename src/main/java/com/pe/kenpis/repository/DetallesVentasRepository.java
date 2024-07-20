package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.DetallesVentasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DetallesVentasRepository extends JpaRepository<DetallesVentasEntity,Integer> {
  @Query(value = "SELECT d.*, p.pro_id, p.pro_nombre, p.pro_tipo, p.pro_precio " +
      "FROM T_DETALLE_VENTA d " +
      "JOIN T_PRODUCTO p ON d.producto_id = p.pro_id", nativeQuery = true)
  List<Object[]> findAllDetailsWithProducts();
}
