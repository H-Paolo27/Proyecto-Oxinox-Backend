// ─────────────────────────────────────────────────────────────
// CotizacionService.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.service;
 
import com.osinox.osinox.dto.CotizacionDto;
import com.osinox.osinox.entity.Cotizacion;
import com.osinox.osinox.mapper.CotizacionMapper;
import com.osinox.osinox.repository.CotizacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.math.BigDecimal;
import java.math.RoundingMode;
 
@Service
@RequiredArgsConstructor
public class CotizacionService {
 
    private static final BigDecimal IGV = new BigDecimal("0.18");
 
    private final CotizacionRepository cotizacionRepository;
 
 
    @Transactional(readOnly = true)
    public CotizacionDto getById(Long id) {
        return CotizacionMapper.toDto(findOrThrow(id));
    }
 
    public CotizacionDto create(CotizacionDto dto) {
        calcularTotales(dto);
        Cotizacion saved = cotizacionRepository.save(CotizacionMapper.toEntity(dto));
        return CotizacionMapper.toDto(saved);
    }
 
    public CotizacionDto update(Long id, CotizacionDto dto) {
        findOrThrow(id); // valida existencia
        dto.setId(id);
        calcularTotales(dto);
        Cotizacion saved = cotizacionRepository.save(CotizacionMapper.toEntity(dto));
        return CotizacionMapper.toDto(saved);
    }
 
    public CotizacionDto cambiarEstado(Long id, String estado) {
        Cotizacion c = findOrThrow(id);
        c.setEstado(Cotizacion.EstadoCotizacion.valueOf(estado));
        return CotizacionMapper.toDto(cotizacionRepository.save(c));
    }
 
    @Transactional
    public void delete(Long id) {
        if (!cotizacionRepository.existsById(id))
            throw new RuntimeException("Cotización no encontrada");
        cotizacionRepository.deleteById(id);
    }
 
    // Calcula subtotal, IGV y total automáticamente
    private void calcularTotales(CotizacionDto dto) {
        if (dto.getDetalles() == null || dto.getDetalles().isEmpty()) return;
        BigDecimal subtotal = dto.getDetalles().stream()
                .map(d -> {
                    BigDecimal base = d.getPrecioUnitario().multiply(d.getCantidad());
                    BigDecimal desc = d.getDescuento() != null ? d.getDescuento() : BigDecimal.ZERO;
                    BigDecimal sub = base.subtract(desc);
                    d.setSubtotal(sub.setScale(2, RoundingMode.HALF_UP));
                    return d.getSubtotal();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal igv = subtotal.multiply(IGV).setScale(2, RoundingMode.HALF_UP);
        dto.setSubtotal(subtotal.setScale(2, RoundingMode.HALF_UP));
        dto.setIgv(igv);
        dto.setTotal(subtotal.add(igv).setScale(2, RoundingMode.HALF_UP));
    }
 
    private Cotizacion findOrThrow(Long id) {
        return cotizacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotización no encontrada"));
    }
    @Transactional(readOnly = true)
    public Page<CotizacionDto> getAll(Pageable pageable) {
        return cotizacionRepository.findAll(pageable).map(CotizacionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<CotizacionDto> getByEstado(String estado, Pageable pageable) {
        return cotizacionRepository
            .findByEstado(Cotizacion.EstadoCotizacion.valueOf(estado), pageable)
            .map(CotizacionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<CotizacionDto> getByCliente(Long clienteId, Pageable pageable) {
        return cotizacionRepository
            .findByCliente_Id(clienteId, pageable)
            .map(CotizacionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<CotizacionDto> buscar(String q, Pageable pageable) {
        return cotizacionRepository
            .findByCodigoContainingIgnoreCase(q, pageable)
            .map(CotizacionMapper::toDto);
    }
}
 