// ─────────────────────────────────────────────────────────────
// CotizacionController.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.controller;
 
import com.osinox.osinox.dto.CotizacionDto;
import com.osinox.osinox.service.CotizacionService;
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
@RequestMapping("/api/v1/cotizacion")
@PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENTAS')")
public class CotizacionController {
 
    private final CotizacionService cotizacionService;
 
    @GetMapping
    public ResponseEntity<Page<CotizacionDto>> getAll(
            @PageableDefault(size = 15, sort = "fechaRegistro") Pageable pageable) {
        Page<CotizacionDto> page = cotizacionService.getAll(pageable);
        return page.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(page);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(cotizacionService.getById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
 
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CotizacionDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(cotizacionService.create(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CotizacionDto dto) {
        try {
            return ResponseEntity.ok(cotizacionService.update(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        try {
            return ResponseEntity.ok(cotizacionService.cambiarEstado(id, estado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            cotizacionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 // Filtrar por estado - GET /api/v1/cotizacion/estado/APROBADA
    @GetMapping("/estado/{estado}")
    public ResponseEntity<Page<CotizacionDto>> getByEstado(
            @PathVariable String estado,
            @PageableDefault(size = 15) Pageable pageable) {
        try {
            return ResponseEntity.ok(cotizacionService.getByEstado(estado, pageable));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Filtrar por cliente - GET /api/v1/cotizacion/cliente/5
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<Page<CotizacionDto>> getByCliente(
            @PathVariable Long clienteId,
            @PageableDefault(size = 15) Pageable pageable) {
        try {
            return ResponseEntity.ok(cotizacionService.getByCliente(clienteId, pageable));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Buscar por código - GET /api/v1/cotizacion/buscar?q=COT-2026
    @GetMapping("/buscar")
    public ResponseEntity<Page<CotizacionDto>> buscar(
            @RequestParam String q,
            @PageableDefault(size = 15) Pageable pageable) {
        try {
            return ResponseEntity.ok(cotizacionService.buscar(q, pageable));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}