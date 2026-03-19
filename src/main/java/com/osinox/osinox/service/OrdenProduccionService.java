// ─────────────────────────────────────────────────────────────
// OrdenProduccionService.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.service;
 
import com.osinox.osinox.dto.OrdenProduccionDto;
import com.osinox.osinox.entity.OrdenProduccion;
import com.osinox.osinox.mapper.OrdenProduccionMapper;
import com.osinox.osinox.repository.OrdenProduccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
@Service
@RequiredArgsConstructor
public class OrdenProduccionService {
 
    private final OrdenProduccionRepository ordenProduccionRepository;
 
    public Page<OrdenProduccionDto> getAll(Pageable pageable) {
        return ordenProduccionRepository.findAll(pageable).map(OrdenProduccionMapper::toDto);
    }
 
    public Page<OrdenProduccionDto> getByProyecto(Long proyectoId, Pageable pageable) {
        return ordenProduccionRepository.findByProyectoId(proyectoId, pageable)
                .map(OrdenProduccionMapper::toDto);
    }
 
    public OrdenProduccionDto getById(Long id) {
        return OrdenProduccionMapper.toDto(findOrThrow(id));
    }
 
    public OrdenProduccionDto create(OrdenProduccionDto dto) {
        OrdenProduccion saved = ordenProduccionRepository.save(OrdenProduccionMapper.toEntity(dto));
        return OrdenProduccionMapper.toDto(saved);
    }
 
    public OrdenProduccionDto update(Long id, OrdenProduccionDto dto) {
        OrdenProduccion o = findOrThrow(id);
        o.setDescripcion(dto.getDescripcion());
        o.setFechaInicio(dto.getFechaInicio());
        o.setFechaFinEstimada(dto.getFechaFinEstimada());
        o.setFechaFinReal(dto.getFechaFinReal());
        o.setObservaciones(dto.getObservaciones());
        if (dto.getEstado() != null)
            o.setEstado(OrdenProduccion.EstadoOrden.valueOf(dto.getEstado()));
        if (dto.getPrioridad() != null)
            o.setPrioridad(OrdenProduccion.Prioridad.valueOf(dto.getPrioridad()));
        return OrdenProduccionMapper.toDto(ordenProduccionRepository.save(o));
    }
 
    public OrdenProduccionDto cambiarEstado(Long id, String estado) {
        OrdenProduccion o = findOrThrow(id);
        o.setEstado(OrdenProduccion.EstadoOrden.valueOf(estado));
        return OrdenProduccionMapper.toDto(ordenProduccionRepository.save(o));
    }
 
    @Transactional
    public void delete(Long id) {
        if (!ordenProduccionRepository.existsById(id))
            throw new RuntimeException("Orden de producción no encontrada");
        ordenProduccionRepository.deleteById(id);
    }
 
    private OrdenProduccion findOrThrow(Long id) {
        return ordenProduccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de producción no encontrada"));
    }
}