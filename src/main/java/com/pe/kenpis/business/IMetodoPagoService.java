package com.pe.kenpis.business;

import com.pe.kenpis.model.api.metodoPago.MetodoPagoDTO;
import com.pe.kenpis.model.api.metodoPago.MetodoPagoRequest;
import com.pe.kenpis.model.api.metodoPago.MetodoPagoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IMetodoPagoService {

  List<MetodoPagoResponse> findAll();

  MetodoPagoResponse update(MetodoPagoDTO request);

  List<MetodoPagoResponse> obtenerMetodosPagoPorEmpresa(Integer empId);

  void crearMetodosPago(MetodoPagoRequest request);

  MetodoPagoResponse delete(String metPagoTipo,Integer metPagoId);

}
