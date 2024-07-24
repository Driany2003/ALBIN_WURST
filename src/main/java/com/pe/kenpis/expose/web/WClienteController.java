package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IClienteService;
import com.pe.kenpis.model.api.cliente.ClienteRequest;
import com.pe.kenpis.model.api.cliente.ClienteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/kenpis/cliente/")
public class WClienteController {
  @Autowired
  private IClienteService clienteService;


  @GetMapping("/find-by-phone/{phone}")
  public ResponseEntity<ClienteResponse> findByPhone(@PathVariable String phone) {
    ClienteResponse user = clienteService.findByCliTelefono(phone);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/create")
  public ResponseEntity<ClienteResponse> create(@RequestBody ClienteRequest request) {
    boolean success = clienteService.registrarCliente(request);
    if (success) {
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }


}
