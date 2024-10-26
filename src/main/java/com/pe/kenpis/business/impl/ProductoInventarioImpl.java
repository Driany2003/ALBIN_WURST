package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IProductoInventarioService;
import com.pe.kenpis.model.api.producto.inventario.ProductoInventarioRequest;
import com.pe.kenpis.model.api.producto.inventario.ProductoInventarioResponse;
import com.pe.kenpis.model.entity.ProductoInventarioEntity;
import com.pe.kenpis.repository.ProductoInventarioRepository;
import com.pe.kenpis.util.funciones.DateUtil;
import com.pe.kenpis.util.funciones.FxComunes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
  public List<ProductoInventarioResponse> findAll() {
    log.info("Implements :: findAll");
    return repository.findProductsWithInventory().stream().map(this::convertMapToResponse).collect(Collectors.toList());
  }

  private ProductoInventarioResponse convertMapToResponse(Map<String, Object> map) {
    ProductoInventarioResponse response = new ProductoInventarioResponse();
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
  public ProductoInventarioResponse findById(Integer id) {
    log.info("Implements :: findById :: " + id);
    return repository.findById(id).map(this::convertEntityToResponse).orElse(new ProductoInventarioResponse());
  }

  @Override
  public ProductoInventarioResponse create(ProductoInventarioRequest request) {
    log.debug("Implements :: create :: Inicio");
    FxComunes.printJson("ProductoInventarioRequest", request);
    return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
  }

  @Override
  public ProductoInventarioResponse update(ProductoInventarioRequest request) {
    ProductoInventarioResponse res = repository.findById(request.getProInvId()).map(this::convertEntityToResponse).orElse(new ProductoInventarioResponse());
    if (res.getProInvId() == null) {
      return new ProductoInventarioResponse();
    } else {
      return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
    }
  }

  @Override
  public ProductoInventarioResponse delete(Integer id) {
    log.debug("Implements :: delete :: ID -> {}", id);
    ProductoInventarioResponse res = repository.findById(id).map(this::convertEntityToResponse).orElse(new ProductoInventarioResponse());
    Optional<ProductoInventarioEntity> ent = repository.findById(res.getProInvId());
    if (ent.isPresent()) {
      return convertEntityToResponse(repository.save(convertRequestToEntity(convertResponseToRequest(res))));
    } else {
      return new ProductoInventarioResponse();
    }
  }

  private ProductoInventarioEntity convertRequestToEntity(ProductoInventarioRequest in) {
    ProductoInventarioEntity out = new ProductoInventarioEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private ProductoInventarioResponse convertEntityToResponse(ProductoInventarioEntity in) {
    ProductoInventarioResponse out = new ProductoInventarioResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private ProductoInventarioRequest convertResponseToRequest(ProductoInventarioResponse in) {
    ProductoInventarioRequest out = new ProductoInventarioRequest();
    BeanUtils.copyProperties(in, out);
    return out;
  }

}
