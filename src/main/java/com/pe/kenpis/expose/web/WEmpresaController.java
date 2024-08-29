package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IEmpresaService;
import com.pe.kenpis.business.IUsuarioService;
import com.pe.kenpis.model.api.empresa.EmpresaRequest;
import com.pe.kenpis.model.api.empresa.EmpresaResponse;
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
@RequestMapping("/kenpis/empresas")
@Slf4j
public class WEmpresaController {

  @Autowired
  private IEmpresaService service;

  @Autowired
  private IUsuarioService usuarioService;

  @GetMapping("/find-all")
  public ResponseEntity<List<EmpresaResponse>> findAll() {
    log.info("Controller :: findAll");
    List<EmpresaResponse> empresas = service.findAll();
    return new ResponseEntity<>(empresas, HttpStatus.OK);
  }

  @GetMapping("/find-by-id/{id}")
  public ResponseEntity<EmpresaResponse> findById(@PathVariable Integer id) {
    log.info("Controller :: findById :: {}", id);
    EmpresaResponse empresa = service.findById(id);
    return ResponseEntity.ok(empresa);
  }

  @PostMapping("/create")
  public ResponseEntity<EmpresaResponse> create(@RequestBody EmpresaRequest request) {
    FxComunes.printJson("que trae" , request);
    log.info("Controller :: create");
    EmpresaResponse empresa = service.create(request);

    return new ResponseEntity<>(empresa, HttpStatus.CREATED);
  }

  @PutMapping("/update")
  public ResponseEntity<EmpresaResponse> update(@RequestBody EmpresaRequest request) {
    log.info("Controller :: update");
    EmpresaResponse empresa = service.update(request);
    return new ResponseEntity<>(empresa, HttpStatus.OK);
  }


    @PutMapping("/update/status")
    public ResponseEntity<EmpresaResponse> updateStatus(@RequestBody EmpresaRequest request) {
    FxComunes.printJson("que trae",request);
      log.info("Controller :: updateStatus");
      EmpresaResponse empresa = service.updateStatus(request);
      return new ResponseEntity<>(empresa, HttpStatus.OK);
    }

  @PutMapping("/update-propietario")
  public ResponseEntity<EmpresaResponse> updatePropietario(@RequestBody EmpresaRequest request) {
    FxComunes.printJson("que trae",request);
    log.info("Controller :: updateStatus");
    EmpresaResponse empresa = service.updateStatus(request);
    return new ResponseEntity<>(empresa, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<EmpresaResponse> delete(@PathVariable Integer id) {
    log.info("Controller :: delete :: {}", id);
    EmpresaResponse empresa = service.delete(id);
    return new ResponseEntity<>(empresa, HttpStatus.OK);
  }

  @PostMapping("/cargar-empresas")
  public ResponseEntity<Map<String, Object>> loadEmpresasByRole(@RequestBody UsuarioResponse usuarioResponse, HttpSession session) {
    log.info("Controller :: loadEmpresasByRole :: {}", usuarioResponse.getUsuId());
    Map<String, Object> response = new HashMap<>();
    UsuarioAuthorityResponse usuarioAuthorityResponse = usuarioService.findUsuarioAuthorityByUsuId(usuarioResponse.getUsuId());

    if (usuarioAuthorityResponse.getAuthRoles().contains("ADMINISTRADOR")) {
      List<EmpresaResponse> listaEmpresa = service.findAll();
      response.put("status", "success");
      response.put("data", listaEmpresa);
      session.setAttribute("listaEmpresa", listaEmpresa);
    } else if (usuarioAuthorityResponse.getAuthRoles().contains("PROPIETARIO")) {
      EmpresaResponse empresaResponse = service.obtenerEmpresaPorUsuario(usuarioResponse.getUsuId());
      response.put("status", "success");
      response.put("data", empresaResponse);
      session.setAttribute("empresaSession", empresaResponse);
    }
    return new ResponseEntity<>(response,HttpStatus.OK);
  }
}

