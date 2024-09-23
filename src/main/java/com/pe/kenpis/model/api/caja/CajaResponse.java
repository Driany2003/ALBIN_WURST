package com.pe.kenpis.model.api.caja;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CajaResponse {

  private Integer cajaId;
  private Integer empId;
  private LocalDateTime cajaFechaApertura;
  private LocalDateTime cajaFechaCierre;
  private float cajaMontoInicial;
  private float cajaMontoFinal;
  private String cajaUsuarioApertura;
  private String cajaUsuarioCierre;
  private Boolean cajaEstado;

}
