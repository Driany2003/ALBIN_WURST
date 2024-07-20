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
  private Integer detvenId;
  private Integer detvenCantidad;
  private double detvenSubtotal;
  private Integer venId;
  private Integer proId;

}
