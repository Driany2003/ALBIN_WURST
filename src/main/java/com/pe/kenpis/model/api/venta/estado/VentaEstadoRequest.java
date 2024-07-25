package com.pe.kenpis.model.api.venta.estado;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VentaEstadoRequest {

  private Integer venEstadoId;
  private Integer ventaId;
  private String venEstado;
  private Date venEstadoFechaRegistrado;
  private Date venEstadoFechaPagado;
  private Date venEstadoFechaEnProceso;
  private Date venEstadoFechaAtendido;
  private Date venEstadoFechaDescartado;

}
