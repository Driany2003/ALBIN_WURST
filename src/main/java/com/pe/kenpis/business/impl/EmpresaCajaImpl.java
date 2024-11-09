package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IEmpresaCajaService;
import com.pe.kenpis.model.api.empresa.EmpresaDTO;
import com.pe.kenpis.model.api.empresa.caja.EmpresaCajaDTO;
import com.pe.kenpis.model.api.empresa.caja.EmpresaCajaRequest;
import com.pe.kenpis.model.api.empresa.caja.EmpresaCajaResponse;
import com.pe.kenpis.model.api.usuario.ResponsablesDTO;
import com.pe.kenpis.model.entity.EmpresaCajaEntity;
import com.pe.kenpis.model.entity.EmpresaEntity;
import com.pe.kenpis.repository.EmpresaCajaRepository;
import com.pe.kenpis.repository.EmpresaRepository;
import com.pe.kenpis.util.funciones.DateUtil;
import com.pe.kenpis.util.funciones.FxComunes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmpresaCajaImpl implements IEmpresaCajaService {

  private final EmpresaCajaRepository repository;
  private final EmpresaRepository empresaRepository;

  @Override
  public List<EmpresaCajaResponse> findAll() {
    log.info("Implements :: findAll");
    return repository.findAll().stream().map(this::convertEntityToResponse).collect(Collectors.toList());
  }

  @Override
  public EmpresaCajaResponse findById(Integer id) {
    log.info("Implements :: findById :: " + id);
    return repository.findById(id).map(this::convertEntityToResponse).orElse(new EmpresaCajaResponse());
  }

  @Override
  public EmpresaCajaResponse create(EmpresaCajaRequest request) {
    log.debug("Implements :: createNewCaja :: Inicio");
    FxComunes.printJson("CajaRequest", request);
    return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
  }

  @Override
  public EmpresaCajaResponse update(EmpresaCajaRequest request) {
    EmpresaCajaResponse res = repository.findById(request.getCajaId()).map(this::convertEntityToResponse).orElse(new EmpresaCajaResponse());
    if (res.getCajaId() == null) {
      return new EmpresaCajaResponse();
    } else {
      return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
    }
  }

  @Override
  public EmpresaCajaResponse delete(Integer id) {
    log.debug("Implements :: delete :: ID -> {}", id);
    EmpresaCajaResponse res = repository.findById(id).map(this::convertEntityToResponse).orElse(new EmpresaCajaResponse());
    Optional<EmpresaCajaEntity> ent = repository.findById(res.getCajaId());
    if (ent.isPresent()) {
      return convertEntityToResponse(repository.save(convertRequestToEntity(convertResponseToRequest(res))));
    } else {
      return new EmpresaCajaResponse();
    }
  }

  @Override
  public EmpresaCajaResponse crearCaja(EmpresaCajaRequest request) {
    log.debug("Implements :: crearCaja :: Inicio");

    // Verificar si la empresa padre existe
    EmpresaEntity empresaPadre = empresaRepository.findById(request.getEmpPadreId())
        .orElseThrow(() -> new IllegalArgumentException("No se encontró la empresa padre con ID: " + request.getEmpPadreId()));

    EmpresaCajaEntity nuevaCaja = new EmpresaCajaEntity();

    // Asignar valores de la empresa padre si son necesarios
    nuevaCaja.setEmpPadreId(request.getEmpPadreId());

    nuevaCaja.setCajaMontoInicial(request.getCajaMontoInicial());
    nuevaCaja.setCajaUsuarioApertura(request.getCajaUsuarioApertura());
    nuevaCaja.setCajaAsignada(request.getCajaAsignada());

    // Asignar valores predeterminados
    nuevaCaja.setCajaFechaApertura(new Date());
    nuevaCaja.setCajaEstado(true);
    // Guardar la nueva caja y convertir a response
    return convertEntityToResponse(repository.save(nuevaCaja));
  }
  @Override
  public EmpresaCajaResponse cerrarCaja(Integer cajaId) {
   Optional<EmpresaCajaEntity> cajaEntityOpt = repository.findById(cajaId);

    if (!cajaEntityOpt.isPresent()) {
      throw new IllegalArgumentException("Caja no encontrada");
    }

    EmpresaCajaEntity cajaEntity = cajaEntityOpt.get();
    cajaEntity.setCajaEstado(false);
    cajaEntity.setCajaFechaCierre(new Date());
    repository.save(cajaEntity);

    return convertEntityToResponse(cajaEntity);
  }

  @Override
  public List<EmpresaCajaResponse> obtenerCajasPorEmpresa(Integer empresaId) {
    log.info("Implements :: obtenerCajasPorEmpresa" + empresaId);

    List<EmpresaCajaEntity> cajas = repository.findCajasByEmpresaId(empresaId);

    // Convertir cada CajaEntity a EmpresaCajaResponse
    return cajas.stream().map(caja -> {
      EmpresaCajaResponse response = new EmpresaCajaResponse();
      response.setCajaId(caja.getCajaId());
      response.setEmpPadreId(caja.getEmpPadreId());
      response.setCajaFechaApertura(caja.getCajaFechaApertura());
      response.setCajaFechaCierre(caja.getCajaFechaCierre());
      response.setCajaMontoInicial(caja.getCajaMontoInicial());
      response.setCajaMontoFinal(caja.getCajaMontoFinal());
      response.setCajaUsuarioApertura(caja.getCajaUsuarioApertura());
      response.setCajaAsignada(caja.getCajaAsignada());
      response.setCajaEstado(caja.getCajaEstado());
      return response;
    }).collect(Collectors.toList());
  }

  private EmpresaCajaEntity convertRequestToEntity(EmpresaCajaRequest in) {
    EmpresaCajaEntity out = new EmpresaCajaEntity();
    BeanUtils.copyProperties(in, out);
    return out;

  }

  private EmpresaCajaResponse convertEntityToResponse(EmpresaCajaEntity in) {
    EmpresaCajaResponse out = new EmpresaCajaResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private EmpresaCajaRequest convertResponseToRequest(EmpresaCajaResponse in) {
    EmpresaCajaRequest out = new EmpresaCajaRequest();
    BeanUtils.copyProperties(in, out);
    return out;
  }

}
