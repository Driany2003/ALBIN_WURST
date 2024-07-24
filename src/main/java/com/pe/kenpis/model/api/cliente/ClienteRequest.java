package com.pe.kenpis.model.api.cliente;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {

  private Integer cliId;
  private String cliTelefono;
  private String cliNombre;
  private String cliCorreo;
  private Boolean cliNotificacion;
  private Boolean cliIsActive;


}
