package com.pe.kenpis.model.api.usuario.authority;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioAuthorityResponse {

  private Integer authId;
  private String authUsername;
  private String authPassword;
  private String authRoles;
  private Integer usuId;
  private Boolean authIsActive;

}
