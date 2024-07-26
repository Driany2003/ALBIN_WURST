package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IVentaService;
import com.pe.kenpis.business.impl.VentaEstadoImpl;
import com.pe.kenpis.model.api.venta.detalle.VentaDetalleResponse;
import com.pe.kenpis.model.api.venta.estado.VentaEstadoRequest;
import com.pe.kenpis.model.api.venta.estado.del_dia.VentaEstadoDelDiaResponse;
import com.pe.kenpis.util.funciones.FxComunes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/kenpis/venta/detalle")
public class WVentaDetalleController {

  @Autowired
  private IVentaService service;

  @Autowired
  VentaEstadoImpl ventaEstado;

  @GetMapping("/find-all")
  public ResponseEntity<List<VentaDetalleResponse>> findAll() {
    List<VentaDetalleResponse> detalles = service.obtenerDetallesDeVenta();
    return new ResponseEntity<>(detalles, HttpStatus.OK);
  }


}
