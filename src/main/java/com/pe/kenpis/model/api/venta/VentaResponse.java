package com.pe.kenpis.model.api.venta;

import com.pe.kenpis.model.api.venta.detalle.VentaDetalleResponse;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VentaResponse {

  private List<VentaDetalleResponse> detallesVentas;
  private Integer venId;
  private Date venFecha;
  private float venTotal;
  private Integer empresaId;
  private Integer clienteId;
  private String venTipoPago ;

}
