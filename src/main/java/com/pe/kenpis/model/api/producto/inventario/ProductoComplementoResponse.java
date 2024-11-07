package com.pe.kenpis.model.api.producto.inventario;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
public class ProductoComplementoResponse {

  private Integer proInvId;
  private Integer productoId;
  private Integer proInvStockInicial;
  private Integer proInvStockVentas;
  private Date proInvFechaCreacion;
  private Double proPrecioCosto;
  private Double proPrecioVenta;
  private String proCategoria;
  private String proDescripcion;
  private Integer proInvStockActual;
  private Integer empId;
  private String proImagen;
  private Boolean proIsActive;
  private Date proInvFechaVencimiento;


  public ProductoComplementoResponse(Integer productoId, Date proInvFechaVencimiento, String proDescripcion, String proImagen, Boolean proIsActive, Double proPrecioCosto, Double proPrecioVenta, String proCategoria, Integer empId, Integer proInvStockInicial, Integer proInvStockVentas, Integer proInvStockActual, Date proInvFechaCreacion) {
    this.productoId = productoId;
    this.proInvFechaVencimiento = proInvFechaVencimiento; // Ajusta aquí el nombre del parámetro
    this.proInvStockInicial = proInvStockInicial;
    this.proInvStockVentas = proInvStockVentas;
    this.proInvStockActual = proInvStockActual;
    this.proInvFechaCreacion = proInvFechaCreacion;
    this.proPrecioCosto = proPrecioCosto;
    this.proPrecioVenta = proPrecioVenta;
    this.proCategoria = proCategoria;
    this.proDescripcion = proDescripcion;
    this.empId = empId;
    this.proImagen = proImagen;
    this.proIsActive = proIsActive;
  }

  public ProductoComplementoResponse() {

  }
}
