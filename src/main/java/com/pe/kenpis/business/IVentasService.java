package com.pe.kenpis.business;

import com.pe.kenpis.model.api.ventas.VentasRequest;
import com.pe.kenpis.model.api.ventas.VentasResponse;
import com.pe.kenpis.model.api.ventas.detalle.DetalleVentaResponse;

import java.util.List;

public interface IVentasService {

  VentasResponse findById(Integer venId);

  List<VentasResponse> findAll();

  VentasResponse registrarVenta(VentasRequest ventaRequest);

  List<DetalleVentaResponse> obtenerDetallesDeVenta();
}
