package com.osinox.osinox.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.osinox.osinox.entity.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByNumeroDocumento(String numeroDocumento);
    Optional<Persona> findByEmail(String email);
    boolean existsByNumeroDocumento(String numeroDocumento);
    Page<Persona> findByNombreRazonSocialContainingIgnoreCase(String nombre, Pageable pageable);
    Page<Persona> findByTipoRelacion(Persona.TipoRelacion tipoRelacion, Pageable pageable);
    Page<Persona> findByEstado(Boolean estado, Pageable pageable);

    @Modifying
    @Query(value = "DELETE FROM persona WHERE id_persona = :id", nativeQuery = true)
    void deletePersonaById(@Param("id") Long id);
 // Busca por nombre O documento, filtrando por tipoRelacion (para autocomplete de clientes)
    @Query("SELECT p FROM Persona p WHERE " +
           "(LOWER(p.nombreRazonSocial) LIKE LOWER(CONCAT('%', :q, '%')) " +
           "OR p.numeroDocumento LIKE CONCAT('%', :q, '%')) " +
           "AND p.tipoRelacion = :tipoRelacion " +
           "AND p.estado = true")
    Page<Persona> buscarPorNombreODocumentoYTipo(
        @Param("q") String q,
        @Param("tipoRelacion") Persona.TipoRelacion tipoRelacion,
        Pageable pageable);

    // Busca por nombre O documento sin filtro de tipo (búsqueda general)
    @Query("SELECT p FROM Persona p WHERE " +
           "(LOWER(p.nombreRazonSocial) LIKE LOWER(CONCAT('%', :q, '%')) " +
           "OR p.numeroDocumento LIKE CONCAT('%', :q, '%')) " +
           "AND p.estado = true")
    Page<Persona> buscarPorNombreODocumento(
        @Param("q") String q,
        Pageable pageable);
}