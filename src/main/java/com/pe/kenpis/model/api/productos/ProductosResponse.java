package com.pe.kenpis.model.api.productos;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductosResponse {

  private Integer proId;
  private String proNombre;
  private String proTipo;
  private float proPrecio;

}
