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
  private Integer cajaId;
  private Integer empresaId;
  private Integer sucursalId;
  private Integer clienteId;
  private String venAlias;
  private String VenConsideraciones;
  private String venTipoPago;
  private Integer usuarioId;

}
