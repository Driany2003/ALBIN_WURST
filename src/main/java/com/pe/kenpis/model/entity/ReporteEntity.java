package com.pe.kenpis.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "T_REPORTE")
public class ReporteEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rep_id")
  private Integer repId;

  @Column(name = "caja_id")
  private Integer cajaId;

  @Column(name = "rep_fecha_creacion")
  private Date repFechaCreacion;

  @Column(name = "sucursal_id")
  private Integer sucursalId;

  @Column(name = "rep_is_active")
  private boolean repIsActive;

  @Column(name = "emp_id")
  private Integer empId;

}
