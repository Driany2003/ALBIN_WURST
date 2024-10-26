package com.pe.kenpis.business;

import com.pe.kenpis.model.api.empresa.caja.EmpresaCajaRequest;
import com.pe.kenpis.model.api.empresa.caja.EmpresaCajaResponse;

import java.util.List;

public interface IEmpresaCajaService {

  List<EmpresaCajaResponse> findAll();

  EmpresaCajaResponse findById(Integer id);

  EmpresaCajaResponse create(EmpresaCajaRequest request);

  EmpresaCajaResponse update(EmpresaCajaRequest request);

  EmpresaCajaResponse delete(Integer id);

 EmpresaCajaResponse abrirCaja(EmpresaCajaRequest empresaCajaRequest);


}
