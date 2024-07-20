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

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class
VentasImpl implements IVentasService {

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
  @Override
  public VentasResponse registrarVenta(VentasRequest ventaRequest) {
    VentasEntity ventaEntity = new VentasEntity();
    ventaEntity.setVenFecha(new Date());
    ventaEntity.setVenTotal(ventaRequest.getVenTotal());
    VentasEntity ventaGuardada = ventasRepository.save(ventaEntity);

    double totalVenta = 0;
    List<DetallesVentasEntity> detallesVentas = new ArrayList<>();

    // Extraer IDs de productos del request
    Set<Integer> productIds = ventaRequest.getDetallesVentas().stream()
        .map(DetalleVentaRequest::getProId)
        .collect(Collectors.toSet());

    // Obtener todos los productos en un solo query
    List<ProductosEntity> productos = productosRepository.findAllById(productIds);

    // Crear un mapa para acceder rápidamente a los productos por ID
    Map<Integer, ProductosEntity> productoMap = productos.stream()
        .collect(Collectors.toMap(ProductosEntity::getProId, producto -> producto));

    for (DetalleVentaRequest detalleRequest : ventaRequest.getDetallesVentas()) {
      DetallesVentasEntity detalleVentaEntity = new DetallesVentasEntity();
      detalleVentaEntity.setVentaId(ventaGuardada.getVenId());
      detalleVentaEntity.setDetvenCantidad(detalleRequest.getDetvenCantidad());
      detalleVentaEntity.setProductoId(detalleRequest.getProId());

      ProductosEntity producto = productoMap.get(detalleRequest.getProId());
      if (producto != null) {
        double subtotal = producto.getProPrecio() * detalleRequest.getDetvenCantidad();
        detalleVentaEntity.setDetvenSubTotal(subtotal);
        detallesVentas.add(detalleVentaEntity);
        totalVenta += subtotal;
      }
    }


    detalleVentaRepository.saveAll(detallesVentas);

    // Crear respuesta
    VentasResponse response = new VentasResponse();
    response.setVenId(ventaGuardada.getVenId());
    response.setVenFecha(ventaGuardada.getVenFecha());
    response.setVenTotal((float) totalVenta);
    response.setDetallesVentas(detallesVentas.stream()
        .map(detalle -> {
          ProductosEntity producto = productoMap.get(detalle.getProductoId());
          ProductosResponse productoResponse = new ProductosResponse();
          if (producto != null) {
            productoResponse.setProId(producto.getProId());
            productoResponse.setProNombre(producto.getProNombre());
            productoResponse.setProTipo(producto.getProTipo());
            productoResponse.setProPrecio(producto.getProPrecio());
          }
          return new DetalleVentaResponse(productoResponse,
              detalle.getDetvenCantidad(),
              producto.getProPrecio(),
              detalle.getDetvenSubTotal()
          );
        }).collect(Collectors.toList())
    );

    return response;
  }


  @Override
  public List<DetalleVentaResponse> obtenerDetallesDeVenta() {
    List<DetallesVentasEntity> detalles = detalleVentaRepository.findAll();
    return detalles.stream().map(detalle -> {
      ProductosEntity producto = productosRepository.findById(detalle.getProductoId()).orElse(null);
      ProductosResponse productoResponse = new ProductosResponse();
      if (producto != null) {
        productoResponse.setProId(producto.getProId());
        productoResponse.setProNombre(producto.getProNombre());
        productoResponse.setProTipo(producto.getProTipo());
        productoResponse.setProPrecio(producto.getProPrecio());
      }
      return new DetalleVentaResponse(
          productoResponse,
          detalle.getDetvenId(),
          detalle.getDetvenCantidad(),
          detalle.getDetvenSubTotal(),
          detalle.getVentaId(),
          producto != null ? producto.getProId() : null,
          producto != null ? producto.getProPrecio() : 0.0
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
