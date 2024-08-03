package com.pe.kenpis.model.api.venta.estado;

import com.pe.kenpis.model.api.producto.ProductoDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class VentasEstadoDTO {

  private String clienteNombre;
  private Integer venEstadoId;
  private List<ProductoDTO> productos;

  public VentasEstadoDTO(String clienteNombre, Integer venEstadoId, List<ProductoDTO> productos) {
    this.clienteNombre = clienteNombre;
    this.venEstadoId = venEstadoId;
    this.productos = productos;
  }

}
