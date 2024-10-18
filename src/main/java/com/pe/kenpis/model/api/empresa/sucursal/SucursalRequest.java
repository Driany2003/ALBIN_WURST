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
  private Integer empId;
  private Integer empPadreId;
  private String empNombreComercial;
  private String empTelefono;

  public SucursalRequest(Integer sucursalId, String empNombreComercial, String empTelefono) {
    this.empId = sucursalId;
    this.empNombreComercial = empNombreComercial;
    this.empTelefono = empTelefono;
  }


}
