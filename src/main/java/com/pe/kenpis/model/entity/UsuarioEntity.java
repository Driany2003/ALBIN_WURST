package com.pe.kenpis.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "T_USUARIO")
public class UsuarioEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "usu_id")
  private Integer usuId;

  @Column(name = "usu_nombre")
  private String usuNombre;

  @Column(name = "usu_ape_paterno")
  private String usuApePaterno;

  @Column(name = "usu_ape_materno")
  private String usuApeMaterno;

  @Column(name = "usu_telefono")
  private String usuTelefono;

  @Column(name = "empresa_id")
  private Integer empresaId;

}
