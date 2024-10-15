package com.pe.kenpis.model.api.usuario.resetClave;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class resetClaveRequest {
  private Integer usuId;
  private String claveActual;
  private String nuevaClave;

}
