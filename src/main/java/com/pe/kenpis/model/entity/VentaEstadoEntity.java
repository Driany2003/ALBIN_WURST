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

  @Column(name = "ven_estado")
  private String venEstado;

  @Column(name = "ven_estado_fecha_registrado")
  private Date venEstadoFechaRegistrado;

  @Column(name = "ven_estado_fecha_pagado")
  private Date venEstadoFechaPagado;

  @Column(name = "ven_estado_fecha_en_proceso")
  private Date venEstadoFechaEnProceso;

  @Column(name = "ven_estado_fecha_atendido")
  private Date venEstadoFechaAtendido;

}
