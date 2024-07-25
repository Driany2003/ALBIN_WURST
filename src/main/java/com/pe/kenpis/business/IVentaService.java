package com.pe.kenpis.business;

import com.pe.kenpis.model.api.venta.VentaRequest;
import com.pe.kenpis.model.api.venta.VentaResponse;
import com.pe.kenpis.model.api.venta.detalle.VentaDetalleResponse;

import java.util.List;

public interface IVentaService {

  VentaResponse findById(Integer venId);

  List<VentaResponse> findAll();

  VentaResponse create(VentaRequest ventaRequest);

  List<VentaDetalleResponse> obtenerDetallesDeVenta();

}
