package com.pe.kenpis.business;
import com.pe.kenpis.model.api.usuario.cliente.ClienteRequest;
import com.pe.kenpis.model.api.usuario.cliente.ClientesResponse;

public interface IClienteService {

  ClientesResponse findByCliTelefono(String celular);

  boolean registrarCliente(ClienteRequest clienteRequest);
}
