package com.pe.kenpis.business;

import com.pe.kenpis.model.api.empresa.EmpresaDTO;
import com.pe.kenpis.model.api.producto.ProductoListDTO;
import com.pe.kenpis.model.api.producto.ProductoRequest;
import com.pe.kenpis.model.api.producto.ProductoResponse;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface IProductoService {

  ProductoResponse findById(Integer id);

  ProductoResponse create(ProductoRequest request);

  ProductoResponse createCategoria(ProductoRequest request);

  ProductoResponse update(ProductoRequest request);

  ProductoResponse updateStatus(ProductoRequest request);

  ProductoResponse delete(Integer id);

  List<ProductoResponse> findAll();

  List<ProductoListDTO> findActiveProductosWithActive();

  List<ProductoListDTO> findActiveProductosWithActiveEmpresa(Integer empId);

  List<ProductoResponse> getProductosByCategoriaId(Integer categoriaId, Integer empresaId);

  List<ProductoListDTO> getAllCategorias(Integer empId);

  List<ProductoListDTO> getCategoriasbyEmpresa(Integer empId);

}
