package com.pe.kenpis.model.api.empresa;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
public class EmpresaDTO {

  private Integer empId;
  private String empNombreComercial;
  private Boolean empIsActive;
  private Date empFechaContratoFin;
  private Date empFechaContratoInicio;
  private String empTelefono;
  private String empImagenLogo;
  private String empResponsable;

  public EmpresaDTO(Integer empId, String empNombreComercial) {
    this.empId = empId;
    this.empNombreComercial = empNombreComercial;
  }

  //para poder listar las sucursales cuando demos click a una empresa.
  public EmpresaDTO( Integer empId,String empNombreComercial, String empTelefono, Boolean empIsActive) {
    this.empNombreComercial = empNombreComercial;
    this.empId = empId;
    this.empTelefono = empTelefono;
    this.empIsActive = empIsActive;

  }
  public EmpresaDTO(Integer empId, String empResponsable,String empImagenLogo, String empNombreComercial, Date empFechaContratoInicio, Date empFechaContratoFin, String empTelefono, Boolean empIsActive) {
    this.empId = empId;
    this.empImagenLogo = empImagenLogo;
    this.empNombreComercial = empNombreComercial;
    this.empFechaContratoInicio = empFechaContratoInicio;
    this.empFechaContratoFin = empFechaContratoFin;
    this.empTelefono = empTelefono;
    this.empIsActive = empIsActive;
    this.empResponsable = empResponsable;

  }

}
