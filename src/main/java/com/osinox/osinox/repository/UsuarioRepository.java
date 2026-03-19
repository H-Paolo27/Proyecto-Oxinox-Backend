package com.osinox.osinox.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.osinox.osinox.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByUsernameOrEmailAcceso(String username, String emailAcceso);
    Optional<Usuario> findByEmailAcceso(String emailAcceso);
    List<Usuario> findByEmailAccesoContainingIgnoreCase(String emailAcceso);
    
    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.roles WHERE u.username = :username")
    Optional<Usuario> findByUsernameWithRoles(@jakarta.annotation.Nonnull String username);
    
    List<Usuario> findByUsernameContainingIgnoreCase(String username);
    
    @Modifying
    @Query(value = "DELETE FROM usuario_rol WHERE id_usuario = :id", nativeQuery = true)
    void deleteRolesByUsuarioId(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM usuario WHERE id_usuario = :id", nativeQuery = true)
    void deleteUsuarioById(@Param("id") Long id);


}