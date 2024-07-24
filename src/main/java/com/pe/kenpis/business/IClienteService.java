package com.pe.kenpis.business;

import com.pe.kenpis.model.api.cliente.ClienteRequest;
import com.pe.kenpis.model.api.cliente.ClienteResponse;

import java.util.List;

public interface IClienteService {

  List<ClienteResponse> findAll();

  ClienteResponse findById(Integer id);

  ClienteResponse findByCliTelefono(String telefono);

  ClienteResponse create(ClienteRequest request);

  ClienteResponse update(ClienteRequest request);

  ClienteResponse delete(Integer id);

}
