package com.pe.kenpis.model.api.reporte;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.security.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReporteResponse {

  private Integer repId;
  private Integer cajaId;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
  private Date repFechaCreacion;
  private boolean repIsActive;
  private Integer sucursalId;
  private Integer empId;
  private String sucursalNombre;

  public ReporteResponse(Integer cajaId, Date repFechaCreacion, String sucursalNombre, boolean repIsActive) {
    this.cajaId = cajaId;
    this.repFechaCreacion = repFechaCreacion;
    this.sucursalNombre = sucursalNombre;
    this.repIsActive = repIsActive;
  }
}
