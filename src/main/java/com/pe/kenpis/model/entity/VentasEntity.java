package com.pe.kenpis.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "T_VENTA")
@Entity
public class VentasEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ven_id")
  private Integer venId;

  @Column(name = "ven_fecha")
  private Date venFecha;

  @Column(name = "ven_total")
  private float venTotal;



}
