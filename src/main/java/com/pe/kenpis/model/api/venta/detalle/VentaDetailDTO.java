package com.pe.kenpis.model.api.venta.detalle;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VentaDetailDTO {
  private Integer ventaId;
  private String productoTipo;
  private Integer cantidad;

}
