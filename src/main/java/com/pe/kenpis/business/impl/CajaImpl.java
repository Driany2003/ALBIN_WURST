package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.ICajaService;
import com.pe.kenpis.model.api.caja.CajaRequest;
import com.pe.kenpis.model.api.caja.CajaResponse;
import com.pe.kenpis.model.api.cliente.ClienteRequest;
import com.pe.kenpis.model.api.cliente.ClienteResponse;
import com.pe.kenpis.model.api.empresa.EmpresaResponse;
import com.pe.kenpis.model.entity.CajaEntity;
import com.pe.kenpis.model.entity.ClienteEntity;
import com.pe.kenpis.model.entity.EmpresaEntity;
import com.pe.kenpis.repository.CajaRepository;
import com.pe.kenpis.repository.EmpresaRepository;
import com.pe.kenpis.util.funciones.FxComunes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jdt.internal.compiler.batch.ClasspathJar;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CajaImpl implements ICajaService {

  private final CajaRepository repository;
  private final EmpresaRepository empresaRepository;

  @Override
  public List<CajaResponse> findAll() {
    log.info("Implements :: findAll");
    return repository.findAll().stream().map(this::convertEntityToResponse).collect(Collectors.toList());
  }

  @Override
  public CajaResponse findById(Integer id) {
    log.info("Implements :: findById :: " + id);
    return repository.findById(id).map(this::convertEntityToResponse).orElse(new CajaResponse());
  }

  @Override
  public CajaResponse create(CajaRequest request) {
    log.debug("Implements :: createNewCaja :: Inicio");
    FxComunes.printJson("CajaRequest", request);
    return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
  }

  @Override
  public CajaResponse update(CajaRequest request) {
    CajaResponse res = repository.findById(request.getCajaId()).map(this::convertEntityToResponse).orElse(new CajaResponse());
    if (res.getCajaId() == null) {
      return new CajaResponse();
    } else {
      return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
    }
  }

  @Override
  public CajaResponse delete(Integer id) {
    log.debug("Implements :: delete :: ID -> {}", id);
    CajaResponse res = repository.findById(id).map(this::convertEntityToResponse).orElse(new CajaResponse());
    Optional<CajaEntity> ent = repository.findById(res.getCajaId());
    if (ent.isPresent()) {
      return convertEntityToResponse(repository.save(convertRequestToEntity(convertResponseToRequest(res))));
    } else {
      return new CajaResponse();
    }
  }

  @Override
  public CajaResponse abrirCaja(CajaRequest cajaRequest) {
    Optional<EmpresaEntity> empresa = Optional.ofNullable(empresaRepository.findById(cajaRequest.getEmpPadreId()).orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada.")));
    if (empresa.isPresent()) {
      cajaRequest.setCajaEstado(true);
      cajaRequest.setCajaFechaApertura(LocalDateTime.now());
    } else {
      return new CajaResponse();
    }
    return convertEntityToResponse(repository.save(convertRequestToEntity(cajaRequest)));
  }

  @Override
  public void cerrarCaja(CajaRequest cajaRequest) {

  }

  @Override
  public boolean verificarCajaAbierta(Integer empId) {
    return false;
  }

  @Override
  public CajaResponse obtenerCajaAbierta(Integer empId) {
    return null;
  }

  private CajaEntity convertRequestToEntity(CajaRequest in) {
    CajaEntity out = new CajaEntity();
    BeanUtils.copyProperties(in, out);
    return out;

  }

  private CajaResponse convertEntityToResponse(CajaEntity in) {
    CajaResponse out = new CajaResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private CajaRequest convertResponseToRequest(CajaResponse in) {
    CajaRequest out = new CajaRequest();
    BeanUtils.copyProperties(in, out);
    return out;
  }

}
