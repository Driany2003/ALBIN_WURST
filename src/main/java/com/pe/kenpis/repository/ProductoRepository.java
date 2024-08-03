package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<ProductoEntity,Integer> {

  @Query("SELECT p FROM ProductoEntity p WHERE p.padreId = 0")
  List<ProductoEntity> findAllCategorias();

  @Query(value = "WITH ProductosRecursivos AS (" +
      "    SELECT pro_id, pro_categoria, pro_precio, pro_descripcion, pro_is_active, padre_id, emp_id, 1 AS nivel" +
      "    FROM T_PRODUCTO" +
      "    WHERE pro_id = :categoriaId" +
      "    UNION ALL" +
      "    SELECT p.pro_id, p.pro_categoria, p.pro_precio, p.pro_descripcion, p.pro_is_active, p.padre_id, p.emp_id, pr.nivel + 1" +
      "    FROM T_PRODUCTO p" +
      "    INNER JOIN ProductosRecursivos pr ON p.padre_id = pr.pro_id" +
      ")" +
      "SELECT * FROM ProductosRecursivos WHERE padre_id = :categoriaId", nativeQuery = true)
  List<ProductoEntity> findProductosByCategoriaId(@Param("categoriaId") int categoriaId);


}
