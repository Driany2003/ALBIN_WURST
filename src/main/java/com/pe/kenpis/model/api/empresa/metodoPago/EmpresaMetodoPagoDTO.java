package com.pe.kenpis.model.api.empresa.metodoPago;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmpresaMetodoPagoDTO {

  private Integer metPagoId;
  private String metPagoTipo;
  private String metPagoLogo;
  private String metPagoQr;
  private String metPagoCuentaNumero;
  private String metPagoCuentaNombre;
  private String metPagoDetalle;

}
