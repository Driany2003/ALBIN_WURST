package com.pe.kenpis.web;

import com.pe.kenpis.business.IProductosService;
import com.pe.kenpis.business.IVentasService;
import com.pe.kenpis.model.api.ventas.VentasRequest;
import com.pe.kenpis.model.api.ventas.VentasResponse;
import com.pe.kenpis.model.api.ventas.detalle.DetalleVentaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/sys/nueva-venta")
public class WNuevaVentaController {

  @Autowired
  private IVentasService ventasService;

  @PostMapping("/registrar")
  public ResponseEntity<VentasResponse>registrarVenta(@RequestBody VentasRequest ventasRequest) {
    System.out.println("lo que trae del js al backend " + ventasRequest);
    VentasResponse ventaResponse = ventasService.registrarVenta(ventasRequest);
    return new ResponseEntity<>(ventaResponse, HttpStatus.CREATED);
  }

  @GetMapping("/listar")
  public ResponseEntity<List<DetalleVentaResponse>> obtenerDetallesDeVenta() {
    List<DetalleVentaResponse> detalles = ventasService.obtenerDetallesDeVenta();
    return new ResponseEntity<>(detalles, HttpStatus.OK);
  }
}
