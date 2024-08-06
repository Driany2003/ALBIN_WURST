package com.pe.kenpis.model.api.producto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductoDTO extends ProductoResponse {

  private String proDescripcion;
  private Integer venDetCantidad;

  public ProductoDTO(String proDescripcion, Integer venDetCantidad) {
    this.proDescripcion = proDescripcion;
    this.venDetCantidad = venDetCantidad;
  }
}
