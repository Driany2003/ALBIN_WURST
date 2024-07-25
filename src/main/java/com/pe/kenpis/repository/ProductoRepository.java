package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepository extends JpaRepository<ProductoEntity,Integer> {
  List<ProductoEntity> findAllByProCategoria(String proCategoria);

  @Query("SELECT DISTINCT p.proCategoria FROM ProductoEntity p WHERE p.proIsActive = true")
  List<String> findDistinctCategories();

}
