package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IProductoComplementoService;
import com.pe.kenpis.model.api.producto.complementos.*;
import com.pe.kenpis.util.funciones.FxComunes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/kenpis/complemento")
@Slf4j
public class WProductoComplementosController {

  @Autowired
  private IProductoComplementoService service;

  @GetMapping("/find-by-id/{id}")
  public ResponseEntity<ProductoComplementosResponse> findById(@PathVariable Integer id) {
    ProductoComplementosResponse dato = service.findById(id);
    return ResponseEntity.ok(dato);
  }

  @GetMapping("/find-complemento-detalles/{id}")
  public ResponseEntity<Map<String, Object>> findComplementoWithDetalles(@PathVariable Integer id) {
    Map<String, Object> response = new HashMap<>();
    ProductoComplementosResponse complemento = service.findById(id);
    List<ProductoComplementoResponseDTO> detalles = service.findDetallesByIdPadre(id, complemento.getEmpId());

    // respuesta JSON
    response.put("status", "success");
    response.put("complemento", complemento);
    response.put("detalles", detalles);

    return ResponseEntity.ok(response);
  }

  @PostMapping("/create")
  public ResponseEntity<ProductoComplementosResponse> create(@RequestBody ProductoComplementosRequest request) {
    FxComunes.printJson("CREATE COMPLEMENTO HIJO", request);
    ProductoComplementosResponse response = service.create(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PostMapping("/create-complemento")
  public ResponseEntity<ProductoComplementoResponseDTO> create(@RequestBody ProductoComplementoRequestRegistrarDTO request) {
    FxComunes.printJson("CREATE COMPLEMENTO PADRE", request);
    ProductoComplementoResponseDTO response = service.createComplemento(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/find-all")
  public ResponseEntity<List<ProductoComplementoResponseDTO>> findAll( HttpSession session) {
    Integer empresaSession = (Integer) session.getAttribute("empresaSessionID");
    System.out.println("empresa"+ empresaSession);
    List<ProductoComplementoResponseDTO> productos = service.findAll(empresaSession);
    FxComunes.printJson("trae complementos por empresa ", productos);
    return new ResponseEntity<>(productos, HttpStatus.OK);
  }
/*
  //para poder listar los complementos por empresa.
  @GetMapping("/find-all/{empId}")
  public ResponseEntity<List<ProductoComplementoResponseDTO>> obtenerComplementosPorEmpresa(@PathVariable Integer empId) {
    List<ProductoComplementoResponseDTO> complementos = service.obtenerComplementosConSubcomplementosPorEmpresa(empId);
    FxComunes.printJson("listar los complementos ",complementos);
    return new ResponseEntity<>(complementos, HttpStatus.OK);
  }
*/
  @PutMapping("/update")
  public ResponseEntity<ProductoComplementosResponse> update(@RequestBody ProductoComplementoRequestUpdateDTO request) {
    FxComunes.printJson("Lo que actualizo del complemento detalle", request);
    ProductoComplementosResponse response = service.update(request);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PutMapping("/update-padre")
  public ResponseEntity<ProductoComplementosResponse> updatePadre(@RequestBody ProductoComplementoRequestUpdateDTO request) {
    FxComunes.printJson("Lo que actualizo del complemento padre", request);
    ProductoComplementosResponse response = service.updatePadre(request);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/delete-padre/{id}")
  public ResponseEntity<ProductoComplementosResponse> deletePadre(@PathVariable Integer id) {
    ProductoComplementosResponse response = service.deleteComplemento(id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<ProductoComplementosResponse> delete(@PathVariable Integer id) {
    ProductoComplementosResponse response = service.delete(id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
