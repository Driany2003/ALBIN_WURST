package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IEmpresaService;
import com.pe.kenpis.business.IUsuarioService;
import com.pe.kenpis.model.api.empresa.EmpresaDTO;
import com.pe.kenpis.model.api.empresa.EmpresaRequest;
import com.pe.kenpis.model.api.empresa.EmpresaResponse;
import com.pe.kenpis.model.api.empresa.EmpresaResponseDTO;
import com.pe.kenpis.model.api.empresa.sucursal.SucursalDTOResponse;
import com.pe.kenpis.model.api.empresa.sucursal.SucursalDTOrequest;
import com.pe.kenpis.model.api.empresa.sucursal.SucursalRequest;
import com.pe.kenpis.model.api.empresa.sucursal.SucursalResponse;
import com.pe.kenpis.model.api.usuario.ResponsablesDTO;
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

  //listar empresas combo box
  @GetMapping("/find-all/empresas")
  public ResponseEntity<List<EmpresaResponseDTO>> findAllEmpresaComboBox() {
    log.info("Controller :: findAllEmpresaComboBox");
    List<EmpresaResponseDTO> empresas = service.findAllByStatus();
    return new ResponseEntity<>(empresas, HttpStatus.OK);
  }

  @GetMapping("/find-by-id/{id}")
  public ResponseEntity<EmpresaResponse> findById(@PathVariable Integer id) {
    log.info("Controller :: findById :: {}", id);
    EmpresaResponse empresa = service.findById(id);
    return ResponseEntity.ok(empresa);
  }

  // crear empresa
  @PostMapping("/create")
  public ResponseEntity<EmpresaResponse> createEmpresa(@RequestBody EmpresaRequest request) {
    log.info("Controller :: create");
    EmpresaResponse empresa = service.create(request);
    return new ResponseEntity<>(empresa, HttpStatus.CREATED);
  }

  // crear sucursal
  @PostMapping("/sucursales-create")
  public ResponseEntity<EmpresaResponse> createSucursal(@RequestBody SucursalRequest request) {
    log.info("Controller :: createSucursal");
    EmpresaResponse sucursal = service.createSucursal(request);
    return new ResponseEntity<>(sucursal, HttpStatus.CREATED);
  }

  //actualizar empresa
  @PutMapping("/update")
  public ResponseEntity<EmpresaResponse> updateEmpresa(@RequestBody EmpresaRequest request) {
    log.info("Controller :: empresa update");
    EmpresaResponse empresa = service.update(request);
    return new ResponseEntity<>(empresa, HttpStatus.OK);
  }

  //actualizar sucursal
  @PutMapping("/sucursal-update")
  public ResponseEntity<SucursalDTOResponse> updateSucursal(@RequestBody SucursalDTOrequest request) {
    FxComunes.printJson("lo que trae  para editar sucursal", request);
    log.info("Controller :: sucursal Update");
    SucursalDTOResponse sucursal = service.updateSucursal(request);
    return new ResponseEntity<>(sucursal, HttpStatus.OK);
  }

  @PutMapping("/propietario/sucursal-update")
  public ResponseEntity<SucursalDTOResponse> updateSucursalPropietario(@RequestBody SucursalDTOrequest request) {
    FxComunes.printJson("lo que trae para editar sucursal Propietario", request);
    log.info("Controller :: sucursal Update Propietario");
    SucursalDTOResponse sucursal = service.updateSucursal(request);
    return new ResponseEntity<>(sucursal, HttpStatus.OK);
  }

  //actualizar estado de empresa
  @PutMapping("/update/status")
  public ResponseEntity<EmpresaResponse> updateStatusEmpresa(@RequestBody EmpresaRequest request) {
    FxComunes.printJson("update", request);
    log.info("Controller :: update Status");
    EmpresaResponse empresa = service.updateStatus(request);

    return new ResponseEntity<>(empresa, HttpStatus.OK);
  }

  //actualizar la vista de propietario
  @PutMapping("/update-propietario")
  public ResponseEntity<EmpresaResponse> updatePropietario(@RequestBody EmpresaRequest request) {
    FxComunes.printJson("que trae para editar", request);
    log.info("Controller :: update  propietario");
    EmpresaResponse empresa = service.updatePropietario(request);
    return new ResponseEntity<>(empresa, HttpStatus.OK);
  }

  //eliminar la sucursal
  @DeleteMapping("/sucursal-delete/{id}")
  public ResponseEntity<EmpresaResponse> deleteSucursal(@PathVariable Integer id) {
    log.info("Controller :: sucursal delete :: {}", id);
    EmpresaResponse empresa = service.delete(id);
    return new ResponseEntity<>(empresa, HttpStatus.OK);
  }

  //eliminar la empresa
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<EmpresaResponse> deleteEmpresa(@PathVariable Integer id) {
    log.info("Controller :: delete :: {}", id);
    EmpresaResponse empresa = service.delete(id);
    return new ResponseEntity<>(empresa, HttpStatus.OK);
  }

  //carga empresas list o  empresa id de acuerdo al nivel de usuSessionNivel
  @PostMapping("/cargar-empresas")
  public ResponseEntity<Map<String, Object>> loadEmpresasByRole(@RequestBody UsuarioResponse usuarioResponse, HttpSession session) {
    log.info("Controller :: loadEmpresasByRole :: {}", usuarioResponse.getUsuId());
    Map<String, Object> response = new HashMap<>();
    String usuSessionNivel = (String) session.getAttribute("usuSessionNivel");

    if (usuSessionNivel.equalsIgnoreCase("ADMINISTRADOR")) {
      List<EmpresaDTO> listaEmpresa = service.findAllActiveEmpresaById();
      response.put("status", "success");
      response.put("data", listaEmpresa);
      session.setAttribute("empresasAdministrador", listaEmpresa);
    } else if (usuSessionNivel.equalsIgnoreCase("PROPIETARIO")) {
      EmpresaResponse empresaResponse = service.obtenerEmpresaPorUsuario(usuarioResponse.getUsuId());
      List<EmpresaDTO> listaSucursales = service.obtenerSucursalesPorEmpresa(empresaResponse.getEmpId());
      response.put("status", "success");
      response.put("data", empresaResponse);
      response.put("listaSucursales", listaSucursales);

      FxComunes.printJson("lista de sucursales de la empresa", listaSucursales);
      session.setAttribute("propietarioEmpresa", empresaResponse);
    }
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  // buscar por empId las sucursales
  @GetMapping("/find-sucursales/{empId}")
  public ResponseEntity<Map<String, Object>> findSucursalesByEmpresa(@PathVariable Integer empId) {
    log.info("Controller :: findSucursalesByEmpresa :: empresaId={}", empId);
    List<EmpresaDTO> sucursales = service.obtenerSucursalesPorEmpresa(empId);
    FxComunes.printJson("trae sucursales para vista de administrador", sucursales);
    Map<String, Object> response = new HashMap<>();
    response.put("status", "success");
    response.put("data", sucursales);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}

