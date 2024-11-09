package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IEmpresaCajaService;
import com.pe.kenpis.business.IEmpresaService;
import com.pe.kenpis.business.IUsuarioService;
import com.pe.kenpis.model.api.empresa.EmpresaDTO;
import com.pe.kenpis.model.api.empresa.EmpresaResponse;
import com.pe.kenpis.model.api.empresa.caja.EmpresaCajaDTO;
import com.pe.kenpis.model.api.empresa.caja.EmpresaCajaRequest;
import com.pe.kenpis.model.api.empresa.caja.EmpresaCajaResponse;
import com.pe.kenpis.model.api.usuario.ResponsablesDTO;
import com.pe.kenpis.util.funciones.FxComunes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/kenpis/caja")
@Slf4j
public class WEmpresaCajaController {
  @Autowired
  private IEmpresaService service;

  @Autowired
  private IUsuarioService usuarioService;

  @Autowired
  private IEmpresaCajaService cajaService;

  @GetMapping("/listar-disponibles/{empId}")
  public ResponseEntity<Map<String, Object>> findSucursalesAndUsuariosByEmpresa(@PathVariable Integer empId) {
    log.info("Controller :: findSucursalesAndUsuariosByEmpresa :: empresaId={}", empId);
    List<EmpresaDTO> sucursales = service.obtenerSucursalesByEmpresa(empId);
    List<ResponsablesDTO> responsable = usuarioService.obtenerUsuariosPorEmpresa(empId);
    Map<String, Object> response = new HashMap<>();
    response.put("status", "success");
    response.put("sucursales", sucursales);
    response.put("responsable", responsable);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  //para registrar la apertura de una caja
  @PostMapping("/abrir")
  public ResponseEntity<EmpresaCajaResponse> abrirCaja(@RequestBody EmpresaCajaRequest request) {
    log.info("Controller :: abrirCaja :: EmpresaCajaRequest={}", request);

    if (request.getEmpPadreId() == null || request.getCajaUsuarioApertura() == null || request.getCajaEstado() == null) {
      throw new IllegalArgumentException("ID de empresa, usuario de apertura y estado son requeridos");
    }

    EmpresaCajaResponse nuevaCaja = cajaService.crearCaja(request);
    return new ResponseEntity<>(nuevaCaja, HttpStatus.CREATED);
  }

  @PostMapping("/cerrar")
  public ResponseEntity<EmpresaCajaResponse> cerrarCaja(@RequestBody Integer cajaId) {
    log.info("Controller :: cerrarCaja :: CajaId={}", cajaId);
      EmpresaCajaResponse cajaCerrada = cajaService.cerrarCaja(cajaId);
    return new ResponseEntity<>(cajaCerrada, HttpStatus.CREATED);
  }

  @GetMapping("/listar/{empresaId}")
  public ResponseEntity<List<EmpresaCajaResponse>> listarCajasPorEmpresa(@PathVariable Integer empresaId) {
    List<EmpresaCajaResponse> cajas = cajaService.obtenerCajasPorEmpresa(empresaId);
    FxComunes.printJson("que trae cajas por empresa ", cajas);
    return ResponseEntity.ok(cajas);
  }
}
