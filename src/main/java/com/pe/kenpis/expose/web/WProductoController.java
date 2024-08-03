package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IProductoService;
import com.pe.kenpis.business.impl.ProductoImpl;
import com.pe.kenpis.model.api.producto.ProductoResponse;
import com.pe.kenpis.model.entity.ProductoEntity;
import com.pe.kenpis.util.funciones.FxComunes;
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
  private IProductoService service;

  @GetMapping("/categorias")
  public ResponseEntity<List<ProductoResponse>> getAllCategories() {
    List<ProductoResponse> categories = service.getAllCategorias();
    return ResponseEntity.ok(categories);
  }

  @GetMapping("/find-all-by-type/{categoria}")
  public ResponseEntity<List<ProductoResponse>> getProductosByCategoria(@PathVariable int categoria) {
    List<ProductoResponse> productos = service.getProductosByCategoriaId(categoria);
    FxComunes.printJson("RESPONSE :: CATEGORIAS " , productos);
    return new ResponseEntity<>(productos, HttpStatus.OK);
  }
}
