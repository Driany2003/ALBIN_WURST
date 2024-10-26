package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.ProductoComplemetosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ProductoComplementosRepository extends JpaRepository<ProductoComplemetosEntity, Integer> {

  //LISTAR COMPLEMENTOS PADRES POR EMPRESA
  @Query(value = "{call SP_LISTA_COMPLEMENTOS_PADRE_POR_EMPRESA}", nativeQuery = true)
  Map<String, Object> SP_LISTA_COMPLEMENTOS_PADRE_POR_EMPRESA();

}
