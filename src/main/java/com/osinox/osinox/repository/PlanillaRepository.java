package com.osinox.osinox.repository;

import com.osinox.osinox.entity.Planilla;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanillaRepository extends JpaRepository<Planilla, Long> {
    Optional<Planilla> findByPeriodoAndTrabajador_Id(String periodo, Long idTrabajador);
    List<Planilla> findByPeriodo(String periodo);
    List<Planilla> findByEstado(Planilla.EstadoPlanilla estado);
    Page<Planilla> findByTrabajadorId(Long trabajadorId, Pageable pageable);

    Page<Planilla> findByPeriodo(String periodo, Pageable pageable);
    Page<Planilla> findByEstado(Planilla.EstadoPlanilla estado, Pageable pageable);

    
}