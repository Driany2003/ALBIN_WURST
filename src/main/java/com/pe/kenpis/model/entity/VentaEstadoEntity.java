package com.pe.kenpis.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "T_VENTA_ESTADO")
@Entity
public class VentaEstadoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ven_estado_id")
  private Integer venEstadoId;

  @Column(name = "venta_id")
  private Integer ventaId;

  @Column(name = "ven_estado_registrado")
  private Boolean venEstadoRegistrado;

  @Column(name = "ven_estado_registrado_fecha")
  private Date venEstadoRegistradoFecha;

  @Column(name = "ven_estado_pagado")
  private Boolean venEstadoPagado;

  @Column(name = "ven_estado_pagado_fecha")
  private Date venEstadoPagadoFecha;

  @Column(name = "ven_estado_en_proceso")
  private Boolean venEstadoEnProceso;

  @Column(name = "ven_estado_en_proceso_fecha")
  private Date venEstadoEnProcesoFecha;

  @Column(name = "ven_estado_atendido")
  private Boolean venEstadoAtendido;

  @Column(name = "ven_estado_atendido_fecha")
  private Date venEstadoAtendidoFecha;

}
