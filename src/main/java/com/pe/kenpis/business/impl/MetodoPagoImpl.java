package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IMetodoPagoService;
import com.pe.kenpis.model.api.metodoPago.MetodoPagoDTO;
import com.pe.kenpis.model.api.metodoPago.MetodoPagoRequest;
import com.pe.kenpis.model.api.metodoPago.MetodoPagoResponse;
import com.pe.kenpis.model.entity.MetodoPagoEntity;
import com.pe.kenpis.repository.MetodoPagoRepository;
import com.pe.kenpis.util.funciones.FxComunes;
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
  public MetodoPagoResponse update(MetodoPagoDTO request) {
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
  public void crearMetodosPago(MetodoPagoRequest request) {
    for (MetodoPagoDTO metodoPagoDTO : request.getMetodosPago()) {
      MetodoPagoEntity metodoPagoEntity = convertRequestMetodoPagoDToEntity(metodoPagoDTO);
      metodoPagoEntity.setEmpId(request.getEmpId());
      metodoPagoRepository.save(metodoPagoEntity);
    }
  }

  //eliminar metodo de pago
  @Override
  public MetodoPagoResponse delete(String metPagoTipo, Integer empId) {
    MetodoPagoEntity metodoPago = metodoPagoRepository.findByTipoAndEmpId(metPagoTipo, empId)
        .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

    metodoPagoRepository.delete(metodoPago);
    return convertEntityToResponse(metodoPago);
  }

  private MetodoPagoEntity convertRequestToEntity(MetodoPagoRequest in) {
    MetodoPagoEntity out = new MetodoPagoEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private MetodoPagoEntity convertRequestMetodoPagoDToEntity(MetodoPagoDTO in) {
    MetodoPagoEntity out = new MetodoPagoEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private MetodoPagoResponse convertEntityToResponse(MetodoPagoEntity entity) {
    MetodoPagoResponse response = new MetodoPagoResponse();
    BeanUtils.copyProperties(entity,response);
    return response;
  }
}
