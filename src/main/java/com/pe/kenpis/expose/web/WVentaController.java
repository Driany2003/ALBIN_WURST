package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IVentaService;
import com.pe.kenpis.model.api.venta.VentaRequest;
import com.pe.kenpis.model.api.venta.VentaResponse;
import com.pe.kenpis.repository.VentaEstadoRepository;
import com.pe.kenpis.util.funciones.FxComunes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/kenpis/venta")
public class WVentaController {

  @Autowired
  private IVentaService service;


  @PostMapping("/create")
  public ResponseEntity<VentaResponse> create(@RequestBody VentaRequest ventaRequest) throws Exception {
    FxComunes.printJson("VentaRequest", ventaRequest);
    VentaResponse ventaResponse = service.create(ventaRequest);
    return new ResponseEntity<>(ventaResponse, HttpStatus.CREATED);
  }

}
