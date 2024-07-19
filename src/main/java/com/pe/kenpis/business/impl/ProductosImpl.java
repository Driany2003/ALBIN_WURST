package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IProductosService;
import com.pe.kenpis.model.api.productos.ProductosRequest;
import com.pe.kenpis.model.api.productos.ProductosResponse;
import com.pe.kenpis.model.entity.ProductosEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductosImpl implements IProductosService {

  @Override
  public ProductosResponse findById(Integer areaId) {
    return null;
  }

  @Override
  public ProductosResponse create(ProductosRequest request) {
    return null;
  }

  @Override
  public ProductosResponse update(ProductosRequest request) {
    return null;
  }

  @Override
  public ProductosResponse delete(ProductosRequest request) {
    return null;
  }

  private ProductosResponse convertProductosEntityToResponse(ProductosEntity in) {
    ProductosResponse out = new ProductosResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private ProductosEntity convertProductosRequestToEntity(ProductosRequest in) {
    ProductosEntity out = new ProductosEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }

}
