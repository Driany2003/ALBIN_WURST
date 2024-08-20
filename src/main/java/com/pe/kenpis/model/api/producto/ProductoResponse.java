package com.pe.kenpis.model.api.producto;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponse {

  private Integer proId;
  private Double proPrecio;
  private String proCategoria;
  private Boolean proIsActive;
  private String proDescripcion;
  private Integer padreId;
  private Integer empId;
  private String proImage;
  private String proImagenLongitud;

}
