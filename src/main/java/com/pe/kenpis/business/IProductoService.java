package com.pe.kenpis.business;

import com.pe.kenpis.model.api.empresa.EmpresaDTO;
import com.pe.kenpis.model.api.producto.ProductoListDTO;
import com.pe.kenpis.model.api.producto.ProductoRequest;
import com.pe.kenpis.model.api.producto.ProductoResponse;

import java.util.List;
import java.util.Map;

public interface IProductoService {

  ProductoResponse findById(Integer id);

  ProductoResponse create(ProductoRequest request);

  ProductoResponse update(ProductoRequest request);

  ProductoResponse updateStatus(ProductoRequest request);

  ProductoResponse delete(Integer id);

  List<ProductoResponse> findAll();

  List<ProductoListDTO> findActiveProductosWithActiveEmpresa();

  List<ProductoResponse> getProductosByCategoriaId(int categoriaId);

  List<ProductoResponse> getAllCategorias();

}
