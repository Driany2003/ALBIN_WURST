package com.pe.kenpis.model.api.ventas;

import com.pe.kenpis.model.api.ventas.detalle.DetalleVentaRequest;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VentasRequest {
  private List<DetalleVentaRequest> detallesVentas;
  private Integer venId;
  private Date venFecha;
  private float venTotal;
  private Integer empresaId;
  private Integer clienteId;
  private String venTipoPago ;

}
