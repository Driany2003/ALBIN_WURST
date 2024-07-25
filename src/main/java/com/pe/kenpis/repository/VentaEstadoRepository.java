package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.VentaEstadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VentaEstadoRepository extends JpaRepository<VentaEstadoEntity, Integer> {

  List<VentaEstadoEntity> findByVenEstado(String estado);

}
