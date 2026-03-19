package com.osinox.osinox.controller;

import com.osinox.osinox.dto.ProductoDto;
import com.osinox.osinox.dto.ProductoRequestDTO;
import com.osinox.osinox.service.CloudinaryService;
import com.osinox.osinox.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/productos")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','GERENTE','ALMACEN','JEFE_TALLER')")
public class AdminProductoController {

    private final ProductoService productoService;
    private final CloudinaryService cloudinaryService;

    @GetMapping
    public ResponseEntity<List<ProductoDto>> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Long idCategoria,
            @RequestParam(required = false) Boolean estado) {
        return ResponseEntity.ok(productoService.buscarAdmin(nombre, idCategoria, estado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.getProductoById(id));
    }

    @PostMapping
    public ResponseEntity<ProductoDto> crear(
            @Valid @RequestBody ProductoRequestDTO dto) {
        ProductoDto creado = productoService.crearDesdeRequest(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.ok(productoService.actualizarDesdeRequest(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        productoService.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/imagen")
    public ResponseEntity<ProductoDto> subirImagen(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo) throws IOException {

        String contentType = archivo.getContentType();
        if (contentType == null || !contentType.startsWith("image/"))
            return ResponseEntity.badRequest().build();

        ProductoDto producto = productoService.getProductoById(id);

        if (producto.getImagenUrl() != null) {
            String publicId = cloudinaryService.extraerPublicId(producto.getImagenUrl());
            if (publicId != null) cloudinaryService.eliminarImagen(publicId);
        }

        String nuevaUrl = cloudinaryService.subirImagen(archivo);
        ProductoDto actualizado = productoService.actualizarImagenUrl(id, nuevaUrl);

        return ResponseEntity.ok(actualizado);
    }
}