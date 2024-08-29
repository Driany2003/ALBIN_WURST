package com.pe.kenpis.business;

import com.pe.kenpis.model.api.empresa.EmpresaRequest;
import com.pe.kenpis.model.api.empresa.EmpresaResponse;
import com.pe.kenpis.model.api.producto.ProductoRequest;
import com.pe.kenpis.model.api.producto.ProductoResponse;

import java.util.List;

public interface IEmpresaService {

  List<EmpresaResponse> findAll();

  EmpresaResponse findById(Integer id);

  EmpresaResponse obtenerEmpresaPorUsuario(Integer usuarioId);

  EmpresaResponse create(EmpresaRequest request);

  EmpresaResponse update(EmpresaRequest request);

  EmpresaResponse updatePropietario(EmpresaRequest request);

  EmpresaResponse updateStatus(EmpresaRequest request);

  EmpresaResponse delete(Integer id);

}