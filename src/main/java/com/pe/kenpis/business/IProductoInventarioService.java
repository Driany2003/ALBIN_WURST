package com.pe.kenpis.business;

import com.pe.kenpis.model.api.producto.inventario.ProductoProductoRequest;
import com.pe.kenpis.model.api.producto.inventario.ProductoComplementoResponse;

import java.util.List;

public interface IProductoInventarioService {

  List<ProductoComplementoResponse> findAll();

  boolean verificarStockSuficiente(Integer productoId, Integer cantidadRequerida);

  ProductoComplementoResponse actualizarStock(Integer productoId, Integer cantidadVendida);

  ProductoComplementoResponse findById(Integer id);

  ProductoComplementoResponse create(ProductoProductoRequest request);

  ProductoComplementoResponse update(ProductoProductoRequest request);

  ProductoComplementoResponse delete(Integer id);

}