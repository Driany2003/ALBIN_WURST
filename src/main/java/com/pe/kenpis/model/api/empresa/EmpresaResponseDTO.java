package com.pe.kenpis.model.api.empresa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmpresaResponseDTO {

  private Integer empId;
  private String empNombreComercial;

  public EmpresaResponseDTO(Integer empId,String empNombreComercial) {
    this.empId = empId;
    this.empNombreComercial = empNombreComercial;
  }

}
