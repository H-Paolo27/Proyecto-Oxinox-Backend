// ─────────────────────────────────────────────────────────────
// ContratoTecnicoRepository.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.repository;
 
import com.osinox.osinox.entity.ContratoTecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
 
public interface ContratoTecnicoRepository extends JpaRepository<ContratoTecnico, Long> {
    Optional<ContratoTecnico> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
    List<ContratoTecnico> findByEstado(ContratoTecnico.EstadoContrato estado);
    List<ContratoTecnico> findByCliente_Id(Long idCliente);
    List<ContratoTecnico> findByProyecto_Id(Long idProyecto);
}