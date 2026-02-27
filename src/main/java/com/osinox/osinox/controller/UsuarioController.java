package com.osinox.osinox.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import lombok.RequiredArgsConstructor;
import com.osinox.osinox.service.UsuarioService;
import com.osinox.osinox.repository.UsuarioRepository;
import com.osinox.osinox.mapper.UsuarioMapper;
import com.osinox.osinox.dto.UsuarioDto;
import com.osinox.osinox.entity.Usuario;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuario")
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> getAllUsers() {
        List<Usuario> usuarios = usuarioService.getAllUsers();
        if (usuarios.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        List<UsuarioDto> dtos = usuarios.stream().map(UsuarioMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<Usuario> opt = usuarioRepository.findById(id);
        if (opt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        return ResponseEntity.ok(UsuarioMapper.toDto(opt.get()));
    }
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> body) {
        try {
            String username = body.get("username");
            String password = body.get("password");
            String role = body.get("role");

            Usuario nuevo = usuarioService.registerUser(username, password, role);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(UsuarioMapper.toDto(nuevo));

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}/username")
    public ResponseEntity<?> updateUsername(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            Usuario u = usuarioService.updateUsername(id, body.get("username"));
            return ResponseEntity.ok(UsuarioMapper.toDto(u));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            usuarioService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<?> activar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(UsuarioMapper.toDto(usuarioService.activar(id)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<?> desactivar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(UsuarioMapper.toDto(usuarioService.desactivar(id)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<?> cambiarPassword(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            usuarioService.cambiarPassword(id, body.get("password"));
            return ResponseEntity.ok("Password actualizado");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/roles/{rolId}")
    public ResponseEntity<?> asignarRol(@PathVariable Long id, @PathVariable Long rolId) {
        try {
            return ResponseEntity.ok(UsuarioMapper.toDto(usuarioService.asignarRol(id, rolId)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/roles/{rolId}")
    public ResponseEntity<?> quitarRol(@PathVariable Long id, @PathVariable Long rolId) {
        try {
            return ResponseEntity.ok(UsuarioMapper.toDto(usuarioService.quitarRol(id, rolId)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}