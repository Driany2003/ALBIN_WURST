package com.pe.kenpis.model.api.venta.estado;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VentasEstadoDTO {
  private String proTipo;
  private Integer venDetCantidad;
  private String clienteNombre;

  public void VentaEstadoDTO(String proTipo, Integer venDetCantidad, String clienteNombre) {
    this.proTipo = proTipo;
    this.venDetCantidad = venDetCantidad;
    this.clienteNombre = clienteNombre;
  }

}
