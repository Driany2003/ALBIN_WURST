package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IClienteService;
import com.pe.kenpis.model.api.cliente.ClienteRequest;
import com.pe.kenpis.model.api.cliente.ClienteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/kenpis/cliente/")
public class WClienteController {
  
  @Autowired
  private IClienteService service;

  @GetMapping("/find-all")
  public ResponseEntity<List<ClienteResponse>> findAll() {
    List<ClienteResponse> response = service.findAll();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/find-by-id/{id}")
  public ResponseEntity<ClienteResponse> findById(@PathVariable Integer id) {
    ClienteResponse response = service.findById(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/find-by-telefono/{telefono}")
  public ResponseEntity<ClienteResponse> findByCliTelefono(@PathVariable String telefono) {
    ClienteResponse user = service.findByCliTelefono(telefono);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/create")
  public ResponseEntity<ClienteResponse> create(@RequestBody ClienteRequest request) {
    ClienteResponse response = service.create(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PostMapping("/update")
  public ResponseEntity<ClienteResponse> update(@RequestBody ClienteRequest request) {
    ClienteResponse response = service.update(request);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/delete/{id}")
  public ResponseEntity<ClienteResponse> delete(@PathVariable Integer id) {
    ClienteResponse response = service.delete(id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}