package com.pe.kenpis.model.api.empresa.sucursal;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SucursalRequest {

  private Integer empPadreId;
  private String empNombreComercial;
  private String empTelefono;

}
