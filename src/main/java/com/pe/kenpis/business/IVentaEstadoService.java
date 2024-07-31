package com.pe.kenpis.business;

import com.pe.kenpis.model.api.venta.estado.VentaEstadoRequest;
import com.pe.kenpis.model.api.venta.estado.VentaEstadoResponse;
import com.pe.kenpis.model.api.venta.estado.VentasEstadoDTO;

import java.util.List;
import java.util.Map;

public interface IVentaEstadoService {

  List<VentaEstadoResponse> findAll();

  VentaEstadoResponse findById(Integer id);

  VentaEstadoResponse create(VentaEstadoRequest request) throws Exception;

  VentaEstadoResponse update(VentaEstadoRequest request) throws Exception;

  VentaEstadoResponse delete(Integer id) throws Exception;

  List<VentasEstadoDTO> SP_LISTA_VENTAS_POR_ESTADO_POR_DIA(String estado);

  Map<String, Object> getCountPedidosXEstado();

}