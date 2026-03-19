// ─────────────────────────────────────────────────────────────
// ComprobanteService.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.service;
 
import com.osinox.osinox.dto.ComprobanteDto;
import com.osinox.osinox.entity.Comprobante;
import com.osinox.osinox.mapper.ComprobanteMapper;
import com.osinox.osinox.repository.ComprobanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.math.BigDecimal;
import java.math.RoundingMode;
 
@Service
@RequiredArgsConstructor
public class ComprobanteService {
 
    private static final BigDecimal IGV = new BigDecimal("0.18");
 
    private final ComprobanteRepository comprobanteRepository;
 
    public Page<ComprobanteDto> getAll(Pageable pageable) {
        return comprobanteRepository.findAll(pageable).map(ComprobanteMapper::toDto);
    }
 
    public ComprobanteDto getById(Long id) {
        return ComprobanteMapper.toDto(findOrThrow(id));
    }
 
    public ComprobanteDto create(ComprobanteDto dto) {
        calcularTotales(dto);
        Comprobante saved = comprobanteRepository.save(ComprobanteMapper.toEntity(dto));
        return ComprobanteMapper.toDto(saved);
    }
 
    public ComprobanteDto update(Long id, ComprobanteDto dto) {
        findOrThrow(id);
        dto.setId(id);
        calcularTotales(dto);
        Comprobante saved = comprobanteRepository.save(ComprobanteMapper.toEntity(dto));
        return ComprobanteMapper.toDto(saved);
    }
 
    public ComprobanteDto cambiarEstado(Long id, String estado) {
        Comprobante c = findOrThrow(id);
        c.setEstado(Comprobante.EstadoComprobante.valueOf(estado));
        return ComprobanteMapper.toDto(comprobanteRepository.save(c));
    }
 
    @Transactional
    public void delete(Long id) {
        if (!comprobanteRepository.existsById(id))
            throw new RuntimeException("Comprobante no encontrado");
        comprobanteRepository.deleteById(id);
    }
 
    private void calcularTotales(ComprobanteDto dto) {
        if (dto.getDetalles() == null || dto.getDetalles().isEmpty()) return;
        BigDecimal subtotal = dto.getDetalles().stream()
                .map(d -> {
                    BigDecimal base = d.getPrecioUnitario().multiply(d.getCantidad());
                    BigDecimal desc = d.getDescuento() != null ? d.getDescuento() : BigDecimal.ZERO;
                    BigDecimal igvUnit = base.subtract(desc)
                            .multiply(IGV).setScale(2, RoundingMode.HALF_UP);
                    BigDecimal sub = base.subtract(desc).setScale(2, RoundingMode.HALF_UP);
                    d.setIgvUnitario(igvUnit);
                    d.setSubtotal(sub);
                    return sub;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal igv = subtotal.multiply(IGV).setScale(2, RoundingMode.HALF_UP);
        dto.setSubtotal(subtotal.setScale(2, RoundingMode.HALF_UP));
        dto.setIgv(igv);
        dto.setTotal(subtotal.add(igv).setScale(2, RoundingMode.HALF_UP));
    }
 
    private Comprobante findOrThrow(Long id) {
        return comprobanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comprobante no encontrado"));
    }
}