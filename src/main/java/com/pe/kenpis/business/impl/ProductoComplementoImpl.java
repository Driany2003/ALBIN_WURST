package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IProductoComplementoService;
import com.pe.kenpis.model.api.empresa.EmpresaRequest;
import com.pe.kenpis.model.api.producto.complementos.*;
import com.pe.kenpis.model.entity.EmpresaEntity;
import com.pe.kenpis.model.entity.ProductoComplemetosEntity;
import com.pe.kenpis.repository.ProductoComplementosRepository;
import com.pe.kenpis.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductoComplementoImpl implements IProductoComplementoService {

  private final ProductoComplementosRepository productoComplementosRepository;

  @Override
  public List<ProductoComplementoResponseDTO> findAll() {
    List<Map<String, Object>> listaComplementos = productoComplementosRepository.SP_LISTA_COMPLEMENTOS_PADRE_POR_EMPRESA();
    return listaComplementos.stream().map(result -> new ProductoComplementoResponseDTO((Integer) result.get("emp_id"), (String) result.get("emp_razon_social"), (Integer) result.get("pro_comp_id"), (String) result.get("pro_comp_nombre"), (Double) result.get("pro_comp_precio"), (Integer) result.get("pro_comp_id_padre"))).collect(Collectors.toList());
  }

  //listar complementos para registrar un producto
  public List<ProductoComplementoResponseDTO> obtenerComplementosConSubcomplementosPorEmpresa(Integer empId) {
    List<Object[]> resultados = productoComplementosRepository.findAllWithSubcomplementosByEmpresa(empId);
    return resultados.stream()
        .map(r -> new ProductoComplementoResponseDTO(
            ((Number) r[0]).intValue(),
            (String) r[1],
            (String) r[2]
        ))
        .collect(Collectors.toList());
  }

  @Override
  public ProductoComplementosResponse findById(Integer id) {
    log.info("Implements :: findById :: " + id);
    return productoComplementosRepository.findById(id).map(this::convertEntityToResponse).orElse(new ProductoComplementosResponse());
  }

  @Override
  public List<ProductoComplementoResponseDTO> findDetallesByIdPadre(Integer idPadre, Integer empId) {
    // Obtener detalles usando la consulta nativa en el repositorio
    List<Map<String, Object>> detallesMap = productoComplementosRepository.findDetallesByIdPadreAndEmpId(idPadre, empId);
    return detallesMap.stream().map(result -> new ProductoComplementoResponseDTO((Integer) result.get("proCompId"), (String) result.get("proCompNombre"), (Double) result.get("proCompPrecio"))).collect(Collectors.toList());
  }

  //crear Complemento Padre
  @Override
  @Transactional
  public ProductoComplementoResponseDTO createComplemento(ProductoComplementoRequestRegistrarDTO request) {
    ProductoComplemetosEntity complementoPadre = new ProductoComplemetosEntity();
    complementoPadre.setProCompNombre(request.getProCompNombre());
    complementoPadre.setEmpId(request.getEmpId());
    complementoPadre.setProCompPrecio(0.0);
    complementoPadre.setProCompIdPadre(0);
    complementoPadre = productoComplementosRepository.save(complementoPadre);

    if (complementoPadre.getProCompId() == null) {
      throw new RuntimeException("Error al generar el ID para el complemento padre");
    }
    ProductoComplemetosEntity finalComplementoPadre = complementoPadre;
    List<ProductoComplemetosEntity> detalles = request.getDetalles().stream().map(detalleRequest -> {
      ProductoComplemetosEntity detalle = new ProductoComplemetosEntity();
      detalle.setProCompNombre(detalleRequest.getProCompNombre());
      detalle.setProCompPrecio(detalleRequest.getProCompPrecio());
      detalle.setProCompIdPadre(finalComplementoPadre.getProCompId());
      detalle.setEmpId(finalComplementoPadre.getEmpId());
      return productoComplementosRepository.save(detalle);
    }).collect(Collectors.toList());

    return convertEntityToResponse(complementoPadre, detalles);
  }

  private ProductoComplementoResponseDTO convertEntityToResponse(ProductoComplemetosEntity complementoPadre, List<ProductoComplemetosEntity> detalles) {
    // Convertir los detalles a una lista de DTOs
    List<ProductoComplementoResponseDTO.DetalleResponse> detallesDTO = detalles.stream().map(detalle -> {
      ProductoComplementoResponseDTO.DetalleResponse detalleResponse = new ProductoComplementoResponseDTO.DetalleResponse();
      detalleResponse.setProCompDetalleId(detalle.getProCompId());
      detalleResponse.setProCompNombre(detalle.getProCompNombre());
      detalleResponse.setProCompPrecio(detalle.getProCompPrecio());
      return detalleResponse;
    }).collect(Collectors.toList());

    return new ProductoComplementoResponseDTO(complementoPadre.getEmpId(), complementoPadre.getProCompId(), complementoPadre.getProCompNombre(), complementoPadre.getProCompPrecio(), complementoPadre.getProCompIdPadre(), detallesDTO);
  }

  //crear Complemento Hijo
  @Override
  public ProductoComplementosResponse create(ProductoComplementosRequest request) {
    ProductoComplementosResponse complemento = productoComplementosRepository.findById(request.getProCompIdPadre()).map(this::convertEntityToResponse).orElse(new ProductoComplementosResponse());
    ProductoComplemetosEntity nuevoComplementoHijo = new ProductoComplemetosEntity();
    nuevoComplementoHijo.setEmpId(complemento.getEmpId());
    nuevoComplementoHijo.setProCompIdPadre(request.getProCompIdPadre());
    nuevoComplementoHijo.setProCompNombre(request.getProCompNombre());
    nuevoComplementoHijo.setProCompPrecio(request.getProCompPrecio());

    return convertEntityToResponse(productoComplementosRepository.save(nuevoComplementoHijo));
  }

  @Override
  @Transactional
  public ProductoComplementosResponse update(ProductoComplementoRequestUpdateDTO request) {
    ProductoComplementosResponse complemento = productoComplementosRepository.findById(request.getProCompId()).map(this::convertEntityToResponse).orElse(new ProductoComplementosResponse());
    if (complemento.getProCompId() == null) {
      return new ProductoComplementosResponse();
    } else {
      productoComplementosRepository.actualizarNombreYPrecio(request.getProCompId(), request.getProCompNombre(), request.getProCompPrecio());
    }
    return convertEntityToResponse(productoComplementosRepository.findById(request.getProCompId()).orElse(null));
  }

  @Override
  public ProductoComplementosResponse updatePadre(ProductoComplementoRequestUpdateDTO request) {
    // Verificar si el complemento padre existe
    ProductoComplementosResponse complemento = productoComplementosRepository.findById(request.getProCompId()).map(this::convertEntityToResponse).orElse(new ProductoComplementosResponse());
    if (complemento.getProCompId() == null) {
      return new ProductoComplementosResponse();
    } else {
     productoComplementosRepository.actualizarNombreComplementoPadre(request.getProCompId(),request.getProCompNombre());
      return convertEntityToResponse(productoComplementosRepository.findById(request.getProCompId()).orElse(null));
    }
  }


  //eliminar complemento "Padre"
  @Override
  @Transactional
  public ProductoComplementosResponse deleteComplemento(Integer id) {
    log.debug("Implements :: delete :: ID -> {}", id);
    Optional<ProductoComplemetosEntity> complementoEliminar = productoComplementosRepository.findById(id);
    if (complementoEliminar.isPresent()) {
      productoComplementosRepository.deleteComplementoAndDetails(id);
      ProductoComplemetosEntity eliminarComplemento = complementoEliminar.get();
      return convertEntityToResponse(eliminarComplemento);
    } else {
      return new ProductoComplementosResponse();
    }
  }

  //eliminar complemento "Hijo"
  @Override
  public ProductoComplementosResponse delete(Integer id) {
    log.debug("Implements :: delete :: ID -> {}", id);
    Optional<ProductoComplemetosEntity> complementoEliminar = productoComplementosRepository.findById(id);
    if (complementoEliminar.isPresent()) {
      productoComplementosRepository.eliminarPorId(id);
      ProductoComplemetosEntity eliminarComplemento = complementoEliminar.get();
      return convertEntityToResponse(eliminarComplemento);
    } else {
      return new ProductoComplementosResponse();
    }
  }

  private ProductoComplementosResponse convertEntityToResponse(ProductoComplemetosEntity in) {
    ProductoComplementosResponse out = new ProductoComplementosResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }
  private ProductoComplemetosEntity convertRequestToEntity(ProductoComplementoRequestUpdateDTO in) {
    ProductoComplemetosEntity out = new ProductoComplemetosEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }
}

