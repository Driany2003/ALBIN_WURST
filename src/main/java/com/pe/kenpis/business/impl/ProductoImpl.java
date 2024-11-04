package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IProductoService;
import com.pe.kenpis.model.api.producto.ProductoListDTO;
import com.pe.kenpis.model.api.producto.ProductoRequest;
import com.pe.kenpis.model.api.producto.ProductoResponse;
import com.pe.kenpis.model.api.producto.complementos.ProductoComplementoResponseDTO;
import com.pe.kenpis.model.entity.ProductoEntity;
import com.pe.kenpis.model.entity.ProductoInventarioEntity;
import com.pe.kenpis.repository.ProductoComplementosRepository;
import com.pe.kenpis.repository.ProductoInventarioRepository;
import com.pe.kenpis.repository.ProductoRepository;
import com.pe.kenpis.util.funciones.Java8Base64Image;
import com.pe.kenpis.util.variables.Constantes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductoImpl implements IProductoService {

  private final ProductoRepository repository;
  private final ProductoComplementosRepository productoComplementosRepository;
  private final ProductoInventarioRepository inventarioRepository;

  @Override
  public List<ProductoResponse> findAll() {
    log.info("Implements :: findAll");
    return repository.findAll().stream().map(this::convertEntityToResponse).collect(Collectors.toList());
  }

  @Override
  public List<ProductoListDTO> findActiveProductosWithActive() {
    log.info("Implements :: findActiveProductosWithActive");
    return repository.findActiveProductosWithActive().stream().map(this::convertToProductoDTO).collect(Collectors.toList());
  }

  private ProductoListDTO convertToProductoDTO(Map<String, Object> map) {
    return new ProductoListDTO((Integer) map.get("proId"), (String) map.get("proDescripcion"), (String) map.get("proImagen"), (Boolean) map.get("proIsActive"), (Double) map.get("proPrecioCosto"), (Double) map.get("proPrecioVenta"));
  }

  @Override
  public List<ProductoListDTO> findActiveProductosWithActiveEmpresa(Integer empId) {
        log.info("llegue"+ empId);
    log.info("Implements :: findActiveProductosWithActiveEmpresa");
    return repository.findActiveProductosWithActiveEmpresa(empId).stream().map(this::convertToProductoDTOEmpresa).collect(Collectors.toList());
  }

  private ProductoListDTO convertToProductoDTOEmpresa(Map<String, Object> map) {
    return new ProductoListDTO((Integer) map.get("empId"),(Integer) map.get("proId"), (String) map.get("proDescripcion"), (String) map.get("proImagen"), (Boolean) map.get("proIsActive"), (Double) map.get("proPrecioCosto"), (Double) map.get("proPrecioVenta"));
  }

  @Override
  public ProductoResponse findById(Integer id) {
    log.info("Implements :: findById :: " + id);
    return repository.findById(id).map(this::convertEntityToResponse).orElse(new ProductoResponse());
  }

  @Override
  public ProductoResponse create(ProductoRequest request) {
    log.debug("Implements :: create :: Inicio");
    ProductoEntity producto = convertRequestToEntity(request);
    producto.setProIsActive(true);

    ProductoEntity savedProducto = repository.save(producto);

    ProductoInventarioEntity inventario = new ProductoInventarioEntity();
    inventario.setProductoId(savedProducto.getProId());
    inventario.setProInvStockInicial(0);
    inventario.setProInvStockVentas(0);
    inventario.setProInvFechaCreacion(new Date());

    inventarioRepository.save(inventario);

    return convertEntityToResponse(savedProducto);
  }

  @Override
  public ProductoResponse createCategoria(ProductoRequest request) {
    log.debug("Implements :: create Categoria :: Inicio");
    ProductoEntity producto = convertRequestToEntity(request);
    producto.setEmpId(request.getEmpId());
    producto.setPadreId(0);
    producto.setProPrecioCosto(0.0);
    producto.setProPrecioVenta(0.0);
    producto.setProDescripcion(" Categoria " +  request.getProCategoria());
    producto.setProIsActive(true);
    producto.setProImagenLongitud("Imagen Longitud");

    ProductoEntity savedProducto = repository.save(producto);


    return convertEntityToResponse(savedProducto);
  }

  @Override
  public ProductoResponse update(ProductoRequest request) {
    ProductoResponse res = repository.findById(request.getProId()).map(this::convertEntityToResponse).orElse(new ProductoResponse());
    if (res.getProId() == null) {
      return new ProductoResponse();
    } else {
      request.setPadreId(res.getPadreId());
      request.setEmpId(res.getEmpId());
      request.setProIsActive(res.getProIsActive());
      res.setProImagen(request.getProImagen());
      request.setProImagenLongitud(res.getProImagenLongitud());
      return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
    }
  }

  public ProductoResponse updateStatus(ProductoRequest request) {
    ProductoResponse res = repository.findById(request.getProId()).map(this::convertEntityToResponse).orElse(new ProductoResponse());
    if (res.getProId() == null) {
      return new ProductoResponse();
    } else {
      request.setProCategoria(res.getProCategoria());
      request.setProPrecioCosto(res.getProPrecioCosto());
      request.setProPrecioVenta(res.getProPrecioVenta());
      request.setProDescripcion(res.getProDescripcion());
      request.setPadreId(res.getPadreId());
      request.setEmpId(res.getEmpId());
      request.setProImagen(res.getProImagen());
      request.setProImagenLongitud(res.getProImagenLongitud());
      return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
    }
  }

  @Override
  public ProductoResponse delete(Integer id) {
    log.debug("Implements :: delete :: ID -> {}", id);
    Optional<ProductoEntity> productoEliminar = repository.findById(id);
    if (productoEliminar.isPresent()) {
      repository.deleteById(id);
      ProductoEntity deletedEntity = productoEliminar.get();
      return convertEntityToResponse(deletedEntity);
    } else {
      return new ProductoResponse();
    }
  }

  public List<ProductoResponse> getProductosByCategoriaId(int categoriaId) {
    List<ProductoEntity> productos = repository.findProductosByCategoriaId(categoriaId);
    return productos.stream().map(this::convertEntityToResponse).collect(Collectors.toList());
  }

  //LISTAR EN EL COMBO DE LA VISTA PRODUCTO "LISTAR CATEGORIAS EN EL COMBO DE REGISTRAR"
  public List<ProductoListDTO> getAllCategorias(Integer empId) {
    return repository.findAllCategorias(empId).stream().map(this::convertToListCategoriaDTO).collect(Collectors.toList());
  }

  private ProductoListDTO convertToListCategoriaDTO(Map<String, Object> map) {
    return new ProductoListDTO((Integer) map.get("proId"), (Integer) map.get("empId"), (String) map.get("proCategoria"));
  }

  //ES LISTAR LA CATEGORIA POR EMPRESA EN NUEVA VENTA
  public List<ProductoListDTO> getCategoriasbyEmpresa(Integer empId) {
    return repository.findAllCategoriaByEmpresa(empId).stream().map(this::convertToCategoriabyEmpresaDTO).collect(Collectors.toList());
  }

  private ProductoListDTO convertToCategoriabyEmpresaDTO(Map<String, Object> map) {
    return new ProductoListDTO((Integer) map.get("proId"), (Integer) map.get("empId"), (String) map.get("proCategoria"), (String) map.get("proDescripcion"), (String) map.get("proImagen"), (Boolean) map.get("proIsActive"));
  }

  private ProductoEntity convertRequestToEntity(ProductoRequest in) {
    ProductoEntity out = new ProductoEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private ProductoResponse convertEntityToResponse(ProductoEntity in) {
    ProductoResponse out = new ProductoResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private ProductoRequest convertResponseToRequest(ProductoResponse in) {
    ProductoRequest out = new ProductoRequest();
    BeanUtils.copyProperties(in, out);
    return out;
  }

}
