package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.ProductoInventarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductoInventarioRepository extends JpaRepository<ProductoInventarioEntity, Integer> {

  @Query(value = "SELECT p.pro_id AS productoId, p.pro_precio_costo AS proPrecioCosto, p.pro_precio_venta AS proPrecioVenta, " + "p.pro_categoria AS proCategoria, p.pro_descripcion AS proDescripcion, p.emp_id AS empId, p.pro_imagen AS proImagen, " + "p.pro_is_active AS proIsActive, l.pro_inv_stock_inicial AS proInvStockInicial, l.pro_inv_stock_ventas AS proInvStockVentas, " + "l.pro_inv_fecha_vencimiento AS proInvFechaVencimiento, " +  // Coma añadida aquí
      "l.pro_inv_fecha_creacion AS proInvFechaCreacion " + "FROM t_producto p INNER JOIN t_producto_inventario l ON p.pro_id = l.producto_id", nativeQuery = true)
  List<Map<String, Object>> findProductsWithInventory();

  Optional<ProductoInventarioEntity> findByProductoId(Integer productoId);

}
