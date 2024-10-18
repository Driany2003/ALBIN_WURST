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
  private Boolean empIsActive;
  public SucursalResponse(Integer sucursalId, Boolean empIsActive) {
    this.empId = sucursalId;
    this.empIsActive = empIsActive;
  }


}
