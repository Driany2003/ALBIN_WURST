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
  private Double proPrecioCosto;
  private Boolean proIsActive;
  private String proDescripcion;
  private String proImagen;
  private Double proPrecioVenta;

  public ProductoListDTO(Integer proId,String proDescripcion, String proImagen, Boolean proIsActive, Double proPrecioCosto ,Double proPrecioVenta) {
    this.proId = proId;
    this.proDescripcion = proDescripcion;
    this.proImagen = proImagen;
    this.proIsActive = proIsActive;
    this.proPrecioCosto = proPrecioCosto;
    this.proPrecioVenta = proPrecioVenta;
  }
//VISTA DE PRODUCTO
  public ProductoListDTO(Integer proId, Integer empId, String proCategoria) {
    this.proId = proId;
    this.empId = empId;
    this.proCategoria = proCategoria;
  }

  //MODULO de VENTA
  public ProductoListDTO(Integer proId, Integer empId,String proDescripcion ,String proCategoria,String proImagen, Boolean proIsActive) {
    this.proId = proId;
    this.empId = empId;
    this.proCategoria = proCategoria;
    this.proDescripcion = proDescripcion;
    this.proImagen = proImagen;
    this.proIsActive = proIsActive;
  }
}
