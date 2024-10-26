package com.pe.kenpis.model.api.producto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequest {

  private Integer proId;
  private Integer empId;
  private Integer padreId;
  private String proCategoria;
  private Double proPrecioCosto;
  private Double proPrecioVenta;
  private String proDescripcion;
  private String proImagen;
  private String proImagenLongitud;
  private String proInventarioRelacion;
  private String proComplementos;
  private Boolean proIsActive;

}
