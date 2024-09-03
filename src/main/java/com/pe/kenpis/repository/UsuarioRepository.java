package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

  @Query("SELECT u FROM UsuarioEntity u WHERE u.usuId IN " + "(SELECT ua.usuId FROM UsuarioAuthorityEntity ua WHERE ua.authUsername = :authUsername)")
  Optional<UsuarioEntity> findByAuthUsername(@Param("authUsername") String authUsername);

  UsuarioEntity findByUsuTelefono(String celular);

  List<UsuarioEntity> findByEmpresaId(Integer empresaId);

}
