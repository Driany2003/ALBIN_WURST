package com.pe.kenpis.model.api.metodoPago;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MetodoPagoResponse {

  private Integer metPagoId;

  private Integer empId;

  private String metPagoTipo;

  private String metPagoLogo;

  private String metPagoQr;

  private String metPagoCuentaNumero;

  private String metPagoCuentaNombre;

  private String metPagoDetalle;

}
