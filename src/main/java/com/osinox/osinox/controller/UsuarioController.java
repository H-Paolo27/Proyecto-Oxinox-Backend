package com.osinox.osinox.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        return ResponseEntity.ok(usuarios.stream()
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/paginado")
    public ResponseEntity<?> getPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> pageResult = usuarioRepository.findAll(pageable);
        return ResponseEntity.ok(Map.of(
            "usuarios", pageResult.getContent().stream()
                    .map(UsuarioMapper::toDto).collect(Collectors.toList()),
            "totalPages", pageResult.getTotalPages(),
            "totalElements", pageResult.getTotalElements(),
            "currentPage", pageResult.getNumber()
        ));
    }

    // Busca por username O emailAcceso
    @GetMapping("/buscar")
    public ResponseEntity<?> buscar(@RequestParam String q) {
        List<Usuario> porUsername = usuarioRepository.findByUsernameContainingIgnoreCase(q);
        List<Usuario> porEmail = usuarioRepository.findByEmailAccesoContainingIgnoreCase(q);
        List<Usuario> combinados = java.util.stream.Stream
                .concat(porUsername.stream(), porEmail.stream())
                .distinct()
                .collect(Collectors.toList());
        if (combinados.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron usuarios");
        return ResponseEntity.ok(combinados.stream()
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList()));
    }

    // Verifica si username O email ya existe
    @GetMapping("/existe")
    public ResponseEntity<?> existe(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email) {
        boolean existeUsername = username != null &&
                usuarioRepository.findByUsername(username).isPresent();
        boolean existeEmail = email != null &&
                usuarioRepository.findByEmailAcceso(email).isPresent();
        return ResponseEntity.ok(Map.of(
            "existeUsername", existeUsername,
            "existeEmail", existeEmail
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<Usuario> opt = usuarioRepository.findById(id);
        if (opt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        return ResponseEntity.ok(UsuarioMapper.toDto(opt.get()));
    }

    // Crear usuario interno con todos los campos
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> body) {
        try {
            Usuario nuevo = usuarioService.registerInterno(
                body.get("username"),
                body.get("emailAcceso"),
                body.get("password"),
                body.get("roleName"),
                body.get("nombreCompleto"),
                body.get("telefono"),
                body.get("tipoDocumento"),
                body.get("numeroDocumento")
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(nuevo));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/username")
    public ResponseEntity<?> updateUsername(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        try {
            return ResponseEntity.ok(UsuarioMapper.toDto(
                usuarioService.updateUsername(id, body.get("username"))));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/email")
    public ResponseEntity<?> updateEmail(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        try {
            return ResponseEntity.ok(UsuarioMapper.toDto(
                usuarioService.updateEmailAcceso(id, body.get("emailAcceso"))));
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
            if (e.getMessage().contains("no encontrado"))
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
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
    public ResponseEntity<?> cambiarPassword(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        try {
            usuarioService.cambiarPassword(id, body.get("password"));
            return ResponseEntity.ok(Map.of("mensaje", "Password actualizado correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/roles")
    public ResponseEntity<?> getRoles(@PathVariable Long id) {
        try {
            Optional<Usuario> opt = usuarioRepository.findById(id);
            if (opt.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            List<Map<String, Object>> roles = opt.get().getRoles().stream()
                    .map(r -> Map.of(
                        "id", (Object) r.getId(),
                        "nombre", r.getNombre(),
                        "descripcion", r.getDescripcion() != null ? r.getDescripcion() : ""
                    ))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(roles);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
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