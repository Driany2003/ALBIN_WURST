package com.pe.kenpis.model.api.usuario;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTORequest  {
//para poder actualizar el usuario
  private Integer usuId;
  private Integer empresaId;
  private String usuNombre;
  private String usuApePaterno;
  private String usuApeMaterno;
  private String usuTelefono;
  private String authRoles;
  private String authUsername;
  private String usuCorreo;
  private char usuGenero;
  private String usuNumeroDocumento;
  private String usuTipoDocumento;

}
