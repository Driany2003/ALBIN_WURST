package com.pe.kenpis.model.api.usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UsuarioDTORequest {

  private Integer impId;
  private String usuNombre;
  private String usuApePaterno;
  private String usuApeMaterno;
  private String usuTelefono;
  private String empNombreComercial;
  private String authRoles;
  private String authUsername;
  private char usuGenero;
  private String usuNumeroDocumento;
  private String usuTipoDocumento;
  private String authPassword;

}
