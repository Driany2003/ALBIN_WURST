package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IProductoInventarioService;
import com.pe.kenpis.model.api.producto.inventario.ProductoProductoRequest;
import com.pe.kenpis.model.api.producto.inventario.ProductoComplementoResponse;
import com.pe.kenpis.model.entity.ProductoInventarioEntity;
import com.pe.kenpis.repository.ProductoInventarioRepository;
import com.pe.kenpis.util.funciones.DateUtil;
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
public class ProductoInventarioImpl implements IProductoInventarioService {

  private final ProductoInventarioRepository repository;

  @Override
  public List<ProductoComplementoResponse> findAll() {
    log.info("Implements :: findAll");
    return repository.findProductsWithInventory().stream().map(this::convertMapToResponse).collect(Collectors.toList());
  }

  private ProductoComplementoResponse convertMapToResponse(Map<String, Object> map) {
    System.out.println("Datos del map: " + map);
    Integer stockInicial = map.get("proInvStockInicial") != null ? (Integer) map.get("proInvStockInicial") : 0;
    Integer stockVentas = map.get("proInvStockVentas") != null ? (Integer) map.get("proInvStockVentas") : 0;
    Integer stockActual = stockInicial - stockVentas;


    return new ProductoComplementoResponse(
        (Integer) map.get("productoId"),
        (Date) map.get("proInvFechaVencimiento"),
        (String) map.get("proDescripcion"),
        (String) map.get("proImagen"),
        (Boolean) map.get("proIsActive"),
        (Double) map.get("proPrecioCosto"),
        (Double) map.get("proPrecioVenta"),
        (String) map.get("proCategoria"),
        (Integer) map.get("empId"),
        stockInicial,
        stockVentas,
        stockActual,
        (Date) map.get("proInvFechaCreacion")

    );

  }




  @Override
  public ProductoComplementoResponse findById(Integer productoId) {
    log.info("Consultando producto con productoId: " + productoId);
    Optional<ProductoInventarioEntity> optional = repository.findByProductoId(productoId);

    if (optional.isPresent()) {
      ProductoInventarioEntity entity = optional.get();
      log.info("Datos obtenidos: " + entity);
      return convertEntityToResponse(entity); // método para convertir
    } else {
      log.warn("Producto con productoId " + productoId + " no encontrado.");
      return null;
    }
  }



  @Override
  public ProductoComplementoResponse create(ProductoProductoRequest request) {
    log.debug("Implements :: create :: Inicio");
    FxComunes.printJson("ProductoInventarioRequest", request);
    return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
  }

  @Override
  public ProductoComplementoResponse update(ProductoProductoRequest request) {
    log.info("Consultando producto con productoId: " + request.getProductoId());

    Optional<ProductoInventarioEntity> optional = repository.findByProductoId(request.getProductoId());
    if (optional.isPresent()) {
      ProductoInventarioEntity entity = optional.get();

      entity.setProInvStockInicial(request.getProInvStockInicial());
      entity.setProInvFechaVencimiento(request.getProInvFechaVencimiento());

      ProductoInventarioEntity updatedEntity = repository.save(entity);
      return convertEntityToResponse(updatedEntity);
    } else {
      log.warn("Producto con productoId " + request.getProductoId() + " no encontrado.");
      return null;
    }
  }


  @Override
  public ProductoComplementoResponse delete(Integer id) {
    log.debug("Implements :: delete :: ID -> {}", id);
    ProductoComplementoResponse res = repository.findById(id).map(this::convertEntityToResponse).orElse(new ProductoComplementoResponse());
    Optional<ProductoInventarioEntity> ent = repository.findById(res.getProInvId());
    if (ent.isPresent()) {
      return convertEntityToResponse(repository.save(convertRequestToEntity(convertResponseToRequest(res))));
    } else {
      return new ProductoComplementoResponse();
    }
  }

  private ProductoInventarioEntity convertRequestToEntity(ProductoProductoRequest in) {
    ProductoInventarioEntity out = new ProductoInventarioEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private ProductoComplementoResponse convertEntityToResponse(ProductoInventarioEntity in) {
    ProductoComplementoResponse out = new ProductoComplementoResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private ProductoProductoRequest convertResponseToRequest(ProductoComplementoResponse in) {
    ProductoProductoRequest out = new ProductoProductoRequest();
    BeanUtils.copyProperties(in, out);
    return out;
  }

}
