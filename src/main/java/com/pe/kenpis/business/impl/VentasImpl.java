package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IVentasService;
import com.pe.kenpis.model.api.productos.ProductosResponse;
import com.pe.kenpis.model.api.ventas.VentasRequest;
import com.pe.kenpis.model.api.ventas.VentasResponse;
import com.pe.kenpis.model.api.ventas.detalle.DetalleVentaRequest;
import com.pe.kenpis.model.api.ventas.detalle.DetalleVentaResponse;
import com.pe.kenpis.model.entity.DetallesVentasEntity;
import com.pe.kenpis.model.entity.ProductosEntity;
import com.pe.kenpis.model.entity.VentasEntity;
import com.pe.kenpis.repository.DetallesVentasRepository;
import com.pe.kenpis.repository.ProductosRepository;
import com.pe.kenpis.repository.VentasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VentasImpl implements IVentasService {

  private final VentasRepository ventasRepository;
  private final ProductosRepository productosRepository;
  private final DetallesVentasRepository detalleVentaRepository;

  @Override
  public VentasResponse findById(Integer venId) {
    log.debug("Implements :: findById :: Inicio");
    Optional<VentasEntity> optionalVenta = ventasRepository.findById(venId);
    return optionalVenta.map(this::convertVentasEntityToResponse).orElse(null);
  }

  @Override
  public List<VentasResponse> findAll() {
    log.debug("Implements :: findAll");
    List<VentasEntity> ventasList = ventasRepository.findAll();
    return ventasList.stream().map(this::convertVentasEntityToResponse).collect(Collectors.toList());
  }

  public VentasResponse registrarVenta(VentasRequest ventaRequest) {
    VentasEntity nuevaVenta = new VentasEntity();
    nuevaVenta.setVenTotal(ventaRequest.getVenTotal());
    nuevaVenta.setVenFecha(new Date());
    // Guardar la nueva venta para obtener su ID
    VentasEntity ventaGuardada = ventasRepository.save(nuevaVenta);

    // Crear los detalles de la venta
    List<DetallesVentasEntity> detallesVentas = new ArrayList<>();
    for (DetalleVentaRequest detalle : ventaRequest.getDetallesVentas()) {
      DetallesVentasEntity nuevoDetalle = new DetallesVentasEntity();
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
    VentasResponse response = new VentasResponse();
    response.setVenId(ventaGuardada.getVenId());
    response.setVenTotal(ventaGuardada.getVenTotal());
    response.setDetallesVentas(detallesVentas.stream()
        .map(detalle -> new DetalleVentaResponse(
            detalle.getProductoId(),
            detalle.getVenDetCantidad(),
            detalle.getVenDetPrecio(),
            detalle.getVenDetSubtotal()))
        .collect(Collectors.toList()));

    return response;
  }

  @Override
  public List<DetalleVentaResponse> obtenerDetallesDeVenta() {
    List<DetallesVentasEntity> detalles = detalleVentaRepository.findAll();
    return detalles.stream().map(detalle -> {
      ProductosEntity producto = productosRepository.findById(detalle.getProductoId()).orElse(null);
      ProductosResponse productoResponse = new ProductosResponse();
      if (producto != null) {
        BeanUtils.copyProperties(producto, productoResponse);
      }
      return new DetalleVentaResponse(
          productoResponse,
          detalle.getVenDetId(),
          detalle.getVenDetCantidad(),
          detalle.getVenDetSubtotal(),
          detalle.getVentaId()
      );
    }).collect(Collectors.toList());
  }

  private VentasEntity convertVentasRequestToEntity(VentasRequest request) {
    VentasEntity entity = new VentasEntity();
    BeanUtils.copyProperties(request, entity);
    return entity;
  }

  private VentasResponse convertVentasEntityToResponse(VentasEntity entity) {
    VentasResponse response = new VentasResponse();
    BeanUtils.copyProperties(entity, response);
    return response;
  }
}
