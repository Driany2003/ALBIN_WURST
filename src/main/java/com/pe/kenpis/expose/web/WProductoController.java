package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IProductoComplementoService;
import com.pe.kenpis.business.IProductoService;
import com.pe.kenpis.model.api.producto.ProductoListDTO;
import com.pe.kenpis.model.api.producto.ProductoRequest;
import com.pe.kenpis.model.api.producto.ProductoResponse;
import com.pe.kenpis.model.api.producto.complementos.ProductoComplementoResponseDTO;
import com.pe.kenpis.util.funciones.FxComunes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/kenpis/producto")
@Slf4j
public class WProductoController {

  @Autowired
  private IProductoService service;
  @Autowired
  private IProductoComplementoService productoComplementoService;

  @GetMapping("/categorias")
  public ResponseEntity<Map<String, Object>> getAllCategories(@RequestParam("empId") Integer empId) {
    Map<String, Object> response = new HashMap<>();
    List<ProductoListDTO> categorias = service.getAllCategorias(empId);
    FxComunes.printJson("listar las categorias por empresa ",categorias);
    List<ProductoComplementoResponseDTO> complementos = productoComplementoService.obtenerComplementosConSubcomplementosPorEmpresa(empId);
    FxComunes.printJson("listar los complementos ",complementos);

    response.put("status", "success");
    response.put("categorias", categorias);
    response.put("complementos", complementos);
    return  ResponseEntity.ok(response);
  }

  @GetMapping("/nuevaVenta-categorias")
  public ResponseEntity<List<ProductoListDTO>> getCategoriasbyEmpresa(@RequestParam("empId") Integer empId) {
    List<ProductoListDTO> categorias = service.getCategoriasbyEmpresa(empId);
    return new ResponseEntity<>(categorias, HttpStatus.OK);
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

  @GetMapping("/find-all-is-active")
  public ResponseEntity<List<ProductoListDTO>> findActiveProductosWithActiveEmpresa() {
    List<ProductoListDTO> productos = service.findActiveProductosWithActiveEmpresa();
    return new ResponseEntity<>(productos, HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<ProductoResponse> create(@RequestBody ProductoRequest request) {
    FxComunes.printJson("TRAE", request);
    ProductoResponse response = service.create(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<ProductoResponse> update(@RequestBody ProductoRequest request) {
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
