package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IVentaService;
import com.pe.kenpis.model.api.producto.ProductoResponse;
import com.pe.kenpis.model.api.venta.VentaRequest;
import com.pe.kenpis.model.api.venta.VentaResponse;
import com.pe.kenpis.model.api.venta.detalle.VentaDetalleRequest;
import com.pe.kenpis.model.api.venta.detalle.VentaDetalleResponse;
import com.pe.kenpis.model.entity.ProductoEntity;
import com.pe.kenpis.model.entity.VentaDetalleEntity;
import com.pe.kenpis.model.entity.VentaEntity;
import com.pe.kenpis.model.entity.VentaEstadoEntity;
import com.pe.kenpis.repository.ProductoRepository;
import com.pe.kenpis.repository.VentaDetalleRepository;
import com.pe.kenpis.repository.VentaEstadoRepository;
import com.pe.kenpis.repository.VentaRepository;
import com.pe.kenpis.util.funciones.DateUtil;
import com.pe.kenpis.util.funciones.FxComunes;
import com.pe.kenpis.util.variables.Constantes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VentaImpl implements IVentaService {

  private final VentaRepository ventaRepository;
  private final ProductoRepository productoRepository;
  private final VentaDetalleRepository detalleVentaRepository;
  private final VentaEstadoRepository ventaEstadoRepository;

  @Override
  public VentaResponse findById(Integer venId) {
    log.debug("Implements :: findById :: Inicio");
    Optional<VentaEntity> optionalVenta = ventaRepository.findById(venId);
    return optionalVenta.map(this::convertVentasEntityToResponse).orElse(null);
  }

  @Override
  public List<VentaResponse> findAll() {
    log.debug("Implements :: findAll");
    List<VentaEntity> ventasList = ventaRepository.findAll();
    return ventasList.stream().map(this::convertVentasEntityToResponse).collect(Collectors.toList());
  }

  @Override
  public VentaResponse create(VentaRequest ventaRequest)  {
    FxComunes.printJson("VentaRequest", ventaRequest);

    VentaEntity nuevaVenta = convertVentasRequestToEntity(ventaRequest);
    nuevaVenta.setVenFecha(new Date());

    VentaEntity ventaGuardada = ventaRepository.save(nuevaVenta);
    FxComunes.printJson("VentaEntity", ventaGuardada);

    List<VentaDetalleEntity> detallesVentas = new ArrayList<>();
    for (VentaDetalleRequest detalle : ventaRequest.getDetallesVentas()) {
      VentaDetalleEntity nuevoDetalle = new VentaDetalleEntity();
      nuevoDetalle.setVenDetObservaciones(detalle.getVenDetObservaciones());
      nuevoDetalle.setVentaId(ventaGuardada.getVenId());
      nuevoDetalle.setProductoId(detalle.getProductoId());
      nuevoDetalle.setVenDetCantidad(detalle.getVenDetCantidad());
      nuevoDetalle.setVenDetPrecio(detalle.getVenDetPrecio());
      nuevoDetalle.setVenDetSubtotal((float) detalle.getVenDetSubtotal());
      detallesVentas.add(nuevoDetalle);

    }

    detalleVentaRepository.saveAll(detallesVentas);

    FxComunes.printJson("VentaDetalleEntity", detallesVentas);

    VentaEstadoEntity estadoInicial = new VentaEstadoEntity();
    estadoInicial.setVentaId(ventaGuardada.getVenId());
    estadoInicial.setVenEstado(Constantes.VENTA_ESTADO.REGISTRADO);
    estadoInicial.setVenEstadoFechaRegistrado(new Date());
    ventaEstadoRepository.save(estadoInicial);


    VentaResponse response = convertVentasEntityToResponse(ventaGuardada);
    return response;
  }


  @Override
  public List<VentaDetalleResponse> obtenerDetallesDeVenta() {
    List<VentaDetalleEntity> detalles = detalleVentaRepository.findAll();
    return detalles.stream().map(detalle -> {
      ProductoEntity producto = productoRepository.findById(detalle.getProductoId()).orElse(null);
      ProductoResponse productoResponse = new ProductoResponse();
      if (producto != null) {
        BeanUtils.copyProperties(producto, productoResponse);
      }
      return new VentaDetalleResponse(productoResponse, detalle.getVenDetId(), detalle.getVenDetCantidad(), detalle.getVenDetSubtotal(), detalle.getVentaId());
    }).collect(Collectors.toList());
  }



  private VentaEntity convertVentasRequestToEntity(VentaRequest request) {
    VentaEntity entity = new VentaEntity();
    BeanUtils.copyProperties(request, entity);
    return entity;
  }

  private VentaResponse convertVentasEntityToResponse(VentaEntity entity) {
    VentaResponse response = new VentaResponse();
    BeanUtils.copyProperties(entity, response);
    return response;
  }
}
