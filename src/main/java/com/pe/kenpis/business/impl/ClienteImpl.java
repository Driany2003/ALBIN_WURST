package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IClienteService;
import com.pe.kenpis.model.api.cliente.ClienteDTO;
import com.pe.kenpis.model.api.cliente.ClienteRequest;
import com.pe.kenpis.model.api.cliente.ClienteResponse;
import com.pe.kenpis.model.api.producto.ProductoResponse;
import com.pe.kenpis.model.api.usuario.UsuarioDTO;
import com.pe.kenpis.model.entity.ClienteEntity;
import com.pe.kenpis.model.entity.ProductoEntity;
import com.pe.kenpis.repository.ClienteRepository;
import com.pe.kenpis.util.funciones.FxComunes;
import com.pe.kenpis.util.variables.Constantes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteImpl implements IClienteService {

  private final ClienteRepository repository;

  public ClienteResponse findByCliTelefono(String celular) {
    return repository.findByCliTelefono(celular).map(this::convertEntityToResponse).orElse(new ClienteResponse());
  }

  @Override
  public List<ClienteDTO> findAll() {
    log.info("Implements :: findAllDto");
    List<Map<String, Object>> results = repository.findActiveClientes();
    log.info(" --------------- {}", results.toString());
    FxComunes.printJson("objeto result", results);
    return results.stream().map(this::convertToClienteDTObyId).collect(Collectors.toList());
  }

  @Override
  public List<ClienteDTO> findAllClientesByEmpresaId(Integer empId) {
    log.info("Implements :: findUsuariosByEmpresaId :: empresaId: {}", empId);
    List<Map<String, Object>> results = repository.findAllClientesByEmpresaId(empId);
    log.info(" --------------- {}", results.toString());
    FxComunes.printJson("objeto result", results);
    return results.stream().map(this::convertToClientesDTO).collect(Collectors.toList());
  }

  private ClienteDTO convertToClientesDTO(Map<String, Object> map) {
    return new ClienteDTO((Integer) map.get("cliId"), (String) map.get("cliTelefono"), (String) map.get("cliNombre"), (String) map.get("cliCorreo"), (Boolean) map.get("cliNotificacion"), (Boolean) map.get("cliIsActive"), (Integer) map.get("empId"), (String) map.get("empNombreComercial"));
  }

  private ClienteDTO convertToClienteDTObyId(Map<String, Object> map) {
    return new ClienteDTO((Integer) map.get("cliId"), (String) map.get("cliTelefono"), (String) map.get("cliNombre"), (String) map.get("cliCorreo"), (Boolean) map.get("cliNotificacion"), (Boolean) map.get("cliIsActive"), (String) map.get("empNombreComercial"));
  }

  public ClienteDTO findById(Integer cliId) {
    log.info("Implements :: findById :: " + cliId);
    Optional<ClienteEntity> results = repository.findById(cliId);
    if (results.isPresent()) {
      return convertClienteEntityToDTO(results.get());
    }
    return new ClienteDTO();
  }

  private ClienteDTO convertClienteEntityToDTO(ClienteEntity clienteEntity) {

    ClienteDTO clienteDTO = new ClienteDTO();
    clienteDTO.setCliId(clienteEntity.getCliId());
    clienteDTO.setCliNombre(clienteEntity.getCliNombre());
    clienteDTO.setCliCorreo(clienteEntity.getCliCorreo());
    clienteDTO.setEmpId(clienteEntity.getEmpId());
    clienteDTO.setCliTelefono(clienteEntity.getCliTelefono());
    clienteDTO.setCliIsActive(clienteEntity.getCliIsActive());
    clienteDTO.setCliNotificacion(clienteEntity.getCliNotificacion());

    return clienteDTO;
  }

  @Override
  public ClienteResponse create(ClienteRequest request) {
    log.debug("Implements :: create :: Inicio");
    request.setCliIsActive(true);
    request.setCliFechaCreacion(new Date());
    FxComunes.printJson("ClienteRequest", request);
    return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
  }

  @Override
  public ClienteDTO update(ClienteDTO request, HttpSession session) {
    ClienteResponse res = repository.findById(request.getCliId()).map(this::convertEntityToResponse).orElse(new ClienteResponse());
    if (res.getCliId() == null) {
      return new ClienteDTO();
    }
    String usuSessionNivel = (String) session.getAttribute("usuSessionNivel");
    if (usuSessionNivel.equalsIgnoreCase(Constantes.NIVELES_USUARIO.PROPIETARIO)) {
      request.setEmpId(res.getEmpId());
      request.setCliFechaCreacion(res.getCliFechaCreacion());
    }
    else if (usuSessionNivel.equalsIgnoreCase(Constantes.NIVELES_USUARIO.ADMINISTRADOR)) {
      request.setCliFechaCreacion(res.getCliFechaCreacion());
    }
    ClienteEntity updatedCliente = convertRequestToEntityToDTO(request);
    updatedCliente = repository.save(updatedCliente);
    return convertEntityToResponseToDTO(updatedCliente);
  }

  @Override
  public ClienteResponse delete(Integer id) {
    log.debug("Implements :: delete :: ID -> {}", id);

    Optional<ClienteEntity> cliente = repository.findById(id);
    if (cliente.isPresent()) {
      repository.deleteById(id);
      ClienteEntity deletedEntity = cliente.get();
      return convertEntityToResponse(deletedEntity);
    }
    return new ClienteResponse();
  }

  private ClienteEntity convertRequestToEntity(ClienteRequest in) {
    ClienteEntity out = new ClienteEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private ClienteEntity convertRequestToEntityToDTO(ClienteDTO in) {
    ClienteEntity out = new ClienteEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private ClienteDTO convertEntityToResponseToDTO(ClienteEntity in) {
    ClienteDTO out = new ClienteDTO();
    BeanUtils.copyProperties(in, out);
    return out;
  }
  private ClienteResponse convertEntityToResponse(ClienteEntity in) {
    ClienteResponse out = new ClienteResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private ClienteRequest convertResponseToRequest(ClienteResponse in) {
    ClienteRequest out = new ClienteRequest();
    BeanUtils.copyProperties(in, out);
    return out;
  }

}
