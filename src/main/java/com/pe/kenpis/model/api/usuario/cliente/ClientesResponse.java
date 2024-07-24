package com.pe.kenpis.model.api.usuario.cliente;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClientesResponse {

  private Integer cliId;
  private String cliTelefono;
  private String cliNombre;
  private String cliCorreo;
  private Boolean cliNotificacion;
  private Boolean cliIsActive;

}
