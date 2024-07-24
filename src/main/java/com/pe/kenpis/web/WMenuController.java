package com.pe.kenpis.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/kenpis/menu")
public class WMenuController {

  @RequestMapping(value = {"/venta/nueva"}, method = RequestMethod.GET)
  public String menuVentaNueva() {
    return "venta-nueva";
  }

  @RequestMapping(value = {"/venta/historial"}, method = RequestMethod.GET)
  public String menuVentaHistorial() {
    return "venta-historial";
  }

  @RequestMapping(value = {"/venta/detalle"}, method = RequestMethod.GET)
  public String menuVentaDetalle() {
    return "venta-detalle";
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
  public String menuCliente() {
    return "configuracion-cliente";
  }

  @RequestMapping(value = {"/configuracion/empresa"}, method = RequestMethod.GET)
  public String menuEmpresa() {
    return "configuracion-empresa";
  }

  @RequestMapping(value = {"/configuracion/producto"}, method = RequestMethod.GET)
  public String menuProducto() {
    return "configuracion-producto";
  }

  @RequestMapping(value = {"/configuracion/almacen"}, method = RequestMethod.GET)
  public String menuAlmacen() {
    return "configuracion-almacen";
  }

}
