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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<PersonaDto>> getAllPersonas() {
        List<Persona> personas = personaRepository.findAll();
        if (personas.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        List<PersonaDto> dtos = personas.stream().map(PersonaMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonaById(@PathVariable Long id) {
        Optional<Persona> opt = personaRepository.findById(id);
        if (opt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada");
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

            // Enums
            if (body.get("tipoPersona") != null) {
                persona.setTipoPersona(Persona.TipoPersona.valueOf(body.get("tipoPersona")));
            }
            if (body.get("tipoDocumento") != null) {
                persona.setTipoDocumento(Persona.TipoDocumento.valueOf(body.get("tipoDocumento")));
            }
            if (body.get("tipoRelacion") != null) {
                persona.setTipoRelacion(Persona.TipoRelacion.valueOf(body.get("tipoRelacion")));
            }

            Persona nueva = personaRepository.save(persona);
            return ResponseEntity.status(HttpStatus.CREATED).body(PersonaMapper.toDto(nueva));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePersona(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Optional<Persona> opt = personaRepository.findById(id);
        if (opt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada");

        Persona persona = opt.get();
        
        if (body.get("nombreRazonSocial") != null) {
            persona.setNombreRazonSocial(body.get("nombreRazonSocial"));
        }
        if (body.get("numeroDocumento") != null) {
            persona.setNumeroDocumento(body.get("numeroDocumento"));
        }
        if (body.get("telefono") != null) {
            persona.setTelefono(body.get("telefono"));
        }
        if (body.get("email") != null) {
            persona.setEmail(body.get("email"));
        }
        if (body.get("direccion") != null) {
            persona.setDireccion(body.get("direccion"));
        }
        if (body.get("tipoPersona") != null) {
            persona.setTipoPersona(Persona.TipoPersona.valueOf(body.get("tipoPersona")));
        }
        if (body.get("tipoDocumento") != null) {
            persona.setTipoDocumento(Persona.TipoDocumento.valueOf(body.get("tipoDocumento")));
        }
        if (body.get("tipoRelacion") != null) {
            persona.setTipoRelacion(Persona.TipoRelacion.valueOf(body.get("tipoRelacion")));
        }

        Persona actualizada = personaRepository.save(persona);
        return ResponseEntity.ok(PersonaMapper.toDto(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePersona(@PathVariable Long id) {
        try {
            if (!personaRepository.existsById(id))
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada");
            personaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<?> activar(@PathVariable Long id) {
        Optional<Persona> opt = personaRepository.findById(id);
        if (opt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada");
        
        Persona persona = opt.get();
        persona.setEstado(true);
        return ResponseEntity.ok(PersonaMapper.toDto(personaRepository.save(persona)));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<?> desactivar(@PathVariable Long id) {
        Optional<Persona> opt = personaRepository.findById(id);
        if (opt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada");
        
        Persona persona = opt.get();
        persona.setEstado(false);
        return ResponseEntity.ok(PersonaMapper.toDto(personaRepository.save(persona)));
    }

    @GetMapping("/documento/{numeroDocumento}")
    public ResponseEntity<?> getPersonaByDocumento(@PathVariable String numeroDocumento) {
        Optional<Persona> opt = personaRepository.findByNumeroDocumento(numeroDocumento);
        if (opt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada");
        return ResponseEntity.ok(PersonaMapper.toDto(opt.get()));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getPersonaByEmail(@PathVariable String email) {
        Optional<Persona> opt = personaRepository.findByEmail(email);
        if (opt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada");
        return ResponseEntity.ok(PersonaMapper.toDto(opt.get()));
    }
}
