package com.pe.kenpis.model.api.empresa.sucursal;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SucursalDTOResponse {
  private String empNombreComercial;
  private String empTelefono;
  private Integer empId;
  private String empResponsable;

  public SucursalDTOResponse(String empNombreComercial, String empTelefono, String empResponsable, Integer empId) {
    this.empNombreComercial = empNombreComercial;
    this.empTelefono = empTelefono;
    this.empResponsable = empResponsable;
    this.empId = empId;
  }


}
