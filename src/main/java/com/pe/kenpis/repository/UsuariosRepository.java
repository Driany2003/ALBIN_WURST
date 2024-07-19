package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.UsuariosAuthorityEntity;
import com.pe.kenpis.model.entity.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuariosRepository extends JpaRepository<UsuariosAuthorityEntity, Integer> {

  @Query("SELECT u FROM UsuariosEntity u WHERE u.usuId IN " + "(SELECT ua.usuId FROM UsuariosAuthorityEntity ua WHERE ua.authUsername = :authUsername)")
  Optional<UsuariosEntity> findByAuthUsername(@Param("authUsername") String authUsername);

}
