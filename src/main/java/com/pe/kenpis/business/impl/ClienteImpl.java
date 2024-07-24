package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IClienteService;
import com.pe.kenpis.model.api.usuario.UsuarioRequest;
import com.pe.kenpis.model.api.usuario.UsuarioResponse;
import com.pe.kenpis.model.api.cliente.ClienteRequest;
import com.pe.kenpis.model.api.cliente.ClienteResponse;
import com.pe.kenpis.model.entity.ClienteEntity;
import com.pe.kenpis.model.entity.UsuarioEntity;
import com.pe.kenpis.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteImpl implements IClienteService {

  private final ClienteRepository repository;

  public ClienteResponse findByCliTelefono(String celular) {
    ClienteEntity cliente = repository.findByCliTelefono(celular);
    if (cliente != null) {
      ClienteResponse response = new ClienteResponse();
      response.setCliNombre(cliente.getCliNombre());
      return response;
    }
    return null;
  }

  public boolean registrarCliente(ClienteRequest clienteRequest) {
    ClienteEntity cliente = new ClienteEntity();
    cliente.setCliTelefono(clienteRequest.getCliTelefono());
    cliente.setCliNombre(clienteRequest.getCliNombre());
    ClienteEntity guardarCliente = repository.save(cliente);
    return guardarCliente != null;
  }

  private UsuarioResponse convertUsuarioEntityToResponse(UsuarioEntity in) {
    UsuarioResponse out = new UsuarioResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private UsuarioEntity convertUsuarioRequestToEntity(UsuarioRequest in) {
    UsuarioEntity out = new UsuarioEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }

}
