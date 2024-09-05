package com.pe.kenpis.model.api.producto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductoListDTO {

  private Double proPrecio;
  private Boolean proIsActive;
  private String proDescripcion;
  private String proImagen;

  public ProductoListDTO(String proDescripcion, String proImagen, Boolean proIsActive, Double proPrecio) {
    this.proDescripcion = proDescripcion;
    this.proImagen = proImagen;
    this.proIsActive = proIsActive;
    this.proPrecio = proPrecio;

  }
}
