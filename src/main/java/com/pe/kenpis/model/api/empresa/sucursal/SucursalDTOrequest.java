package com.pe.kenpis.model.api.empresa.sucursal;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SucursalDTOrequest {
  private String empNombreComercial;
  private String empTelefono;
  private Integer empId;
  private String empResponsable;


}
