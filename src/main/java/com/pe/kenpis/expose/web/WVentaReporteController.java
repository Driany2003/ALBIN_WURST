package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IVentaService;
import com.pe.kenpis.model.api.venta.reporteVentasDTO.ReporteVentas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
@RestController
@RequestMapping(value = "/kenpis/venta/reporte")
public class WVentaReporteController {

  @Autowired
  private IVentaService service;

  @GetMapping("/filtro")
  public ResponseEntity<ReporteVentas> obtenerReporteVentas(HttpSession session, Integer cajaId) {
    Integer empresaId = (Integer) session.getAttribute("empresaSessionID");
    ReporteVentas reporte = service.obtenerReporteVentas(empresaId, cajaId);
    return ResponseEntity.ok(reporte);
  }

  @GetMapping("/filtroXfecha")
  public ResponseEntity<ReporteVentas> obtenerReporteVentas(@RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio, @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin, @RequestParam("empresaId") Integer empresaId) {

    ReporteVentas reporteVentas = service.obtenerReporteVentasXFecha(fechaInicio, fechaFin, empresaId);

    return ResponseEntity.ok(reporteVentas);
  }

}
