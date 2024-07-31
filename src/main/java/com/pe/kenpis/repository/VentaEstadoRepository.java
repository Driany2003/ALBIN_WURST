package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.VentaEstadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface VentaEstadoRepository extends JpaRepository<VentaEstadoEntity, Integer> {

  @Query(value = "{call SP_COUNT_PEDIDOS_X_ESTADO}", nativeQuery = true)
  Map<String, Object> SP_COUNT_PEDIDOS_X_ESTADO();

  // Consulta nativa corregida
  @Query(value = "{call SP_LISTA_VENTAS_POR_ESTADO_POR_DIA(:VEN_ESTADO)}", nativeQuery = true)
  List<Map<String, Object>> SP_LISTA_VENTAS_POR_ESTADO_POR_DIA(@Param("VEN_ESTADO") String VEN_ESTADO);
}