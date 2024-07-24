package com.pe.kenpis.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "T_PRODUCTO_INVENTARIO")
public class ProductoInventarioEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "pro_inv_id")
  private Integer proInvId;

  @Column(name = "producto_id")
  private Integer productoId;

  @Column(name = "pro_inv_stock_inicial")
  private Integer proInvStockInicial;

  @Column(name = "pro_inv_stock_ventas")
  private Integer proInvStockVentas;

  @Column(name = "pro_inv_fecha_creacion")
  private Date proInvFechaCreacion;

}
