package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.ProductosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductosRepository  extends JpaRepository<ProductosEntity,Integer> {

  List<ProductosEntity> findByProTipo(String tipo);


}
