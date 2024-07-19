package com.pe.kenpis.business.websec;

import com.pe.kenpis.model.entity.UsuariosAuthorityEntity;
import com.pe.kenpis.repository.UsuarioAuthorityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UsuarioAuthorityRepository usuarioAuthorityRepository;

  @Override
  public UserDetails loadUserByUsername(String authUsername) throws UsernameNotFoundException {
    Optional<UsuariosAuthorityEntity> user = usuarioAuthorityRepository.findByUsername(authUsername);
    user.orElseThrow(() -> new UsernameNotFoundException(authUsername + " not found."));
    return user.map(UserDetailsImpl::new).get();
  }

}
