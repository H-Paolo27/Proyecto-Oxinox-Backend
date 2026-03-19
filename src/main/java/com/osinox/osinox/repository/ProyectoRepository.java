package com.osinox.osinox.repository;

import com.osinox.osinox.entity.Proyecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    Optional<Proyecto> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);

    // ── listas simples (las que ya tenías) ──
    List<Proyecto> findByEstado(Proyecto.EstadoProyecto estado);
    List<Proyecto> findByCliente_Id(Long idCliente);
    List<Proyecto> findByResponsable_Id(Long idResponsable);
    List<Proyecto> findByNombreContainingIgnoreCase(String nombre);

    // ── paginados (nuevos) ──
    Page<Proyecto> findByNombreContainingIgnoreCase(String q, Pageable pageable);
    Page<Proyecto> findByEstado(Proyecto.EstadoProyecto estado, Pageable pageable);
    Page<Proyecto> findByCliente_Id(Long clienteId, Pageable pageable);
}