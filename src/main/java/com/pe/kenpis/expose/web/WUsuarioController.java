package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IEmpresaService;
import com.pe.kenpis.business.IUsuarioService;
import com.pe.kenpis.model.api.empresa.EmpresaDTO;
import com.pe.kenpis.model.api.producto.ProductoResponse;
import com.pe.kenpis.model.api.usuario.*;
import com.pe.kenpis.util.funciones.FxComunes;
import com.pe.kenpis.util.variables.Constantes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kenpis/usuario")
@Slf4j
public class WUsuarioController {

  @Autowired
  private IUsuarioService service;

  @Autowired
  private IEmpresaService serviceEmpresa;

  @GetMapping("/find-by-phone/{phone}")
  public ResponseEntity<UsuarioResponse> findByPhone(@PathVariable String phone) {
    UsuarioResponse user = service.findByPhone(phone);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/create")
  public ResponseEntity<UsuarioDTO> create(@RequestBody UsuarioRequest request) {
    FxComunes.printJson("QUE TRAE DE REGISTRAR ", request);
    UsuarioDTO response = service.create(request);
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

    String usuSessionNivel = (String) session.getAttribute("usuSessionNivel");
    log.info(usuSessionNivel);

    if (usuSessionNivel.equalsIgnoreCase(Constantes.NIVELES_USUARIO.ADMINISTRADOR)) {
      List<UsuarioDTO> listaUsuarios = service.findAllUsers();
      response.put("data", listaUsuarios);
      session.setAttribute("listaUsuarios", listaUsuarios);
      FxComunes.printJson("QUE LLEGA DE ADMINISTRADOR", listaUsuarios);
      //LISTAR
      List<EmpresaDTO> listaEmpresa = serviceEmpresa.findAllByStatus();
      response.put("empresasList", listaEmpresa);
      FxComunes.printJson("Trae empresa admin ", listaEmpresa);

    } else if (usuSessionNivel.equalsIgnoreCase(Constantes.NIVELES_USUARIO.PROPIETARIO)) {
      List<UsuarioDTO> listaUsuarios = service.findUsuariosByEmpresaId(usuarioDTO.getUsuId());
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

  @GetMapping("/find-by-id/{usuId}")
  public ResponseEntity<?> findById(@PathVariable Integer usuId, HttpSession session) {
    UsuarioDTO usuario = service.findById(usuId);
    String usuSessionNivel = (String) session.getAttribute("usuSessionNivel");
    if (usuario != null) {
      Map<String, Object> response = new HashMap<>();
      response.put("usuario", usuario);
      FxComunes.printJson("LO QUE TRAE DE USUARIO ", usuario);
      if (usuSessionNivel.equalsIgnoreCase(Constantes.NIVELES_USUARIO.ADMINISTRADOR)) {
        List<EmpresaDTO> empresasList = serviceEmpresa.findAllByStatus();
        response.put("empresasList", empresasList);
        FxComunes.printJson("LO QUE TRAE EMPRESA ", empresasList);
      }
      return ResponseEntity.ok(response);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
    }
  }

  @PutMapping("/update")
  public ResponseEntity<UsuarioDTO> update(@RequestBody UsuarioDTORequest request, HttpSession session) {
    log.info("Controller :: update");
    FxComunes.printJson("lo que trae al editar ", request);
    UsuarioDTO response = service.update(request, session);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<UsuarioResponse> delete(@PathVariable Integer id) {
    log.info("Controller :: delete :: {}", id);
    UsuarioResponse response = service.delete(id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
