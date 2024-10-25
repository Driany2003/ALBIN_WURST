package com.pe.kenpis.model.api.empresa;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaResponse {

  private Integer empId;
  private String empDocumentoTipo;
  private String empDocumentoNumero;
  private String empRazonSocial;
  private String empResponsable;
  private String empTelefono;
  private String empImagenLogo;
  private String empNombreComercial;
  private Date empFechaContratoInicio;
  private Date empFechaContratoFin;
  private Date empFechaCreacion;
  private Long empQrYape;
  private Long empQrPlin;
  private Long empQrPagos;
  private Boolean empIsActive;
  private String empEmail;
  private Integer empPadreId;

}