package com.pe.kenpis.web;

import com.pe.kenpis.business.IClienteService;
import com.pe.kenpis.business.IUsuariosService;
import com.pe.kenpis.model.api.usuario.UsuarioRequest;
import com.pe.kenpis.model.api.usuario.UsuarioResponse;
import com.pe.kenpis.model.api.usuario.cliente.ClienteRequest;
import com.pe.kenpis.model.api.usuario.cliente.ClientesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/sys/nueva-venta")
public class WClienteController {
  @Autowired
  private IClienteService clienteService;


  @GetMapping("/verificar-cliente/{celular}")
  public ResponseEntity<ClientesResponse> verifyUser(@PathVariable String celular) {
    ClientesResponse user = clienteService.findByCliTelefono(celular);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/registrar-cliente")
  public ResponseEntity<ClientesResponse> registerCliente(@RequestBody ClienteRequest clienteRequest) {
    boolean success = clienteService.registrarCliente(clienteRequest);
    if (success) {
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }


}
