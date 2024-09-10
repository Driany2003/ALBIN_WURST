package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.UsuarioAuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioAuthorityRepository extends JpaRepository<UsuarioAuthorityEntity, Integer> {

  @Query("SELECT u FROM UsuarioAuthorityEntity u WHERE u.authUsername = :authUsername")
  Optional<UsuarioAuthorityEntity> findByUsername(@Param("authUsername") String authUsername);

  @Query("SELECT u FROM UsuarioAuthorityEntity u WHERE u.usuId = :usuId")
  Optional<UsuarioAuthorityEntity> findByUsuarioId(@Param("usuId") Integer usuId);
  
}
