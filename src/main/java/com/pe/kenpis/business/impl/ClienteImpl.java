package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IClienteService;
import com.pe.kenpis.model.api.cliente.ClienteRequest;
import com.pe.kenpis.model.api.cliente.ClienteResponse;
import com.pe.kenpis.model.entity.ClienteEntity;
import com.pe.kenpis.repository.ClienteRepository;
import com.pe.kenpis.util.funciones.FxComunes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
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
  public List<ClienteResponse> findAll() {
    log.info("Implements :: findAll");
    return repository.findAll().stream().map(this::convertEntityToResponse).collect(Collectors.toList());
  }

  @Override
  public ClienteResponse findById(Integer id) {
    log.info("Implements :: findById :: " + id);
    return repository.findById(id).map(this::convertEntityToResponse).orElse(new ClienteResponse());
  }

  @Override
  public ClienteResponse create(ClienteRequest request) {
    log.debug("Implements :: create :: Inicio");
    request.setCliIsActive(true);
    FxComunes.printJson(request);
    return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
  }

  @Override
  public ClienteResponse update(ClienteRequest request) {//request.getUsuId()
    ClienteResponse res = repository.findById(request.getCliId()).map(this::convertEntityToResponse).orElse(new ClienteResponse());
    //request.setUsuClave(passwordEncoder.encode(request.getUsuClave()));
    if (res.getCliId() == null) {
      return new ClienteResponse();
    } else {
      return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
    }
  }

  @Override
  public ClienteResponse delete(Integer id) {
    log.debug("Implements :: delete :: ID -> {}", id);
    ClienteResponse res = repository.findById(id).map(this::convertEntityToResponse).orElse(new ClienteResponse());
    res.setCliIsActive(false);
    Optional<ClienteEntity> ent = repository.findById(res.getCliId());
    if (ent.isPresent()) {
      return convertEntityToResponse(repository.save(convertRequestToEntity(convertResponseToRequest(res))));
    } else {
      return new ClienteResponse();
    }
  }

  private ClienteEntity convertRequestToEntity(ClienteRequest in) {
    ClienteEntity out = new ClienteEntity();
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
