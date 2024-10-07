package com.pe.kenpis.business;

import com.pe.kenpis.model.api.empresa.EmpresaDTO;
import com.pe.kenpis.model.api.empresa.EmpresaRequest;
import com.pe.kenpis.model.api.empresa.EmpresaResponse;
import com.pe.kenpis.model.api.empresa.EmpresaResponseDTO;

import java.util.List;

public interface IEmpresaService {

  List<EmpresaDTO> findAllActiveEmpresaById();

  List<EmpresaDTO> findAllByStatus();

  List<EmpresaResponseDTO> findEmpresaAndSucursalByUsuarioId(Integer empId);

  //este es el servicio para poder listar las sucursales por empresa.
  List<EmpresaDTO> findSucursalByEmpresa(Integer empresaId);

  EmpresaResponse findById(Integer id);

  EmpresaResponse obtenerEmpresaPorUsuario(Integer usuarioId);

  EmpresaResponse create(EmpresaRequest request);

  EmpresaResponse update(EmpresaRequest request);

  EmpresaResponse updatePropietario(EmpresaRequest request);

  EmpresaResponse updateStatus(EmpresaRequest request);

  EmpresaResponse delete(Integer id);

}