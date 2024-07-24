package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IEmpresaService;
import com.pe.kenpis.model.api.empresa.EmpresaRequest;
import com.pe.kenpis.model.api.empresa.EmpresaResponse;
import com.pe.kenpis.model.entity.EmpresaEntity;
import com.pe.kenpis.repository.EmpresaRepository;
import com.pe.kenpis.util.funciones.FxComunes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmpresaImpl implements IEmpresaService {

  private final EmpresaRepository repository;

  @Override
  public List<EmpresaResponse> findAll() {
    log.info("Implements :: findAll");
    return repository.findAll().stream().map(this::convertEntityToResponse).collect(Collectors.toList());
  }

  @Override
  public EmpresaResponse findById(Integer id) {
    log.info("Implements :: findById :: " + id);
    return repository.findById(id).map(this::convertEntityToResponse).orElse(new EmpresaResponse());
  }

  @Override
  public EmpresaResponse create(EmpresaRequest request) {
    log.debug("Implements :: create :: Inicio");
    request.setEmpIsActive(true);
    FxComunes.printJson(request);
    return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
  }

  @Override
  public EmpresaResponse update(EmpresaRequest request) {//request.getUsuId()
    EmpresaResponse res = repository.findById(request.getEmpId()).map(this::convertEntityToResponse).orElse(new EmpresaResponse());
    //request.setUsuClave(passwordEncoder.encode(request.getUsuClave()));
    if (res.getEmpId() == null) {
      return new EmpresaResponse();
    } else {
      return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
    }
  }

  @Override
  public EmpresaResponse delete(Integer id) {
    log.debug("Implements :: delete :: ID -> {}", id);
    EmpresaResponse res = repository.findById(id).map(this::convertEntityToResponse).orElse(new EmpresaResponse());
    res.setEmpIsActive(false);
    Optional<EmpresaEntity> ent = repository.findById(res.getEmpId());
    if (ent.isPresent()) {
      return convertEntityToResponse(repository.save(convertRequestToEntity(convertResponseToRequest(res))));
    } else {
      return new EmpresaResponse();
    }
  }

  private EmpresaEntity convertRequestToEntity(EmpresaRequest in) {
    EmpresaEntity out = new EmpresaEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private EmpresaResponse convertEntityToResponse(EmpresaEntity in) {
    EmpresaResponse out = new EmpresaResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private EmpresaRequest convertResponseToRequest(EmpresaResponse in) {
    EmpresaRequest out = new EmpresaRequest();
    BeanUtils.copyProperties(in, out);
    return out;
  }

}
