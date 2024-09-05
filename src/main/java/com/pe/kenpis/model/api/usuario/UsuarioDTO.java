package com.pe.kenpis.model.api.usuario;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

  private Integer usuId;
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

  public UsuarioDTO(Integer usuId, String usuNombre, String usuApePaterno, String usuApeMaterno, String usuTelefono, String usuNumeroDocumento, String usuTipoDocumento, char usuGenero, String empNombreComercial, String authRoles, String authUsername) {
    this.usuId = usuId;
    this.usuNombre = usuNombre;
    this.usuApePaterno = usuApePaterno;
    this.usuApeMaterno = usuApeMaterno;
    this.usuTelefono = usuTelefono;
    this.usuNumeroDocumento = usuNumeroDocumento;
    this.usuTipoDocumento = usuTipoDocumento;
    this.usuGenero = usuGenero;
    this.empNombreComercial = empNombreComercial;
    this.authRoles = authRoles;
    this.authUsername = authUsername;
  }

  public UsuarioDTO(Integer usuId, Integer impId, String usuNombre, String usuApePaterno, String usuApeMaterno, String usuTelefono, String usuNumeroDocumento, String usuTipoDocumento, char usuGenero, String empNombreComercial, String authRoles, String authUsername) {
    this.usuId = usuId;
    this.impId = impId;
    this.usuNombre = usuNombre;
    this.usuApePaterno = usuApePaterno;
    this.usuApeMaterno = usuApeMaterno;
    this.usuTelefono = usuTelefono;
    this.usuNumeroDocumento = usuNumeroDocumento;
    this.usuTipoDocumento = usuTipoDocumento;
    this.usuGenero = usuGenero;
    this.empNombreComercial = empNombreComercial;
    this.authRoles = authRoles;
    this.authUsername = authUsername;
  }

}
