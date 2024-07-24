package com.pe.kenpis.model.api.venta.detalle;

import com.pe.kenpis.model.api.producto.ProductoRequest;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaRequest {
  private ProductoRequest producto;
  private Integer venDetId;
  private Integer venDetCantidad;
  private double venDetSubtotal;
  private Integer ventaId;
  private Integer productoId;
  private float venDetPrecio;

}
