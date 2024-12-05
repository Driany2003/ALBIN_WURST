package com.pe.kenpis.model.api.reporte;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReporteRequest {
  private Integer repId;
  private Integer cajaId;
  private Date repFechaCreacion;
  private boolean repIsActive;
  private Integer sucursalId;
  private Integer empId;

}
