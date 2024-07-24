package com.pe.kenpis.model.api.ventas.detalle;

import com.pe.kenpis.model.api.productos.ProductosRequest;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaRequest {
  private ProductosRequest producto;
  private Integer venDetId;
  private Integer venDetCantidad;
  private double venDetSubtotal;
  private Integer ventaId;
  private Integer productoId;
  private float venDetPrecio;

}
