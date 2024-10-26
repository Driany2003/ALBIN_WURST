package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.EmpresaCajaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaCajaRepository extends JpaRepository<EmpresaCajaEntity, Integer> {


}
