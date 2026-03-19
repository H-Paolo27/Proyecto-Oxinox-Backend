// ─────────────────────────────────────────────────────────────
// ProyectoService.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.service;
 
import com.osinox.osinox.dto.ProyectoDto;
import com.osinox.osinox.entity.Proyecto;
import com.osinox.osinox.mapper.ProyectoMapper;
import com.osinox.osinox.repository.ProyectoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
@Service
@RequiredArgsConstructor
public class ProyectoService {
 
    private final ProyectoRepository proyectoRepository;
 
    public Page<ProyectoDto> getAll(Pageable pageable) {
        return proyectoRepository.findAll(pageable).map(ProyectoMapper::toDto);
    }
 
    public ProyectoDto getById(Long id) {
        return ProyectoMapper.toDto(findOrThrow(id));
    }
 
    public ProyectoDto create(ProyectoDto dto) {
        Proyecto saved = proyectoRepository.save(ProyectoMapper.toEntity(dto));
        return ProyectoMapper.toDto(proyectoRepository.findById(saved.getId()).orElse(saved));
    }
 
    public ProyectoDto update(Long id, ProyectoDto dto) {
        Proyecto p = findOrThrow(id);
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setFechaInicio(dto.getFechaInicio());
        p.setFechaFinEstimada(dto.getFechaFinEstimada());
        p.setFechaFinReal(dto.getFechaFinReal());
        p.setPresupuesto(dto.getPresupuesto());
        p.setCostoReal(dto.getCostoReal());
        p.setObservaciones(dto.getObservaciones());
        if (dto.getTipo() != null)
            p.setTipo(Proyecto.TipoProyecto.valueOf(dto.getTipo()));
        if (dto.getPrioridad() != null)
            p.setPrioridad(Proyecto.Prioridad.valueOf(dto.getPrioridad()));
        return ProyectoMapper.toDto(proyectoRepository.save(p));
    }
 
    public ProyectoDto cambiarEstado(Long id, String estado) {
        Proyecto p = findOrThrow(id);
        p.setEstado(Proyecto.EstadoProyecto.valueOf(estado));
        return ProyectoMapper.toDto(proyectoRepository.save(p));
    }
 
    @Transactional
    public void delete(Long id) {
        if (!proyectoRepository.existsById(id))
            throw new RuntimeException("Proyecto no encontrado");
        proyectoRepository.deleteById(id);
    }
 
    private Proyecto findOrThrow(Long id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    }
    public Page<ProyectoDto> buscar(String q, Pageable pageable) {
        return proyectoRepository
            .findByNombreContainingIgnoreCase(q, pageable)
            .map(ProyectoMapper::toDto);
    }

    public Page<ProyectoDto> getByEstado(String estado, Pageable pageable) {
        return proyectoRepository
            .findByEstado(Proyecto.EstadoProyecto.valueOf(estado), pageable)
            .map(ProyectoMapper::toDto);
    }

    public Page<ProyectoDto> getByCliente(Long clienteId, Pageable pageable) {
        return proyectoRepository
            .findByCliente_Id(clienteId, pageable)  // ✅
            .map(ProyectoMapper::toDto);
    }
}