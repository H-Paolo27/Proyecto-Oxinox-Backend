package com.osinox.osinox.service;

import com.osinox.osinox.dto.PlanillaDto;
import com.osinox.osinox.entity.Planilla;
import com.osinox.osinox.entity.Trabajador;
import com.osinox.osinox.mapper.PlanillaMapper;
import com.osinox.osinox.repository.PlanillaRepository;
import com.osinox.osinox.repository.TrabajadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class PlanillaService {

    private static final BigDecimal ESSALUD  = new BigDecimal("0.09");
    private static final BigDecimal ONP      = new BigDecimal("0.13");
    private static final BigDecimal AFP      = new BigDecimal("0.127");
    private static final BigDecimal UIT_2024 = new BigDecimal("5150");

    private final PlanillaRepository  planillaRepository;
    private final TrabajadorRepository trabajadorRepository;

    @Transactional(readOnly = true)
    public Page<PlanillaDto> getAll(Pageable pageable) {
        return planillaRepository.findAll(pageable).map(PlanillaMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PlanillaDto> getByTrabajador(Long trabajadorId, Pageable pageable) {
        return planillaRepository.findByTrabajadorId(trabajadorId, pageable)
                .map(PlanillaMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PlanillaDto> getByPeriodo(String periodo, Pageable pageable) {
        return planillaRepository.findByPeriodo(periodo, pageable)
                .map(PlanillaMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PlanillaDto> getByEstado(String estado, Pageable pageable) {
        return planillaRepository
                .findByEstado(Planilla.EstadoPlanilla.valueOf(estado), pageable)
                .map(PlanillaMapper::toDto);
    }

    public PlanillaDto getById(Long id) {
        return PlanillaMapper.toDto(findOrThrow(id));
    }

    public PlanillaDto calcularYCrear(PlanillaDto dto) {
        Trabajador t = trabajadorRepository.findById(dto.getTrabajadorId())
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));
        calcularPlanilla(dto, t);
        Planilla saved = planillaRepository.save(PlanillaMapper.toEntity(dto));
        return PlanillaMapper.toDto(saved);
    }

    public PlanillaDto aprobar(Long id) {
        Planilla p = findOrThrow(id);
        p.setEstado(Planilla.EstadoPlanilla.APROBADO);
        return PlanillaMapper.toDto(planillaRepository.save(p));
    }

    public PlanillaDto pagar(Long id) {
        Planilla p = findOrThrow(id);
        if (p.getEstado() != Planilla.EstadoPlanilla.APROBADO)
            throw new RuntimeException("La planilla debe estar aprobada antes de pagar");
        p.setEstado(Planilla.EstadoPlanilla.PAGADO);
        return PlanillaMapper.toDto(planillaRepository.save(p));
    }

    public PlanillaDto anular(Long id) {
        Planilla p = findOrThrow(id);
        if (p.getEstado() == Planilla.EstadoPlanilla.PAGADO)
            throw new RuntimeException("No se puede anular una planilla ya pagada");
        p.setEstado(Planilla.EstadoPlanilla.ANULADO);
        return PlanillaMapper.toDto(planillaRepository.save(p));
    }

    @Transactional
    public void delete(Long id) {
        Planilla p = findOrThrow(id);
        if (p.getEstado() == Planilla.EstadoPlanilla.PAGADO)
            throw new RuntimeException("No se puede eliminar una planilla ya pagada");
        planillaRepository.deleteById(id);
    }

    private void calcularPlanilla(PlanillaDto dto, Trabajador t) {
        BigDecimal sueldo = dto.getSueldoBase();
        int dias = dto.getDiasTrabajados() != null ? dto.getDiasTrabajados() : 30;

        BigDecimal sueldoProporcional = sueldo
                .multiply(BigDecimal.valueOf(dias))
                .divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_UP);

        BigDecimal montoHE = BigDecimal.ZERO;
        if (dto.getHorasExtra() != null && dto.getHorasExtra().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal valorHora = sueldo.divide(BigDecimal.valueOf(8 * 30), 4, RoundingMode.HALF_UP);
            montoHE = valorHora.multiply(dto.getHorasExtra())
                    .multiply(new BigDecimal("1.35")).setScale(2, RoundingMode.HALF_UP);
        }
        dto.setMontoHorasExtra(montoHE);

        BigDecimal asigFamiliar = dto.getAsignacionFamiliar() != null ?
                dto.getAsignacionFamiliar() : BigDecimal.ZERO;

        BigDecimal gratificacion = sueldo.divide(BigDecimal.valueOf(6), 2, RoundingMode.HALF_UP);
        dto.setGratificacion(gratificacion);

        BigDecimal cts = sueldo.add(gratificacion)
                .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
        dto.setCts(cts);

        BigDecimal totalBruto = sueldoProporcional.add(montoHE).add(asigFamiliar);
        dto.setTotalBruto(totalBruto.setScale(2, RoundingMode.HALF_UP));

        dto.setEssalud(sueldo.multiply(ESSALUD).setScale(2, RoundingMode.HALF_UP));

        String tipoPension = dto.getTipoPension() != null ?
                dto.getTipoPension() : t.getTipoPension().name();
        BigDecimal descPension = tipoPension.equals("ONP") ?
                totalBruto.multiply(ONP) : totalBruto.multiply(AFP);
        dto.setDescuentoPension(descPension.setScale(2, RoundingMode.HALF_UP));
        dto.setTipoPension(tipoPension);

        BigDecimal rentaAnual = totalBruto.multiply(BigDecimal.valueOf(14))
                .subtract(UIT_2024.multiply(new BigDecimal("7")));
        BigDecimal rentaQuinta = rentaAnual.compareTo(BigDecimal.ZERO) > 0 ?
                rentaAnual.multiply(new BigDecimal("0.08"))
                        .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;
        dto.setRentaQuinta(rentaQuinta);

        BigDecimal otrosDesc = dto.getOtrosDescuentos() != null ?
                dto.getOtrosDescuentos() : BigDecimal.ZERO;
        BigDecimal totalDesc = descPension.add(rentaQuinta).add(otrosDesc)
                .setScale(2, RoundingMode.HALF_UP);
        dto.setTotalDescuentos(totalDesc);
        dto.setTotalNeto(totalBruto.subtract(totalDesc).setScale(2, RoundingMode.HALF_UP));
    }

    private Planilla findOrThrow(Long id) {
        return planillaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Planilla no encontrada"));
    }
}