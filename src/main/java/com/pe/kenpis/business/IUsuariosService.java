package com.pe.kenpis.business;

import com.pe.kenpis.model.api.usuario.UsuarioResponse;
import com.pe.kenpis.model.api.usuario.authority.UsuarioAuthorityResponse;

public interface IUsuariosService {

  UsuarioResponse findUsuarioByAuthUsername(String usuUsername);

  UsuarioAuthorityResponse findUsuarioAuthorityByUsuId(Integer usuId);

}
