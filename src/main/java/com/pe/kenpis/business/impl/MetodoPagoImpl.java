package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IMetodoPagoService;
import com.pe.kenpis.model.api.metodoPago.MetodoPagoRequest;
import com.pe.kenpis.model.api.metodoPago.MetodoPagoResponse;
import com.pe.kenpis.model.entity.MetodoPagoEntity;
import com.pe.kenpis.repository.MetodoPagoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetodoPagoImpl implements IMetodoPagoService {

  @Autowired
  private MetodoPagoRepository metodoPagoRepository;

  //listar metodos de pago
  @Override
  public List<MetodoPagoResponse> findAll() {
    List<MetodoPagoEntity> metodos = metodoPagoRepository.findAll();
    return metodos.stream().map(this::convertEntityToResponse).collect(Collectors.toList());
  }

  //actualizar metodos de pago
  @Override
  public MetodoPagoResponse update(MetodoPagoRequest request) {
    MetodoPagoEntity metodoPago = metodoPagoRepository.findById(request.getMetPagoId()).orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

    metodoPago.setMetPagoTipo(request.getMetPagoTipo());
    metodoPago.setMetPagoLogo(request.getMetPagoLogo());
    metodoPago.setMetPagoQr(request.getMetPagoQr());
    metodoPago.setMetPagoCuentaNombre(request.getMetPagoCuentaNombre());
    metodoPago.setMetPagoCuentaNumero(request.getMetPagoCuentaNumero());
    MetodoPagoEntity updatedEntity = metodoPagoRepository.save(metodoPago);
    return convertEntityToResponse(updatedEntity);
  }

  // obtener metodos de pago por empresa
  @Override
  public List<MetodoPagoResponse> obtenerMetodosPagoPorEmpresa(Integer empresaId) {
    List<MetodoPagoEntity> metodosPago = metodoPagoRepository.findMetodoPagoByEmpId(empresaId);
    return metodosPago.stream().map(this::convertEntityToResponse).collect(Collectors.toList());
  }

  // crear metodo de pago
  @Override
  public MetodoPagoResponse crearMetodoPago(MetodoPagoRequest metodoPagoRequest) {
    return convertEntityToResponse(metodoPagoRepository.save(convertRequestToEntity(metodoPagoRequest)));
  }

  //eliminar metodo de pago
  @Override
  public MetodoPagoResponse delete(Integer id) {
    MetodoPagoEntity metodoPago = metodoPagoRepository.findById(id).orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
    metodoPagoRepository.delete(metodoPago);
    return convertEntityToResponse(metodoPago);
  }



  private MetodoPagoEntity convertRequestToEntity(MetodoPagoRequest in) {
    MetodoPagoEntity out = new MetodoPagoEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private MetodoPagoResponse convertEntityToResponse(MetodoPagoEntity in) {
    MetodoPagoResponse out = new MetodoPagoResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }
}
