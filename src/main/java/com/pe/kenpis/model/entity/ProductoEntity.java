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

  @Column(name = "pro_categoria")
  private String proCategoria;

  @Column(name = "pro_precio")
  private Double proPrecio;

  @Column(name = "pro_descripcion")
  private String proDescripcion;

  @Column(name = "pro_is_active")
  private Boolean proIsActive;

  @Column(name = "padre_id")
  private Integer padreId;

  @Column(name = "emp_id")
  private Integer empId;

  @Column(name = "pro_imagen")
  private String proImagen;

  @Column(name = "pro_imagen_longitud")
  private String proImagenLongitud;

}
