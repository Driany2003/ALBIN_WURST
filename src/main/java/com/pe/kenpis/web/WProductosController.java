package com.pe.kenpis.web;

import com.pe.kenpis.business.IProductosService;
import com.pe.kenpis.model.api.productos.ProductosResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/api/productos")
public class WProductosController {

  @Autowired
  IProductosService productosService;

  @GetMapping("/tipo/{tipo}")
  public ResponseEntity<List<ProductosResponse>> obtenerProductosPorTipo(@PathVariable String tipo) {
    List<ProductosResponse> productos = productosService.findAllByTipo(tipo);
    return ResponseEntity.ok(productos);
  }

}
