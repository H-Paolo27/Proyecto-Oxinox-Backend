package com.osinox.osinox.controller;

import java.time.LocalDate;
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

import com.osinox.osinox.dto.TrabajadorDto;
import com.osinox.osinox.entity.Persona;
import com.osinox.osinox.entity.Trabajador;
import com.osinox.osinox.mapper.PersonaMapper;
import com.osinox.osinox.mapper.TrabajadorMapper;
import com.osinox.osinox.repository.PersonaRepository;
import com.osinox.osinox.repository.TrabajadorRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/trabajador")
@PreAuthorize("hasRole('ADMIN')")
public class TrabajadorController {

    private final TrabajadorRepository trabajadorRepository;
    private final PersonaRepository personaRepository;

    @GetMapping
    public ResponseEntity<List<TrabajadorDto>> getAllTrabajadores() {
        List<Trabajador> trabajadores = trabajadorRepository.findAll();
        if (trabajadores.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        List<TrabajadorDto> dtos = trabajadores.stream().map(TrabajadorMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrabajadorById(@PathVariable Long id) {
        Optional<Trabajador> opt = trabajadorRepository.findById(id);
        if (opt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trabajador no encontrado");
        return ResponseEntity.ok(TrabajadorMapper.toDto(opt.get()));
    }

    @PostMapping
    public ResponseEntity<?> createTrabajador(@RequestBody Map<String, String> body) {
        try {
            Long personaId = Long.parseLong(body.get("personaId"));
            Optional<Persona> optPersona = personaRepository.findById(personaId);
            
            if (optPersona.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada");

            // Verificar si ya existe un trabajador para esa persona
            Optional<Trabajador> existente = trabajadorRepository.findByPersonaId(personaId);
            if (existente.isPresent())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya existe un trabajador para esta persona");

            Persona persona = optPersona.get();
            
            // Actualizar el tipo de relación de la persona si es necesario
            if (persona.getTipoRelacion() == null || 
                (persona.getTipoRelacion() != Persona.TipoRelacion.TRABAJADOR_INTERNO && 
                 persona.getTipoRelacion() != Persona.TipoRelacion.MIXTO)) {
                persona.setTipoRelacion(Persona.TipoRelacion.TRABAJADOR_INTERNO);
                personaRepository.save(persona);
            }

            Trabajador trabajador = new Trabajador();
            trabajador.setPersona(persona);
            trabajador.setCargo(body.get("cargo"));
            
            if (body.get("fechaContratacion") != null) {
                trabajador.setFechaContratacion(LocalDate.parse(body.get("fechaContratacion")));
            }
            
            if (body.get("salario") != null) {
                trabajador.setSalario(new java.math.BigDecimal(body.get("salario")));
            }

            Trabajador nuevo = trabajadorRepository.save(trabajador);
            return ResponseEntity.status(HttpStatus.CREATED).body(TrabajadorMapper.toDto(nuevo));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear trabajador: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTrabajador(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Optional<Trabajador> opt = trabajadorRepository.findById(id);
        if (opt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trabajador no encontrado");

        Trabajador trabajador = opt.get();

        if (body.get("cargo") != null) {
            trabajador.setCargo(body.get("cargo"));
        }
        if (body.get("fechaContratacion") != null) {
            trabajador.setFechaContratacion(LocalDate.parse(body.get("fechaContratacion")));
        }
        if (body.get("salario") != null) {
            trabajador.setSalario(new java.math.BigDecimal(body.get("salario")));
        }

        Trabajador actualizado = trabajadorRepository.save(trabajador);
        return ResponseEntity.ok(TrabajadorMapper.toDto(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrabajador(@PathVariable Long id) {
        try {
            if (!trabajadorRepository.existsById(id))
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trabajador no encontrado");
            trabajadorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/persona/{personaId}")
    public ResponseEntity<?> getTrabajadorByPersonaId(@PathVariable Long personaId) {
        Optional<Trabajador> opt = trabajadorRepository.findByPersonaId(personaId);
        if (opt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trabajador no encontrado para esa persona");
        return ResponseEntity.ok(TrabajadorMapper.toDto(opt.get()));
    }

    @GetMapping("/{id}/persona")
    public ResponseEntity<?> getPersonaByTrabajadorId(@PathVariable Long id) {
        Optional<Trabajador> opt = trabajadorRepository.findById(id);
        if (opt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trabajador no encontrado");
        
        Persona persona = opt.get().getPersona();
        return ResponseEntity.ok(PersonaMapper.toDto(persona));
    }
}
