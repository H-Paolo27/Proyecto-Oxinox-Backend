package com.osinox.osinox.repository;

import org.springframework.stereotype.Repository;

import com.osinox.osinox.entity.Rol;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombre(String nombre);

}