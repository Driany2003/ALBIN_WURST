package com.pe.kenpis.model.api.producto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductoList extends ProductoResponse {

  private Integer chorizoId;
  private Integer chorizoCantidad;
  private Integer bebidaId;
  private Integer bebidaCantidad;

}
