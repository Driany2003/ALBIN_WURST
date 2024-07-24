package com.pe.kenpis.business;

import com.pe.kenpis.model.api.producto.ProductoRequest;
import com.pe.kenpis.model.api.producto.ProductoResponse;

import java.util.List;

public interface IProductoService {

  ProductoResponse findById(Integer id);

  ProductoResponse create(ProductoRequest request);

  ProductoResponse update(ProductoRequest request);

  ProductoResponse delete(ProductoRequest request);

  List<ProductoResponse> findAll();

  List<ProductoResponse> findAllByProCategoria(String dato);

}
