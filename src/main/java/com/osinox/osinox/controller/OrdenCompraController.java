// ─────────────────────────────────────────────────────────────
// OrdenCompraController.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.controller;
 
import com.osinox.osinox.dto.OrdenCompraDto;
import com.osinox.osinox.service.OrdenCompraService;
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
@RequestMapping("/api/v1/orden-compra")
@PreAuthorize("hasAnyRole('ADMIN','GERENTE','ALMACEN')")
public class OrdenCompraController {
 
    private final OrdenCompraService ordenCompraService;
 
    @GetMapping
    public ResponseEntity<Page<OrdenCompraDto>> getAll(
            @PageableDefault(size = 15) Pageable pageable) {
        Page<OrdenCompraDto> page = ordenCompraService.getAll(pageable);
        return page.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(page);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ordenCompraService.getById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
 
    @PostMapping
    public ResponseEntity<?> create(@RequestBody OrdenCompraDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(ordenCompraService.create(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody OrdenCompraDto dto) {
        try {
            return ResponseEntity.ok(ordenCompraService.update(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        try {
            return ResponseEntity.ok(ordenCompraService.cambiarEstado(id, estado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            ordenCompraService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}