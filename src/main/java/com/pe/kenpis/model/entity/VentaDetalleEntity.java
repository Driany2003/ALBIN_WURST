package com.pe.kenpis.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "T_VENTA_DETALLE")
public class VentaDetalleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ven_det_id")
  private Integer venDetId;

  @Column(name = "venta_id")
  private Integer ventaId;

  @Column(name = "producto_id")
  private Integer productoId;

  @Column(name = "ven_det_cantidad")
  private Integer venDetCantidad;

  @Column(name = "ven_det_subtotal")
  private float venDetSubtotal;

  @Column(name = "ven_det_precio")
  private float venDetPrecio;

}