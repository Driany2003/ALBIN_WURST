package com.pe.kenpis.model.api.cliente;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ClienteDTO {


  private Integer cliId;
  private String cliTelefono;
  private String cliNombre;
  private String cliCorreo;
  private Boolean cliNotificacion;
  private Boolean cliIsActive;
  private Integer empId;
  private String empNombreComercial;
  private Date cliFechaCreacion;

  // para listar los clientes por empresa
  public ClienteDTO(Integer cliId, String cliTelefono, String cliNombre,String cliCorreo, Boolean cliNotificacion, Boolean cliIsActive, Integer empId, String empNombreComercial) {
    this.cliId = cliId;
    this.cliTelefono = cliTelefono;
    this.cliNombre = cliNombre;
    this.cliCorreo = cliCorreo;
    this.cliNotificacion = cliNotificacion;
    this.cliIsActive = cliIsActive;
    this.empId = empId;
    this.empNombreComercial = empNombreComercial;
  }


  // Para listar todos los clientes
  public ClienteDTO(Integer cliId, String cliTelefono, String cliNombre,String cliCorreo, Boolean cliNotificacion, Boolean cliIsActive, String empNombreComercial) {
    this.cliId = cliId;
    this.cliTelefono = cliTelefono;
    this.cliNombre = cliNombre;
    this.cliCorreo = cliCorreo;
    this.cliNotificacion = cliNotificacion;
    this.cliIsActive = cliIsActive;
    this.empNombreComercial = empNombreComercial;
  }


}
