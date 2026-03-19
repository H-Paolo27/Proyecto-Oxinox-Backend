package com.osinox.osinox.repository;

import com.osinox.osinox.entity.OrdenProduccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdenProduccionRepository extends JpaRepository<OrdenProduccion, Long> {
    Optional<OrdenProduccion> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
    List<OrdenProduccion> findByEstado(OrdenProduccion.EstadoOrden estado);
    List<OrdenProduccion> findByProyecto_Id(Long idProyecto);
    List<OrdenProduccion> findByResponsable_Id(Long idResponsable);
    Page<OrdenProduccion> findByProyectoId(Long proyectoId, Pageable pageable);
}