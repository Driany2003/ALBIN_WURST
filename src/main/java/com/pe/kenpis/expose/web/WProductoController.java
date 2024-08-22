package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IProductoService;
import com.pe.kenpis.model.api.producto.ProductoRequest;
import com.pe.kenpis.model.api.producto.ProductoResponse;
import com.pe.kenpis.util.funciones.FxComunes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/kenpis/producto")
public class WProductoController {

  @Autowired
  private IProductoService service;

  @GetMapping("/categorias")
  public ResponseEntity<List<ProductoResponse>> getAllCategories() {
    List<ProductoResponse> categories = service.getAllCategorias();
    return ResponseEntity.ok(categories);
  }

  @GetMapping("/find-all-by-type/{categoria}")
  public ResponseEntity<List<ProductoResponse>> getProductosByCategoria(@PathVariable int categoria) {
    List<ProductoResponse> productos = service.getProductosByCategoriaId(categoria);
    return new ResponseEntity<>(productos, HttpStatus.OK);
  }
  @GetMapping("/find-by-id/{id}")
  public ResponseEntity<ProductoResponse> findById(@PathVariable Integer id) {
    ProductoResponse dato = service.findById(id);
    return ResponseEntity.ok(dato);
  }

  @GetMapping("/find-all")
  public ResponseEntity<List<ProductoResponse>> findAll() {
    List<ProductoResponse> productos = service.findAll();
    return new ResponseEntity<>(productos, HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<ProductoResponse> create(@RequestBody ProductoRequest request) {
    ProductoResponse response = service.create(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping ("/update")
  public ResponseEntity<ProductoResponse> update(@RequestBody ProductoRequest request) {
    FxComunes.printJson( "fff" , request);
    ProductoResponse response = service.update(request);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PutMapping("/update/status")
  public ResponseEntity<ProductoResponse> updateStatus(@RequestBody ProductoRequest request) {
    ProductoResponse response = service.updateStatus(request);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<ProductoResponse> delete(@PathVariable Integer id) {
    ProductoResponse response = service.delete(id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
