package com.pe.kenpis.model.api.empresa.sucursal;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SucursalResponse {

  private Integer empId;
  private String empNombreComercial;
  private String empTelefono;
  private Integer empPadreId;
  private String empResponsable;

  public SucursalResponse(Integer sucursalId, String empNombreComercial, String empTelefono, String empResponsable) {
    this.empId = sucursalId;
    this.empNombreComercial = empNombreComercial;
    this.empTelefono = empTelefono;
    this.empResponsable = empResponsable;
  }

}
