package com.pe.kenpis.business;

import com.pe.kenpis.model.api.usuario.*;
import com.pe.kenpis.model.api.usuario.MiPerfil.MiPerfilDTORequest;
import com.pe.kenpis.model.api.usuario.authority.UsuarioAuthorityResponse;
import com.pe.kenpis.model.api.usuario.resetClave.resetClaveRequest;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface IUsuarioService {

  //Menu desplegable --> ActualizarClave // y tambien para la vista de usuario
  void actualizarPassword(Integer usuId, String nuevaPassword);

  List<UsuarioResponse> findAll();

  List<UsuarioDTO> findAllUsers();

  List<UsuarioDTO> findUsuariosByEmpresaId(Integer usuId);

  List<ResponsablesDTO> obtenerUsuariosPorEmpresa(Integer empId);

  UsuarioDTO findById(Integer usuSessionId);

  UsuarioDTO create(UsuarioRequest request);

  UsuarioDTO update(UsuarioDTORequest request, HttpSession session);

  //Menu desplegable --> Miperfil

  void actualizarMiPerfil(MiPerfilDTORequest miPerfilDTORequest);

  //Menu desplegable --> ValidarClave

  boolean validarCLave(Integer usuId, String claveActual);

  UsuarioResponse delete(Integer id);

  UsuarioResponse findUsuarioByAuthUsername(String usuUsername);

  UsuarioAuthorityResponse findUsuarioAuthorityByUsuId(Integer usuId);

  UsuarioResponse findByPhone(String celular);

  List<Map<String, Object>> obtenerUsuariosConNombres();

}
