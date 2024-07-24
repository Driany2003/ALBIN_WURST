package com.pe.kenpis.business;

import com.pe.kenpis.model.api.cliente.ClienteRequest;
import com.pe.kenpis.model.api.cliente.ClienteResponse;

public interface IClienteService {

  ClienteResponse findByCliTelefono(String celular);

  boolean registrarCliente(ClienteRequest clienteRequest);

}
