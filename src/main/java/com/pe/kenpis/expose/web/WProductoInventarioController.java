package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IProductoInventarioService;
import com.pe.kenpis.model.api.producto.inventario.ProductoProductoRequest;
import com.pe.kenpis.model.api.producto.inventario.ProductoComplementoResponse;
import com.pe.kenpis.util.funciones.FxComunes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/kenpis/inventario")
@Slf4j
public class WProductoInventarioController {

  @Autowired
  private IProductoInventarioService service;

  @GetMapping("/find-by-id/{id}")
  public ResponseEntity<ProductoComplementoResponse> findById(@PathVariable Integer id) {
    ProductoComplementoResponse dato = service.findById(id);
    return ResponseEntity.ok(dato);
  }

  @PostMapping("/create")
  public ResponseEntity<ProductoComplementoResponse> create(@RequestBody ProductoProductoRequest request) {
    ProductoComplementoResponse response = service.create(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/find-all")
  public ResponseEntity<List<ProductoComplementoResponse>> findAll() {
    List<ProductoComplementoResponse> productos = service.findAll();
    return new ResponseEntity<>(productos, HttpStatus.OK);
  }
  @PutMapping("/update")
  public ResponseEntity<ProductoComplementoResponse> update(@RequestBody ProductoProductoRequest request) {
    FxComunes.printJson("fff", request);
    ProductoComplementoResponse response = service.update(request);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<ProductoComplementoResponse> delete(@PathVariable Integer id) {
    ProductoComplementoResponse response = service.delete(id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
