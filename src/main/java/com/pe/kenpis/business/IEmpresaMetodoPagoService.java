package com.pe.kenpis.business;

import com.pe.kenpis.model.api.empresa.metodoPago.EmpresaMetodoPagoDTO;
import com.pe.kenpis.model.api.empresa.metodoPago.EmpresaMetodoPagoRequest;
import com.pe.kenpis.model.api.empresa.metodoPago.EmpresaMetodoPagoResponse;

import java.util.List;

public interface IEmpresaMetodoPagoService {

  List<EmpresaMetodoPagoResponse> findAll();

  EmpresaMetodoPagoResponse update(EmpresaMetodoPagoDTO request);

  List<EmpresaMetodoPagoResponse> obtenerMetodosPagoPorEmpresa(Integer empId);

  void crearMetodosPago(EmpresaMetodoPagoRequest request);

  EmpresaMetodoPagoResponse delete(String metPagoTipo, Integer metPagoId);

}
