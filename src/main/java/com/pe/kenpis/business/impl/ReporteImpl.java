package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IReporteService;
import com.pe.kenpis.model.api.producto.ProductoListDTO;
import com.pe.kenpis.model.api.reporte.ReporteRequest;
import com.pe.kenpis.model.api.reporte.ReporteResponse;
import com.pe.kenpis.model.entity.EmpresaEntity;
import com.pe.kenpis.model.entity.ReporteEntity;
import com.pe.kenpis.repository.EmpresaRepository;
import com.pe.kenpis.repository.ReportesRepository;
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
public class ReporteImpl implements IReporteService {

  private final ReportesRepository repository;
  private final EmpresaRepository empresaRepository;

  @Override
  public ReporteResponse findById(Integer id) {
    log.info("Implements :: findById :: " + id);
    return repository.findById(id).map(this::convertEntityToResponse).orElse(new ReporteResponse());
  }

  @Override
  public List<ReporteResponse> obtenerReportesPorEmpresaId(Integer empId) {
    return repository.findReportesWithSucursalNameByEmpId(empId).stream().map(this::convertToListReporteResponseDTO).collect(Collectors.toList());
  }

  private ReporteResponse convertToListReporteResponseDTO(Map<String, Object> map) {
    return new ReporteResponse((Integer) map.get("cajaId"), (Date) map.get("repFechaCreacion"), (String) map.get("sucursalNombre"), (Boolean) map.get("repIsActive"));
  }

  private String obtenerNombreSucursal(Integer sucursalId) {
    return empresaRepository.findById(sucursalId).map(EmpresaEntity::getEmpNombreComercial).orElse("Sucursal no encontrada");
  }

  @Override
  public ReporteResponse create(ReporteRequest request) {
    log.debug("Implements :: create :: Inicio");
    return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
  }

  @Override
  public ReporteResponse delete(Integer id) {
    log.debug("Implements :: delete :: ID -> {}", id);
    Optional<ReporteEntity> reporteEliminar = repository.findById(id);
    if (reporteEliminar.isPresent()) {
      repository.deleteById(id);
      ReporteEntity deletedEntity = reporteEliminar.get();
      return convertEntityToResponse(deletedEntity);
    } else {
      return new ReporteResponse();
    }
  }

  private ReporteEntity convertRequestToEntity(ReporteRequest in) {
    ReporteEntity out = new ReporteEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private ReporteResponse convertEntityToResponse(ReporteEntity in) {
    ReporteResponse out = new ReporteResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }

}
