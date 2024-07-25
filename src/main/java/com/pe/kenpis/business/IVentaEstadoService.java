package com.pe.kenpis.business;

import com.pe.kenpis.model.api.venta.estado.VentaEstadoRequest;
import com.pe.kenpis.model.api.venta.estado.VentaEstadoResponse;

import java.util.List;

public interface IVentaEstadoService {

  List<VentaEstadoResponse> findAll();

  VentaEstadoResponse findById(Integer id);

  VentaEstadoResponse create(VentaEstadoRequest request) throws Exception;

  VentaEstadoResponse update(VentaEstadoRequest request) throws Exception;

  VentaEstadoResponse delete(Integer id) throws Exception;

}