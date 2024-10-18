package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IMetodoPagoService;
import com.pe.kenpis.model.api.metodoPago.MetodoPagoRequest;
import com.pe.kenpis.model.api.metodoPago.MetodoPagoResponse;
import com.pe.kenpis.util.funciones.FxComunes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/kenpis/metodoPago")
@Slf4j
public class WMetodoPagoController {

  @Autowired
  private IMetodoPagoService metodoPagoService;

  //listar todos los metodos de pago
  @GetMapping("/find-all")
  public ResponseEntity<List<MetodoPagoResponse>> findAll() {
    log.info("Controller :: findAll");
    List<MetodoPagoResponse> metodosPago = metodoPagoService.findAll();
    return new ResponseEntity<>(metodosPago, HttpStatus.OK);
  }

  //actualziar metodo de pago
  @PutMapping("/update")
  public ResponseEntity<MetodoPagoResponse> updateMetodoPago(@RequestBody MetodoPagoRequest request) {
    log.info("Controller :: empresa update");
    MetodoPagoResponse metodosPago = metodoPagoService.update(request);
    FxComunes.printJson("trae para actualizar un metodo de pago", metodosPago);
    return new ResponseEntity<>(metodosPago, HttpStatus.OK);
  }

  // Obtener todos los métodos de pago por empresa
  @GetMapping("/empresa/{empId}")
  public ResponseEntity<Map<String, Object>> obtenerMetodosPagoPorEmpresa(@PathVariable Integer empId) {
    List<MetodoPagoResponse> metodosPago = metodoPagoService.obtenerMetodosPagoPorEmpresa(empId);
    Map<String, Object> response = new HashMap<>();

    if (metodosPago.isEmpty()) {
      response.put("message", "No se encontraron métodos de pago para la empresa con ID " + empId);
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    response.put("data", metodosPago);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  // Crear un nuevo método de pago
  @PostMapping("/create")
  public ResponseEntity<MetodoPagoResponse> crearMetodoPago(@RequestBody MetodoPagoRequest metodoPago) {
    log.info("Controller :: createMetodoPago" + metodoPago);
    MetodoPagoResponse nuevoMetodoPago = metodoPagoService.crearMetodoPago(metodoPago);
    return new ResponseEntity<>(nuevoMetodoPago, HttpStatus.CREATED);
  }

  //eliminar la empresa
  @DeleteMapping("/delete/{metPagoId}")
  public ResponseEntity<MetodoPagoResponse> deleteMetodoPago(@PathVariable Integer metPagoId) {
    log.info("Controller :: deleteMetodoPago :: {}", metPagoId);
    MetodoPagoResponse metodosPago = metodoPagoService.delete(metPagoId);
    return new ResponseEntity<>(metodosPago, HttpStatus.OK);
  }

}
