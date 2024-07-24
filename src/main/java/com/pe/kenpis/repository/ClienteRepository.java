package com.pe.kenpis.repository;

import com.pe.kenpis.model.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {

  ClienteEntity findByCliTelefono(String phone);

}
