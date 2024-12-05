package com.pe.kenpis.model.api.empresa.caja;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaCajaResponse {

  private Integer cajaId;
  private Integer empPadreId;
  private Date cajaFechaApertura;
  private Date cajaFechaCierre;
  private float cajaMontoInicial;
  private float cajaMontoFinal;
  private String cajaAsignada;
  private Integer sucursalId;
  private Integer cajaUsuarioApertura;
  private Integer cajaUsuarioCierre;
  private Boolean cajaEstado;
  private String cajaUsuarioCierreNombre;
  private String cajaUsuarioAperturaNombre;

}
