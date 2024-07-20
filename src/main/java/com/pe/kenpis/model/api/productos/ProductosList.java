package com.pe.kenpis.model.api.productos;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductosList extends ProductosResponse {

  private Integer chorizoId;
  private Integer chorizoCantidad;
  private Integer bebidaId;
  private Integer bebidaCantidad;

}
