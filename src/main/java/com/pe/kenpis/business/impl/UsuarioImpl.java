package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IUsuarioService;
import com.pe.kenpis.model.api.usuario.UsuarioDTO;
import com.pe.kenpis.model.api.usuario.UsuarioDTORequest;
import com.pe.kenpis.model.api.usuario.UsuarioRequest;
import com.pe.kenpis.model.api.usuario.UsuarioResponse;
import com.pe.kenpis.model.api.usuario.authority.UsuarioAuthorityResponse;
import com.pe.kenpis.model.entity.UsuarioAuthorityEntity;
import com.pe.kenpis.model.entity.UsuarioEntity;
import com.pe.kenpis.repository.UsuarioAuthorityRepository;
import com.pe.kenpis.repository.UsuarioRepository;
import com.pe.kenpis.util.funciones.FxComunes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioImpl implements IUsuarioService {

  private final UsuarioRepository repository;
  private final UsuarioAuthorityRepository usuarioAuthorityRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public List<UsuarioResponse> findAll() {
    log.info("Implements :: findAll");
    return repository.findAll().stream().map(this::convertEntityToResponse).collect(Collectors.toList());
  }

  @Override
  public List<UsuarioDTO> findAllUsers() {
    log.info("Implements :: findAllDto");
    List<Map<String, Object>> results = repository.findAllUsers();
    log.info(" --------------- {}", results.toString());
    FxComunes.printJson("objeto result", results);
    return results.stream().map(this::convertToUsuarioDTO).collect(Collectors.toList());
  }

  @Override
  public List<UsuarioDTO> findUsuariosByEmpresaId(Integer usuId) {
    log.info("Implements :: findUsuariosByEmpresaId :: empresaId: {}", usuId);
    List<Map<String, Object>> results = repository.findUsuariosBySesionEmpresaId(usuId);
    log.info(" --------------- {}", results.toString());
    FxComunes.printJson("objeto result", results);
    return results.stream().map(this::convertToUsuarioDTObyEmpresaId).collect(Collectors.toList());
  }

  private UsuarioDTO convertToUsuarioDTO(Map<String, Object> map) {
    return new UsuarioDTO((Integer) map.get("usuId"), (String) map.get("usuNombre"), (String) map.get("usuApePaterno"), (String) map.get("usuApeMaterno"), (String) map.get("usuTelefono"), (String) map.get("usuNumeroDocumento"), (String) map.get("usuTipoDocumento"), (char) map.get("usuGenero"), (String) map.get("empNombreComercial"), (String) map.get("authRoles"), (String) map.get("authUsername"));
  }

  private UsuarioDTO convertToUsuarioDTObyEmpresaId(Map<String, Object> map) {
    return new UsuarioDTO((Integer) map.get("usuId"), (Integer) map.get("empresaId"), (String) map.get("usuNombre"), (String) map.get("usuApePaterno"), (String) map.get("usuApeMaterno"), (String) map.get("usuTelefono"), (String) map.get("usuNumeroDocumento"), (String) map.get("usuTipoDocumento"), (char) map.get("usuGenero"), (String) map.get("empNombreComercial"), (String) map.get("authRoles"), (String) map.get("authUsername"));
  }


  @Override
  public UsuarioResponse findById(Integer id) {
    log.info("Implements :: findById :: " + id);
    return repository.findById(id).map(this::convertEntityToResponse).orElse(new UsuarioResponse());
  }

  @Override
  public UsuarioDTO create(UsuarioDTORequest request) {
    log.debug("Implements :: create :: Inicio");

    UsuarioEntity usuarioCreado = convertRequestDTOToEntity(request);
    usuarioCreado.setEmpresaId(request.getImpId());
    UsuarioEntity saveUsuario = repository.save(usuarioCreado);

    UsuarioAuthorityEntity usuarioAuthority = new UsuarioAuthorityEntity();
    usuarioAuthority.setUsuId(saveUsuario.getUsuId());
    usuarioAuthority.setAuthIsActive(true);
    usuarioAuthority.setAuthUsername(request.getAuthUsername());
    usuarioAuthority.setAuthRoles(request.getAuthRoles());
    usuarioAuthority.setAuthPassword(passwordEncoder.encode(request.getAuthPassword()));
    usuarioAuthorityRepository.save(usuarioAuthority);

    FxComunes.printJson("UsuarioCreadoRequest", request);
    return convertEntityToDTOResponse(saveUsuario);
  }

  @Override
  public UsuarioResponse update(UsuarioRequest request) {//request.getUsuId()
    UsuarioResponse res = repository.findById(request.getUsuId()).map(this::convertEntityToResponse).orElse(new UsuarioResponse());
    //request.setUsuClave(passwordEncoder.encode(request.getUsuClave()));
    if (res.getUsuId() == null) {
      return new UsuarioResponse();
    } else {
      return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
    }
  }

  @Override
  public UsuarioResponse delete(Integer id) {
    log.debug("Implements :: delete :: ID -> {}", id);
    UsuarioResponse res = repository.findById(id).map(this::convertEntityToResponse).orElse(new UsuarioResponse());
    Optional<UsuarioEntity> ent = repository.findById(res.getUsuId());
    if (ent.isPresent()) {
      return convertEntityToResponse(repository.save(convertRequestToEntity(convertResponseToRequest(res))));
    } else {
      return new UsuarioResponse();
    }
  }

  @Override
  public UsuarioAuthorityResponse findUsuarioAuthorityByUsuId(Integer usuId) {
    log.debug("Implements :: findUsuarioAuthorityByUsuId :: " + usuId);
    return usuarioAuthorityRepository.findByUsuarioId(usuId).map(this::convertUsuarioAuthorityEntityToResponse).orElse(new UsuarioAuthorityResponse());
  }

  public UsuarioResponse findByPhone(String celular) {
    UsuarioEntity user = repository.findByUsuTelefono(celular);
    if (user != null) {
      UsuarioResponse response = new UsuarioResponse();
      response.setUsuNombre(user.getUsuNombre());
      return response;
    }
    return null;
  }

  @Override
  public UsuarioResponse findUsuarioByAuthUsername(String usuUsername) {
    log.debug("Implements :: findByUsuUsername :: " + usuUsername);
    //log.info("CLAVE :: Lima1234.. ----> " + Encriptacion.encriptar("Lima1234.."));
    //log.info("CLAVE :: Lima1234.. ----> " + passwordEncoder.encode("Lima1234.."));
    //log.info("CLAVE :: 1234567890 ----> " + passwordEncoder.encode("1234567890"));
    return repository.findByAuthUsername(usuUsername).map(this::convertEntityToResponse).orElse(new UsuarioResponse());
  }

  private UsuarioEntity convertRequestDTOToEntity(UsuarioDTORequest in) {
    UsuarioEntity out = new UsuarioEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private UsuarioEntity convertRequestToEntity(UsuarioRequest in) {
    UsuarioEntity out = new UsuarioEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private UsuarioResponse convertEntityToResponse(UsuarioEntity in) {
    UsuarioResponse out = new UsuarioResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private UsuarioDTO convertEntityToDTOResponse(UsuarioEntity in) {
    UsuarioDTO out = new UsuarioDTO();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private UsuarioRequest convertResponseToRequest(UsuarioResponse in) {
    UsuarioRequest out = new UsuarioRequest();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private UsuarioAuthorityResponse convertUsuarioAuthorityEntityToResponse(UsuarioAuthorityEntity in) {
    UsuarioAuthorityResponse out = new UsuarioAuthorityResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }
}
