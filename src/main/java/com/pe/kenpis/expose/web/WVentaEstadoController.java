package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IVentaEstadoService;
import com.pe.kenpis.model.api.venta.estado.VentaEstadoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/kenpis/venta/estado")
@Slf4j
public class WVentaEstadoController {

  @Autowired
  private IVentaEstadoService ventaEstadoService;

  @PostMapping("/update")
  public ResponseEntity<String> update(@RequestBody VentaEstadoRequest request) {
    try {
      log.info("Datos recibidos del frontend: " + request);
      ventaEstadoService.update(request);
      return ResponseEntity.ok().body("Movimiento registrado con éxito");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Error al registrar el movimiento: " + e.getMessage());
    }
  }
  @GetMapping("/count")
  public Map<String, Object> getCountPedidosEstado() {
    return ventaEstadoService.getCountPedidosXEstado();
  }

}
