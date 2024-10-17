package com.pe.kenpis.repository;

import com.pe.kenpis.model.api.cliente.ClienteDTO;
import com.pe.kenpis.model.api.cliente.ClienteResponse;
import com.pe.kenpis.model.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {

  Optional<ClienteEntity> findByCliTelefono(String phone);

  @Query(value = "SELECT " + "    c.cli_id AS cliId, " + "    c.cli_nombre AS cliNombre, " + "    c.cli_telefono AS cliTelefono, " + "    c.cli_correo AS cliCorreo, " + "    c.cli_is_active AS cliIsActive, " + "    c.cli_notificacion AS cliNotificacion, " + "    e.emp_nombre_comercial AS empNombreComercial " + "FROM " + "    T_CLIENTE c " + "INNER JOIN " + "    T_EMPRESA e ON e.emp_id = c.emp_id " + "WHERE " + "    e.emp_is_active = 1 AND e.emp_id = :empId", nativeQuery = true)
  List<Map<String, Object>> findAllClientesByEmpresaId(@Param("empId") Integer empId);

  @Query(value = "SELECT " + "    c.cli_id AS cliId, " + "    c.cli_nombre AS cliNombre, " + "    c.cli_telefono AS cliTelefono, " + "    c.cli_correo AS cliCorreo, " + "    c.cli_is_active AS cliIsActive, " + "    c.cli_notificacion AS cliNotificacion, " + "    e.emp_nombre_comercial AS empNombreComercial " + "FROM " + "    T_CLIENTE c " + "INNER JOIN " + "    T_EMPRESA e ON e.emp_id = c.emp_id " + "WHERE " + "    e.emp_is_active = 1", nativeQuery = true)
  List<Map<String, Object>> findActiveClientes();

}
