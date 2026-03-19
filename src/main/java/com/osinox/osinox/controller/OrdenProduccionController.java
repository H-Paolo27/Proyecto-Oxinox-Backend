// ─────────────────────────────────────────────────────────────
// OrdenProduccionController.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.controller;
 
import com.osinox.osinox.dto.OrdenProduccionDto;
import com.osinox.osinox.service.OrdenProduccionService;
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
@RequestMapping("/api/v1/orden-produccion")
@PreAuthorize("hasAnyRole('ADMIN','GERENTE','JEFE_TALLER')")
public class OrdenProduccionController {
 
    private final OrdenProduccionService ordenProduccionService;
 
    @GetMapping
    public ResponseEntity<Page<OrdenProduccionDto>> getAll(
            @PageableDefault(size = 15) Pageable pageable) {
        Page<OrdenProduccionDto> page = ordenProduccionService.getAll(pageable);
        return page.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(page);
    }
 
    @GetMapping("/proyecto/{proyectoId}")
    public ResponseEntity<Page<OrdenProduccionDto>> getByProyecto(
            @PathVariable Long proyectoId,
            @PageableDefault(size = 15) Pageable pageable) {
        Page<OrdenProduccionDto> page = ordenProduccionService.getByProyecto(proyectoId, pageable);
        return page.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(page);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ordenProduccionService.getById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
 
    @PostMapping
    public ResponseEntity<?> create(@RequestBody OrdenProduccionDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(ordenProduccionService.create(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody OrdenProduccionDto dto) {
        try {
            return ResponseEntity.ok(ordenProduccionService.update(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        try {
            return ResponseEntity.ok(ordenProduccionService.cambiarEstado(id, estado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            ordenProduccionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}