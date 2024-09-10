package com.pe.kenpis.business;

import com.pe.kenpis.model.api.usuario.UsuarioDTO;
import com.pe.kenpis.model.api.usuario.UsuarioDTORequest;
import com.pe.kenpis.model.api.usuario.UsuarioRequest;
import com.pe.kenpis.model.api.usuario.UsuarioResponse;
import com.pe.kenpis.model.api.usuario.authority.UsuarioAuthorityResponse;

import java.util.List;
import java.util.Map;

public interface IUsuarioService {

  List<UsuarioResponse> findAll();

  List<UsuarioDTO> findAllUsers();

  List<UsuarioDTO> findUsuariosByEmpresaId(Integer usuId);

  UsuarioDTO findById(Integer id);

  UsuarioDTO create(UsuarioDTORequest request);

  UsuarioDTO update(UsuarioDTORequest request);

  UsuarioResponse delete(Integer id);

  UsuarioResponse findUsuarioByAuthUsername(String usuUsername);

  UsuarioAuthorityResponse findUsuarioAuthorityByUsuId(Integer usuId);

  UsuarioResponse findByPhone(String celular);

}
