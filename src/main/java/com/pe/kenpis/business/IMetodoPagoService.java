package com.pe.kenpis.business;

import com.pe.kenpis.model.api.metodoPago.MetodoPagoRequest;
import com.pe.kenpis.model.api.metodoPago.MetodoPagoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IMetodoPagoService {

  List<MetodoPagoResponse> findAll();

  MetodoPagoResponse update(MetodoPagoRequest request);

  List<MetodoPagoResponse> obtenerMetodosPagoPorEmpresa(Integer empId);

  MetodoPagoResponse crearMetodoPago(MetodoPagoRequest metodoPago);

  MetodoPagoResponse delete(Integer metPagoId);

}
