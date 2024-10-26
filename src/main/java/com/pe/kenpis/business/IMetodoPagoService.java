package com.pe.kenpis.business;

import com.pe.kenpis.model.api.empresa.metodoPago.MetodoPagoDTO;
import com.pe.kenpis.model.api.empresa.metodoPago.MetodoPagoRequest;
import com.pe.kenpis.model.api.empresa.metodoPago.MetodoPagoResponse;

import java.util.List;

public interface IMetodoPagoService {

  List<MetodoPagoResponse> findAll();

  MetodoPagoResponse update(MetodoPagoDTO request);

  List<MetodoPagoResponse> obtenerMetodosPagoPorEmpresa(Integer empId);

  void crearMetodosPago(MetodoPagoRequest request);

  MetodoPagoResponse delete(String metPagoTipo,Integer metPagoId);

}
