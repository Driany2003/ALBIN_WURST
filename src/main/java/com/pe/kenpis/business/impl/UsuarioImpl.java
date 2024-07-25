package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IUsuarioService;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioImpl implements IUsuarioService {

  private final UsuarioRepository repository;

  private final UsuarioAuthorityRepository usuarioAuthorityRepository;

  @Override
  public List<UsuarioResponse> findAll() {
    log.info("Implements :: findAll");
    return repository.findAll().stream().map(this::convertEntityToResponse).collect(Collectors.toList());
  }

  @Override
  public UsuarioResponse findById(Integer id) {
    log.info("Implements :: findById :: " + id);
    return repository.findById(id).map(this::convertEntityToResponse).orElse(new UsuarioResponse());
  }

  @Override
  public UsuarioResponse create(UsuarioRequest request) {
    log.debug("Implements :: create :: Inicio");
    FxComunes.printJson("UsuarioRequest",request);
    return convertEntityToResponse(repository.save(convertRequestToEntity(request)));
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
