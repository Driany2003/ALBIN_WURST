package com.pe.kenpis.model.api.usuario;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {

  private Integer usuId;
  private String usuNombre;
  private String usuApePaterno;
  private String usuApeMaterno;
  private String usuTelefono;

}
