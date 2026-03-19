// CategoriaProductoRepository.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.repository;
 
import com.osinox.osinox.entity.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
 
public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Long> {
    Optional<CategoriaProducto> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
 