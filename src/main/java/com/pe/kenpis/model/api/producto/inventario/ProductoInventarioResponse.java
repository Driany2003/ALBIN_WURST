package com.pe.kenpis.model.api.producto.inventario;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductoInventarioResponse {

  private Integer proInvId;
  private Integer productoId;
  private Integer proInvStockInicial;
  private Integer proInvStockVentas;
  private Date proInvFechaCreacion;
  //
  private Double proPrecio;
  private String proDescripcion;
  private Integer empId;
  private String proImagen;
  private Boolean proIsActive;

}
