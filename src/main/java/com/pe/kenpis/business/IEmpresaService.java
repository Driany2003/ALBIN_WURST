package com.pe.kenpis.business;

import com.pe.kenpis.model.api.empresa.EmpresaDTO;
import com.pe.kenpis.model.api.empresa.EmpresaRequest;
import com.pe.kenpis.model.api.empresa.EmpresaResponse;
import com.pe.kenpis.model.api.empresa.EmpresaResponseDTO;
import com.pe.kenpis.model.api.empresa.sucursal.SucursalDTOResponse;
import com.pe.kenpis.model.api.empresa.sucursal.SucursalDTOrequest;
import com.pe.kenpis.model.api.empresa.sucursal.SucursalRequest;
import com.pe.kenpis.model.api.empresa.sucursal.SucursalResponse;

import java.util.List;

public interface IEmpresaService {

  List<EmpresaDTO> findAllActiveEmpresaById();

  List<EmpresaResponseDTO> findAllByStatus();

  //este es el servicio para poder listar las sucursales por empresa.
  List<EmpresaDTO> obtenerSucursalesPorEmpresa(Integer empId);

  //trae sucursales activas para registrar una caja
  List<EmpresaDTO> obtenerSucursalesByEmpresa(Integer empId);

  EmpresaResponse findById(Integer id);

  EmpresaResponse obtenerEmpresaPorUsuario(Integer usuarioId);

  EmpresaResponse create(EmpresaRequest request);

  EmpresaResponse createSucursal(SucursalRequest request);

  EmpresaResponse update(EmpresaRequest request);

  SucursalDTOResponse updateSucursal(SucursalDTOrequest request);

  EmpresaResponse updatePropietario(EmpresaRequest request);

  EmpresaResponse updateStatus(EmpresaRequest request);

  EmpresaResponse delete(Integer id);

}