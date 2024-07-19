package com.pe.kenpis.model.api.ventas;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VentasRequest {

  private Integer venId;
  private Date venFecha;
  private float venTotal;

}
