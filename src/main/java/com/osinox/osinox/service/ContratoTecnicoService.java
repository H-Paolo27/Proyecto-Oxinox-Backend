// ─────────────────────────────────────────────────────────────
// ContratoTecnicoService.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.service;
 
import com.osinox.osinox.dto.ContratoTecnicoDto;
import com.osinox.osinox.entity.ContratoTecnico;
import com.osinox.osinox.mapper.ContratoTecnicoMapper;
import com.osinox.osinox.repository.ContratoTecnicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
@Service
@RequiredArgsConstructor
public class ContratoTecnicoService {
 
    private final ContratoTecnicoRepository contratoTecnicoRepository;
 
    public Page<ContratoTecnicoDto> getAll(Pageable pageable) {
        return contratoTecnicoRepository.findAll(pageable).map(ContratoTecnicoMapper::toDto);
    }
 
    public ContratoTecnicoDto getById(Long id) {
        return ContratoTecnicoMapper.toDto(findOrThrow(id));
    }
 
    public ContratoTecnicoDto create(ContratoTecnicoDto dto) {
        ContratoTecnico saved = contratoTecnicoRepository.save(ContratoTecnicoMapper.toEntity(dto));
        return ContratoTecnicoMapper.toDto(saved);
    }
 
    public ContratoTecnicoDto update(Long id, ContratoTecnicoDto dto) {
        ContratoTecnico c = findOrThrow(id);
        c.setDescripcion(dto.getDescripcion());
        c.setMonto(dto.getMonto());
        c.setFechaInicio(dto.getFechaInicio());
        c.setFechaFin(dto.getFechaFin());
        c.setArchivoUrl(dto.getArchivoUrl());
        c.setObservaciones(dto.getObservaciones());
        if (dto.getTipo() != null)
            c.setTipo(ContratoTecnico.TipoContrato.valueOf(dto.getTipo()));
        if (dto.getEstado() != null)
            c.setEstado(ContratoTecnico.EstadoContrato.valueOf(dto.getEstado()));
        if (dto.getMoneda() != null)
            c.setMoneda(ContratoTecnico.Moneda.valueOf(dto.getMoneda()));
        return ContratoTecnicoMapper.toDto(contratoTecnicoRepository.save(c));
    }
 
    public ContratoTecnicoDto cambiarEstado(Long id, String estado) {
        ContratoTecnico c = findOrThrow(id);
        c.setEstado(ContratoTecnico.EstadoContrato.valueOf(estado));
        return ContratoTecnicoMapper.toDto(contratoTecnicoRepository.save(c));
    }
 
    @Transactional
    public void delete(Long id) {
        if (!contratoTecnicoRepository.existsById(id))
            throw new RuntimeException("Contrato técnico no encontrado");
        contratoTecnicoRepository.deleteById(id);
    }
 
    private ContratoTecnico findOrThrow(Long id) {
        return contratoTecnicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato técnico no encontrado"));
    }
}
 