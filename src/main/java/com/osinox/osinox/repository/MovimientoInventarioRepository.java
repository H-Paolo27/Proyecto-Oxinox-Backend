// ─────────────────────────────────────────────────────────────
// MovimientoInventarioRepository.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.repository;
 
import com.osinox.osinox.entity.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
 
public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Long> {
    List<MovimientoInventario> findByProducto_IdOrderByFechaDesc(Long idProducto);
    List<MovimientoInventario> findByTipo(MovimientoInventario.TipoMovimiento tipo);
}