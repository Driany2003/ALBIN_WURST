package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IEmpresaMetodoPagoService;
import com.pe.kenpis.model.api.empresa.metodoPago.EmpresaMetodoPagoDTO;
import com.pe.kenpis.model.api.empresa.metodoPago.EmpresaMetodoPagoRequest;
import com.pe.kenpis.model.api.empresa.metodoPago.EmpresaMetodoPagoResponse;
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
@RequestMapping( "/kenpis/metodoPago")
@Slf4j
public class WEmpresaMetodoPagoController {

  @Autowired
  private IEmpresaMetodoPagoService metodoPagoService;

  //listar todos los metodos de pago
  @GetMapping("/find-all")
  public ResponseEntity<List<EmpresaMetodoPagoResponse>> findAll() {
    log.info("Controller :: findAll");
    List<EmpresaMetodoPagoResponse> metodosPago = metodoPagoService.findAll();
    return new ResponseEntity<>(metodosPago, HttpStatus.OK);
  }

  //actualziar metodo de pago
  @PutMapping("/update")
  public ResponseEntity<EmpresaMetodoPagoResponse> updateMetodoPago(@RequestBody EmpresaMetodoPagoDTO request) {
    log.info("Controller :: empresa update");
    EmpresaMetodoPagoResponse metodosPago = metodoPagoService.update(request);
    FxComunes.printJson("trae para actualizar un metodo de pago", metodosPago);
    return new ResponseEntity<>(metodosPago, HttpStatus.OK);
  }

  // Obtener todos los métodos de pago por empresa
  @GetMapping("/empresa/{empId}")
  public ResponseEntity<Map<String, Object>> obtenerMetodosPagoPorEmpresa(@PathVariable Integer empId) {
    List<EmpresaMetodoPagoResponse> metodosPago = metodoPagoService.obtenerMetodosPagoPorEmpresa(empId);
    Map<String, Object> response = new HashMap<>();
    if (metodosPago.isEmpty()) {
      response.put("message", "No se encontraron métodos de pago para la empresa ");
      return new ResponseEntity<>(response, HttpStatus.OK);
    }
    response.put("data", metodosPago);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  // Crear un nuevo método de pago
  @PostMapping("/create")
  public ResponseEntity<?> crearMetodoPago(@RequestBody EmpresaMetodoPagoRequest metodoPago) {
     metodoPagoService.crearMetodosPago(metodoPago);
    return  ResponseEntity.ok("Metodo de pago creado con exito");
  }

  //eliminar la empresa
  @DeleteMapping("/delete/{metPagoTipo}/{empId}")
  public ResponseEntity<EmpresaMetodoPagoResponse> deleteMetodoPago(@PathVariable String metPagoTipo, @PathVariable Integer empId) {
    log.info("Controller :: deleteMetodoPago :: Tipo de Pago: {}, EmpId: {}", metPagoTipo, empId);

    EmpresaMetodoPagoResponse empresaMetodoPagoResponse = metodoPagoService.delete(metPagoTipo, empId);
    return new ResponseEntity<>(empresaMetodoPagoResponse, HttpStatus.OK);
  }


}
