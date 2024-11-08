package com.pe.kenpis.business;

import com.pe.kenpis.model.api.venta.VentaRequest;
import com.pe.kenpis.model.api.venta.VentaResponse;
import com.pe.kenpis.model.api.venta.detalle.VentaDetalleResponse;
import com.pe.kenpis.model.api.venta.reporteVentasDTO.ReporteVentas;

import java.time.LocalDate;
import java.util.List;

public interface IVentaService {

  VentaResponse findById(Integer venId);

  List<VentaResponse> findAll();

  VentaResponse create(VentaRequest ventaRequest) ;

  List<VentaDetalleResponse> obtenerDetallesDeVenta();

  ReporteVentas obtenerReporteVentas(LocalDate fechaInicio, LocalDate fechaFin);

  ReporteVentas obtenerReporteVentasXFecha(LocalDate fechaInicio, LocalDate fechaFin, Integer empresaId);



}
