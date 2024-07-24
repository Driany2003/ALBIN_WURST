package com.pe.kenpis.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "T_EMPRESA")
public class EmpresaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "emp_id")
  private Integer empId;

  @Column(name = "emp_documento_tipo")
  private String empDocumentoTipo;

  @Column(name = "emp_documento_numero")
  private String empDocumentoNumero;

  @Column(name = "emp_razon_social")
  private String empRazonSocial;

  @Column(name = "emp_responsable")
  private String empResponsable;

  @Column(name = "emp_telefono")
  private String empTelefono;

  @Column(name = "emp_imagen_logo")
  private Long empImageLogo;

  @Column(name = "emp_nombre_comercial")
  private String empNombreComercial;

  @Column(name = "emp_fecha_contrato_inicio")
  private Date empFechaContratoInicio;

  @Column(name = "emp_fecha_contrato_fin")
  private Date empFechaContratoFin;

  @Column(name = "emp_fecha_creacion")
  private LocalDateTime empFechaCreacion;

  @Column(name = "emp_is_active")
  private Integer empIsActive;

  @Column(name = "emp_qr_yape")
  private Long empQrYape;

  @Column(name = "emp_qr_plin")
  private Long empQrPlin;

  @Column(name = "emp_qr_Pagos")
  private Long empQrPagos;






}
