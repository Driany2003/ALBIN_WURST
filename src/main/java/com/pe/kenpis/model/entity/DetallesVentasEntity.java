package com.pe.kenpis.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "T_DETALLE_VENTA")
public class DetallesVentasEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "detven_id")
  private Integer detvenId;

  @Column(name = "detven_cantidad")
  private Integer detvenCantidad;

  @Column(name = "detven_subtotal")
  private double detvenSubTotal;

  @Column(name = "venta_id")
  private Integer ventaId;

  @Column(name = "producto_id")
  private Integer productoId;

}
