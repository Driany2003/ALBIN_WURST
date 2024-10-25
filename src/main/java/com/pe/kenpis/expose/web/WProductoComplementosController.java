package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IProductoInventarioService;
import com.pe.kenpis.model.api.producto.inventario.ProductoInventarioRequest;
import com.pe.kenpis.model.api.producto.inventario.ProductoInventarioResponse;
import com.pe.kenpis.util.funciones.FxComunes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/kenpis/complementos")
@Slf4j
public class WProductoComplementosController {

  @Autowired
  private IProductoInventarioService service;

  @GetMapping("/find-by-id/{id}")
  public ResponseEntity<ProductoInventarioResponse> findById(@PathVariable Integer id) {
    ProductoInventarioResponse dato = service.findById(id);
    return ResponseEntity.ok(dato);
  }

  @PostMapping("/create")
  public ResponseEntity<ProductoInventarioResponse> create(@RequestBody ProductoInventarioRequest request) {
    ProductoInventarioResponse response = service.create(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/find-all")
  public ResponseEntity<List<ProductoInventarioResponse>> findAll() {
    List<ProductoInventarioResponse> productos = service.findAll();
    return new ResponseEntity<>(productos, HttpStatus.OK);
  }
  @PutMapping("/update")
  public ResponseEntity<ProductoInventarioResponse> update(@RequestBody ProductoInventarioRequest request) {
    FxComunes.printJson("fff", request);
    ProductoInventarioResponse response = service.update(request);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<ProductoInventarioResponse> delete(@PathVariable Integer id) {
    ProductoInventarioResponse response = service.delete(id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
