package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IClienteService;
import com.pe.kenpis.model.api.usuario.UsuarioRequest;
import com.pe.kenpis.model.api.usuario.UsuarioResponse;
import com.pe.kenpis.model.api.usuario.cliente.ClienteRequest;
import com.pe.kenpis.model.api.usuario.cliente.ClientesResponse;
import com.pe.kenpis.model.entity.ClienteEntity;
import com.pe.kenpis.model.entity.UsuariosEntity;
import com.pe.kenpis.repository.ClientesRepository;
import com.pe.kenpis.repository.UsuariosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientesImpl implements IClienteService {

  private final ClientesRepository repository;
  public ClientesResponse findByCliTelefono(String celular) {
    ClienteEntity cliente = repository.findByCliTelefono(celular);
    if (cliente != null) {
      ClientesResponse response = new ClientesResponse();
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




  private UsuarioResponse convertUsuarioEntityToResponse(UsuariosEntity in) {
    UsuarioResponse out = new UsuarioResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private UsuariosEntity convertUsuarioRequestToEntity(UsuarioRequest in) {
    UsuariosEntity out = new UsuariosEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }


}
