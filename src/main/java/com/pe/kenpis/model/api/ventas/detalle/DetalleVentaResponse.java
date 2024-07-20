package com.pe.kenpis.model.api.ventas.detalle;

import com.pe.kenpis.model.api.productos.ProductosResponse;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DetalleVentaResponse {
  private ProductosResponse producto;
  private Integer detvenId;
  private Integer detvenCantidad;
  private double detvenSubtotal;
  private Integer venId;
  private Integer proId;
  private double precioUnitario;

  public DetalleVentaResponse(ProductosResponse producto, Integer detvenId, Integer detvenCantidad, double detvenSubtotal, Integer venId, Integer proId, double precioUnitario) {
    this.producto = producto;
    this.detvenId = detvenId;
    this.detvenCantidad = detvenCantidad;
    this.detvenSubtotal = detvenSubtotal;
    this.venId = venId;
    this.proId = proId;
    this.precioUnitario = precioUnitario;
  }

  public DetalleVentaResponse(ProductosResponse productoResponse, Integer detvenCantidad, float proPrecio, double detvenSubTotal) {
  }
}
