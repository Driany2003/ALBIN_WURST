package com.pe.kenpis.model.api.ventas.detalle;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaResponse {

  private Integer detvenId;
  private Integer detvenCantidad;
  private double detvenSubtotal;
  private Integer venId;
  private Integer proId;

}
