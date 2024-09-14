package com.pe.kenpis.model.api.usuario;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {
//recibe lo que trae de registrar
  private Integer empresaId;
  private String usuNombre;
  private String usuApePaterno;
  private String usuApeMaterno;
  private String usuTelefono;
  private String authRoles;
  private String authUsername;
  private char usuGenero;
  private String usuNumeroDocumento;
  private String usuTipoDocumento;
  private String authPassword;

}
