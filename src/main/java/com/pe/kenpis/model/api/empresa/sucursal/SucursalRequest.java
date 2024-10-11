package com.pe.kenpis.model.api.empresa.sucursal;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SucursalRequest {
  //lo estoy usando para poder registrar y para actualizar
  private Integer empPadreId;
  private String empNombreComercial;
  private String empTelefono;
  private Integer empId;
  private String empResponsable;

  public SucursalRequest(Integer sucursalId, String empNombreComercial, String empTelefono, String empResponsable) {
    this.empId = sucursalId;
    this.empNombreComercial = empNombreComercial;
    this.empTelefono = empTelefono;
    this.empResponsable = empResponsable;
  }

}
