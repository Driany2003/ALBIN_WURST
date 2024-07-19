package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IUsuariosService;
import com.pe.kenpis.model.api.usuario.UsuarioRequest;
import com.pe.kenpis.model.api.usuario.UsuarioResponse;
import com.pe.kenpis.model.api.usuario.authority.UsuarioAuthorityResponse;
import com.pe.kenpis.model.entity.UsuariosAuthorityEntity;
import com.pe.kenpis.model.entity.UsuariosEntity;
import com.pe.kenpis.repository.UsuarioAuthorityRepository;
import com.pe.kenpis.repository.UsuariosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuariosImpl implements IUsuariosService {

  private final UsuariosRepository repository;

  private final UsuarioAuthorityRepository usuarioAuthorityRepository;

  @Override
  public UsuarioAuthorityResponse findUsuarioAuthorityByUsuId(Integer usuId) {
    log.debug("Implements :: findUsuarioAuthorityByUsuId :: " + usuId);
    return usuarioAuthorityRepository.findByUsuarioId(usuId).map(this::convertUsuarioAuthorityEntityToResponse).orElse(new UsuarioAuthorityResponse());
  }

  @Override
  public UsuarioResponse findUsuarioByAuthUsername(String usuUsername) {
    log.debug("Implements :: findByUsuUsername :: " + usuUsername);
    //log.info("CLAVE :: Lima1234.. ----> " + Encriptacion.encriptar("Lima1234.."));
    //log.info("CLAVE :: Lima1234.. ----> " + passwordEncoder.encode("Lima1234.."));
    //log.info("CLAVE :: 1234567890 ----> " + passwordEncoder.encode("1234567890"));
    return repository.findByAuthUsername(usuUsername).map(this::convertUsuarioEntityToResponse).orElse(new UsuarioResponse());
  }

  private UsuarioResponse convertUsuarioEntityToResponse(UsuariosEntity in) {
    UsuarioResponse out = new UsuarioResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private UsuariosEntity convertUsuarioRequestToEntity(UsuarioRequest in) {
    UsuariosEntity out = new UsuariosEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private UsuarioAuthorityResponse convertUsuarioAuthorityEntityToResponse(UsuariosAuthorityEntity in) {
    UsuarioAuthorityResponse out = new UsuarioAuthorityResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }
}
