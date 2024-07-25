package com.pe.kenpis.model.api.cliente;

import lombok.*;

import java.util.Date;

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
  private Date cliFechaCreacion;


}
