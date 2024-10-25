package com.pe.kenpis.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "T_PRODUCTO_COMPLEMENTOS")
public class ProductoComplemetosEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "pro_comp_id")
  private Integer proCompId;

  @Column(name = "emp_id")
  private Integer empId;

  @Column(name = "pro_comp_nombre")
  private String proCompNombre;

  @Column(name = "pro_comp_precio")
  private Double proCompPrecio;

  @Column(name = "pro_comp_id_padre")
  private Integer proCompIdPadre;

}
