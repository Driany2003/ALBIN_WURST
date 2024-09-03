package com.pe.kenpis.model.api.usuario;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

  private Integer usuId;
  private String usuNombre;
  private String usuApePaterno;
  private String usuApeMaterno;
  private String usuTelefono;
  private String empresaNombre;
  private String authRoles;
  private String authUsername;
  private char usuGenero;
  private String usuNumeroDocumento;
  private String usuTipoDocumento;

}
