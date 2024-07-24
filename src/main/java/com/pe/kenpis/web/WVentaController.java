package com.pe.kenpis.web;

import com.pe.kenpis.business.IVentaService;
import com.pe.kenpis.model.api.venta.VentaRequest;
import com.pe.kenpis.model.api.venta.VentaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/kenpis/venta")
public class WVentaController {

  @Autowired
  private IVentaService service;

  @PostMapping("/create")
  public ResponseEntity<VentaResponse> create(@RequestBody VentaRequest ventaRequest) {
    System.out.println("lo que trae del js al backend " + ventaRequest);
    VentaResponse ventaResponse = service.registrarVenta(ventaRequest);
    return new ResponseEntity<>(ventaResponse, HttpStatus.CREATED);
  }

}
