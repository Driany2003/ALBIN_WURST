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
    ProductoComplementoResponse response = new ProductoComplementoResponse();
    response.setProPrecioCosto((Double) map.get("proPrecioCosto"));
    response.setProPrecioVenta((Double) map.get("proPrecioVenta"));
    response.setProCategoria((String) map.get("proCategoria"));
    response.setProDescripcion((String) map.get("proDescripcion"));
    response.setEmpId((Integer) map.get("empId"));
    response.setProImagen((String) map.get("proImagen"));
    response.setProIsActive((Boolean) map.get("proIsActive"));
    response.setProInvStockInicial((Integer) map.get("proInvStockInicial"));
    response.setProInvStockVentas((Integer) map.get("proInvStockVentas"));
    response.setProInvStockActual((Integer) map.get("proInvStockInicial") - (Integer) map.get("proInvStockVentas"));
    response.setProInvFechaCreacion(DateUtil.diasParaVencimiento((Date) map.get("proInvFechaCreacion")));
    return response;
  }

  @Override
  public ProductoComplementoResponse findById(Integer id) {
    log.info("Implements :: findById :: " + id);
    return repository.findById(id).map(this::convertEntityToResponse).orElse(new ProductoComplementoResponse());
  }

  @Override
  public ProductoComplementoResponse create(ProductoProductoRequest request) {
    log.debug("Implements :: create :: Inicio");
    FxComunes.printJson("ProductoInventarioRequest", request);
    return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
  }

  @Override
  public ProductoComplementoResponse update(ProductoProductoRequest request) {
    ProductoComplementoResponse res = repository.findById(request.getProInvId()).map(this::convertEntityToResponse).orElse(new ProductoComplementoResponse());
    if (res.getProInvId() == null) {
      return new ProductoComplementoResponse();
    } else {
      return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
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
