package com.pe.kenpis.model.api.producto.inventario;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductoComplementoResponse {

  private Integer proInvId;
  private Integer productoId;
  private Integer proInvStockInicial;
  private Integer proInvStockVentas;
  private Integer proInvFechaCreacion;
  //
  private Double proPrecioCosto;
  private Double proPrecioVenta;
  private String proCategoria;
  private String proDescripcion;
  private Integer proInvStockActual;
  private Integer empId;
  private String proImagen;
  private Boolean proIsActive;

}
