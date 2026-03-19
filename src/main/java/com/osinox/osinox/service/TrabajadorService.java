// ─────────────────────────────────────────────────────────────
// TrabajadorService.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.service;
 
import com.osinox.osinox.dto.TrabajadorDto;
import com.osinox.osinox.entity.Trabajador;
import com.osinox.osinox.mapper.TrabajadorMapper;
import com.osinox.osinox.repository.PersonaRepository;
import com.osinox.osinox.repository.TrabajadorRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
@Service
@RequiredArgsConstructor
public class TrabajadorService {
 
    private final TrabajadorRepository trabajadorRepository;
    private final PersonaRepository personaRepository;
 
    public Page<TrabajadorDto> getAll(Pageable pageable) {
        return trabajadorRepository.findAll(pageable).map(TrabajadorMapper::toDto);
    }
 
    public TrabajadorDto getById(Long id) {
        return TrabajadorMapper.toDto(findOrThrow(id));
    }
 
    public TrabajadorDto create(TrabajadorDto dto) {
        if (!personaRepository.existsById(dto.getPersonaId()))
            throw new RuntimeException("Persona no encontrada: " + dto.getPersonaId());
        if (trabajadorRepository.existsByPersonaId(dto.getPersonaId()))
            throw new RuntimeException("La persona ya tiene un trabajador registrado");
        Trabajador saved = trabajadorRepository.save(TrabajadorMapper.toEntity(dto));
        return TrabajadorMapper.toDto(trabajadorRepository.findById(saved.getId()).orElse(saved));
    }
 
    public TrabajadorDto update(Long id, TrabajadorDto dto) {
        Trabajador t = findOrThrow(id);
        t.setCargo(dto.getCargo());
        t.setFechaContratacion(dto.getFechaContratacion());
        t.setFechaCese(dto.getFechaCese());
        t.setSalario(dto.getSalario());
        t.setNombreAfp(dto.getNombreAfp());
        if (dto.getTipoPension() != null)
            t.setTipoPension(Trabajador.TipoPension.valueOf(dto.getTipoPension()));
        if (dto.getEstado() != null)
            t.setEstado(Trabajador.EstadoTrabajador.valueOf(dto.getEstado()));
        return TrabajadorMapper.toDto(trabajadorRepository.save(t));
    }
 
    @Transactional
    public void delete(Long id) {
        if (!trabajadorRepository.existsById(id))
            throw new RuntimeException("Trabajador no encontrado");
        trabajadorRepository.deleteById(id);
    }
 
    public TrabajadorDto cambiarEstado(Long id, String estado) {
        Trabajador t = findOrThrow(id);
        t.setEstado(Trabajador.EstadoTrabajador.valueOf(estado));
        return TrabajadorMapper.toDto(trabajadorRepository.save(t));
    }
 
    private Trabajador findOrThrow(Long id) {
        return trabajadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));
    }
    public Page<TrabajadorDto> buscar(String q, Pageable pageable) {
        return trabajadorRepository
            .findByPersonaNombreRazonSocialContainingIgnoreCase(q, pageable)
            .map(TrabajadorMapper::toDto);
    }

    public Page<TrabajadorDto> getByEstado(String estado, Pageable pageable) {
        return trabajadorRepository
            .findByEstado(Trabajador.EstadoTrabajador.valueOf(estado), pageable)
            .map(TrabajadorMapper::toDto);
    }
    public List<TrabajadorDto> getActivos() {
        return trabajadorRepository
            .findByEstadoOrderByPersonaNombreRazonSocialAsc(Trabajador.EstadoTrabajador.ACTIVO)
            .stream()
            .map(TrabajadorMapper::toDto)
            .collect(java.util.stream.Collectors.toList());
    }
    
}
 