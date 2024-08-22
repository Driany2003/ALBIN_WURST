package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IProductoService;
import com.pe.kenpis.model.api.producto.ProductoRequest;
import com.pe.kenpis.model.api.producto.ProductoResponse;
import com.pe.kenpis.model.entity.ProductoEntity;
import com.pe.kenpis.repository.ProductoRepository;
import com.pe.kenpis.util.funciones.FxComunes;
import com.pe.kenpis.util.funciones.Java8Base64Image;
import com.pe.kenpis.util.variables.Constantes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
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
  private String imageOutFoto = Constantes.RUTAS.BASE;

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
    FxComunes.printJson("ProductoRequest", request);
    return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
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
      request.setProPrecio(res.getProPrecio());
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

  public List<ProductoResponse> getAllCategorias() {
    List<ProductoEntity> categorias = repository.findAllCategorias();
    return categorias.stream().map(this::convertEntityToResponse).collect(Collectors.toList());
  }

  private ProductoEntity convertRequestToEntity(ProductoRequest in) {
    ProductoEntity out = new ProductoEntity();
    imageOutFoto = "\\" + in.getProId() + "_foto.jpg";
    if (in.getProImagen().length() > 0) {
      Java8Base64Image.decoder(Base64.encodeBase64String(in.getProImagen().getBytes(Charsets.ISO_8859_1)), imageOutFoto);
      out.setProImagen(Java8Base64Image.encoder(imageOutFoto));
      out.setProImagenLongitud(Java8Base64Image.convertStringToBytes(in.getProImagen()));
    } else {
      out.setProImagen(Constantes.IMAGENES.SIN_FOTO);
      out.setProImagenLongitud(Java8Base64Image.convertStringToBytes(in.getProImagen()));
    }
    Java8Base64Image.eliminaArchivo(imageOutFoto);
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private ProductoResponse convertEntityToResponse(ProductoEntity in) {
    ProductoResponse out = new ProductoResponse();
    imageOutFoto = "\\" + in.getProId() + "_foto.jpg";
    if (in.getProImagen().length() > 0) {
      Java8Base64Image.decoder(Base64.encodeBase64String(in.getProImagen().getBytes(Charsets.ISO_8859_1)), imageOutFoto);
      out.setProImagen(Java8Base64Image.encoder(imageOutFoto));
      out.setProImagenLongitud(Java8Base64Image.convertStringToBytes(in.getProImagen()));
    } else {
      out.setProImagen(Constantes.IMAGENES.SIN_FOTO);
      out.setProImagenLongitud(Java8Base64Image.convertStringToBytes(in.getProImagen()));
    }
    Java8Base64Image.eliminaArchivo(imageOutFoto);
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private ProductoRequest convertResponseToRequest(ProductoResponse in) {
    ProductoRequest out = new ProductoRequest();
    imageOutFoto = "\\" + in.getProId() + "_foto.jpg";
    if (in.getProImagen().length() > 0) {
      Java8Base64Image.decoder(Base64.encodeBase64String(in.getProImagen().getBytes(Charsets.ISO_8859_1)), imageOutFoto);
      out.setProImagen(Java8Base64Image.encoder(imageOutFoto));
      out.setProImagenLongitud(Java8Base64Image.convertStringToBytes(in.getProImagen()));
    } else {
      out.setProImagen(Constantes.IMAGENES.SIN_FOTO);
      out.setProImagenLongitud(Java8Base64Image.convertStringToBytes(in.getProImagen()));
    }
    Java8Base64Image.eliminaArchivo(imageOutFoto);
    BeanUtils.copyProperties(in, out);
    return out;
  }

}
