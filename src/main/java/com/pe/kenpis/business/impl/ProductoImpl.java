package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IProductoService;
import com.pe.kenpis.model.api.producto.ProductoRequest;
import com.pe.kenpis.model.api.producto.ProductoResponse;
import com.pe.kenpis.model.entity.ProductoEntity;
import com.pe.kenpis.repository.ProductoRepository;
import com.pe.kenpis.util.funciones.FxComunes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductoImpl implements IProductoService {

  private final ProductoRepository repository;

  @Override
  public List<ProductoResponse> findAll() {
    log.info("Implements :: findAll");
    return repository.findAll().stream().map(this::convertEntityToResponse).collect(Collectors.toList());
  }

  @Override
  public ProductoResponse findById(Integer id) {
    log.info("Implements :: findById :: " + id);
    return repository.findById(id).map(this::convertEntityToResponse).orElse(new ProductoResponse());
  }

  @Override
  public ProductoResponse create(ProductoRequest request) {
    log.debug("Implements :: create :: Inicio");
    request.setProIsActive(true);
    FxComunes.printJson("ProductoRequest",request);
    return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
  }

  @Override
  public ProductoResponse update(ProductoRequest request) {
    ProductoResponse res = repository.findById(request.getProId()).map(this::convertEntityToResponse).orElse(new ProductoResponse());
    if (res.getProId() == null) {
      return new ProductoResponse();
    } else {
      return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
    }
  }

  @Override
  public ProductoResponse delete(Integer id) {
    log.debug("Implements :: delete :: ID -> {}", id);
    ProductoResponse res = repository.findById(id).map(this::convertEntityToResponse).orElse(new ProductoResponse());
    res.setProIsActive(false);
    Optional<ProductoEntity> ent = repository.findById(res.getProId());
    if (ent.isPresent()) {
      return convertEntityToResponse(repository.save(convertRequestToEntity(convertResponseToRequest(res))));
    } else {
      return new ProductoResponse();
    }
  }

  public List<ProductoResponse> findAllByProCategoria(String dato) {
    if (dato == null || dato.isEmpty()) {
      throw new IllegalArgumentException("El tipo no puede ser nulo o vacío");
    }
    List<ProductoEntity> productos = repository.findAllByProCategoria(dato);
    return productos.stream().map(this::convertEntityToResponse).collect(Collectors.toList());
  }

  @Override
  public List<String> findAllCategories() {
    return repository.findDistinctCategories();
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
