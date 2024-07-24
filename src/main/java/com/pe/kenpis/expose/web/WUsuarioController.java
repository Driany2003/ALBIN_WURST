package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IUsuarioService;
import com.pe.kenpis.model.api.usuario.UsuarioRequest;
import com.pe.kenpis.model.api.usuario.UsuarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kenpis/usuario")
public class WUsuarioController {

  @Autowired
  private IUsuarioService service;

  @GetMapping("/find-by-phone/{phone}")
  public ResponseEntity<UsuarioResponse> findByPhone(@PathVariable String phone) {
    UsuarioResponse user = service.findByPhone(phone);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/create")
  public ResponseEntity<UsuarioResponse> create(@RequestBody UsuarioRequest request) {
    UsuarioResponse response = service.create(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

}
