package com.pe.kenpis.model.api.venta.detalle;

import com.pe.kenpis.model.api.producto.ProductoResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DetalleVentaResponse {
  private ProductoResponse producto;
  private Integer venDetId;
  private Integer venDetCantidad;
  private double venDetSubtotal;
  private Integer ventaId;
  private Integer productoId;
  private float venDetPrecio;
  public DetalleVentaResponse(Integer productoId, Integer venDetCantidad, float venDetPrecio, float venDetSubtotal) {
  }

  public DetalleVentaResponse(ProductoResponse productoResponse, Integer venDetId, Integer venDetCantidad, float venDetSubtotal, Integer ventaId) {
  }
}
