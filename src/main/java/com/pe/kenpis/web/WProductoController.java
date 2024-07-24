package com.pe.kenpis.web;

import com.pe.kenpis.business.IProductoService;
import com.pe.kenpis.business.impl.ProductoImpl;
import com.pe.kenpis.model.api.producto.ProductoResponse;
import com.pe.kenpis.model.entity.ProductoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
  ProductoImpl service;

  @GetMapping("/find-all-by-type/{type}")
  public ResponseEntity<List<ProductoResponse>> getProductosByCategoria(@PathVariable String categoria) {
    List<ProductoResponse> productos = service.findAllByProCategoria(categoria);
    return new ResponseEntity<>(productos, HttpStatus.OK);
  }
}