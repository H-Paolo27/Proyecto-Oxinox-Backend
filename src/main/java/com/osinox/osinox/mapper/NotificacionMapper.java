// ─────────────────────────────────────────────────────────────
// NotificacionMapper.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.mapper;
 
import com.osinox.osinox.entity.Notificacion;
import com.osinox.osinox.entity.Usuario;
import com.osinox.osinox.dto.NotificacionDto;
 
public class NotificacionMapper {
 
    public static NotificacionDto toDto(Notificacion n) {
        if (n == null) return null;
        Long usuarioId = n.getUsuario() == null ? null : n.getUsuario().getId();
        return new NotificacionDto(
            n.getId(),
            usuarioId,
            n.getTitulo(),
            n.getMensaje(),
            n.getTipo() == null ? null : n.getTipo().name(),
            n.getLeida(),
            n.getEntidadTipo(),
            n.getEntidadId(),
            n.getFechaRegistro()
        );
    }
 
    public static Notificacion toEntity(NotificacionDto d) {
        if (d == null) return null;
        Notificacion n = new Notificacion();
        n.setId(d.getId());
        if (d.getUsuarioId() != null) {
            Usuario u = new Usuario();
            u.setId(d.getUsuarioId());
            n.setUsuario(u);
        }
        n.setTitulo(d.getTitulo());
        n.setMensaje(d.getMensaje());
        if (d.getTipo() != null)
            n.setTipo(Notificacion.TipoNotificacion.valueOf(d.getTipo()));
        n.setLeida(d.getLeida());
        n.setEntidadTipo(d.getEntidadTipo());
        n.setEntidadId(d.getEntidadId());
        return n;
    }
}