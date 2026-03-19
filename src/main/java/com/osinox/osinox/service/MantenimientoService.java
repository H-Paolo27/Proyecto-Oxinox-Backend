// ─────────────────────────────────────────────────────────────
// MantenimientoService.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.service;
 
import com.osinox.osinox.dto.MantenimientoDto;
import com.osinox.osinox.entity.Mantenimiento;
import com.osinox.osinox.mapper.MantenimientoMapper;
import com.osinox.osinox.repository.MantenimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
@Service
@RequiredArgsConstructor
public class MantenimientoService {
 
    private final MantenimientoRepository mantenimientoRepository;
 
    public Page<MantenimientoDto> getAll(Pageable pageable) {
        return mantenimientoRepository.findAll(pageable).map(MantenimientoMapper::toDto);
    }
 
    public MantenimientoDto getById(Long id) {
        return MantenimientoMapper.toDto(findOrThrow(id));
    }
 
    public MantenimientoDto create(MantenimientoDto dto) {
        Mantenimiento saved = mantenimientoRepository.save(MantenimientoMapper.toEntity(dto));
        return MantenimientoMapper.toDto(saved);
    }
 
    public MantenimientoDto update(Long id, MantenimientoDto dto) {
        Mantenimiento m = findOrThrow(id);
        m.setDescripcion(dto.getDescripcion());
        m.setFechaProgramada(dto.getFechaProgramada());
        m.setFechaRealizada(dto.getFechaRealizada());
        m.setCosto(dto.getCosto());
        m.setInformeUrl(dto.getInformeUrl());
        m.setObservaciones(dto.getObservaciones());
        if (dto.getTipo() != null)
            m.setTipo(Mantenimiento.TipoMantenimiento.valueOf(dto.getTipo()));
        if (dto.getEstado() != null)
            m.setEstado(Mantenimiento.EstadoMantenimiento.valueOf(dto.getEstado()));
        return MantenimientoMapper.toDto(mantenimientoRepository.save(m));
    }
 
    public MantenimientoDto cambiarEstado(Long id, String estado) {
        Mantenimiento m = findOrThrow(id);
        m.setEstado(Mantenimiento.EstadoMantenimiento.valueOf(estado));
        return MantenimientoMapper.toDto(mantenimientoRepository.save(m));
    }
 
    @Transactional
    public void delete(Long id) {
        if (!mantenimientoRepository.existsById(id))
            throw new RuntimeException("Mantenimiento no encontrado");
        mantenimientoRepository.deleteById(id);
    }
 
    private Mantenimiento findOrThrow(Long id) {
        return mantenimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mantenimiento no encontrado"));
    }
}