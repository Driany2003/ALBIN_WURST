package com.pe.kenpis.repository;

import com.pe.kenpis.model.api.empresa.EmpresaResponse;
import com.pe.kenpis.model.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

  @Query("SELECT u FROM UsuarioEntity u WHERE u.usuId IN " + "(SELECT ua.usuId FROM UsuarioAuthorityEntity ua WHERE ua.authUsername = :authUsername)")
  Optional<UsuarioEntity> findByAuthUsername(@Param("authUsername") String authUsername);

  UsuarioEntity findByUsuTelefono(String celular);

  @Query(value = "SELECT u.usu_id AS usuId, u.usu_nombre AS usuNombre, u.usu_ape_paterno AS usuApePaterno, u.usu_ape_materno AS usuApeMaterno, u.usu_telefono AS usuTelefono, " + "u.usu_numero_documento AS usuNumeroDocumento, u.usu_tipo_documento AS usuTipoDocumento, u.usu_genero AS usuGenero, e.emp_nombre_comercial AS empNombreComercial, " + "a.auth_roles AS authRoles, a.auth_username AS authUsername" + " FROM T_USUARIO u " + "INNER JOIN T_EMPRESA e ON u.empresa_id = e.emp_id " + "INNER JOIN T_USUARIO_AUTHORITY a ON u.usu_id = a.usu_id" + " WHERE e.emp_is_active = 1", nativeQuery = true)
  List<Map<String, Object>> findAllUsers();

  @Query(value = "SELECT u.usu_id AS usuId, u.usu_nombre AS usuNombre, u.usu_ape_paterno AS usuApePaterno, " + "u.usu_ape_materno AS usuApeMaterno , u.usu_telefono AS usuTelefono, u.usu_numero_documento AS usuNumeroDocumento, " + "u.usu_tipo_documento AS usuTipoDocumento, u.usu_genero AS usuGenero, e.emp_nombre_comercial AS empNombreComercial, " + "a.auth_roles AS authRoles, a.auth_username AS authUsername" + // Añadir estos campos
      " FROM T_USUARIO u " + "INNER JOIN T_EMPRESA e ON e.emp_id = u.empresa_id " + "INNER JOIN T_USUARIO_AUTHORITY a ON a.usu_id = u.usu_id " + "WHERE e.emp_id = :empresaId", nativeQuery = true)
  List<Map<String, Object>> findUsuariosByEmpresaId(@Param("empresaId") Integer empresaId);


}
