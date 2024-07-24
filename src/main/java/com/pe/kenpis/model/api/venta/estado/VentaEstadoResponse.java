package com.pe.kenpis.model.api.venta.estado;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VentaEstadoResponse {

  private Integer venEstadoId;
  private Integer ventaId;
  private Boolean venEstadoRegistrado;
  private Date venEstadoRegistradoFecha;
  private Boolean venEstadoPagado;
  private Date venEstadoPagadoFecha;
  private Boolean venEstadoEnProceso;
  private Date venEstadoEnProcesoFecha;
  private Boolean venEstadoAtendido;
  private Date venEstadoAtendidoFecha;

}
