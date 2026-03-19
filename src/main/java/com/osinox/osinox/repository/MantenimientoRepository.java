// ─────────────────────────────────────────────────────────────
// MantenimientoRepository.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.repository;
 
import com.osinox.osinox.entity.Mantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
 
public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Long> {
    Optional<Mantenimiento> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
    List<Mantenimiento> findByEstado(Mantenimiento.EstadoMantenimiento estado);
    List<Mantenimiento> findByTecnico_Id(Long idTecnico);
    List<Mantenimiento> findByCliente_Id(Long idCliente);
    List<Mantenimiento> findByProyecto_Id(Long idProyecto);
}