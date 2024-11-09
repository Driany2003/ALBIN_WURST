package com.pe.kenpis.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "T_EMPRESA_CAJA")
public class EmpresaCajaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "caja_id")
  private Integer cajaId;

  @Column(name = "caja_fecha_apertura")
  private Date cajaFechaApertura;

  @Column(name = "caja_fecha_cierre")
  private Date cajaFechaCierre;

  @Column(name = "caja_monto_inicial")
  private float cajaMontoInicial;

  @Column(name = "caja_monto_final")
  private float cajaMontoFinal;

  @Column(name = "caja_usuario_apertura")
  private String cajaUsuarioApertura;

  @Column(name = "caja_usuario_cierre")
  private String cajaUsuarioCierre;

  @Column(name = "emp_padre_id")
  private Integer empPadreId;

  @Column(name = "caja_estado")
  private Boolean cajaEstado;

  @Column(name = "caja_asignada")
  private String cajaAsignada;

}