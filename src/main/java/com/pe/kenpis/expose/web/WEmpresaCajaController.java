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
import com.pe.kenpis.model.entity.EmpresaCajaEntity;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/kenpis/caja")
@Slf4j
public class WEmpresaCajaController {
  @Autowired
  private IEmpresaService service;

  @Autowired
  private IEmpresaCajaService cajaService;

  //lista combo para registrar una caja
  @GetMapping("/listar-disponibles/{empId}")
  public ResponseEntity<Map<String, Object>> findSucursalesAndUsuariosByEmpresa(@PathVariable Integer empId) {
    log.info("Controller :: findSucursalesAndUsuariosByEmpresa :: empresaId={}", empId);
    List<EmpresaDTO> sucursales = service.obtenerSucursalesByEmpresa(empId);
    Map<String, Object> response = new HashMap<>();
    response.put("status", "success");
    response.put("sucursales", sucursales);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/listar-cajas-activas/{empresaId}")
  public ResponseEntity<Map<String, Object>> listarCajasActivasPorEmpresa(@PathVariable Integer empresaId) {
    List<EmpresaCajaResponse> cajasActivas = cajaService.obtenerCajasActivasPorEmpresa(empresaId);

    Map<String, Object> response = new HashMap<>();
    response.put("status", "success");
    response.put("cajas", cajasActivas);

    return ResponseEntity.ok(response);
  }

  //para registrar la apertura de una caja
  @PostMapping("/abrir")
  public ResponseEntity<EmpresaCajaResponse> abrirCaja(@RequestBody EmpresaCajaRequest request, HttpSession session) {
    EmpresaCajaResponse cajaResponse = cajaService.abrirCajaSiNoExiste(request);
    session.setAttribute("cajaId", cajaResponse.getCajaId());
    session.setAttribute("sucursalId", request.getSucursalId());
    return ResponseEntity.ok(cajaResponse);
  }

  @PostMapping("/cerrar")
  public ResponseEntity<EmpresaCajaResponse> cerrarCaja(@RequestBody Integer cajaId, HttpSession session) {
    Integer usuarioId = (Integer) session.getAttribute("usuSessionId");
    log.info("el id del usuario" + usuarioId);
    EmpresaCajaResponse cajaCerrada = cajaService.cerrarCaja(cajaId, usuarioId);
    session.removeAttribute("cajaId");
    session.removeAttribute("sucursalId");
    return new ResponseEntity<>(cajaCerrada, HttpStatus.OK);
  }

  @GetMapping("/listar/{empresaId}")
  public ResponseEntity<List<EmpresaCajaResponse>> listarCajasPorEmpresa(@PathVariable Integer empresaId) {
    List<EmpresaCajaResponse> cajas = cajaService.obtenerCajasPorEmpresa(empresaId);
    FxComunes.printJson("que trae cajas por empresa ", cajas);
    return ResponseEntity.ok(cajas);
  }

  @PostMapping("/seleccionar")
  public ResponseEntity<Void> seleccionarCaja(@RequestParam Integer cajaId, @RequestParam String sucursalNombre, HttpSession session) {
    session.setAttribute("cajaSeleccionada", cajaId);
    session.setAttribute("sucursalNombre", sucursalNombre);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/obtener-caja-seleccionada")
  public ResponseEntity<Map<String, Object>> obtenerCajaSeleccionada(HttpSession session) {
    Integer cajaSeleccionada = (Integer) session.getAttribute("cajaSeleccionada");
    String sucursalNombre = (String) session.getAttribute("sucursalNombre");

    if (cajaSeleccionada != null && sucursalNombre == null) {
      sucursalNombre = cajaService.obtenerNombreSucursalPorCajaId(cajaSeleccionada);
      session.setAttribute("sucursalNombre", sucursalNombre);
    }

    Map<String, Object> response = new HashMap<>();
    response.put("cajaId", cajaSeleccionada);
    response.put("sucursalNombre", sucursalNombre);
    return ResponseEntity.ok(response);
  }

}
