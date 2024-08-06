package com.pe.kenpis.model.api.venta;

import com.pe.kenpis.model.api.venta.detalle.VentaDetalleRequest;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VentaRequest {

  private List<VentaDetalleRequest> detallesVentas;
  private Integer venId;
  private Date venFecha;
  private float venTotal;
  private Integer empresaId;
  private Integer clienteId;
  private String alias;
  private String venTipoPago;
  private Integer usuarioId;

}
