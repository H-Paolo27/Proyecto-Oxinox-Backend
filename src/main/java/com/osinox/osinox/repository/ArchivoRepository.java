// ─────────────────────────────────────────────────────────────
// ArchivoRepository.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.repository;
 
import com.osinox.osinox.entity.Archivo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
 
public interface ArchivoRepository extends JpaRepository<Archivo, Long> {
    List<Archivo> findByEntidadTipoAndEntidadId(String entidadTipo, Long entidadId);
    List<Archivo> findByUsuario_Id(Long idUsuario);
}