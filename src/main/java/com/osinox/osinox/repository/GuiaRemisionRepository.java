// ─────────────────────────────────────────────────────────────
// GuiaRemisionRepository.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.repository;
 
import com.osinox.osinox.entity.GuiaRemision;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
 
public interface GuiaRemisionRepository extends JpaRepository<GuiaRemision, Long> {
    Optional<GuiaRemision> findBySerieAndCorrelativo(String serie, String correlativo);
    List<GuiaRemision> findByEstado(GuiaRemision.EstadoGuia estado);
    List<GuiaRemision> findByDestinatario_Id(Long idDestinatario);
    List<GuiaRemision> findByProyecto_Id(Long idProyecto);
}