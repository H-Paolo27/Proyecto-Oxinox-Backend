package com.osinox.osinox.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.osinox.osinox.dto.PersonaDto;
import com.osinox.osinox.entity.Persona;
import com.osinox.osinox.mapper.PersonaMapper;
import com.osinox.osinox.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/persona")
@PreAuthorize("hasRole('ADMIN')")
public class PersonaController {

    private final PersonaRepository personaRepository;

    @GetMapping
    public ResponseEntity<Page<PersonaDto>> getAllPersonas(
            @PageableDefault(size = 15, sort = "nombreRazonSocial") Pageable pageable) {
        Page<Persona> personas = personaRepository.findAll(pageable);
        if (personas.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(personas.map(PersonaMapper::toDto));
    }

    // ← solo este buscar, el original fue eliminado
    @GetMapping("/buscar")
    public ResponseEntity<Page<PersonaDto>> buscarPorNombre(
            @RequestParam String q,
            @RequestParam(required = false) String tipoRelacion,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<Persona> resultado;
        if (tipoRelacion != null) {
            try {
                Persona.TipoRelacion tipo = Persona.TipoRelacion.valueOf(tipoRelacion.toUpperCase());
                resultado = personaRepository.buscarPorNombreODocumentoYTipo(q, tipo, pageable);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } else {
            resultado = personaRepository.buscarPorNombreODocumento(q, pageable);
        }
        if (resultado.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(resultado.map(PersonaMapper::toDto));
    }

    @GetMapping("/tipo-relacion/{tipoRelacion}")
    public ResponseEntity<Page<PersonaDto>> getByTipoRelacion(
            @PathVariable String tipoRelacion,
            @PageableDefault(size = 15) Pageable pageable) {
        try {
            Persona.TipoRelacion tipo = Persona.TipoRelacion.valueOf(tipoRelacion.toUpperCase());
            Page<Persona> resultado = personaRepository.findByTipoRelacion(tipo, pageable);
            if (resultado.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return ResponseEntity.ok(resultado.map(PersonaMapper::toDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/activos")
    public ResponseEntity<Page<PersonaDto>> getActivos(
            @PageableDefault(size = 15) Pageable pageable) {
        Page<Persona> resultado = personaRepository.findByEstado(true, pageable);
        if (resultado.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(resultado.map(PersonaMapper::toDto));
    }

    @GetMapping("/inactivos")
    public ResponseEntity<Page<PersonaDto>> getInactivos(
            @PageableDefault(size = 15) Pageable pageable) {
        Page<Persona> resultado = personaRepository.findByEstado(false, pageable);
        if (resultado.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(resultado.map(PersonaMapper::toDto));
    }

    @GetMapping("/existe/documento/{numeroDocumento}")
    public ResponseEntity<Map<String, Boolean>> existeDocumento(
            @PathVariable String numeroDocumento) {
        boolean existe = personaRepository.existsByNumeroDocumento(numeroDocumento);
        return ResponseEntity.ok(Map.of("existe", existe));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonaById(@PathVariable Long id) {
        Optional<Persona> opt = personaRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada");
        return ResponseEntity.ok(PersonaMapper.toDto(opt.get()));
    }

    @PostMapping
    public ResponseEntity<?> createPersona(@RequestBody Map<String, String> body) {
        try {
            Persona persona = new Persona();
            persona.setNombreRazonSocial(body.get("nombreRazonSocial"));
            persona.setNumeroDocumento(body.get("numeroDocumento"));
            persona.setTelefono(body.get("telefono"));
            persona.setEmail(body.get("email"));
            persona.setDireccion(body.get("direccion"));
            persona.setEstado(true);
            if (body.get("tipoPersona") != null)
                persona.setTipoPersona(Persona.TipoPersona.valueOf(body.get("tipoPersona")));
            if (body.get("tipoDocumento") != null)
                persona.setTipoDocumento(Persona.TipoDocumento.valueOf(body.get("tipoDocumento")));
            if (body.get("tipoRelacion") != null)
                persona.setTipoRelacion(Persona.TipoRelacion.valueOf(body.get("tipoRelacion")));
            Persona nueva = personaRepository.save(persona);
            return ResponseEntity.status(HttpStatus.CREATED).body(PersonaMapper.toDto(nueva));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePersona(@PathVariable Long id,
            @RequestBody Map<String, String> body) {
        Optional<Persona> opt = personaRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada");
        Persona persona = opt.get();
        if (body.get("nombreRazonSocial") != null) persona.setNombreRazonSocial(body.get("nombreRazonSocial"));
        if (body.get("numeroDocumento") != null) persona.setNumeroDocumento(body.get("numeroDocumento"));
        if (body.get("telefono") != null) persona.setTelefono(body.get("telefono"));
        if (body.get("email") != null) persona.setEmail(body.get("email"));
        if (body.get("direccion") != null) persona.setDireccion(body.get("direccion"));
        if (body.get("tipoPersona") != null)
            persona.setTipoPersona(Persona.TipoPersona.valueOf(body.get("tipoPersona")));
        if (body.get("tipoDocumento") != null)
            persona.setTipoDocumento(Persona.TipoDocumento.valueOf(body.get("tipoDocumento")));
        if (body.get("tipoRelacion") != null)
            persona.setTipoRelacion(Persona.TipoRelacion.valueOf(body.get("tipoRelacion")));
        return ResponseEntity.ok(PersonaMapper.toDto(personaRepository.save(persona)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePersona(@PathVariable Long id) {
        try {
            if (!personaRepository.existsById(id))
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada");
            personaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("No se puede eliminar: la persona tiene un usuario o trabajador asociado");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<?> activar(@PathVariable Long id) {
        Optional<Persona> opt = personaRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada");
        opt.get().setEstado(true);
        return ResponseEntity.ok(PersonaMapper.toDto(personaRepository.save(opt.get())));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<?> desactivar(@PathVariable Long id) {
        Optional<Persona> opt = personaRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada");
        opt.get().setEstado(false);
        return ResponseEntity.ok(PersonaMapper.toDto(personaRepository.save(opt.get())));
    }

    @GetMapping("/documento/{numeroDocumento}")
    public ResponseEntity<?> getPersonaByDocumento(@PathVariable String numeroDocumento) {
        Optional<Persona> opt = personaRepository.findByNumeroDocumento(numeroDocumento);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada");
        return ResponseEntity.ok(PersonaMapper.toDto(opt.get()));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getPersonaByEmail(@PathVariable String email) {
        Optional<Persona> opt = personaRepository.findByEmail(email);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada");
        return ResponseEntity.ok(PersonaMapper.toDto(opt.get()));
    }

    @GetMapping("/clientes/autocomplete")
    public ResponseEntity<List<PersonaDto>> autocompleteClientes(@RequestParam String q) {
        Page<Persona> resultado = personaRepository.buscarPorNombreODocumentoYTipo(
                q, Persona.TipoRelacion.CLIENTE,
                org.springframework.data.domain.PageRequest.of(0, 10));
        if (resultado.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(resultado.getContent()
                .stream().map(PersonaMapper::toDto).collect(Collectors.toList()));
    }
}