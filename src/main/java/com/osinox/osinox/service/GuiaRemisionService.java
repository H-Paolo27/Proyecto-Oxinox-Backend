// ─────────────────────────────────────────────────────────────
// GuiaRemisionService.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.service;
 
import com.osinox.osinox.dto.GuiaRemisionDto;
import com.osinox.osinox.entity.GuiaRemision;
import com.osinox.osinox.mapper.GuiaRemisionMapper;
import com.osinox.osinox.repository.GuiaRemisionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
@Service
@RequiredArgsConstructor
public class GuiaRemisionService {
 
    private final GuiaRemisionRepository guiaRemisionRepository;
 
    public Page<GuiaRemisionDto> getAll(Pageable pageable) {
        return guiaRemisionRepository.findAll(pageable).map(GuiaRemisionMapper::toDto);
    }
 
    public GuiaRemisionDto getById(Long id) {
        return GuiaRemisionMapper.toDto(findOrThrow(id));
    }
 
    public GuiaRemisionDto create(GuiaRemisionDto dto) {
        GuiaRemision saved = guiaRemisionRepository.save(GuiaRemisionMapper.toEntity(dto));
        return GuiaRemisionMapper.toDto(saved);
    }
 
    public GuiaRemisionDto update(Long id, GuiaRemisionDto dto) {
        findOrThrow(id);
        dto.setId(id);
        GuiaRemision saved = guiaRemisionRepository.save(GuiaRemisionMapper.toEntity(dto));
        return GuiaRemisionMapper.toDto(saved);
    }
 
    public GuiaRemisionDto cambiarEstado(Long id, String estado) {
        GuiaRemision g = findOrThrow(id);
        g.setEstado(GuiaRemision.EstadoGuia.valueOf(estado));
        return GuiaRemisionMapper.toDto(guiaRemisionRepository.save(g));
    }
 
    @Transactional
    public void delete(Long id) {
        if (!guiaRemisionRepository.existsById(id))
            throw new RuntimeException("Guía de remisión no encontrada");
        guiaRemisionRepository.deleteById(id);
    }
 
    private GuiaRemision findOrThrow(Long id) {
        return guiaRemisionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guía de remisión no encontrada"));
    }
}
 