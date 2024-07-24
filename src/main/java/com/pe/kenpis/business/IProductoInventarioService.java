package com.pe.kenpis.business;

import com.pe.kenpis.model.api.producto.inventario.ProductoInventarioRequest;
import com.pe.kenpis.model.api.producto.inventario.ProductoInventarioResponse;

import java.util.List;

public interface IProductoInventarioService {

  List<ProductoInventarioResponse> findAll();

  ProductoInventarioResponse findById(Integer id);

  ProductoInventarioResponse create(ProductoInventarioRequest request);

  ProductoInventarioResponse update(ProductoInventarioRequest request);

  ProductoInventarioResponse delete(Integer id);

}