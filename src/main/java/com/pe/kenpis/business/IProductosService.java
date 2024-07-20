package com.pe.kenpis.business;

import com.pe.kenpis.model.api.productos.ProductosRequest;
import com.pe.kenpis.model.api.productos.ProductosResponse;

import java.util.List;

public interface IProductosService {

  ProductosResponse findById(Integer areaId);

  ProductosResponse create(ProductosRequest request);

  ProductosResponse update(ProductosRequest request);

  ProductosResponse delete(ProductosRequest request);

  List<ProductosResponse> findAll();

  List<ProductosResponse> findAllByTipo(String tipo);
}
