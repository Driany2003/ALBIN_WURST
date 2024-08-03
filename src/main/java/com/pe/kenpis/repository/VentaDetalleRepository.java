package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.VentaDetalleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaDetalleRepository extends JpaRepository<VentaDetalleEntity,Integer> {

}
