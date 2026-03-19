// ─────────────────────────────────────────────────────────────
// TrabajadorController.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.controller;
 
import com.osinox.osinox.dto.TrabajadorDto;
import com.osinox.osinox.service.TrabajadorService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
 
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/trabajador")
@PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
public class TrabajadorController {
 
    private final TrabajadorService trabajadorService;
 
    @GetMapping
    public ResponseEntity<Page<TrabajadorDto>> getAll(
            @PageableDefault(size = 15) Pageable pageable) {
        Page<TrabajadorDto> page = trabajadorService.getAll(pageable);
        return page.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(page);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(trabajadorService.getById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
 
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody TrabajadorDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(trabajadorService.create(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TrabajadorDto dto) {
        try {
            return ResponseEntity.ok(trabajadorService.update(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 
    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        try {
            return ResponseEntity.ok(trabajadorService.cambiarEstado(id, estado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            trabajadorService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/buscar")
    public ResponseEntity<Page<TrabajadorDto>> buscar(
            @RequestParam String q,
            @PageableDefault(size = 15) Pageable pageable) {
        try {
            return ResponseEntity.ok(trabajadorService.buscar(q, pageable));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<Page<TrabajadorDto>> getByEstado(
            @PathVariable String estado,
            @PageableDefault(size = 15) Pageable pageable) {
        try {
            return ResponseEntity.ok(trabajadorService.getByEstado(estado, pageable));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
 // GET /api/v1/trabajador/activos  → lista plana para <select> o dropdown
    @GetMapping("/activos")
    public ResponseEntity<List<TrabajadorDto>> getActivos() {
        List<TrabajadorDto> lista = trabajadorService.getActivos();
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }
}