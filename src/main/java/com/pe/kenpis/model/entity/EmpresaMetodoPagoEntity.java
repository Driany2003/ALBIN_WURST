package com.pe.kenpis.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "T_EMPRESA_METODO_PAGO")
public class EmpresaMetodoPagoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "met_pago_id")
  private Integer metPagoId;

  @Column(name = "emp_id")
  private Integer empId;

  @Column(name = "met_pago_tipo")
  private String metPagoTipo;

  @Column(name = "met_pago_logo")
  private String metPagoLogo;

  @Column(name = "met_pago_qr")
  private String metPagoQr;

  @Column(name = "met_pago_cuenta_numero")
  private String metPagoCuentaNumero;

  @Column(name = "met_pago_cuenta_nombre")
  private String metPagoCuentaNombre;

  @Column(name = "met_pago_detalle")
  private String metPagoDetalle;

}
