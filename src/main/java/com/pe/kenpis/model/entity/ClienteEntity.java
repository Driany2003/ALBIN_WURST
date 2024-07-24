package com.pe.kenpis.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "T_CLIENTE" )
public class ClienteEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cli_id")
  private Integer cliId;

  @Column(name = "cli_telefono")
  private String cliTelefono;

  @Column(name = "cli_nombre")
  private String cliNombre;

  @Column(name = "cli_correo")
  private String cliCorreo;

  @Column(name = "cli_notificacion")
  private Boolean cliNotificacion;

  @Column(name = "cli_is_active")
  private Boolean cliIsActive;

  @Column(name = "cli_fecha_creacion")
  private Date cliFechaCreacion;

}
