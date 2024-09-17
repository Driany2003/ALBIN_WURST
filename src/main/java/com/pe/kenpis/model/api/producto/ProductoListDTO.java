package com.pe.kenpis.model.api.producto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductoListDTO {
  private Integer proId;
  private Integer empId;
  private String proCategoria;
  private Double proPrecio;
  private Boolean proIsActive;
  private String proDescripcion;
  private String proImagen;

  public ProductoListDTO(Integer proId,String proDescripcion, String proImagen, Boolean proIsActive, Double proPrecio) {
    this.proId = proId;
    this.proDescripcion = proDescripcion;
    this.proImagen = proImagen;
    this.proIsActive = proIsActive;
    this.proPrecio = proPrecio;

  }

  public ProductoListDTO(Integer proId, Integer empId, String proCategoria) {
    this.proId = proId;
    this.empId = empId;
    this.proCategoria = proCategoria;
  }

  public ProductoListDTO(Integer proId, Integer empId,String proDescripcion ,String proCategoria,String proImagen, Boolean proIsActive, Double proPrecio) {
    this.proId = proId;
    this.empId = empId;
    this.proCategoria = proCategoria;
    this.proDescripcion = proDescripcion;
    this.proImagen = proImagen;
    this.proIsActive = proIsActive;
    this.proPrecio = proPrecio;
  }
}
