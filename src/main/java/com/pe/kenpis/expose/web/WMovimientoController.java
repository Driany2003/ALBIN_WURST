package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IVentaEstadoService;
import com.pe.kenpis.model.api.card_movimiento.movimientoRequest;
import com.pe.kenpis.model.entity.VentaEstadoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class WMovimientoController {

  @Autowired
  private IVentaEstadoService ventaEstadoService;

  @PostMapping("/registroMovimiento")
  public ResponseEntity<String> registrarMovimiento(@RequestBody movimientoRequest movimientoRequest) {
    // Crear una nueva entidad para el movimiento
    VentaEstadoEntity ventaEstado = new VentaEstadoEntity();
    ventaEstado.setVentaId(Integer.parseInt(movimientoRequest.getDivId()));
    ventaEstado.setVenEstado(movimientoRequest.getNuevoContainer());


    Date currentDate = new Date();
    switch (movimientoRequest.getNuevoContainer()) {
      case "EN_PROCESO":
        ventaEstado.setVenEstadoFechaEnProceso(currentDate);
        break;
      case "PAGADO":
        ventaEstado.setVenEstadoFechaPagado(currentDate);
        break;
      case "ATENDIDO":
        ventaEstado.setVenEstadoFechaAtendido(currentDate);
        break;
      case "DESCARTADO":
        ventaEstado.setVenEstadoFechaDescartado(currentDate);
        break;
      default:
        break;
    }

    ventaEstadoService.save(ventaEstado);

    return ResponseEntity.ok("Movimiento registrado con éxito");
  }
}
