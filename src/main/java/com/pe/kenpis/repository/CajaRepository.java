package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.CajaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CajaRepository extends JpaRepository<CajaEntity, Integer> {


}
