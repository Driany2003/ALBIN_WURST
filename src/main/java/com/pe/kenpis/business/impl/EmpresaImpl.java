package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IEmpresaService;
import com.pe.kenpis.model.api.empresa.EmpresaDTO;
import com.pe.kenpis.model.api.empresa.EmpresaRequest;
import com.pe.kenpis.model.api.empresa.EmpresaResponse;
import com.pe.kenpis.model.entity.EmpresaEntity;
import com.pe.kenpis.model.entity.UsuarioEntity;
import com.pe.kenpis.repository.EmpresaRepository;
import com.pe.kenpis.repository.UsuarioRepository;
import com.pe.kenpis.util.funciones.FxComunes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmpresaImpl implements IEmpresaService {

  private final EmpresaRepository repository;
  private final UsuarioRepository usuarioRepository;

  @Override
  public List<EmpresaResponse> findAll() {
    log.info("Implements :: findAll");
    return repository.findAll().stream().map(this::convertEntityToResponse).collect(Collectors.toList());
  }

  @Override
  public List<EmpresaDTO> findAllByStatus() {
    List<Map<String, Object>> results = repository.findAllByEmpIsActive();
    return results.stream().map(result -> new EmpresaDTO((Integer) result.get("empId"), (String) result.get("empRazonSocial"))).collect(Collectors.toList());
  }

  @Override
  public EmpresaResponse findById(Integer id) {
    log.info("Implements :: findById :: " + id);
    return repository.findById(id).map(this::convertEntityToResponse).orElse(new EmpresaResponse());
  }

  @Override
  public EmpresaResponse obtenerEmpresaPorUsuario(Integer usuarioId) {
    log.info("Implements :: obtenerEmpresaPorUsuario :: {}", usuarioId);
    Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findById(usuarioId);

    if (usuarioOpt.isPresent()) {
      UsuarioEntity usuario = usuarioOpt.get();
      Integer empresaId = usuario.getEmpresaId();

      Optional<EmpresaEntity> empresaOpt = repository.findById(empresaId);
      return empresaOpt.map(this::convertEntityToResponse).orElse(new EmpresaResponse());
    } else {
      return new EmpresaResponse();
    }
  }

  @Override
  public EmpresaResponse create(EmpresaRequest request) {
    log.debug("Implements :: create :: Inicio");
    request.setEmpIsActive(true);
    request.setEmpFechaCreacion(new Date());
    FxComunes.printJson("EmpresaRequest", request);
    return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
  }

  @Override
  public EmpresaResponse update(EmpresaRequest request) {
    EmpresaResponse res = repository.findById(request.getEmpId()).map(this::convertEntityToResponse).orElse(new EmpresaResponse());
    //request.setUsuClave(passwordEncoder.encode(request.getUsuClave()));
    if (res.getEmpId() == null) {
      return new EmpresaResponse();
    } else {
      request.setEmpIsActive(res.getEmpIsActive());
      return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
    }
  }

  public EmpresaResponse updatePropietario(EmpresaRequest request) {
    EmpresaResponse res = repository.findById(request.getEmpId()).map(this::convertEntityToResponse).orElse(new EmpresaResponse());
    if (res.getEmpId() == null) {
      return new EmpresaResponse();
    } else {
      request.setEmpFechaContratoFin(res.getEmpFechaContratoFin());
      request.setEmpFechaContratoInicio(res.getEmpFechaContratoInicio());
      request.setEmpFechaCreacion(res.getEmpFechaCreacion());
      request.setEmpResponsable(res.getEmpResponsable());
      request.setEmpQrPlin(res.getEmpQrPlin());
      request.setEmpQrYape(res.getEmpQrYape());
      request.setEmpQrPagos(res.getEmpQrPagos());

      return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
    }
  }

  public EmpresaResponse updateStatus(EmpresaRequest request) {
    EmpresaResponse res = repository.findById(request.getEmpId()).map(this::convertEntityToResponse).orElse(new EmpresaResponse());
    if (res.getEmpId() == null) {
      return new EmpresaResponse();
    } else {
      request.setEmpDocumentoTipo(res.getEmpDocumentoTipo());
      request.setEmpDocumentoNumero(res.getEmpDocumentoNumero());
      request.setEmpEmail(res.getEmpEmail());
      request.setEmpFechaContratoFin(res.getEmpFechaContratoFin());
      request.setEmpFechaContratoInicio(res.getEmpFechaContratoInicio());
      request.setEmpFechaCreacion(res.getEmpFechaCreacion());
      request.setEmpImageLogo(res.getEmpImageLogo());
      request.setEmpNombreComercial(res.getEmpNombreComercial());
      request.setEmpRazonSocial(res.getEmpRazonSocial());
      request.setEmpTelefono(res.getEmpTelefono());
      request.setEmpResponsable(res.getEmpResponsable());
      request.setEmpQrPlin(res.getEmpQrPlin());
      request.setEmpQrYape(res.getEmpQrYape());
      request.setEmpQrPagos(res.getEmpQrPagos());

      return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
    }
  }

  @Override
  public EmpresaResponse delete(Integer id) {
    log.debug("Implements :: delete :: ID -> {}", id);
    Optional<EmpresaEntity> empresaEliminar = repository.findById(id);
    if (empresaEliminar.isPresent()) {
      repository.deleteById(id);
      EmpresaEntity deletedEntity = empresaEliminar.get();
      return convertEntityToResponse(deletedEntity);
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
