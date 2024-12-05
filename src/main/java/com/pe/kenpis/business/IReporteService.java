package com.pe.kenpis.business;

import com.pe.kenpis.model.api.reporte.ReporteRequest;
import com.pe.kenpis.model.api.reporte.ReporteResponse;

import java.util.List;

public interface IReporteService {

  List<ReporteResponse> obtenerReportesPorEmpresaId(Integer empresaId);

  ReporteResponse findById(Integer usuSessionId);

  ReporteResponse create(ReporteRequest request);

  ReporteResponse delete(Integer id);

}
