package com.pe.kenpis.model.api.usuario.authority;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {
  private Integer usuId;
  private String newPassword;

}
