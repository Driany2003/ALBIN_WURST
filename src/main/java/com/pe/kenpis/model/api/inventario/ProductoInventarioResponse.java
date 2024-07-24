package com.pe.kenpis.model.api.inventario;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductoInventarioResponse {

  private Integer proInvId;
  private Integer productoId;
  private Integer proInvStockInicial;
  private Integer proInvStockVentas;
  private Date proInvFechaCreacion;

}