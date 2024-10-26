package com.pe.kenpis.expose.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/kenpis/menu")
public class WMenuController {

  @RequestMapping(value = {"/venta/caja"}, method = RequestMethod.GET)
  public String menuAperturaCierre() {
    return "configuracion-caja";
  }

  @RequestMapping(value = {"/venta/nueva"}, method = RequestMethod.GET)
  public String menuVentaNueva() {
    return "venta-nueva";
  }

  @RequestMapping(value = {"/venta/reporte"}, method = RequestMethod.GET)
  public String menuVentaHistorial() {
    return "venta-reporte";
  }

  @RequestMapping(value = {"/venta/nueva/usuario"}, method = RequestMethod.GET)
  public String menuVentaNuevaUsuario() {
    return "venta-nueva-usuario";
  }

  @RequestMapping(value = {"/configuracion/usuario"}, method = RequestMethod.GET)
  public String menuUsuario() {
    return "configuracion-usuario";
  }

  @RequestMapping(value = {"/configuracion/cliente"}, method = RequestMethod.GET)
  public String menuCliente() {return "configuracion-cliente";}

  @RequestMapping(value = {"/configuracion/metodoPago"}, method = RequestMethod.GET)
  public String menuMetodoPago() {
    return "configuracion-empresa";
  }

  @RequestMapping(value = {"/configuracion/empresa"}, method = RequestMethod.GET)
  public String menuEmpresa() {
    return "configuracion-empresa";
  }

  @RequestMapping(value = {"/configuracion/producto"}, method = RequestMethod.GET)
  public String menuProducto() {
    return "configuracion-productos";
  }

  @RequestMapping(value = {"/configuracion/complementos"}, method = RequestMethod.GET)
  public String menuComplementos() {
    return "configuracion-complementos";
  }

  @RequestMapping(value = {"/configuracion/inventario"}, method = RequestMethod.GET)
  public String menuInventario() {
    return "configuracion-inventario";
  }

}
