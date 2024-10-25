package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.ProductoComplemetosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoComplementosRepository extends JpaRepository<ProductoComplemetosEntity, Integer> {

}
