package com.pe.kenpis.business;

import com.pe.kenpis.model.api.empresa.EmpresaRequest;
import com.pe.kenpis.model.api.empresa.EmpresaResponse;

import java.util.List;

public interface IEmpresaService {

  List<EmpresaResponse> findAll();

  EmpresaResponse findById(Integer id);

  EmpresaResponse create(EmpresaRequest request);

  EmpresaResponse update(EmpresaRequest request);

  EmpresaResponse delete(Integer id);

}