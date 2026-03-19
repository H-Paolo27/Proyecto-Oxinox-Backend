// ─────────────────────────────────────────────────────────────
// PlanillaController.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.controller;
 
import com.osinox.osinox.dto.PlanillaDto;
import com.osinox.osinox.service.PlanillaService;
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
@RequestMapping("/api/v1/planilla")
@PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
public class PlanillaController {
 
    private final PlanillaService planillaService;
 
    @GetMapping
    public ResponseEntity<Page<PlanillaDto>> getAll(
            @PageableDefault(size = 15, sort = "periodo") Pageable pageable) {
        Page<PlanillaDto> page = planillaService.getAll(pageable);
        return page.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(page);
    }
 
    @GetMapping("/trabajador/{trabajadorId}")
    public ResponseEntity<Page<PlanillaDto>> getByTrabajador(
            @PathVariable Long trabajadorId,
            @PageableDefault(size = 15) Pageable pageable) {
        Page<PlanillaDto> page = planillaService.getByTrabajador(trabajadorId, pageable);
        return page.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(page);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(planillaService.getById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
 
    // Calcula automáticamente y crea la planilla
    @PostMapping("/calcular")
    public ResponseEntity<?> calcularYCrear(@RequestBody PlanillaDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(planillaService.calcularYCrear(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 
    @PatchMapping("/{id}/aprobar")
    public ResponseEntity<?> aprobar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(planillaService.aprobar(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 
    @PatchMapping("/{id}/pagar")
    public ResponseEntity<?> pagar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(planillaService.pagar(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            planillaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
 // Filtrar por periodo - GET /api/v1/planilla/periodo/2026-03
    @GetMapping("/periodo/{periodo}")
    public ResponseEntity<Page<PlanillaDto>> getByPeriodo(
            @PathVariable String periodo,
            @PageableDefault(size = 15) Pageable pageable) {
        try {
            return ResponseEntity.ok(planillaService.getByPeriodo(periodo, pageable));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Filtrar por estado - GET /api/v1/planilla/estado/BORRADOR
    @GetMapping("/estado/{estado}")
    public ResponseEntity<Page<PlanillaDto>> getByEstado(
            @PathVariable String estado,
            @PageableDefault(size = 15) Pageable pageable) {
        try {
            return ResponseEntity.ok(planillaService.getByEstado(estado, pageable));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Anular planilla - PATCH /api/v1/planilla/{id}/anular
    @PatchMapping("/{id}/anular")
    public ResponseEntity<?> anular(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(planillaService.anular(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}