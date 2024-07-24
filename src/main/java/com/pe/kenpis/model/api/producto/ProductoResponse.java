package com.pe.kenpis.model.api.producto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponse {

  private Integer proId;
  private String proTipo;
  private float proPrecio;
  private String proCategoria;
  private Boolean proIsActive;

}
