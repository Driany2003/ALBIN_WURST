package com.pe.kenpis.model.api.empresa.metodoPago;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class EmpresaMetodoPagoRequest {

  private Integer empId;
  private List<EmpresaMetodoPagoDTO> metodosPago;

}
