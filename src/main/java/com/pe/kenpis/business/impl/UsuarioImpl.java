package com.pe.kenpis.business.impl;

import com.pe.kenpis.business.IUsuarioService;
import com.pe.kenpis.model.api.usuario.UsuarioRequest;
import com.pe.kenpis.model.api.usuario.UsuarioResponse;
import com.pe.kenpis.model.api.usuario.authority.UsuarioAuthorityResponse;
import com.pe.kenpis.model.entity.UsuarioAuthorityEntity;
import com.pe.kenpis.model.entity.UsuarioEntity;
import com.pe.kenpis.repository.UsuarioAuthorityRepository;
import com.pe.kenpis.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioImpl implements IUsuarioService {

  private final UsuarioRepository repository;

  private final UsuarioAuthorityRepository usuarioAuthorityRepository;

  public UsuarioResponse findByPhone(String celular) {
    UsuarioEntity user = repository.findByUsuTelefono(celular);
    if (user != null) {
      UsuarioResponse response = new UsuarioResponse();
      response.setUsuNombre(user.getUsuNombre());
      return response;
    }
    return null;
  }

  public boolean create(UsuarioRequest usuarioRequest) {
    UsuarioEntity user = new UsuarioEntity();
    user.setUsuTelefono(usuarioRequest.getUsuTelefono());
    user.setUsuNombre(usuarioRequest.getUsuNombre());
    UsuarioEntity guardarUsuario = repository.save(user);
    return guardarUsuario != null;
  }

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

  private UsuarioResponse convertUsuarioEntityToResponse(UsuarioEntity in) {
    UsuarioResponse out = new UsuarioResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private UsuarioEntity convertUsuarioRequestToEntity(UsuarioRequest in) {
    UsuarioEntity out = new UsuarioEntity();
    BeanUtils.copyProperties(in, out);
    return out;
  }

  private UsuarioAuthorityResponse convertUsuarioAuthorityEntityToResponse(UsuarioAuthorityEntity in) {
    UsuarioAuthorityResponse out = new UsuarioAuthorityResponse();
    BeanUtils.copyProperties(in, out);
    return out;
  }
}
