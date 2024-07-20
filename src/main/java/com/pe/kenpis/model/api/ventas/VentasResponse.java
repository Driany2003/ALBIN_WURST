package com.pe.kenpis.model.api.ventas;

import com.pe.kenpis.model.api.ventas.detalle.DetalleVentaResponse;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VentasResponse {

  private Integer venId;
  private Date venFecha;
  private float venTotal;
  private List<DetalleVentaResponse> detallesVentas;

}
