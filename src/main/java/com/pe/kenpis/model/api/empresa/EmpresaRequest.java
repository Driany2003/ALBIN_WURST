package com.pe.kenpis.model.api.empresa;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaRequest {

  private Integer empId;
  private String empDocumentoTipo;
  private String empDocumentoNumero;
  private String empRazonSocial;
  private String empResponsable;
  private String empTelefono;
  private Long empImageLogo;
  private String empNombreComercial;
  private Date empFechaContratoInicio;
  private Date empFechaContratoFin;
  private Date empFechaCreacion;
  private Integer empIsActive;
  private Long empQrYape;
  private Long empQrPlin;
  private Long empQrPagos;

}