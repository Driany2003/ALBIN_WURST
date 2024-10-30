package com.pe.kenpis.model.api.producto.complementos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductoComplementoRequestUpdateDTO {
  private Integer proCompId;
  private String proCompNombre;
  private Double proCompPrecio;

}
