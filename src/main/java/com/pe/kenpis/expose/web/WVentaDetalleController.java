package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IVentaService;
import com.pe.kenpis.business.impl.VentaVentaDetalleImpl;
import com.pe.kenpis.model.api.venta.detalle.VentaDetalleResponse;
import com.pe.kenpis.model.api.venta.estado.VentaEstadoRequest;
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
@RequestMapping(value = "/kenpis/venta/detalle")
public class WVentaDetalleController {

  @Autowired
  private IVentaService service;

  @Autowired
  private VentaVentaDetalleImpl ventaDetalle;

  @GetMapping("/find-all")
  public ResponseEntity<List<VentaDetalleResponse>> findAll() {
    List<VentaDetalleResponse> detalles = service.obtenerDetallesDeVenta();
    return new ResponseEntity<>(detalles, HttpStatus.OK);
  }


  @PostMapping("/actualizarEstado")
  public ResponseEntity<?> updateStatus(@RequestBody VentaEstadoRequest request) {
    boolean actualizar = ventaDetalle.updateEstado(request.getId(), request.getNuevoEstado());
    if (actualizar) {
      return ResponseEntity.ok().body("Estado actualizado correctamente");
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar estado");
    }
  }

}
