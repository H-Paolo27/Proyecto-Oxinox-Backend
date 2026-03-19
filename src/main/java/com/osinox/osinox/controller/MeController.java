package com.osinox.osinox.controller;

import com.osinox.osinox.mapper.UsuarioMapper;
import com.osinox.osinox.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/me")
@PreAuthorize("isAuthenticated()")
public class MeController {

    private final UsuarioRepository usuarioRepository;

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<?> getMe(Authentication authentication) {
        return usuarioRepository
            .findByUsernameOrEmailAcceso(
                authentication.getName(),
                authentication.getName()
            )
            .map(u -> {
                // Devolver solo los campos necesarios sin usar el mapper
                Map<String, Object> result = new java.util.HashMap<>();
                result.put("id", u.getId());
                result.put("username", u.getUsername());
                result.put("emailAcceso", u.getEmailAcceso());
                result.put("activo", u.getActivo());
                result.put("personaId", u.getPersona() != null ? u.getPersona().getId() : null);
                result.put("nombreCompleto", u.getPersona() != null ? u.getPersona().getNombreRazonSocial() : null);
                return ResponseEntity.ok((Object) result);
            })
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}