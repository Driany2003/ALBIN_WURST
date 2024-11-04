package com.pe.kenpis.repository;

import com.pe.kenpis.model.api.producto.complementos.ProductoComplementoResponseDTO;
import com.pe.kenpis.model.entity.ProductoComplemetosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Repository
public interface ProductoComplementosRepository extends JpaRepository<ProductoComplemetosEntity, Integer> {

  //LISTAR COMPLEMENTOS PADRES POR EMPRESA
  @Query(value = "{call SP_COMPLEMENTOS_PADRE_POR_EMPRESA(:empId)}", nativeQuery = true)
  List<Map<String, Object>> SP_LISTA_COMPLEMENTOS_PADRE_POR_EMPRESA(@Param("empId") Integer empId);


  @Query(value = "SELECT pc.pro_comp_id AS proCompId , pc.pro_comp_nombre AS proCompNombre, pc.pro_comp_precio AS proCompPrecio FROM T_PRODUCTO_COMPLEMENTOS pc WHERE pc.pro_comp_id_padre = :idPadre AND pc.emp_id = :empId", nativeQuery = true)
  List<Map<String, Object>> findDetallesByIdPadreAndEmpId(@Param("idPadre") Integer idPadre, @Param("empId") Integer empId);

  @Modifying
  @Transactional
  @Query(value = "UPDATE T_PRODUCTO_COMPLEMENTOS SET pro_comp_nombre = CASE WHEN :pro_comp_nombre IS NOT NULL AND pro_comp_nombre <> :pro_comp_nombre THEN :pro_comp_nombre ELSE pro_comp_nombre END, pro_comp_precio = CASE WHEN :pro_comp_precio IS NOT NULL AND pro_comp_precio <> :pro_comp_precio THEN :pro_comp_precio ELSE pro_comp_precio END WHERE pro_comp_id = :pro_comp_id", nativeQuery = true)
  void actualizarNombreYPrecio(@Param("pro_comp_id") Integer pro_comp_id, @Param("pro_comp_nombre") String pro_comp_nombre, @Param("pro_comp_precio") Double pro_comp_precio);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM T_PRODUCTO_COMPLEMENTOS WHERE pro_comp_id = :id", nativeQuery = true)
  void eliminarPorId(@Param("id") Integer id);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM T_PRODUCTO_COMPLEMENTOS WHERE pro_comp_id = :id OR pro_comp_id_padre = :id", nativeQuery = true)
  void deleteComplementoAndDetails(@Param("id") Integer id);

  @Modifying
  @Transactional
  @Query("UPDATE ProductoComplemetosEntity p SET p.proCompNombre = :proCompNombre WHERE p.proCompId = :proCompId")
  void actualizarNombreComplementoPadre(@Param("proCompId") Integer proCompId, @Param("proCompNombre") String proCompNombre);

  //PARA REGISTRAR
  @Query(value = "SELECT " + "p1.pro_comp_id AS id_complemento_principal, " + "p1.pro_comp_nombre AS nombre_complemento_principal, " + "STUFF((SELECT ', ' + p2.pro_comp_nombre " + "FROM T_PRODUCTO_COMPLEMENTOS p2 " + "WHERE p2.pro_comp_id_padre = p1.pro_comp_id " + "AND p2.emp_id = p1.emp_id " + "FOR XML PATH(''), TYPE).value('.', 'NVARCHAR(MAX)'), 1, 2, '') AS subcomplementos " + "FROM T_PRODUCTO_COMPLEMENTOS p1 " + "WHERE (p1.pro_comp_id_padre = 0 OR p1.pro_comp_id_padre IS NULL) " + "AND p1.emp_id = :empId " + "GROUP BY p1.pro_comp_id, p1.pro_comp_nombre, p1.emp_id", nativeQuery = true)
  List<Object[]> findAllWithSubcomplementosByEmpresa(@Param("empId") Integer empId);

  //PARA EDITAR
  @Query(value = "WITH ComplementosSeleccionados AS ( " + "    SELECT value AS complemento_id " + "    FROM STRING_SPLIT((SELECT pro_complementos FROM T_PRODUCTO WHERE pro_id = :productId), ',') " + "), " + "ComplementosPrincipales AS ( " + "    SELECT p1.pro_comp_id AS id_complemento_principal, " + "           p1.pro_comp_nombre AS nombre_complemento_principal, " + "           STUFF(( " + "               SELECT ', ' + p2.pro_comp_nombre " + "               FROM T_PRODUCTO_COMPLEMENTOS p2 " + "               WHERE p2.pro_comp_id_padre = p1.pro_comp_id " + "                 AND p2.emp_id = p1.emp_id " + "               FOR XML PATH(''), TYPE " + "           ).value('.', 'NVARCHAR(MAX)'), 1, 2, '') AS subcomplementos " + "    FROM T_PRODUCTO_COMPLEMENTOS p1 " + "    WHERE (p1.pro_comp_id_padre = 0 OR p1.pro_comp_id_padre IS NULL) " + "      AND p1.emp_id = :empId " + "      AND p1.pro_comp_id IN (SELECT complemento_id FROM ComplementosSeleccionados) " + ") " + "SELECT * FROM ComplementosPrincipales", nativeQuery = true)
  List<Object[]> findComplementosConSubcomplementosByProductoId(@Param("productId") Integer productId, @Param("empId") Integer empId);

}

