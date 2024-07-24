package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {

  Optional<ClienteEntity> findByCliTelefono(String phone);

}
