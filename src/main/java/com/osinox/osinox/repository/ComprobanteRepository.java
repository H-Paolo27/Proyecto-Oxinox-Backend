package com.osinox.osinox.repository;

import com.osinox.osinox.entity.Comprobante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ComprobanteRepository extends JpaRepository<Comprobante, Long> {

    // ── búsquedas simples existentes ──
    Optional<Comprobante> findByTipoAndSerieAndCorrelativo(
        Comprobante.TipoComprobante tipo, String serie, String correlativo);
    List<Comprobante> findByEstado(Comprobante.EstadoComprobante estado);
    List<Comprobante> findByPersona_Id(Long idPersona);
    List<Comprobante> findByProyecto_Id(Long idProyecto);
    List<Comprobante> findByTipo(Comprobante.TipoComprobante tipo);

    // ── paginados nuevos ──
    Page<Comprobante> findByTipo(Comprobante.TipoComprobante tipo, Pageable pageable);
    Page<Comprobante> findByEstado(Comprobante.EstadoComprobante estado, Pageable pageable);
    Page<Comprobante> findByPersonaId(Long personaId, Pageable pageable);
    Optional<Comprobante> findBySerieAndCorrelativo(String serie, String correlativo);
}