package com.pe.kenpis.model.api.producto.complementos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
public class ProductoComplementoResponseDTO {
  private Integer empId;
  private String empNombreComercial;
  private Integer proCompId;
  private String proCompNombre;
  private Double proCompPrecio;
  private Integer proCompIdPadre;
  private List<DetalleResponse> detalles;

  @Getter
  @Setter
  @ToString
  public static class DetalleResponse {
    private Integer proCompDetalleId;
    private String proCompNombre;
    private Double proCompPrecio;
  }

  // Constructor
  public ProductoComplementoResponseDTO(Integer empId, String empNombreComercial, Integer proCompId, String proCompNombre, Double proCompPrecio, Integer proCompIdPadre) {
    this.empId = empId;
    this.empNombreComercial = empNombreComercial;
    this.proCompId = proCompId;
    this.proCompNombre = proCompNombre;
    this.proCompPrecio = proCompPrecio;
    this.proCompIdPadre = proCompIdPadre;
  }

  public ProductoComplementoResponseDTO(Integer proCompId, String proCompNombre, Double proCompPrecio) {
    this.proCompId = proCompId;
    this.proCompNombre = proCompNombre;
    this.proCompPrecio = proCompPrecio;
  }

  public ProductoComplementoResponseDTO(Integer empId, Integer proCompId, String proCompNombre, Double proCompPrecio, Integer proCompIdPadre, List<DetalleResponse> detalles) {
    this.empId = empId;
    this.proCompId = proCompId;
    this.proCompNombre = proCompNombre;
    this.proCompPrecio = proCompPrecio;
    this.proCompIdPadre = proCompIdPadre;
    this.detalles = detalles;
  }

}
