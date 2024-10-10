package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IEmpresaService;
import com.pe.kenpis.model.api.empresa.EmpresaDTO;
import com.pe.kenpis.model.api.empresa.EmpresaRequest;
import com.pe.kenpis.model.api.empresa.EmpresaResponse;
import com.pe.kenpis.model.api.empresa.sucursal.SucursalDTOResponse;
import com.pe.kenpis.model.api.empresa.sucursal.SucursalDTOrequest;
import com.pe.kenpis.model.api.empresa.sucursal.SucursalRequest;
import com.pe.kenpis.model.api.empresa.sucursal.SucursalResponse;
import com.pe.kenpis.model.api.usuario.UsuarioResponse;
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

  @GetMapping("/find-all")
  public ResponseEntity<List<EmpresaDTO>> findAll() {
    log.info("Controller :: findAll");
    List<EmpresaDTO> empresas = service.findAllActiveEmpresaById();
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
    FxComunes.printJson("create Empresa", request);
    log.info("Controller :: create");
    EmpresaResponse empresa = service.create(request);

    return new ResponseEntity<>(empresa, HttpStatus.CREATED);
  }

  @PostMapping("/sucursales-create")
  public ResponseEntity<EmpresaResponse> create(@RequestBody SucursalRequest request) {
    FxComunes.printJson("create Sucursal", request);
    log.info("Controller :: createSucursal");
    EmpresaResponse sucursal = service.createSucursal(request);
    return new ResponseEntity<>(sucursal, HttpStatus.CREATED);
  }

  //actualizar empresa
  @PutMapping("/update")
  public ResponseEntity<EmpresaResponse> update(@RequestBody EmpresaRequest request) {
    log.info("Controller :: empresa update");
    EmpresaResponse empresa = service.update(request);
    return new ResponseEntity<>(empresa, HttpStatus.OK);
  }

  //actualizar sucursal
  @PutMapping("/sucursal-update")
  public ResponseEntity<SucursalDTOResponse> updateSucursal(@RequestBody SucursalDTOrequest request) {
    FxComunes.printJson("lo que trae JS para editar sucursal", request);
    log.info("Controller :: sucursal update");
    SucursalDTOResponse sucursal = service.updateSucursal(request);
    return new ResponseEntity<>(sucursal, HttpStatus.OK);
  }

  @PutMapping("/update/status")
  public ResponseEntity<EmpresaResponse> updateStatus(@RequestBody EmpresaRequest request) {
    FxComunes.printJson("update", request);
    log.info("Controller :: updateStatus");
    EmpresaResponse empresa = service.updateStatus(request);
    return new ResponseEntity<>(empresa, HttpStatus.OK);
  }

  @PutMapping("/update-propietario")
  public ResponseEntity<EmpresaResponse> updatePropietario(@RequestBody EmpresaRequest request) {
    FxComunes.printJson("que trae", request);
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
    String usuSessionNivel = (String) session.getAttribute("usuSessionNivel");

    if (usuSessionNivel.equalsIgnoreCase("ADMINISTRADOR")) {
      List<EmpresaDTO> listaEmpresa = service.findAllActiveEmpresaById();
      FxComunes.printJson("que trae de emppresa", listaEmpresa);
      response.put("status", "success");
      response.put("data", listaEmpresa);
      session.setAttribute("empresasAdministrador", listaEmpresa);
    } else if (usuSessionNivel.equalsIgnoreCase("PROPIETARIO")) {
      EmpresaResponse empresaResponse = service.obtenerEmpresaPorUsuario(usuarioResponse.getUsuId());
      response.put("status", "success");
      response.put("data", empresaResponse);
      session.setAttribute("propietarioEmpresa", empresaResponse);
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/find-sucursales/{empId}")
  public ResponseEntity<Map<String, Object>> findSucursalesByEmpresa(@PathVariable Integer empId) {
    log.info("Controller :: findSucursalesByEmpresa :: empresaId={}", empId);
    List<EmpresaDTO> sucursales = service.findSucursalByEmpresa(empId);
    Map<String, Object> response = new HashMap<>();
    response.put("status", "success");
    response.put("data", sucursales);
    FxComunes.printJson("trae sucursales", sucursales);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}

