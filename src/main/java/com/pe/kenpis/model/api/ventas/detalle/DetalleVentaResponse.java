package com.pe.kenpis.model.api.ventas.detalle;

import com.pe.kenpis.model.api.productos.ProductosResponse;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DetalleVentaResponse {
  private ProductosResponse producto;
  private Integer venDetId;
  private Integer venDetCantidad;
  private double venDetSubtotal;
  private Integer ventaId;
  private Integer productoId;
  private float venDetPrecio;
  public DetalleVentaResponse(Integer productoId, Integer venDetCantidad, float venDetPrecio, float venDetSubtotal) {
  }

  public DetalleVentaResponse(ProductosResponse productoResponse, Integer venDetId, Integer venDetCantidad, float venDetSubtotal, Integer ventaId) {
  }
}
