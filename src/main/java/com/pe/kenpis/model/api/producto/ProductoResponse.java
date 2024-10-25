package com.pe.kenpis.model.api.producto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponse {

  private Integer proId;
  private Double proPrecioCosto;
  private Double proPrecioVenta;
  private String proCategoria;
  private Boolean proIsActive;
  private String proDescripcion;
  private Integer padreId;
  private Integer empId;
  private String proImagen;
  private String proImagenLongitud;

}
