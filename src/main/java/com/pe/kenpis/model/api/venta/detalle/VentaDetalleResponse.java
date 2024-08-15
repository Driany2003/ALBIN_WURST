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
public class VentaDetalleResponse {

  private ProductoResponse producto;
  private Integer venDetId;
  private Integer venDetCantidad;
  private double venDetSubtotal;
  private Integer ventaId;
  private Integer productoId;
  private float venDetPrecio;
  private String venDetObservaciones;

  public VentaDetalleResponse(ProductoResponse productoResponse, Integer venDetId, Integer venDetCantidad, float venDetSubtotal, Integer ventaId) {
  }

}
