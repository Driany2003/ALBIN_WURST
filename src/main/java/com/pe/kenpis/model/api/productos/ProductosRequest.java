package com.pe.kenpis.model.api.productos;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductosRequest {

  private Integer proId;
  private String proTipo;
  private float proPrecio;
  private String proCategoria;
  private Boolean proIsActive;

}
