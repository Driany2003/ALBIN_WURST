package com.pe.kenpis.model.api.card_movimiento;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class movimientoRequest {

  private String divId;
  private String antiguoContainer;
  private String nuevoContainer;


}
