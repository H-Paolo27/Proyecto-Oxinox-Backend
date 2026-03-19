package com.osinox.osinox.repository;

import com.osinox.osinox.entity.Notificacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    Page<Notificacion> findByUsuarioId(Long usuarioId, Pageable pageable);
    Page<Notificacion> findByUsuarioIdAndLeida(Long usuarioId, Boolean leida, Pageable pageable);
    long countByUsuarioIdAndLeida(Long usuarioId, Boolean leida);

    @Modifying
    @Query("UPDATE Notificacion n SET n.leida = true WHERE n.usuario.id = :usuarioId")
    void marcarTodasLeidas(@Param("usuarioId") Long usuarioId);
    void deleteByUsuarioIdAndLeidaTrue(Long usuarioId);
}