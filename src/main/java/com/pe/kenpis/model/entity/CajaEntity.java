package com.pe.kenpis.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_CAJA")
public class CajaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "caja_id")
  private Integer cajaId;

  @Column(name = "caja_fecha_apertura")
  private LocalDateTime cajaFechaApertura;

  @Column(name = "caja_fecha_cierre")
  private LocalDateTime cajaFechaCierre;

  @Column(name = "caja_monto_inicial")
  private float cajaMontoInicial;

  @Column(name = "caja_monto_final")
  private float cajaMontoFinal;

  @Column(name = "caja_usuario_apertura")
  private String cajaUsuarioApertura;

  @Column(name = "caja_usuario_cierre")
  private String cajaUsuarioCierre;

  @Column(name = "emp_id")
  private Integer empId;

  @Column(name = "caja_estado")
  private Boolean cajaEstado;


}