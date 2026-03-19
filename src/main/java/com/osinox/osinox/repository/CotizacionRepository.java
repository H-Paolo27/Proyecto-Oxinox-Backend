// ─────────────────────────────────────────────────────────────
// CotizacionRepository.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.repository;
 
import com.osinox.osinox.entity.Cotizacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
 
public interface CotizacionRepository extends JpaRepository<Cotizacion, Long> {
    Optional<Cotizacion> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
    List<Cotizacion> findByEstado(Cotizacion.EstadoCotizacion estado);
    List<Cotizacion> findByCliente_Id(Long idCliente);
    List<Cotizacion> findByVendedor_Id(Long idVendedor);
    List<Cotizacion> findByProyecto_Id(Long idProyecto);
    Page<Cotizacion> findByEstado(Cotizacion.EstadoCotizacion estado, Pageable pageable);
    Page<Cotizacion> findByCliente_Id(Long clienteId, Pageable pageable);
    Page<Cotizacion> findByCodigoContainingIgnoreCase(String q, Pageable pageable);
}