package com.pe.kenpis.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "T_VENTA_ESTADO_DEL_DIA")
@Entity
public class VentaEstadoDelDiaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ven_estado_del_dia_Id")
  private Integer venEstadoDelDiaId;

  @Column(name = "ven_estado_del_dia_registrado")
  private Integer REGISTRADO;

  @Column(name = "ven_estado_del_dia_pagado")
  private Integer PAGADI;

  @Column(name = "ven_estado_del_dia_enproceso")
  private Integer EN_PROCESO;

  @Column(name = "ven_estado_del_dia_atendido")
  private Integer ATENDIDO;

  @Column(name = "ven_estado_del_dia_descartado")
  private Integer DESCARTADO;

}
