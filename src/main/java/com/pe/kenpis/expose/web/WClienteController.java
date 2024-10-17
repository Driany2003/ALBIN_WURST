package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IClienteService;
import com.pe.kenpis.business.IEmpresaService;
import com.pe.kenpis.model.api.cliente.ClienteDTO;
import com.pe.kenpis.model.api.cliente.ClienteRequest;
import com.pe.kenpis.model.api.cliente.ClienteResponse;
import com.pe.kenpis.model.api.empresa.EmpresaResponseDTO;
import com.pe.kenpis.util.funciones.FxComunes;
import com.pe.kenpis.util.variables.Constantes;
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
@RequestMapping(value = "/kenpis/cliente/")
@Slf4j
public class WClienteController {
  
  @Autowired
  private IClienteService service;

  @Autowired
  private IEmpresaService serviceEmpresa;

  @GetMapping("/find-all")
  public ResponseEntity<List<ClienteDTO>> findAll() {
    List<ClienteDTO> response = service.findAll();
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/find-by-id/{cliId}")
  public ResponseEntity<?> findById(@PathVariable Integer cliId, HttpSession session) {
    ClienteDTO cliente = service.findById(cliId);
    String usuSessionNivel = (String) session.getAttribute("usuSessionNivel");
    if (cliente != null) {
      Map<String, Object> response = new HashMap<>();
      response.put("cliente", cliente);
      FxComunes.printJson("POR CLIENTE ID ", cliente);
      if (usuSessionNivel.equalsIgnoreCase(Constantes.NIVELES_USUARIO.ADMINISTRADOR)) {
        List<EmpresaResponseDTO> empresasList = serviceEmpresa.findAllByStatus();
        response.put("empresasList", empresasList);
        FxComunes.printJson("POR EMPRESA ID ", empresasList);
      }
      return ResponseEntity.ok(response);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
    }
  }

  @GetMapping("/find-by-telefono/{telefono}")
  public ResponseEntity<ClienteResponse> findByCliTelefono(@PathVariable String telefono) {
    ClienteResponse cliente = service.findByCliTelefono(telefono);
    return ResponseEntity.ok(cliente);
  }

  @PostMapping("/create")
  public ResponseEntity<ClienteResponse> create(@RequestBody ClienteRequest request) {
    ClienteResponse response = service.create(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PostMapping("/cargar-clientes")
  public ResponseEntity<Map<String, Object>> loadEmpresasByRole(@RequestBody ClienteRequest clienteRequest, HttpSession session) {
    log.info("Controller :: loadEmpresasByRole");
    Map<String, Object> response = new HashMap<>();

    String usuSessionNivel = (String) session.getAttribute("usuSessionNivel");
    if (usuSessionNivel.equalsIgnoreCase(Constantes.NIVELES_USUARIO.ADMINISTRADOR)) {
      List<ClienteDTO> listaClientes = service.findAll();
      response.put("data", listaClientes);
      session.setAttribute("listaClientes", listaClientes);
      FxComunes.printJson("QUE LLEGA DE ADMINISTRADOR", listaClientes);
      //LISTAR
      List<EmpresaResponseDTO> listaEmpresa = serviceEmpresa.findAllByStatus();
      response.put("empresasList", listaEmpresa);
      FxComunes.printJson("Trae empresa admin ", listaEmpresa);

    } else if (usuSessionNivel.equalsIgnoreCase(Constantes.NIVELES_USUARIO.PROPIETARIO)) {
      List<ClienteDTO> listaClientes = service.findAllClientesByEmpresaId(clienteRequest.getEmpId());
      response.put("data", listaClientes);
      session.setAttribute("listaClientes", listaClientes);
      FxComunes.printJson("QUE LLEGA DE PROPIETARIO", listaClientes);
    } else {
      response.put("status", "error");
      response.put("message", "Rol de usuario no reconocido.");
      return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    return new ResponseEntity<>(response, HttpStatus.OK);
  }


  @PutMapping("/update")
  public ResponseEntity<ClienteDTO> update(@RequestBody ClienteDTO request, HttpSession session) {
    FxComunes.printJson("lo que trae al editar cliente", request);
    ClienteDTO response = service.update(request, session);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<ClienteResponse> delete(@PathVariable Integer id) {
    ClienteResponse response = service.delete(id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}