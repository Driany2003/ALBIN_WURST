package com.pe.kenpis.business;

import com.pe.kenpis.model.api.empresa.EmpresaDTO;
import com.pe.kenpis.model.api.empresa.EmpresaRequest;
import com.pe.kenpis.model.api.empresa.EmpresaResponse;

import java.util.List;
import java.util.Optional;

public interface IEmpresaService {

  List<EmpresaResponse> findAll();

  List<EmpresaDTO> findAllByStatus();

  EmpresaResponse findById(Integer id);

  EmpresaResponse obtenerEmpresaPorUsuario(Integer usuarioId);

  EmpresaResponse create(EmpresaRequest request);

  EmpresaResponse update(EmpresaRequest request);

  EmpresaResponse updatePropietario(EmpresaRequest request);

  EmpresaResponse updateStatus(EmpresaRequest request);

  EmpresaResponse delete(Integer id);

}