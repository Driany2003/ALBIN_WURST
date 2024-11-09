package com.pe.kenpis.business;

import com.pe.kenpis.model.api.empresa.caja.EmpresaCajaRequest;
import com.pe.kenpis.model.api.empresa.caja.EmpresaCajaResponse;

import java.util.List;
import java.util.Map;

public interface IEmpresaCajaService {

  List<EmpresaCajaResponse> findAll();

  EmpresaCajaResponse findById(Integer id);

  EmpresaCajaResponse create(EmpresaCajaRequest request);

  EmpresaCajaResponse update(EmpresaCajaRequest request);

  EmpresaCajaResponse delete(Integer id);

  EmpresaCajaResponse crearCaja(EmpresaCajaRequest request);

  EmpresaCajaResponse cerrarCaja(Integer cajaId);

  List<EmpresaCajaResponse> obtenerCajasPorEmpresa(Integer empresaId);
}
