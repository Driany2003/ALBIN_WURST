package com.pe.kenpis.business;

import com.pe.kenpis.model.api.usuario.*;
import com.pe.kenpis.model.api.usuario.authority.UsuarioAuthorityResponse;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface IUsuarioService {

  void actualizarPassword(Integer usuId, String nuevaPassword);

  List<UsuarioResponse> findAll();

  List<UsuarioDTO> findAllUsers();

  List<UsuarioDTO> findUsuariosByEmpresaId(Integer usuId);

  List<ResponsablesDTO> obtenerUsuariosPorEmpresa(Integer empId);

  UsuarioDTO findById(Integer usuSessionId);

  UsuarioDTO create(UsuarioRequest request);

  UsuarioDTO update(UsuarioDTORequest request, HttpSession session);

  void actualizarMiPerfil(MiPerfilDTORequest miPerfilDTORequest);

  UsuarioResponse delete(Integer id);

  UsuarioResponse findUsuarioByAuthUsername(String usuUsername);

  UsuarioAuthorityResponse findUsuarioAuthorityByUsuId(Integer usuId);

  UsuarioResponse findByPhone(String celular);

}
