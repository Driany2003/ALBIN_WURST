package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.UsuariosAuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioAuthorityRepository extends JpaRepository<UsuariosAuthorityEntity, Integer> {

  @Query("SELECT u FROM UsuariosAuthorityEntity u WHERE u.authUsername = :authUsername")
  Optional<UsuariosAuthorityEntity> findByUsername(@Param("authUsername") String authUsername);

  @Query("SELECT u FROM UsuariosAuthorityEntity u WHERE u.usuId = :usuId")
  Optional<UsuariosAuthorityEntity> findByUsuarioId(@Param("usuId") Integer usuId);
}
