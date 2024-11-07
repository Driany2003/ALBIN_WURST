package com.pe.kenpis.repository;

import com.pe.kenpis.model.api.producto.ProductoListDTO;
import com.pe.kenpis.model.api.producto.ProductoResponse;
import com.pe.kenpis.model.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Integer> {

  @Query(value = "SELECT p.pro_id AS proId, p.emp_id AS empId, p.pro_categoria AS proCategoria " + "FROM T_PRODUCTO p " + "INNER JOIN T_EMPRESA e ON e.emp_id = p.emp_id " + "WHERE p.padre_id = 0 AND p.emp_id = :empId", nativeQuery = true)
  List<Map<String, Object>> findAllCategorias(@Param("empId") Integer empId);

  @Query(value = "SELECT p.pro_id AS proId, " + "p.emp_id AS empId, " + "p.pro_categoria AS proCategoria, " + "p.pro_descripcion AS proDescripcion, " + "p.pro_imagen AS proImagen " + "FROM T_PRODUCTO p " + "INNER JOIN T_EMPRESA e ON e.emp_id = p.emp_id " + "WHERE p.padre_id = 0 " + "AND p.emp_id = :empId", nativeQuery = true)
  List<Map<String, Object>> findAllCategoriaByEmpresa(@Param("empId") Integer empId);

  @Query(value = "SELECT pro.pro_id AS proId, pro.pro_descripcion AS proDescripcion, pro.pro_imagen AS proImagen, pro.pro_is_active AS proIsActive, pro.pro_precio_costo AS proPrecioCosto, pro.pro_precio_venta AS proPrecioVenta FROM T_PRODUCTO pro INNER JOIN T_EMPRESA e ON pro.emp_id = e.emp_id WHERE pro.padre_id <> 0 AND e.emp_is_active = 1", nativeQuery = true)
  List<Map<String, Object>> findActiveProductosWithActive();

  @Query(value = "SELECT pro.pro_id AS proId, pro.pro_descripcion AS proDescripcion, pro.pro_imagen AS proImagen, pro.pro_is_active AS proIsActive, pro.pro_precio_costo AS proPrecioCosto, pro.pro_precio_venta AS proPrecioVenta " + "FROM T_PRODUCTO pro " + "INNER JOIN T_EMPRESA e ON pro.emp_id = e.emp_id " + "WHERE pro.padre_id <> 0 AND pro.pro_is_active = 1 AND e.emp_is_active = 1 AND e.emp_id = :empId", nativeQuery = true)
  List<Map<String, Object>> findActiveProductosWithActiveEmpresa(@Param("empId") Integer empId);

  @Query(value = "WITH ProductosRecursivos AS ( " + "    SELECT p.pro_id, p.pro_categoria, p.pro_complementos, p.pro_inventario_relacion , p.pro_precio_costo, p.pro_precio_venta, " + "           p.pro_descripcion, p.pro_imagen, p.pro_imagen_longitud, p.pro_is_active, p.padre_id, p.emp_id, 1 AS nivel " + "    FROM T_PRODUCTO p " + "    JOIN T_EMPRESA e ON p.emp_id = e.emp_id " + "    WHERE p.pro_id = :categoriaId AND e.emp_is_active = 1 " + "    UNION ALL " + "    SELECT p.pro_id, p.pro_categoria, p.pro_complementos, p.pro_complementos,p.pro_precio_costo, p.pro_precio_venta, " + "           p.pro_descripcion, p.pro_imagen, p.pro_imagen_longitud, p.pro_is_active, p.padre_id, p.emp_id, pr.nivel + 1 " + "    FROM T_PRODUCTO p " + "    JOIN ProductosRecursivos pr ON p.padre_id = pr.pro_id " + "    JOIN T_EMPRESA e ON p.emp_id = e.emp_id " + "    WHERE e.emp_is_active = 1 " + ") " + "SELECT * FROM ProductosRecursivos " + "WHERE pro_is_active = 1",  // Filtrar solo productos activos
      nativeQuery = true)
  List<ProductoEntity> findProductosByCategoriaId(@Param("categoriaId") int categoriaId);

}
