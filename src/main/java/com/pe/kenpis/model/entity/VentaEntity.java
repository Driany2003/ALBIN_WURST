package com.pe.kenpis.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "T_VENTA")
@Entity
public class VentaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ven_id")
  private Integer venId;

  @Column(name = "ven_fecha")
  private Date venFecha;

  @Column(name = "ven_total")
  private float venTotal;

  @Column(name = "empresa_id")
  private Integer empresaId;

  @Column(name = "cliente_id")
  private Integer clienteId;

  @Column(name = "ven_alias")
  private String VenAlias;

  @Column(name = "ven_consideraciones")
  private String VenConsideraciones;

  @Column(name = "ven_tipo_pago")
  private String venTipoPago;

  @Column(name = "usuario_id")
  private Integer usuarioId;

}
