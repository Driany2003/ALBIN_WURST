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
  private IUsuarioService usuariosService;

  @GetMapping("/find-by-phone/{phone}")
  public ResponseEntity<UsuarioResponse> findByPhone(@PathVariable String phone) {
    UsuarioResponse user = usuariosService.findByPhone(phone);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/create")
  public ResponseEntity<UsuarioResponse> create(@RequestBody UsuarioRequest request) {
    boolean success = usuariosService.create(request);
    if (success) {
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

}
