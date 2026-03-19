package com.osinox.osinox.repository;

import com.osinox.osinox.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // ── los que ya tenías ──────────────────────────────────────
    Optional<Producto> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
    List<Producto> findByEstado(Boolean estado);
    List<Producto> findByCategoria_Id(Long idCategoria);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT p FROM Producto p WHERE p.stockActual <= p.stockMinimo AND p.estado = true")
    List<Producto> findProductosBajoStockMinimo();

    // ── nuevos para el service de productos ───────────────────

    // Público: solo activos, filtra por nombre y/o categoría
    @Query("""
        SELECT p FROM Producto p
        WHERE p.estado = true
          AND (:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
          AND (:idCategoria IS NULL OR p.categoria.id = :idCategoria)
    """)
    List<Producto> buscarActivos(
        @Param("nombre") String nombre,
        @Param("idCategoria") Long idCategoria
    );

    // Admin: incluye inactivos, filtros opcionales
    @Query("""
        SELECT p FROM Producto p
        WHERE (:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
          AND (:idCategoria IS NULL OR p.categoria.id = :idCategoria)
          AND (:estado IS NULL OR p.estado = :estado)
    """)
    List<Producto> buscarAdmin(
        @Param("nombre") String nombre,
        @Param("idCategoria") Long idCategoria,
        @Param("estado") Boolean estado
    );
}