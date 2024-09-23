package com.pe.kenpis.business;

import com.pe.kenpis.model.api.caja.CajaRequest;
import com.pe.kenpis.model.api.caja.CajaResponse;

import java.util.List;

public interface ICajaService {

  List<CajaResponse> findAll();

  CajaResponse findById(Integer id);

  CajaResponse create(CajaRequest request);

  CajaResponse update(CajaRequest request);

  CajaResponse delete(Integer id);

  CajaResponse abrirCaja(CajaRequest cajaRequest);

  void cerrarCaja(CajaRequest cajaRequest);

  boolean verificarCajaAbierta(Integer empId);

  CajaResponse obtenerCajaAbierta(Integer empId);

}
