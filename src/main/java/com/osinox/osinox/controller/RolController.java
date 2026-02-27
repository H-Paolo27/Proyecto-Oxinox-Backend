package com.osinox.osinox.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osinox.osinox.dto.RolDto;
import com.osinox.osinox.entity.Rol;
import com.osinox.osinox.mapper.RolMapper;
import com.osinox.osinox.repository.RolRepository;
import com.osinox.osinox.service.RolService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/rol")
@PreAuthorize("hasRole('ADMIN')")
public class RolController {

    private final RolRepository rolRepository;
    private final RolService rolService;

    @GetMapping
    public ResponseEntity<List<RolDto>> getAllRoles() {
        List<Rol> roles = rolRepository.findAll();
        if (roles.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        List<RolDto> dtos = roles.stream().map(RolMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRolById(@PathVariable Long id) {
        Optional<Rol> opt = rolRepository.findById(id);
        if (opt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");
        return ResponseEntity.ok(RolMapper.toDto(opt.get()));
    }

    @PostMapping
    public ResponseEntity<?> createRol(@RequestBody Map<String, String> body) {
        try {
            String nombre = body.get("nombre");
            String descripcion = body.get("descripcion");

            // Verificar si ya existe un rol con ese nombre
            Optional<Rol> existente = rolRepository.findByNombre(nombre);
            if (existente.isPresent())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya existe un rol con ese nombre");

            Rol rol = new Rol();
            rol.setNombre(nombre);
            rol.setDescripcion(descripcion);

            Rol nuevo = rolRepository.save(rol);
            return ResponseEntity.status(HttpStatus.CREATED).body(RolMapper.toDto(nuevo));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRol(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Optional<Rol> opt = rolRepository.findById(id);
        if (opt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");

        Rol rol = opt.get();

        if (body.get("nombre") != null) {
            // Verificar si el nuevo nombre ya existe en otro rol
            Optional<Rol> existente = rolRepository.findByNombre(body.get("nombre"));
            if (existente.isPresent() && !existente.get().getId().equals(id))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya existe un rol con ese nombre");
            rol.setNombre(body.get("nombre"));
        }
        if (body.get("descripcion") != null) {
            rol.setDescripcion(body.get("descripcion"));
        }

        Rol actualizado = rolRepository.save(rol);
        return ResponseEntity.ok(RolMapper.toDto(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRol(@PathVariable Long id) {
        try {
            if (!rolRepository.existsById(id))
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");
            rolRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede eliminar el rol porque está en uso");
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> getRolByNombre(@PathVariable String nombre) {
        Optional<Rol> opt = rolRepository.findByNombre(nombre);
        if (opt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");
        return ResponseEntity.ok(RolMapper.toDto(opt.get()));
    }
}
