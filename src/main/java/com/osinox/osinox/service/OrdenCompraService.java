// ─────────────────────────────────────────────────────────────
// OrdenCompraService.java
// ─────────────────────────────────────────────────────────────
package com.osinox.osinox.service;
 
import com.osinox.osinox.dto.OrdenCompraDto;
import com.osinox.osinox.entity.OrdenCompra;
import com.osinox.osinox.mapper.OrdenCompraMapper;
import com.osinox.osinox.repository.OrdenCompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.math.BigDecimal;
import java.math.RoundingMode;
 
@Service
@RequiredArgsConstructor
public class OrdenCompraService {
 
    private static final BigDecimal IGV = new BigDecimal("0.18");
 
    private final OrdenCompraRepository ordenCompraRepository;
 
    public Page<OrdenCompraDto> getAll(Pageable pageable) {
        return ordenCompraRepository.findAll(pageable).map(OrdenCompraMapper::toDto);
    }
 
    public OrdenCompraDto getById(Long id) {
        return OrdenCompraMapper.toDto(findOrThrow(id));
    }
 
    public OrdenCompraDto create(OrdenCompraDto dto) {
        calcularTotales(dto);
        OrdenCompra saved = ordenCompraRepository.save(OrdenCompraMapper.toEntity(dto));
        return OrdenCompraMapper.toDto(saved);
    }
 
    public OrdenCompraDto update(Long id, OrdenCompraDto dto) {
        findOrThrow(id);
        dto.setId(id);
        calcularTotales(dto);
        OrdenCompra saved = ordenCompraRepository.save(OrdenCompraMapper.toEntity(dto));
        return OrdenCompraMapper.toDto(saved);
    }
 
    public OrdenCompraDto cambiarEstado(Long id, String estado) {
        OrdenCompra o = findOrThrow(id);
        o.setEstado(OrdenCompra.EstadoOC.valueOf(estado));
        return OrdenCompraMapper.toDto(ordenCompraRepository.save(o));
    }
 
    @Transactional
    public void delete(Long id) {
        if (!ordenCompraRepository.existsById(id))
            throw new RuntimeException("Orden de compra no encontrada");
        ordenCompraRepository.deleteById(id);
    }
 
    private void calcularTotales(OrdenCompraDto dto) {
        if (dto.getDetalles() == null || dto.getDetalles().isEmpty()) return;
        BigDecimal subtotal = dto.getDetalles().stream()
                .map(d -> {
                    BigDecimal sub = d.getPrecioUnitario().multiply(d.getCantidad())
                            .setScale(2, RoundingMode.HALF_UP);
                    d.setSubtotal(sub);
                    return sub;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal igv = subtotal.multiply(IGV).setScale(2, RoundingMode.HALF_UP);
        dto.setSubtotal(subtotal);
        dto.setIgv(igv);
        dto.setTotal(subtotal.add(igv).setScale(2, RoundingMode.HALF_UP));
    }
 
    private OrdenCompra findOrThrow(Long id) {
        return ordenCompraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden de compra no encontrada"));
    }
}
 