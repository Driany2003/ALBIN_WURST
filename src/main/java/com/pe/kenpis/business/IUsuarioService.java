package com.pe.kenpis.business;

import com.pe.kenpis.model.api.usuario.UsuarioRequest;
import com.pe.kenpis.model.api.usuario.UsuarioResponse;
import com.pe.kenpis.model.api.usuario.authority.UsuarioAuthorityResponse;

import java.util.List;

public interface IUsuarioService {

  List<UsuarioResponse> findAll();

  UsuarioResponse findById(Integer id);

  UsuarioResponse create(UsuarioRequest request);

  UsuarioResponse update(UsuarioRequest request);

  UsuarioResponse delete(Integer id);

  UsuarioResponse findUsuarioByAuthUsername(String usuUsername);

  UsuarioAuthorityResponse findUsuarioAuthorityByUsuId(Integer usuId);

  UsuarioResponse findByPhone(String celular);

}
