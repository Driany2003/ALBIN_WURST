package com.pe.kenpis.web;

import com.pe.kenpis.business.IUsuariosService;
import com.pe.kenpis.model.api.usuario.UsuarioRequest;
import com.pe.kenpis.model.api.usuario.UsuarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class WUsuarioController {

  @Autowired
  private IUsuariosService usuariosService;

  @GetMapping("/verificar/{celular}")
  public ResponseEntity<UsuarioResponse> verifyUser(@PathVariable String celular) {
    UsuarioResponse user = usuariosService.findByUsuTelefono(celular);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/registrar")
  public ResponseEntity<UsuarioResponse> registerUser(@RequestBody UsuarioRequest userRequest) {
    boolean success = usuariosService.registrarUsuario(userRequest);
    if (success) {
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

}
