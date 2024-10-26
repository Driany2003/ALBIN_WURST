package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IEmpresaCajaService;
import com.pe.kenpis.model.api.empresa.caja.EmpresaCajaRequest;
import com.pe.kenpis.model.api.empresa.caja.EmpresaCajaResponse;
import com.pe.kenpis.model.entity.EmpresaCajaEntity;
import com.pe.kenpis.model.entity.EmpresaEntity;
import com.pe.kenpis.repository.EmpresaCajaRepository;
import com.pe.kenpis.repository.EmpresaRepository;
import com.pe.kenpis.util.funciones.FxComunes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
  public EmpresaCajaResponse abrirCaja(EmpresaCajaRequest empresaCajaRequest) {
    Optional<EmpresaEntity> empresa = Optional.ofNullable(empresaRepository.findById(empresaCajaRequest.getEmpPadreId()).orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada.")));
    if (empresa.isPresent()) {
      empresaCajaRequest.setCajaEstado(true);
      empresaCajaRequest.setCajaFechaApertura(LocalDateTime.now());
    } else {
      return new EmpresaCajaResponse();
    }
    return convertEntityToResponse(repository.save(convertRequestToEntity(empresaCajaRequest)));
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
