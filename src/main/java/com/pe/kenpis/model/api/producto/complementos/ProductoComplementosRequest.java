package com.pe.kenpis.model.api.producto.complementos;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductoComplementosRequest {

  private Integer proCompId;
  private Integer empId;
  private String proCompNombre;
  private Double proCompPrecio;
  private Integer proCompIdPadre;

}
