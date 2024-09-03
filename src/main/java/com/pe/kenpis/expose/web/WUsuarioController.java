package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IUsuarioService;
import com.pe.kenpis.model.api.usuario.UsuarioDTO;
import com.pe.kenpis.model.api.usuario.UsuarioRequest;
import com.pe.kenpis.model.api.usuario.UsuarioResponse;
import com.pe.kenpis.model.api.usuario.authority.UsuarioAuthorityResponse;
import com.pe.kenpis.util.funciones.FxComunes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/kenpis/usuario")
@Slf4j
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

  @GetMapping("/find-all")
  public ResponseEntity<List<UsuarioResponse>> findAll() {
    log.info("Controller :: findAll");
    List<UsuarioResponse> response = service.findAll();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/cargar-usuarios")
  public ResponseEntity<Map<String, Object>> loadEmpresasByRole(@RequestBody UsuarioDTO usuarioDTO, HttpSession session) {
    log.info("Controller :: loadEmpresasByRole :: {}", usuarioDTO.getUsuId());
    Map<String, Object> response = new HashMap<>();
    UsuarioAuthorityResponse usuarioAuthorityResponse = service.findUsuarioAuthorityByUsuId(usuarioDTO.getUsuId());
    if (usuarioAuthorityResponse.getAuthRoles().contains("ADMINISTRADOR")) {
      List<UsuarioDTO> listaUsuarios = service.findAllDto();
      response.put("status", "success");
      response.put("data", listaUsuarios);
      session.setAttribute("listaUsuarios", listaUsuarios);
      FxComunes.printJson("QUE LLEGA DE ADMINISTRADOR", listaUsuarios);
    } else if (usuarioAuthorityResponse.getAuthRoles().contains("PROPIETARIO")) {
      List<UsuarioDTO> listaUsuarios = service.findUsuariosByEmpresaId(usuarioDTO.getUsuId());
      response.put("status", "success");
      response.put("data", listaUsuarios);
      session.setAttribute("listaUsuarios", listaUsuarios);
      FxComunes.printJson("QUE LLEGA DE PROPIETARIO", listaUsuarios);
    } else {
      response.put("status", "error");
      response.put("message", "Rol de usuario no reconocido.");
      return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/find-by-id/{id}")
  public ResponseEntity<UsuarioResponse> findById(@PathVariable Integer id) {
    log.info("Controller :: findById :: {}", id);
    UsuarioResponse response = service.findById(id);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/update")
  public ResponseEntity<UsuarioResponse> update(@RequestBody UsuarioRequest request) {
    log.info("Controller :: update");
    UsuarioResponse response = service.update(request);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<UsuarioResponse> delete(@PathVariable Integer id) {
    log.info("Controller :: delete :: {}", id);
    UsuarioResponse response = service.delete(id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
