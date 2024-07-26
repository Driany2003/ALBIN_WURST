package com.pe.kenpis.repository;

import com.pe.kenpis.model.api.venta.detalle.VentaDetailDTO;
import com.pe.kenpis.model.entity.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VentaRepository extends JpaRepository<VentaEntity,Integer> {

}
