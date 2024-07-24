package com.pe.kenpis.web;

import com.pe.kenpis.business.IProductoService;
import com.pe.kenpis.model.api.producto.ProductoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/kenpis/producto")
public class WProductoController {

  @Autowired
  IProductoService service;

  @GetMapping("/find-all-by-type/{type}")
  public ResponseEntity<List<ProductoResponse>> findAllByType(@PathVariable String type) {
    List<ProductoResponse> productos = service.findAllByProCategoria(type);
    return ResponseEntity.ok(productos);
  }
}
