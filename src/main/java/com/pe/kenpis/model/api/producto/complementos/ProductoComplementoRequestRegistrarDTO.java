package com.pe.kenpis.model.api.producto.complementos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductoComplementoRequestRegistrarDTO {

  private Integer empId;
  private String proCompNombre;
  private List<DetalleRequest> detalles;
  @Getter
  @Setter
  @ToString
  public static class DetalleRequest {
    private String proCompNombre;
    private Double proCompPrecio;
  }

}
