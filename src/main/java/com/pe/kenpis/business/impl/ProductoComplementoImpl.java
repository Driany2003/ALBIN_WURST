package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IProductoComplementoService;
import com.pe.kenpis.model.api.producto.inventario.ProductoComplementoResponse;
import com.pe.kenpis.model.api.producto.inventario.ProductoProductoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductoComplementoImpl implements IProductoComplementoService {



  @Override
  public List<ProductoComplementoResponse> findAll() {
    return Collections.emptyList();
  }

  @Override
  public ProductoComplementoResponse findById(Integer id) {
    return null;
  }

  @Override
  public ProductoComplementoResponse create(ProductoProductoRequest request) {
    return null;
  }

  @Override
  public ProductoComplementoResponse update(ProductoProductoRequest request) {
    return null;
  }

  @Override
  public ProductoComplementoResponse delete(Integer id) {
    return null;
  }
}
