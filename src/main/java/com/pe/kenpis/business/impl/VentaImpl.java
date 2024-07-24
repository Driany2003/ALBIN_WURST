package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IVentaService;
import com.pe.kenpis.model.api.producto.ProductoResponse;
import com.pe.kenpis.model.api.venta.VentaRequest;
import com.pe.kenpis.model.api.venta.VentaResponse;
import com.pe.kenpis.model.api.venta.detalle.DetalleVentaRequest;
import com.pe.kenpis.model.api.venta.detalle.DetalleVentaResponse;
import com.pe.kenpis.model.entity.ProductoEntity;
import com.pe.kenpis.model.entity.VentaDetalleEntity;
import com.pe.kenpis.model.entity.VentaEntity;
import com.pe.kenpis.repository.ProductoRepository;
import com.pe.kenpis.repository.VentaDetalleRepository;
import com.pe.kenpis.repository.VentaRepository;
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

  public VentaResponse registrarVenta(VentaRequest ventaRequest) {
    VentaEntity nuevaVenta = new VentaEntity();
    nuevaVenta.setVenTotal(ventaRequest.getVenTotal());
    nuevaVenta.setVenFecha(new Date());
    // Guardar la nueva venta para obtener su ID
    VentaEntity ventaGuardada = ventaRepository.save(nuevaVenta);

    // Crear los detalles de la venta
    List<VentaDetalleEntity> detallesVentas = new ArrayList<>();
    for (DetalleVentaRequest detalle : ventaRequest.getDetallesVentas()) {
      VentaDetalleEntity nuevoDetalle = new VentaDetalleEntity();
      nuevoDetalle.setVentaId(ventaGuardada.getVenId());
      nuevoDetalle.setProductoId(detalle.getProducto().getProId());
      nuevoDetalle.setVenDetCantidad(detalle.getVenDetCantidad());
      nuevoDetalle.setVenDetPrecio(detalle.getVenDetPrecio());
      nuevoDetalle.setVenDetSubtotal((float) detalle.getVenDetSubtotal());
      detallesVentas.add(nuevoDetalle);
    }

    // Guardar todos los detalles de la venta
    detalleVentaRepository.saveAll(detallesVentas);

    // Preparar la respuesta
    VentaResponse response = new VentaResponse();
    response.setVenId(ventaGuardada.getVenId());
    response.setVenTotal(ventaGuardada.getVenTotal());
    response.setDetallesVentas(detallesVentas.stream().map(detalle -> new DetalleVentaResponse(detalle.getProductoId(), detalle.getVenDetCantidad(), detalle.getVenDetPrecio(), detalle.getVenDetSubtotal())).collect(Collectors.toList()));

    return response;
  }

  @Override
  public List<DetalleVentaResponse> obtenerDetallesDeVenta() {
    List<VentaDetalleEntity> detalles = detalleVentaRepository.findAll();
    return detalles.stream().map(detalle -> {
      ProductoEntity producto = productoRepository.findById(detalle.getProductoId()).orElse(null);
      ProductoResponse productoResponse = new ProductoResponse();
      if (producto != null) {
        BeanUtils.copyProperties(producto, productoResponse);
      }
      return new DetalleVentaResponse(productoResponse, detalle.getVenDetId(), detalle.getVenDetCantidad(), detalle.getVenDetSubtotal(), detalle.getVentaId());
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
