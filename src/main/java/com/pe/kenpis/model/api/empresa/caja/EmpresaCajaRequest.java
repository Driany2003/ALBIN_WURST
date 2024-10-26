package com.pe.kenpis.model.api.empresa.caja;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaCajaRequest {

  private Integer cajaId;
  private Integer empPadreId;
  private LocalDateTime cajaFechaApertura;
  private LocalDateTime cajaFechaCierre;
  private float cajaMontoInicial;
  private float cajaMontoFinal;
  private Integer cajaUsuarioApertura;
  private Integer cajaUsuarioCierre;
  private Boolean cajaEstado;

}
