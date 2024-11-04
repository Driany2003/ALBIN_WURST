package com.pe.kenpis.business;

import com.pe.kenpis.model.api.producto.complementos.*;

import java.util.List;

public interface IProductoComplementoService {

   List<ProductoComplementoResponseDTO> findAll(Integer id);

  List<ProductoComplementoResponseDTO> obtenerComplementosConSubcomplementosPorEmpresa(Integer empId);

  List<ProductoComplementoResponseDTO> obtenerComplementosConSubcomplementos(Integer productId, Integer empId);

  List<ProductoComplementoResponseDTO> findDetallesByIdPadre(Integer idPadre, Integer empId);

  ProductoComplementosResponse findById(Integer id);

  ProductoComplementosResponse create(ProductoComplementosRequest request);

  ProductoComplementoResponseDTO createComplemento(ProductoComplementoRequestRegistrarDTO request);

  ProductoComplementosResponse update(ProductoComplementoRequestUpdateDTO request);

  ProductoComplementosResponse updatePadre(ProductoComplementoRequestUpdateDTO request);

  ProductoComplementosResponse deleteComplemento(Integer id);

  ProductoComplementosResponse delete(Integer id);

}