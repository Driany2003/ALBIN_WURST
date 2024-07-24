package com.pe.kenpis.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "T_USUARIO_AUTHORITY")
public class UsuarioAuthorityEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "auth_id")
  private Integer authId;

  @Column(name = "auth_username")
  private String authUsername;

  @Column(name = "auth_password")
  private String authPassword;

  @Column(name = "auth_roles")
  private String authRoles;

  @Column(name = "usu_id")
  private Integer usuId;

  @Column(name = "auth_is_active")
  private Boolean authIsActive;

}
