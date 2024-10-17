package com.pe.kenpis.business;

import com.pe.kenpis.model.api.cliente.ClienteDTO;
import com.pe.kenpis.model.api.cliente.ClienteRequest;
import com.pe.kenpis.model.api.cliente.ClienteResponse;
import com.pe.kenpis.model.entity.ClienteEntity;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface IClienteService {

  List<ClienteDTO> findAll();

  List<ClienteDTO> findAllClientesByEmpresaId(Integer usuId);

  ClienteDTO findById(Integer id);

  ClienteResponse findByCliTelefono(String telefono);

  ClienteResponse create(ClienteRequest request);

  ClienteDTO update(ClienteDTO request, HttpSession session);

  ClienteResponse delete(Integer id);

}
