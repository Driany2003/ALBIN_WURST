package com.pe.kenpis.web;

import com.pe.kenpis.business.IVentaService;
import com.pe.kenpis.model.api.venta.detalle.DetalleVentaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/kenpis/venta/detalle")
public class WVentaDetalleController {

  @Autowired
  private IVentaService service;

  @GetMapping("/find-all")
  public ResponseEntity<List<DetalleVentaResponse>> findAll() {
    List<DetalleVentaResponse> detalles = service.obtenerDetallesDeVenta();
    return new ResponseEntity<>(detalles, HttpStatus.OK);
  }

}
