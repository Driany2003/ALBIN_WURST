package com.pe.kenpis.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/sys")
public class WVentasController {

  @RequestMapping(value = {"/nueva-venta"}, method = RequestMethod.GET)
  public String okrObjetivos() {
    return "nueva-venta";
  }

  @RequestMapping(value = {"/historial-de-ventas"}, method = RequestMethod.GET)
  public String okrKeyResult() {
    return "historial-de-ventas";
  }

  @RequestMapping(value = {"/detalle-venta"}, method = RequestMethod.GET)
  public String okrSeguimiento() {
    return "detalle-venta";
  }

}
