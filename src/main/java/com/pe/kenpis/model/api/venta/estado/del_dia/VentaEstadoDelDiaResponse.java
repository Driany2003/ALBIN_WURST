package com.pe.kenpis.model.api.venta.estado.del_dia;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VentaEstadoDelDiaResponse {
  private Integer REGISTRADO;
  private Integer PAGADO;
  private Integer EN_PROCESO;
  private Integer ATENDIDO;
  private Integer DESCARTADO;



}
