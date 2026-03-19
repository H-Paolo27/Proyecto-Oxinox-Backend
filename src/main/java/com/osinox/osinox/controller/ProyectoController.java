// ─────────────────────────────────────────────────────────────
// ProyectoController.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.controller;
 
import com.osinox.osinox.dto.ProyectoDto;
import com.osinox.osinox.service.ProyectoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
 
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/proyecto")
@PreAuthorize("hasAnyRole('ADMIN','GERENTE','JEFE_TALLER')")
public class ProyectoController {
 
    private final ProyectoService proyectoService;
 
    @GetMapping
    public ResponseEntity<Page<ProyectoDto>> getAll(
            @PageableDefault(size = 15, sort = "fechaRegistro") Pageable pageable) {
        Page<ProyectoDto> page = proyectoService.getAll(pageable);
        return page.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(page);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(proyectoService.getById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
 
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<?> create(@RequestBody ProyectoDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(proyectoService.create(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProyectoDto dto) {
        try {
            return ResponseEntity.ok(proyectoService.update(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 
    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','JEFE_TALLER')")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        try {
            return ResponseEntity.ok(proyectoService.cambiarEstado(id, estado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            proyectoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 // Buscar por nombre - GET /api/v1/proyecto/buscar?q=
    @GetMapping("/buscar")
    public ResponseEntity<Page<ProyectoDto>> buscar(
            @RequestParam String q,
            @PageableDefault(size = 15) Pageable pageable) {
        try {
            return ResponseEntity.ok(proyectoService.buscar(q, pageable));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Filtrar por estado - GET /api/v1/proyecto/estado/EN_PROCESO
    @GetMapping("/estado/{estado}")
    public ResponseEntity<Page<ProyectoDto>> getByEstado(
            @PathVariable String estado,
            @PageableDefault(size = 15) Pageable pageable) {
        try {
            return ResponseEntity.ok(proyectoService.getByEstado(estado, pageable));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Filtrar por cliente - GET /api/v1/proyecto/cliente/5
    @GetMapping("/cliente/{clienteId}")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<Page<ProyectoDto>> getByCliente(
            @PathVariable Long clienteId,
            @PageableDefault(size = 15) Pageable pageable) {
        try {
            return ResponseEntity.ok(proyectoService.getByCliente(clienteId, pageable));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}