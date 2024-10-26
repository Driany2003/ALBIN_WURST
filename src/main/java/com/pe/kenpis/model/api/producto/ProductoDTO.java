package com.pe.kenpis.model.api.producto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductoDTO extends ProductoResponse {

  private String proDescripcion;
  private Integer venDetCantidad;
  private Double proPrecio;

  public ProductoDTO(String proDescripcion, Integer venDetCantidad, Double proPrecio) {
    this.proDescripcion = proDescripcion;
    this.venDetCantidad = venDetCantidad;
    this.proPrecio = proPrecio;
  }

}
