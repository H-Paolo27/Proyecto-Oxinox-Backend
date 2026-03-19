// ─────────────────────────────────────────────────────────────
// NotificacionService.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.service;
 
import com.osinox.osinox.dto.NotificacionDto;
import com.osinox.osinox.entity.Notificacion;
import com.osinox.osinox.mapper.NotificacionMapper;
import com.osinox.osinox.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
@Service
@RequiredArgsConstructor
public class NotificacionService {
 
    private final NotificacionRepository notificacionRepository;
 
    public Page<NotificacionDto> getByUsuario(Long usuarioId, Pageable pageable) {
        return notificacionRepository.findByUsuarioId(usuarioId, pageable)
                .map(NotificacionMapper::toDto);
    }
 
    public Page<NotificacionDto> getNoLeidas(Long usuarioId, Pageable pageable) {
        return notificacionRepository.findByUsuarioIdAndLeida(usuarioId, false, pageable)
                .map(NotificacionMapper::toDto);
    }
 
    public long countNoLeidas(Long usuarioId) {
        return notificacionRepository.countByUsuarioIdAndLeida(usuarioId, false);
    }
 
    @Transactional
    public void marcarLeida(Long id) {
        Notificacion n = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        n.setLeida(true);
        notificacionRepository.save(n);
    }
 
    @Transactional
    public void marcarTodasLeidas(Long usuarioId) {
        notificacionRepository.marcarTodasLeidas(usuarioId);
    }
 
    @Transactional
    public void delete(Long id) {
        if (!notificacionRepository.existsById(id))
            throw new RuntimeException("Notificación no encontrada");
        notificacionRepository.deleteById(id);
    }
    // Para DELETE /api/v1/notificacion/usuario/{usuarioId}/leidas
    @Transactional
    public void deleteLeidas(Long usuarioId) {
        notificacionRepository.deleteByUsuarioIdAndLeidaTrue(usuarioId);
    }
    public NotificacionDto create(NotificacionDto dto) {
        Notificacion n = NotificacionMapper.toEntity(dto);
        n.setLeida(false); // ✅ siempre false al crear
        return NotificacionMapper.toDto(notificacionRepository.save(n));
    }
}