package com.pe.kenpis.model.api.venta.reporteVentasDTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ReporteVentas {

  // Resumen financiero
  private Double totalVenta;
  private Double totalCosto;
  private Double gananciaTotal;

  // Resumen de métodos de pago
  private Integer numeroVentas;
  private Double totalYape;
  private Double totalPlin;
  private Double totalEfectivo;

  // Lista de productos más vendidos
  private List<ProductoMasVendido> productosMasVendidos;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ProductoMasVendido {
    private Integer productoId;
    private String productoNombre;
    private Integer cantidadVendida;
    private Double popularidad;
  }

}



