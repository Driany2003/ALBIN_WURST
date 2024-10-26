package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IEmpresaMetodoPagoService;
import com.pe.kenpis.model.api.empresa.metodoPago.EmpresaMetodoPagoDTO;
import com.pe.kenpis.model.api.empresa.metodoPago.EmpresaMetodoPagoRequest;
import com.pe.kenpis.model.api.empresa.metodoPago.EmpresaMetodoPagoResponse;
import com.pe.kenpis.model.entity.EmpresaMetodoPagoEntity;
import com.pe.kenpis.repository.EmpresaMetodoPagoRepository;
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
public class EmpresaMetodoPagoImpl implements IEmpresaMetodoPagoService {

  @Autowired
  private EmpresaMetodoPagoRepository empresaMetodoPagoRepository;

  //listar metodos de pago
  @Override
  public List<EmpresaMetodoPagoResponse> findAll() {
    List<EmpresaMetodoPagoEntity> metodos = empresaMetodoPagoRepository.findAll();
    return metodos.stream().map(this::convertEntityToResponse).collect(Collectors.toList());
  }

  //actualizar metodos de pago
  @Override
  public EmpresaMetodoPagoResponse update(EmpresaMetodoPagoDTO request) {
    EmpresaMetodoPagoEntity metodoPago = empresaMetodoPagoRepository.findById(request.getMetPagoId()).orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

    metodoPago.setMetPagoTipo(request.getMetPagoTipo());
    metodoPago.setMetPagoLogo(request.getMetPagoLogo());
    metodoPago.setMetPagoQr(request.getMetPagoQr());
    metodoPago.setMetPagoCuentaNombre(request.getMetPagoCuentaNombre());
    metodoPago.setMetPagoCuentaNumero(request.getMetPagoCuentaNumero());
    EmpresaMetodoPagoEntity updatedEntity = empresaMetodoPagoRepository.save(metodoPago);
    return convertEntityToResponse(updatedEntity);
  }

  // obtener metodos de pago por empresa
  @Override
  public List<EmpresaMetodoPagoResponse> obtenerMetodosPagoPorEmpresa(Integer empresaId) {
    List<EmpresaMetodoPagoEntity> metodosPago = empresaMetodoPagoRepository.findMetodoPagoByEmpId(empresaId);
    return metodosPago.stream().map(this::convertEntityToResponse).collect(Collectors.toList());
  }

  // crear metodo de pago
  @Override
  public void crearMetodosPago(EmpresaMetodoPagoRequest request) {
    for (EmpresaMetodoPagoDTO empresaMetodoPagoDTO : request.getMetodosPago()) {
      EmpresaMetodoPagoEntity empresaMetodoPagoEntity = convertRequestMetodoPagoDToEntity(empresaMetodoPagoDTO);
      empresaMetodoPagoEntity.setEmpId(request.getEmpId());
      empresaMetodoPagoRepository.save(empresaMetodoPagoEntity);
    }
  }

  //eliminar metodo de pago
  @Override
  public EmpresaMetodoPagoResponse delete(String metPagoTipo, Integer empId) {
    EmpresaMetodoPagoEntity metodoPago = empresaMetodoPagoRepository.findByTipoAndEmpId(metPagoTipo, empId)
        .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));

    empresaMetodoPagoRepository.delete(metodoPago);
    return convertEntityToResponse(metodoPago);
  }

  private EmpresaMetodoPagoEntity convertRequestToEntity(EmpresaMetodoPagoRequest in) {
    EmpresaMetodoPagoEntity out = new EmpresaMetodoPagoEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private EmpresaMetodoPagoEntity convertRequestMetodoPagoDToEntity(EmpresaMetodoPagoDTO in) {
    EmpresaMetodoPagoEntity out = new EmpresaMetodoPagoEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private EmpresaMetodoPagoResponse convertEntityToResponse(EmpresaMetodoPagoEntity entity) {
    EmpresaMetodoPagoResponse response = new EmpresaMetodoPagoResponse();
    BeanUtils.copyProperties(entity,response);
    return response;
  }
}
