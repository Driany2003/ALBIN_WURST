package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IEmpresaCajaService;
import com.pe.kenpis.model.api.empresa.EmpresaDTO;
import com.pe.kenpis.model.api.empresa.caja.EmpresaCajaDTO;
import com.pe.kenpis.model.api.empresa.caja.EmpresaCajaRequest;
import com.pe.kenpis.model.api.empresa.caja.EmpresaCajaResponse;
import com.pe.kenpis.model.api.usuario.ResponsablesDTO;
import com.pe.kenpis.model.entity.EmpresaCajaEntity;
import com.pe.kenpis.model.entity.EmpresaEntity;
import com.pe.kenpis.model.entity.ReporteEntity;
import com.pe.kenpis.repository.EmpresaCajaRepository;
import com.pe.kenpis.repository.EmpresaRepository;
import com.pe.kenpis.repository.ReportesRepository;
import com.pe.kenpis.util.funciones.DateUtil;
import com.pe.kenpis.util.funciones.FxComunes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmpresaCajaImpl implements IEmpresaCajaService {

  private final EmpresaCajaRepository repository;
  private final ReportesRepository reportesRepository;

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
  public EmpresaCajaResponse abrirCajaSiNoExiste(EmpresaCajaRequest request) {
    EmpresaCajaEntity nuevaCaja = new EmpresaCajaEntity();
    nuevaCaja.setEmpPadreId(request.getEmpPadreId());
    nuevaCaja.setSucursalId(request.getSucursalId());
    nuevaCaja.setCajaMontoInicial(request.getCajaMontoInicial());
    nuevaCaja.setCajaUsuarioApertura(request.getCajaUsuarioApertura());
    nuevaCaja.setCajaAsignada(request.getCajaAsignada());
    nuevaCaja.setCajaFechaApertura(new Date());
    nuevaCaja.setCajaEstado(true);

    nuevaCaja = repository.save(nuevaCaja);

    ReporteEntity nuevoReporte = new ReporteEntity();
    nuevoReporte.setSucursalId(request.getSucursalId());
    nuevoReporte.setRepIsActive(true);
    nuevoReporte.setEmpId(request.getEmpPadreId());
    nuevoReporte.setRepFechaCreacion(new Date());
    nuevoReporte.setCajaId(nuevaCaja.getCajaId());

    reportesRepository.save(nuevoReporte);

    return convertEntityToResponse(nuevaCaja);
  }

  public float obtenerTotalPorCaja(Integer empresaId, Integer cajaId) {
    Float totalPorCaja = repository.obtenerTotalPorEmpresaYCaja(empresaId, cajaId);
    if (totalPorCaja == null) {
      return 0.0f;
    }
    return totalPorCaja.floatValue();
  }


  @Override
  public EmpresaCajaResponse cerrarCaja(Integer cajaId, Integer usuarioId) {
    log.info("id" + cajaId + "usuId" + usuarioId);
    EmpresaCajaEntity caja = repository.findById(cajaId).orElseThrow(() -> new IllegalArgumentException("Caja no encontrada"));

    // Obtener el total de ventas para la caja
    float totalPorCaja = obtenerTotalPorCaja(caja.getEmpPadreId(), cajaId);

    // Configurar los campos de cierre de la caja
    caja.setCajaEstado(false);
    caja.setCajaFechaCierre(new Date());
    caja.setCajaUsuarioCierre(usuarioId);
    caja.setCajaMontoFinal(totalPorCaja);

    // Actualizar el estado del reporte correspondiente
    ReporteEntity reporte = reportesRepository.findByCajaId(cajaId).orElseThrow(() -> new IllegalArgumentException("Reporte no encontrado para la caja"));
    reporte.setRepIsActive(false);
    reportesRepository.save(reporte);

    // Guardar los cambios en la base de datos y devolver la respuesta
    return convertEntityToResponse(repository.save(caja));
  }

  @Override
  public List<EmpresaCajaResponse> obtenerCajasActivasPorEmpresa(Integer empresaId) {
    List<EmpresaCajaEntity> cajasActivas = repository.findByEmpresaIdAndCajaEstado(empresaId);
    return cajasActivas.stream().map(this::convertEntityToResponse).collect(Collectors.toList());
  }

  @Override
  public List<EmpresaCajaResponse> obtenerCajasPorEmpresa(Integer empresaId) {
    log.info("Implements :: obtenerCajasPorEmpresa" + empresaId);

    List<EmpresaCajaEntity> cajas = repository.findCajasByEmpresaId(empresaId);

    return cajas.stream().map(caja -> {
      EmpresaCajaResponse response = new EmpresaCajaResponse();
      response.setCajaId(caja.getCajaId());
      response.setEmpPadreId(caja.getEmpPadreId());
      response.setCajaFechaApertura(caja.getCajaFechaApertura());
      response.setCajaFechaCierre(caja.getCajaFechaCierre());
      response.setCajaMontoInicial(caja.getCajaMontoInicial());
      response.setCajaMontoFinal(caja.getCajaMontoFinal());
      response.setCajaUsuarioApertura(caja.getCajaUsuarioApertura());
      response.setCajaUsuarioCierre(caja.getCajaUsuarioCierre());
      response.setCajaAsignada(caja.getCajaAsignada());
      response.setCajaEstado(caja.getCajaEstado());
      return response;
    }).collect(Collectors.toList());
  }

  public String obtenerNombreSucursalPorCajaId(Integer cajaId) {
    Optional<EmpresaCajaEntity> caja = repository.findById(cajaId);
    return caja.map(EmpresaCajaEntity::getCajaAsignada).orElse(null);
  }

  private EmpresaCajaEntity convertRequestToEntity(EmpresaCajaRequest in) {
    EmpresaCajaEntity out = new EmpresaCajaEntity();
    BeanUtils.copyProperties(in, out);
    return out;

  }

  private EmpresaCajaResponse convertEntityToResponse(EmpresaCajaEntity in) {
    EmpresaCajaResponse response = new EmpresaCajaResponse();
    BeanUtils.copyProperties(in, response);
    return response;
  }

  private EmpresaCajaRequest convertResponseToRequest(EmpresaCajaResponse in) {
    EmpresaCajaRequest out = new EmpresaCajaRequest();
    BeanUtils.copyProperties(in, out);
    return out;
  }

}
