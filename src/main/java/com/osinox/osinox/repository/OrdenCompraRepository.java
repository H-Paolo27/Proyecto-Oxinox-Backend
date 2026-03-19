// ─────────────────────────────────────────────────────────────
// OrdenCompraRepository.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.repository;
 
import com.osinox.osinox.entity.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
 
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {
    Optional<OrdenCompra> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
    List<OrdenCompra> findByEstado(OrdenCompra.EstadoOC estado);
    List<OrdenCompra> findByProveedor_Id(Long idProveedor);
    List<OrdenCompra> findBySolicitante_Id(Long idSolicitante);
}