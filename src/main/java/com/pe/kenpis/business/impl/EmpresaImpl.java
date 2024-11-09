package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IEmpresaService;
import com.pe.kenpis.model.api.empresa.EmpresaDTO;
import com.pe.kenpis.model.api.empresa.EmpresaRequest;
import com.pe.kenpis.model.api.empresa.EmpresaResponse;
import com.pe.kenpis.model.api.empresa.EmpresaResponseDTO;
import com.pe.kenpis.model.api.empresa.sucursal.SucursalDTOResponse;
import com.pe.kenpis.model.api.empresa.sucursal.SucursalDTOrequest;
import com.pe.kenpis.model.api.empresa.sucursal.SucursalRequest;
import com.pe.kenpis.model.api.empresa.sucursal.SucursalResponse;
import com.pe.kenpis.model.api.usuario.ResponsablesDTO;
import com.pe.kenpis.model.entity.EmpresaEntity;
import com.pe.kenpis.model.entity.UsuarioEntity;
import com.pe.kenpis.repository.EmpresaRepository;
import com.pe.kenpis.repository.UsuarioRepository;
import com.pe.kenpis.util.funciones.FxComunes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmpresaImpl implements IEmpresaService {

  private final EmpresaRepository repository;
  private final UsuarioRepository usuarioRepository;

  @Autowired
  private UsuarioImpl usuarioImpl;

  //lista empresas activas solo para mostrar en el modulo de empresas
  @Override
  public List<EmpresaDTO> findAllActiveEmpresaById() {
    log.info("Implements  :: findAll");
    List<Map<String, Object>> results = repository.findAllActiveEmpresaById();
    return results.stream().map(result -> new EmpresaDTO((Integer) result.get("empId"), (String) result.get("empResponsable"), (String) result.get("empImagenLogo"), (String) result.get("empNombreComercial"), (Date) result.get("empFechaContratoInicio"), (Date) result.get("empFechaContratoFin"), (String) result.get("empTelefono"), (Boolean) result.get("empIsActive"))).collect(Collectors.toList());
  }

  //lista empresas activas solo para los combos
  @Override
  public List<EmpresaResponseDTO> findAllByStatus() {
    List<Map<String, Object>> results = repository.findAllByEmpIsActive();
    return results.stream().map(result -> new EmpresaResponseDTO((Integer) result.get("empId"), (String) result.get("empNombreComercial"))).collect(Collectors.toList());
  }

  @Override
  public EmpresaResponse findById(Integer id) {
    log.info("Implements :: findById :: " + id);
    return repository.findById(id).map(this::convertEntityToResponse).orElse(new EmpresaResponse());
  }

  //VISTA DE PROPIETARIO PARA VER LA INFO PERSONAL
  @Override
  public EmpresaResponse obtenerEmpresaPorUsuario(Integer usuarioId) {
    log.info("Implements :: obtenerEmpresaPorUsuario :: {}", usuarioId);
    Optional<EmpresaEntity> empresaOpt = usuarioRepository.findEmpresaByUsuarioId(usuarioId);
    return empresaOpt.map(this::convertEntityToResponse).orElse(new EmpresaResponse());
  }

  //listar en combo
  @Override
  public List<EmpresaDTO> obtenerSucursalesPorEmpresa(Integer empId) {
    log.info("Implements :: obtenerSucursalesPorEmpresa :: {}", empId);
    List<Map<String, Object>> results = repository.findSucursalesByEmpresaPadreId(empId);
    return results.stream().map(result -> new EmpresaDTO((Integer) result.get("empId"), (String) result.get("empNombreComercial"), (String) result.get("empTelefono"), (Boolean) result.get("empIsActive"))).collect(Collectors.toList());

  }
  //listar sucursales activas para registrar caja
  @Override
  public List<EmpresaDTO> obtenerSucursalesByEmpresa(Integer empId) {
    log.info("Implements :: obtenerSucursalesPorEmpresa :: {}", empId);
    List<Map<String, Object>> results = repository.findSucursalesByEmpresa(empId);
    return results.stream().map(result -> new EmpresaDTO((Integer) result.get("empId"), (String) result.get("empNombreComercial"), (String) result.get("empTelefono"), (Boolean) result.get("empIsActive"))).collect(Collectors.toList());

  }

  /* METODOS PARA REGISTRAR */

  @Override
  public EmpresaResponse create(EmpresaRequest request) {
    log.debug("Implements :: create :: Inicio");
    request.setEmpIsActive(true);
    request.setEmpFechaCreacion(new Date());
    request.setEmpPadreId(0);
    return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
  }

  @Override
  public EmpresaResponse createSucursal(SucursalRequest request) {
    log.debug("Implements :: createSucursal :: Inicio");

    EmpresaEntity empresaPadre = repository.findById(request.getEmpPadreId()).orElseThrow(() -> new IllegalArgumentException("No se encontró la empresa padre con ID: " + request.getEmpPadreId()));
    EmpresaEntity nuevaSucursal = new EmpresaEntity();

    nuevaSucursal.setEmpDocumentoTipo(empresaPadre.getEmpDocumentoTipo());
    nuevaSucursal.setEmpDocumentoNumero(empresaPadre.getEmpDocumentoNumero());
    nuevaSucursal.setEmpRazonSocial(empresaPadre.getEmpRazonSocial());
    nuevaSucursal.setEmpImagenLogo("no tiene logo");
    nuevaSucursal.setEmpFechaContratoInicio(empresaPadre.getEmpFechaContratoInicio());
    nuevaSucursal.setEmpFechaContratoFin(empresaPadre.getEmpFechaContratoFin());
    nuevaSucursal.setEmpEmail(empresaPadre.getEmpEmail());
    nuevaSucursal.setEmpResponsable("sin responsable");
    //aca estan los datos del request
    nuevaSucursal.setEmpPadreId(request.getEmpPadreId());
    nuevaSucursal.setEmpTelefono(request.getEmpTelefono());
    nuevaSucursal.setEmpNombreComercial(request.getEmpNombreComercial());

    nuevaSucursal.setEmpIsActive(true);
    nuevaSucursal.setEmpFechaCreacion(new Date());

    return convertEntityToResponse(repository.save(nuevaSucursal));
  }

  /* METODOS PARA ACTUALIZAR */
  @Override
  public SucursalDTOResponse updateSucursal(SucursalDTOrequest request) {
    EmpresaEntity entidadActualizar = repository.findById(request.getEmpId()).orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

    if (request.getEmpNombreComercial() != null) {
      entidadActualizar.setEmpNombreComercial(request.getEmpNombreComercial());
    }
    if (request.getEmpTelefono() != null) {
      entidadActualizar.setEmpTelefono(request.getEmpTelefono());
    }
    if (request.getEmpResponsable() != null) {
      entidadActualizar.setEmpResponsable(request.getEmpResponsable());
    }

    EmpresaEntity entidadGuardada = repository.save(entidadActualizar);

    return convertEntityToResponseSucursalDTO(entidadGuardada);
  }

  //actualizar una empresa
  @Override
  public EmpresaResponse update(EmpresaRequest request) {
    EmpresaResponse res = repository.findById(request.getEmpId()).map(this::convertEntityToResponse).orElse(new EmpresaResponse());
    if (res.getEmpId() == null) {
      return new EmpresaResponse();
    } else {
      request.setEmpIsActive(true);
      request.setEmpPadreId(res.getEmpPadreId());
      res.setEmpFechaContratoInicio(request.getEmpFechaContratoInicio());
      res.setEmpFechaContratoFin(request.getEmpFechaContratoFin());
      request.setEmpFechaCreacion(res.getEmpFechaCreacion());
      return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
    }
  }

  public EmpresaResponse updatePropietario(EmpresaRequest request) {
    EmpresaResponse res = repository.findById(request.getEmpId()).map(this::convertEntityToResponse).orElse(new EmpresaResponse());
    if (res.getEmpId() == null) {
      return new EmpresaResponse();
    } else {
      request.setEmpPadreId(res.getEmpPadreId());
      request.setEmpIsActive(res.getEmpIsActive());
      request.setEmpImagenLogo(res.getEmpImagenLogo());
      request.setEmpFechaContratoFin(res.getEmpFechaContratoFin());
      request.setEmpFechaContratoInicio(res.getEmpFechaContratoInicio());
      request.setEmpFechaCreacion(res.getEmpFechaCreacion());
      request.setEmpResponsable(res.getEmpResponsable());
      return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
    }
  }

  //estado de un empresa - activo,inactivo
  public EmpresaResponse updateStatus(EmpresaRequest request) {
    EmpresaResponse res = repository.findById(request.getEmpId()).map(this::convertEntityToResponse).orElse(new EmpresaResponse());

    // Si no se encuentra la empresa, devolver una nueva respuesta vacía
    if (res.getEmpId() == null) {
      return new EmpresaResponse();
    } else {
      repository.updateEstadoEmpresaYSucursales(request.getEmpId(), request.getEmpIsActive());
      // Retornar la respuesta actualizada
      return convertEntityToResponse(repository.findById(request.getEmpId()).orElse(null));
    }
  }


  /* METODOS PARA ELIMINAR */

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

  //Request empresa
  private EmpresaEntity convertRequestToEntity(EmpresaRequest in) {
    EmpresaEntity out = new EmpresaEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private SucursalDTOResponse convertEntityToResponseSucursalDTO(EmpresaEntity in) {
    SucursalDTOResponse out = new SucursalDTOResponse();
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
