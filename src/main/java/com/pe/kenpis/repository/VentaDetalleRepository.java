package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.VentaDetalleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VentaDetalleRepository extends JpaRepository<VentaDetalleEntity,Integer> {

}
