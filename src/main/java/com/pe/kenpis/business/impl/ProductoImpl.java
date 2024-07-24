package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IProductoService;
import com.pe.kenpis.model.api.producto.ProductoRequest;
import com.pe.kenpis.model.api.producto.ProductoResponse;
import com.pe.kenpis.model.entity.ProductoEntity;
import com.pe.kenpis.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductoImpl implements IProductoService {

  private final ProductoRepository productoRepository;

  @Override
  public ProductoResponse findById(Integer id) {
    return null;
  }

  @Override
  public ProductoResponse create(ProductoRequest request) {
    return null;
  }

  @Override
  public ProductoResponse update(ProductoRequest request) {
    return null;
  }

  @Override
  public ProductoResponse delete(ProductoRequest request) {
    return null;
  }

  @Override
  public List<ProductoResponse> findAll() {
    return null;
  }

  public List<ProductoResponse> findAllByProCategoria(String dato) {
    if (dato == null || dato.isEmpty()) {
      throw new IllegalArgumentException("El tipo no puede ser nulo o vacío");
    }
    List<ProductoEntity> productos = productoRepository.findAllByProCategoria(dato);
    return productos.stream().map(this::convertProductosEntityToResponse).collect(Collectors.toList());
  }

  private ProductoResponse convertProductosEntityToResponse(ProductoEntity in) {
    ProductoResponse out = new ProductoResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private ProductoEntity convertProductosRequestToEntity(ProductoRequest in) {
    ProductoEntity out = new ProductoEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }

}
