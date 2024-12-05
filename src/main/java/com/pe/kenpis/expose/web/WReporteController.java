package com.pe.kenpis.expose.web;

import com.pe.kenpis.business.IReporteService;
import com.pe.kenpis.model.api.empresa.caja.EmpresaCajaResponse;
import com.pe.kenpis.model.api.reporte.ReporteResponse;
import com.pe.kenpis.model.entity.ReporteEntity;
import com.pe.kenpis.util.funciones.FxComunes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/kenpis/reporte")
@Slf4j
public class WReporteController {

  @Autowired
  private IReporteService reporteService;

  @GetMapping("/listar")
  public ResponseEntity<List<ReporteResponse>> obtenerReportesPorEmpresa(@RequestParam Integer empId) {
    List<ReporteResponse> reportes = reporteService.obtenerReportesPorEmpresaId(empId);
    FxComunes.printJson("reportes", reportes);
    return ResponseEntity.ok(reportes);
  }

}
