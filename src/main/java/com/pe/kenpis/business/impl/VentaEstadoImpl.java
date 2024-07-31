package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IVentaEstadoService;
import com.pe.kenpis.model.api.venta.estado.VentaEstadoRequest;
import com.pe.kenpis.model.api.venta.estado.VentaEstadoResponse;
import com.pe.kenpis.model.api.venta.estado.VentasEstadoDTO;
import com.pe.kenpis.model.entity.VentaEstadoEntity;
import com.pe.kenpis.repository.VentaEstadoRepository;
import com.pe.kenpis.util.funciones.DateUtil;
import com.pe.kenpis.util.funciones.FxComunes;
import com.pe.kenpis.util.variables.Constantes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VentaEstadoImpl implements IVentaEstadoService {

  private final VentaEstadoRepository repository;

  private final VentaEstadoRepository ventaEstadoRepository;

  @Override
  public List<VentaEstadoResponse> findAll() {
    log.info("Implements :: findAll");
    return repository.findAll().stream().map(this::convertEntityToResponse).collect(Collectors.toList());
  }

  @Override
  public VentaEstadoResponse findById(Integer id) {
    log.info("Implements :: findById :: " + id);
    return repository.findById(id).map(this::convertEntityToResponse).orElse(new VentaEstadoResponse());
  }

  @Override
  public VentaEstadoResponse create(VentaEstadoRequest request) throws Exception {
    log.debug("Implements :: create :: Inicio");
    request.setVenEstado(Constantes.VENTA_ESTADO.REGISTRADO);
    request.setVenEstadoFechaRegistrado(DateUtil.fechaStringToDate(DateUtil.fechaActual()));
    FxComunes.printJson("VentaEstadoRequest", request);
    return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
  }

  @Override
  public VentaEstadoResponse update(VentaEstadoRequest request) throws Exception {//request.getUsuId()
    VentaEstadoResponse res = repository.findById(request.getVenEstadoId()).map(this::convertEntityToResponse).orElse(new VentaEstadoResponse());

    if (request.getVenEstado().equalsIgnoreCase(Constantes.VENTA_ESTADO.REGISTRADO)) {
      request.setVenEstado(Constantes.VENTA_ESTADO.REGISTRADO);
      request.setVenEstadoFechaRegistrado(DateUtil.fechaStringToDate(DateUtil.fechaActual()));
    }

    if (request.getVenEstado().equalsIgnoreCase(Constantes.VENTA_ESTADO.PAGADO)) {
      request.setVenEstado(Constantes.VENTA_ESTADO.PAGADO);
      request.setVenEstadoFechaPagado(DateUtil.fechaStringToDate(DateUtil.fechaActual()));
    }

    if (request.getVenEstado().equalsIgnoreCase(Constantes.VENTA_ESTADO.EN_PROCESO)) {
      request.setVenEstado(Constantes.VENTA_ESTADO.EN_PROCESO);
      request.setVenEstadoFechaEnProceso(DateUtil.fechaStringToDate(DateUtil.fechaActual()));
    }

    if (request.getVenEstado().equalsIgnoreCase(Constantes.VENTA_ESTADO.ATENDIDO)) {
      request.setVenEstado(Constantes.VENTA_ESTADO.ATENDIDO);
      request.setVenEstadoFechaAtendido(DateUtil.fechaStringToDate(DateUtil.fechaActual()));
    }

    if (request.getVenEstado().equalsIgnoreCase(Constantes.VENTA_ESTADO.DESCARTADO)) {
      request.setVenEstado(Constantes.VENTA_ESTADO.DESCARTADO);
      request.setVenEstadoFechaDescartado(DateUtil.fechaStringToDate(DateUtil.fechaActual()));
    }

    if (res.getVenEstadoId() == null) {
      return new VentaEstadoResponse();
    } else {
      return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
    }
  }

  @Override
  public VentaEstadoResponse delete(Integer id) throws Exception {
    log.debug("Implements :: delete :: ID -> {}", id);
    VentaEstadoResponse res = repository.findById(id).map(this::convertEntityToResponse).orElse(new VentaEstadoResponse());
    res.setVenEstado(Constantes.VENTA_ESTADO.DESCARTADO);
    res.setVenEstadoFechaDescartado(DateUtil.fechaStringToDate(DateUtil.fechaActual()));
    Optional<VentaEstadoEntity> ent = repository.findById(res.getVenEstadoId());
    if (ent.isPresent()) {
      return convertEntityToResponse(repository.save(convertRequestToEntity(convertResponseToRequest(res))));
    } else {
      return new VentaEstadoResponse();
    }
  }

  @Override
  public List<VentasEstadoDTO> SP_LISTA_VENTAS_POR_ESTADO_POR_DIA(String estado) {
    List<Map<String, Object>> result = ventaEstadoRepository.SP_LISTA_VENTAS_POR_ESTADO_POR_DIA(estado);
    return result.stream().map(this::convertMapToDTO).collect(Collectors.toList());
  }

  public Map<String, Object> getCountPedidosXEstado() {
    return repository.SP_COUNT_PEDIDOS_X_ESTADO();
  }


  public void save(VentaEstadoEntity ventaEstado) {
    ventaEstadoRepository.save(ventaEstado);
  }

  private VentasEstadoDTO convertMapToDTO(Map<String, Object> map) {
    String proTipo = (String) map.get("proTipo");
    Integer venDetCantidad = (Integer) map.get("venDetCantidad");
    String clienteNombre = (String) map.get("clienteNombre");
    Integer idVenta = (Integer) map.get("id");

    return new VentasEstadoDTO(proTipo, venDetCantidad, clienteNombre,idVenta);
  }

  private VentaEstadoEntity convertRequestToEntity(VentaEstadoRequest in) {
    VentaEstadoEntity out = new VentaEstadoEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private VentaEstadoResponse convertEntityToResponse(VentaEstadoEntity in) {
    VentaEstadoResponse out = new VentaEstadoResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private VentaEstadoRequest convertResponseToRequest(VentaEstadoResponse in) {
    VentaEstadoRequest out = new VentaEstadoRequest();
    BeanUtils.copyProperties(in, out);
    return out;
  }

}
