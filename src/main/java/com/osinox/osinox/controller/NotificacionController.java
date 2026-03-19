// ─────────────────────────────────────────────────────────────
// NotificacionController.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.controller;
 
import com.osinox.osinox.dto.NotificacionDto;
import com.osinox.osinox.entity.Usuario;
import com.osinox.osinox.repository.UsuarioRepository;
import com.osinox.osinox.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import com.osinox.osinox.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
 
import java.util.Map;
 
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notificacion")
@PreAuthorize("isAuthenticated()")
public class NotificacionController {

    private final NotificacionService notificacionService;
    private final UsuarioRepository usuarioRepository;

    // ── helper: verifica que el usuarioId del path es del usuario autenticado
    //           o que es ADMIN
    private void validarAcceso(Long usuarioId, Authentication auth) {
        boolean esAdmin = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (esAdmin) return;
        Usuario u = usuarioRepository
            .findByUsernameOrEmailAcceso(auth.getName(), auth.getName())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!u.getId().equals(usuarioId))
            throw new org.springframework.security.access.AccessDeniedException(
                "No puedes acceder a notificaciones de otro usuario");
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Page<NotificacionDto>> getByUsuario(
            @PathVariable Long usuarioId,
            @PageableDefault(size = 20) Pageable pageable,
            Authentication auth) {
        validarAcceso(usuarioId, auth);
        Page<NotificacionDto> page = notificacionService.getByUsuario(usuarioId, pageable);
        return page.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(page);
    }

    @GetMapping("/usuario/{usuarioId}/no-leidas")
    public ResponseEntity<Page<NotificacionDto>> getNoLeidas(
            @PathVariable Long usuarioId,
            @PageableDefault(size = 20) Pageable pageable,
            Authentication auth) {
        validarAcceso(usuarioId, auth);
        Page<NotificacionDto> page = notificacionService.getNoLeidas(usuarioId, pageable);
        return page.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(page);
    }

    @GetMapping("/usuario/{usuarioId}/count")
    public ResponseEntity<Map<String, Long>> countNoLeidas(
            @PathVariable Long usuarioId,
            Authentication auth) {
        validarAcceso(usuarioId, auth);
        return ResponseEntity.ok(Map.of("noLeidas", notificacionService.countNoLeidas(usuarioId)));
    }

    @PatchMapping("/{id}/leer")
    public ResponseEntity<?> marcarLeida(@PathVariable Long id) {
        try {
            notificacionService.marcarLeida(id);
            return ResponseEntity.ok(Map.of("mensaje", "Notificación marcada como leída"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/usuario/{usuarioId}/leer-todas")
    public ResponseEntity<?> marcarTodasLeidas(
            @PathVariable Long usuarioId,
            Authentication auth) {
        validarAcceso(usuarioId, auth);
        notificacionService.marcarTodasLeidas(usuarioId);
        return ResponseEntity.ok(Map.of("mensaje", "Todas las notificaciones marcadas como leídas"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            notificacionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<?> create(@RequestBody NotificacionDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificacionService.create(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/usuario/{usuarioId}/leidas")
    public ResponseEntity<?> deleteLeidas(
            @PathVariable Long usuarioId,
            Authentication auth) {
        validarAcceso(usuarioId, auth);
        notificacionService.deleteLeidas(usuarioId);
        return ResponseEntity.ok(Map.of("mensaje", "Notificaciones leídas eliminadas"));
    }
}