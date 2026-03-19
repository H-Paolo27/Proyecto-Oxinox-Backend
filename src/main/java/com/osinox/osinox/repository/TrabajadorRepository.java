package com.osinox.osinox.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.osinox.osinox.entity.Trabajador;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {

    Optional<Trabajador> findByPersonaId(Long personaId);
    
    boolean existsByPersonaId(Long personaId);
    
 // ── agregar estas dos ──
    
    Page<Trabajador> findByEstado(Trabajador.EstadoTrabajador estado, Pageable pageable);

    Page<Trabajador> findByPersonaNombreRazonSocialContainingIgnoreCase(String q, Pageable pageable);

 // Lista simple de trabajadores activos (para selector/dropdown)
    List<Trabajador> findByEstadoOrderByPersonaNombreRazonSocialAsc(
        Trabajador.EstadoTrabajador estado);
}