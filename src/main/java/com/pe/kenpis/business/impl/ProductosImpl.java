package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IProductosService;
import com.pe.kenpis.model.api.productos.ProductosRequest;
import com.pe.kenpis.model.api.productos.ProductosResponse;
import com.pe.kenpis.model.entity.ProductosEntity;
import com.pe.kenpis.repository.ProductosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductosImpl implements IProductosService {


  private final ProductosRepository productosRepository;

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

  @Override
  public List<ProductosResponse> findAll() {
    return null;
  }


  public List<ProductosResponse> findAllByTipo(String tipo) {
    if (tipo == null || tipo.isEmpty()) {
      throw new IllegalArgumentException("El tipo no puede ser nulo o vacío");
    }
    List<ProductosEntity> productos = productosRepository.findByProCategoria(tipo);
    return productos.stream()
        .map(this::convertProductosEntityToResponse)
        .collect(Collectors.toList());
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
