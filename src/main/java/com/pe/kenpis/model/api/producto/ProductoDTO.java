package com.pe.kenpis.model.api.producto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductoDTO extends ProductoResponse {

  private String proCategoria;
  private Integer venDetCantidad;

  public ProductoDTO(String proCategoria, Integer venDetCantidad) {
    this.proCategoria = proCategoria;
    this.venDetCantidad = venDetCantidad;
  }
}
