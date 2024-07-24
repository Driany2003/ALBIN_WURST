package com.pe.kenpis.model.api.producto.inventario;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductoInventarioRequest {

  private Integer proInvId;
  private Integer productoId;
  private Integer proInvStockInicial;
  private Integer proInvStockVentas;
  private Date proInvFechaCreacion;

}
