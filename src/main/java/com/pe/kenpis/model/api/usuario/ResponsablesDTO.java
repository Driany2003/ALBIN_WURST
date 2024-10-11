package com.pe.kenpis.model.api.usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResponsablesDTO {
  private Integer usuId;
  private String usuNombre;
  private String usuApePaterno;

  public ResponsablesDTO(Integer usuId, String usuNombre, String usuApePaterno) {
    this.usuId = usuId;;
    this.usuNombre = usuNombre;
    this.usuApePaterno = usuApePaterno;
  }
}
