package com.pe.kenpis.model.api.producto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequest {

  private Integer proId;
  private Float proPrecio;
  private String proCategoria;
  private Boolean proIsActive;
  private String proDescripcion;
  private Integer padreId;
  private Integer empId;

}
