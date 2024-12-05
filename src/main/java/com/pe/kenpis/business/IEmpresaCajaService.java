package com.pe.kenpis.business;

import com.pe.kenpis.model.api.empresa.caja.EmpresaCajaRequest;
import com.pe.kenpis.model.api.empresa.caja.EmpresaCajaResponse;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IEmpresaCajaService {

  List<EmpresaCajaResponse> findAll();

  EmpresaCajaResponse findById(Integer id);

  EmpresaCajaResponse create(EmpresaCajaRequest request);

  EmpresaCajaResponse update(EmpresaCajaRequest request);

  EmpresaCajaResponse delete(Integer id);

  EmpresaCajaResponse abrirCajaSiNoExiste(EmpresaCajaRequest request);

  EmpresaCajaResponse cerrarCaja(Integer cajaId, Integer usuarioId);

  List<EmpresaCajaResponse> obtenerCajasPorEmpresa(Integer empresaId);

  List<EmpresaCajaResponse> obtenerCajasActivasPorEmpresa(Integer empresaId);

  String obtenerNombreSucursalPorCajaId(Integer cajaId);
}
