package com.pe.kenpis.model.api.usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MiPerfilDTORequest {
  private Integer usuId;
  private Integer empresaId;
  private String usuNombre;
  private String usuApePaterno;
  private String usuApeMaterno;
  private String usuTelefono;
  private String usuCorreo;
  private String usuNumeroDocumento;
  private String usuTipoDocumento;

}
