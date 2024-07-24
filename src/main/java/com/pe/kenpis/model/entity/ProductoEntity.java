package com.pe.kenpis.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "T_PRODUCTO")
public class ProductoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "pro_id")
  private Integer proId;

  @Column(name = "pro_tipo")
  private String proTipo;

  @Column(name = "pro_precio")
  private float proPrecio;

  @Column(name = "pro_categoria")
  private String proCategoria;

  @Column(name = "pro_is_active")
  private Boolean proIsActive;

}
