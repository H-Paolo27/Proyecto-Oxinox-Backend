package com.osinox.osinox.controller;

import com.osinox.osinox.dto.ProductoPublicoDTO;
import com.osinox.osinox.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class PublicoProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoPublicoDTO>> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Long idCategoria) {
        return ResponseEntity.ok(productoService.listarPublicos(nombre, idCategoria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoPublicoDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPublicoPorId(id));
    }
}