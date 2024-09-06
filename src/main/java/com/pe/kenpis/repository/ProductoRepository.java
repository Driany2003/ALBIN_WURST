package com.pe.kenpis.repository;

import com.pe.kenpis.model.api.producto.ProductoListDTO;
import com.pe.kenpis.model.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Integer> {

  @Query("SELECT p FROM ProductoEntity p WHERE p.padreId = 0")
  List<ProductoEntity> findAllCategorias();

  @Query(value = "SELECT " + "productoen0_.pro_id AS proId," + "productoen0_.pro_descripcion AS proDescripcion, " + "productoen0_.pro_imagen AS proImagen, " + "productoen0_.pro_is_active AS proIsActive, " + "productoen0_.pro_precio AS proPrecio " + "FROM T_PRODUCTO productoen0_ " + "INNER JOIN T_EMPRESA e ON productoen0_.emp_id = e.emp_id " + "WHERE productoen0_.padre_id <> 0 " + "AND productoen0_.pro_is_active = 1 " + "AND e.emp_is_active = 1", nativeQuery = true)
  List<Map<String, Object>> findActiveProductosWithActiveEmpresa();

  @Query(value = "WITH ProductosRecursivos AS (" + "    SELECT p.pro_id, p.pro_categoria, p.pro_precio, p.pro_descripcion, p.pro_imagen, p.pro_imagen_longitud, p.pro_is_active, p.padre_id, p.emp_id, 1 AS nivel" + "    FROM T_PRODUCTO p" + "    JOIN T_EMPRESA e ON p.emp_id = e.emp_id" + "    WHERE p.pro_id = :categoriaId AND e.emp_is_active = 1" + "    UNION ALL" + "    SELECT p.pro_id, p.pro_categoria, p.pro_precio, p.pro_descripcion, p.pro_imagen, p.pro_imagen_longitud, p.pro_is_active, p.padre_id, p.emp_id, pr.nivel + 1" + "    FROM T_PRODUCTO p" + "    JOIN ProductosRecursivos pr ON p.padre_id = pr.pro_id" + "    JOIN T_EMPRESA e ON p.emp_id = e.emp_id" + "    WHERE e.emp_is_active = 1" + ")" + "SELECT * FROM ProductosRecursivos " + "WHERE padre_id = :categoriaId AND pro_is_active = 1",  // Producto activo
      nativeQuery = true)
  List<ProductoEntity> findProductosByCategoriaId(@Param("categoriaId") int categoriaId);

}
