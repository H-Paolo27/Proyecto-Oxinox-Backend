// ─────────────────────────────────────────────────────────────
// AuditoriaRepository.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.repository;
 
import com.osinox.osinox.entity.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
 
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
    List<Auditoria> findByEntidadAndEntidadIdOrderByFechaDesc(String entidad, Long entidadId);
    List<Auditoria> findByUsuario_IdOrderByFechaDesc(Long idUsuario);
    List<Auditoria> findByAccion(String accion);
}