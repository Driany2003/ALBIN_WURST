package com.pe.kenpis.business.websec;

import com.pe.kenpis.model.entity.UsuariosAuthorityEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsImpl implements UserDetails {

  private static final long serialVersionUID = 1L;
  private String userName;
  private String password;
  private String roles;
  private boolean estado;
  private List<GrantedAuthority> authorities;

  public UserDetailsImpl(UsuariosAuthorityEntity user) {
    this.userName = user.getAuthUsername();
    this.password = user.getAuthPassword();
    this.roles = user.getAuthroles();
    this.estado = user.getAuthIsActive();
    this.authorities = Arrays.stream(roles.split(";")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

  public UserDetailsImpl() {
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return userName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return estado;
  }

}
